package com.example.sincalculator.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.prefs.Preferences;

public class SinModel {
    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private static final String KEY_NAME = "name";
    private static final String KEY_BIRTH_DATE = "birthDate";
    private static final String KEY_SMOKING = "smokingYears";
    private static final String KEY_DELAYS = "delaysPerWeek";
    private static final String KEY_SOCIAL = "socialHoursPerDay";
    private static final String KEY_PROMISES = "brokenPromisesPerWeek";
    private static final String KEY_SWEARING = "swearingPerWeek";

    private final Preferences preferences = Preferences.userNodeForPackage(SinModel.class);
    private SinData data;
    private int result;
    private final List<ModelListener> listeners = new ArrayList<>();

    public interface ModelListener {
        void modelChanged();
    }

    public SinModel() {
        data = loadSavedData();
        if (!data.getName().isBlank() || !data.getBirthDate().isBlank()) {
            try {
                result = calculateValue(data);
            } catch (IllegalArgumentException ignored) {
                data = new SinData();
                result = 0;
            }
        }
    }

    public void addListener(ModelListener listener) {
        listeners.add(listener);
    }

    public SinData getData() { return data; }
    public int getResult() { return result; }

    public void calculate(SinData newData) {
        validate(newData);
        data = newData;
        result = calculateValue(data);
        saveData(data);
        notifyListeners();
    }

    private int calculateValue(SinData value) {
        return value.getSmokingYears() * 10
                + value.getDelaysPerWeek() * 4
                + (int) Math.round(value.getSocialHoursPerDay() * 2)
                + value.getBrokenPromisesPerWeek() * 3
                + value.getSwearingPerWeek() * 5;
    }

    private void validate(SinData value) {
        if (value.getName() == null || value.getName().isBlank()) {
            throw new IllegalArgumentException("Введите имя.");
        }
        if (value.getBirthDate() == null || value.getBirthDate().isBlank()) {
            throw new IllegalArgumentException("Введите дату рождения.");
        }

        try {
            LocalDate date = LocalDate.parse(value.getBirthDate(), FORMATTER);
            if (date.isAfter(LocalDate.now())) {
                throw new IllegalArgumentException("Дата рождения не может быть в будущем.");
            }
        } catch (DateTimeParseException exception) {
            throw new IllegalArgumentException("Дата должна быть в формате ДД.ММ.ГГГГ.");
        }

        if (value.getSmokingYears() < 0
                || value.getDelaysPerWeek() < 0
                || value.getSocialHoursPerDay() < 0
                || value.getBrokenPromisesPerWeek() < 0
                || value.getSwearingPerWeek() < 0) {
            throw new IllegalArgumentException("Числовые значения не могут быть отрицательными.");
        }
        if (value.getSocialHoursPerDay() > 24) {
            throw new IllegalArgumentException("Количество часов в социальных сетях не может превышать 24.");
        }
    }

    private void saveData(SinData value) {
        preferences.put(KEY_NAME, value.getName());
        preferences.put(KEY_BIRTH_DATE, value.getBirthDate());
        preferences.putInt(KEY_SMOKING, value.getSmokingYears());
        preferences.putInt(KEY_DELAYS, value.getDelaysPerWeek());
        preferences.putDouble(KEY_SOCIAL, value.getSocialHoursPerDay());
        preferences.putInt(KEY_PROMISES, value.getBrokenPromisesPerWeek());
        preferences.putInt(KEY_SWEARING, value.getSwearingPerWeek());
    }

    private SinData loadSavedData() {
        return new SinData(
                preferences.get(KEY_NAME, ""),
                preferences.get(KEY_BIRTH_DATE, ""),
                preferences.getInt(KEY_SMOKING, 0),
                preferences.getInt(KEY_DELAYS, 0),
                preferences.getDouble(KEY_SOCIAL, 0.0),
                preferences.getInt(KEY_PROMISES, 0),
                preferences.getInt(KEY_SWEARING, 0));
    }

    private void notifyListeners() {
        for (ModelListener listener : listeners) {
            listener.modelChanged();
        }
    }
}
