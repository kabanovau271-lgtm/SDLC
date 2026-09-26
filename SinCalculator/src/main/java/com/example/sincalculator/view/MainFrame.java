package com.example.sincalculator.view;

import com.example.sincalculator.model.SinData;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class MainFrame extends JFrame {
    private final JLabel[] dataLabels = new JLabel[7];
    private final JLabel statusLabel = new JLabel(
            "Сначала грехи, потом покаяние...", SwingConstants.CENTER);
    private final JLabel resultNumber = new JLabel("0", SwingConstants.CENTER);
    private final JPanel angelPanel = createAngelPanel();
    private Runnable calculateRequest;

    public MainFrame() {
        Theme.apply();
        setTitle("Калькулятор грехов");
        setSize(1080, 700);
        setMinimumSize(new Dimension(960, 640));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel root = new JPanel(new BorderLayout(18, 16));
        root.setBackground(Theme.BG);
        root.setBorder(new EmptyBorder(22, 28, 24, 28));
        root.add(createHeader(), BorderLayout.NORTH);
        root.add(createCenter(), BorderLayout.CENTER);
        root.add(createFooter(), BorderLayout.SOUTH);
        setContentPane(root);
    }

    private JPanel createHeader() {
        JPanel header = new JPanel(new BorderLayout(20, 0));
        header.setOpaque(false);

        JPanel texts = new JPanel();
        texts.setOpaque(false);
        texts.setLayout(new BoxLayout(texts, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("КАЛЬКУЛЯТОР ГРЕХОВ");
        title.setForeground(Theme.PINK);
        title.setFont(Theme.TITLE_FONT);
        JLabel subtitle = new JLabel("Сколько грехов ты успел(а) накопить?");
        subtitle.setForeground(Theme.MUTED);
        subtitle.setFont(Theme.BODY_FONT);

        texts.add(title);
        texts.add(Box.createVerticalStrut(5));
        texts.add(subtitle);
        header.add(texts, BorderLayout.CENTER);
        header.add(new CatImageLabel(220, 135), BorderLayout.EAST);
        return header;
    }

    private JPanel createCenter() {
        JPanel center = new JPanel(new GridLayout(1, 2, 20, 0));
        center.setOpaque(false);
        center.add(createDataCard());
        center.add(createResultCard());
        return center;
    }

    private JPanel createDataCard() {
        JPanel card = new JPanel(new BorderLayout(12, 12));
        card.setBackground(Theme.PANEL);
        card.setBorder(Theme.neonBorder());

        JLabel title = new JLabel("ПОСЛЕДНИЕ ВВЕДЁННЫЕ ДАННЫЕ");
        title.setForeground(Theme.PINK_SOFT);
        title.setFont(Theme.SECTION_FONT);
        card.add(title, BorderLayout.NORTH);

        JPanel list = new JPanel();
        list.setOpaque(false);
        list.setLayout(new BoxLayout(list, BoxLayout.Y_AXIS));

        String[] icons = {"name.png", "date.png", "smoking.png", "delays.png",
                "social.png", "promises.png", "swearing.png"};
        for (int i = 0; i < dataLabels.length; i++) {
            JPanel row = new JPanel(new BorderLayout(12, 0));
            row.setOpaque(false);
            row.setBorder(new EmptyBorder(5, 0, 5, 0));
            row.add(new IconLabel(icons[i], 30), BorderLayout.WEST);
            dataLabels[i] = new JLabel();
            dataLabels[i].setForeground(Theme.TEXT);
            dataLabels[i].setFont(Theme.BODY_FONT.deriveFont(16f));
            row.add(dataLabels[i], BorderLayout.CENTER);
            list.add(row);
        }
        card.add(list, BorderLayout.CENTER);
        return card;
    }

    private JPanel createResultCard() {
        JPanel card = new JPanel(new BorderLayout(8, 8));
        card.setBackground(Theme.PANEL);
        card.setBorder(Theme.neonBorder());

        JLabel title = new JLabel("ТВОЙ РЕЗУЛЬТАТ", SwingConstants.CENTER);
        title.setForeground(Theme.TEXT);
        title.setFont(Theme.SECTION_FONT);
        card.add(title, BorderLayout.NORTH);

        resultNumber.setForeground(Theme.PINK);
        resultNumber.setFont(Theme.NUMBER_FONT);
        card.add(resultNumber, BorderLayout.CENTER);

        angelPanel.setVisible(false);
        card.add(angelPanel, BorderLayout.SOUTH);
        return card;
    }

    private JPanel createAngelPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 8));
        panel.setBackground(Theme.PANEL_2);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Theme.BORDER, 1, true),
                BorderFactory.createEmptyBorder(6, 10, 6, 10)));
        panel.add(new IconLabel("angel.png", 34));
        JLabel text = new JLabel("Ты сегодня ангел! Ни одного греха!");
        text.setForeground(Theme.PINK_SOFT);
        text.setFont(Theme.BODY_FONT.deriveFont(Font.BOLD, 15f));
        panel.add(text);
        return panel;
    }

    private JPanel createFooter() {
        JPanel footer = new JPanel(new BorderLayout(10, 10));
        footer.setOpaque(false);
        statusLabel.setForeground(Theme.MUTED);
        statusLabel.setFont(Theme.BODY_FONT);
        footer.add(statusLabel, BorderLayout.NORTH);

        JButton calculateButton = new JButton("РАССЧИТАТЬ");
        Theme.styleButton(calculateButton);
        calculateButton.addActionListener(event -> fireCalculateRequest());
        footer.add(calculateButton, BorderLayout.CENTER);
        return footer;
    }

    public void setCalculateRequest(Runnable request) {
        calculateRequest = request;
    }

    private void fireCalculateRequest() {
        if (calculateRequest != null) {
            calculateRequest.run();
        }
    }

    public void updateResult(SinData data, int result) {
        dataLabels[0].setText("Имя: " + data.getName());
        dataLabels[1].setText("Дата рождения: " + data.getBirthDate());
        dataLabels[2].setText("Лет курения: " + data.getSmokingYears());
        dataLabels[3].setText("Опозданий в неделю: " + data.getDelaysPerWeek());
        dataLabels[4].setText("Соцсети в день: " + data.getSocialHoursPerDay() + " ч.");
        dataLabels[5].setText("Нарушений обещаний в неделю: "
                + data.getBrokenPromisesPerWeek());
        dataLabels[6].setText("Ругательств в неделю: " + data.getSwearingPerWeek());

        resultNumber.setText(String.valueOf(result));
        angelPanel.setVisible(result == 0);

        if (result > 1000) {
            statusLabel.setText("КРИТИЧЕСКИЙ УРОВЕНЬ ГРЕХОВ. Батюшка уже в пути...");
            statusLabel.setForeground(Theme.PINK);
        } else {
            statusLabel.setText("Сначала грехи, потом покаяние...");
            statusLabel.setForeground(Theme.MUTED);
        }
    }

    public void showError(String message) {
        JOptionPane.showMessageDialog(
                this, message, "Ошибка ввода", JOptionPane.ERROR_MESSAGE);
    }
}
