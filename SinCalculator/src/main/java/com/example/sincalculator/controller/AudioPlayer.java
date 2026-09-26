package com.example.sincalculator.controller;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/** Plays the prepared WAV without opening the audio device on every click. */
public class AudioPlayer {
    private final byte[] audioData;
    private final AudioFormat format;
    private final SourceDataLine line;
    private volatile boolean playing;

    public AudioPlayer(String resourcePath) {
        AudioData loaded = load(resourcePath);
        audioData = loaded.data;
        format = loaded.format;
        line = openLine(format);
    }

    private SourceDataLine openLine(AudioFormat audioFormat) {
        try {
            DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);
            SourceDataLine result = (SourceDataLine) AudioSystem.getLine(info);
            int bufferSize = Math.max(16384, audioFormat.getFrameSize() * 2048);
            result.open(audioFormat, bufferSize);
            return result;
        } catch (Exception exception) {
            throw new IllegalStateException(
                    "Не удалось подготовить звук: " + exception.getMessage(), exception);
        }
    }

    private AudioData load(String resourcePath) {
        try (InputStream input = getClass().getResourceAsStream(resourcePath)) {
            if (input == null) {
                throw new IOException("Аудиофайл не найден: " + resourcePath);
            }
            try (AudioInputStream source = AudioSystem.getAudioInputStream(input)) {
                AudioFormat base = source.getFormat();
                AudioFormat decoded = new AudioFormat(
                        AudioFormat.Encoding.PCM_SIGNED,
                        base.getSampleRate(), 16, base.getChannels(),
                        base.getChannels() * 2, base.getSampleRate(), false);
                try (AudioInputStream pcm = AudioSystem.getAudioInputStream(decoded, source)) {
                    return new AudioData(decoded, readAll(pcm));
                }
            }
        } catch (Exception exception) {
            throw new IllegalStateException(
                    "Не удалось загрузить звук: " + exception.getMessage(), exception);
        }
    }

    private byte[] readAll(AudioInputStream stream) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        byte[] buffer = new byte[16384];
        int read;
        while ((read = stream.read(buffer)) != -1) {
            output.write(buffer, 0, read);
        }
        return output.toByteArray();
    }

    public synchronized void play() {
        stop();
        Thread playbackThread = new Thread(() -> {
            try {
                synchronized (AudioPlayer.this) {
                    line.flush();
                    line.start();
                    playing = true;
                }

                int offset = 0;
                while (offset < audioData.length && playing) {
                    int length = Math.min(4096, audioData.length - offset);
                    int written = line.write(audioData, offset, length);
                    offset += written;
                }

                if (playing) {
                    line.drain();
                }
            } catch (IllegalStateException exception) {
                System.err.println("Не удалось воспроизвести звук: " + exception.getMessage());
            } finally {
                synchronized (AudioPlayer.this) {
                    line.stop();
                    line.flush();
                    playing = false;
                }
            }
        }, "sin-calculator-audio");
        playbackThread.setDaemon(true);
        playbackThread.start();
    }

    public synchronized void stop() {
        playing = false;
        if (line.isRunning()) {
            line.stop();
        }
        line.flush();
    }

    public void close() {
        synchronized (this) {
            playing = false;
            line.stop();
            line.flush();
            line.close();
        }
    }

    private record AudioData(AudioFormat format, byte[] data) { }
}
