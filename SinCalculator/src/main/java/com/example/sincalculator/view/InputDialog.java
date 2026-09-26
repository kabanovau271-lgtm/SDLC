package com.example.sincalculator.view;

import com.example.sincalculator.model.SinData;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.text.AbstractDocument;
import java.awt.*;

public class InputDialog extends JDialog {

    private static final int FIELD_WIDTH = 360;
    private static final int FIELD_HEIGHT = 48;

    private final JTextField nameField = new JTextField();
    private final JTextField birthDateField = new JTextField();
    private final JTextField smokingField = new JTextField();
    private final JTextField delaysField = new JTextField();
    private final JTextField socialField = new JTextField();
    private final JTextField promisesField = new JTextField();
    private final JTextField swearingField = new JTextField();

    private SinData result;

    public InputDialog(JFrame owner, SinData previous) {
        super(owner, "Введите данные", true);

        Theme.apply();
        setSize(820, 820);
        setMinimumSize(new Dimension(800, 780));
        setLocationRelativeTo(owner);
        setResizable(false);

        JPanel root = new JPanel(new BorderLayout(0, 22));
        root.setBackground(Theme.BG);
        root.setBorder(new EmptyBorder(28, 34, 28, 34));

        root.add(createHeader(), BorderLayout.NORTH);
        root.add(createForm(previous), BorderLayout.CENTER);
        root.add(createButtons(), BorderLayout.SOUTH);

        setContentPane(root);
    }

    private JPanel createHeader() {
        JPanel header = new JPanel(new BorderLayout(25, 0));
        header.setOpaque(false);
        header.setBorder(new EmptyBorder(0, 4, 4, 4));

        JPanel texts = new JPanel();
        texts.setOpaque(false);
        texts.setLayout(new BoxLayout(texts, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("ВВЕДИ СВОИ ДАННЫЕ");
        title.setForeground(Theme.PINK);
        title.setFont(Theme.TITLE_FONT.deriveFont(Font.BOLD, 32f));

        JLabel hint = new JLabel(
                "Отвечай честно. Котёнок всё равно узнает..."
        );
        hint.setForeground(Theme.MUTED);
        hint.setFont(Theme.BODY_FONT.deriveFont(18f));

        texts.add(title);
        texts.add(Box.createVerticalStrut(8));
        texts.add(hint);

        header.add(texts, BorderLayout.CENTER);

        // Картинка котёнка
        header.add(
                new CatImageLabel(180, 110),
                BorderLayout.EAST
        );

        return header;
    }

    /**
     * Карточка с полями.
     */
    private JPanel createForm(SinData previous) {

        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Theme.PANEL);

        /*
         * Внешняя рамка + внутренний отступ.
         * Благодаря этому карточка визуально отделяется
         * от фона окна.
         */
        card.setBorder(new CompoundBorder(
                new LineBorder(Theme.BORDER, 2, true),
                new EmptyBorder(18, 22, 18, 22)
        ));

        JPanel fieldsPanel = new JPanel(new GridBagLayout());
        fieldsPanel.setOpaque(false);

        fillPreviousData(previous);
        styleFields();

        addRow(
                fieldsPanel,
                0,
                "name.png",
                "Имя:",
                nameField
        );

        addRow(
                fieldsPanel,
                1,
                "date.png",
                "Дата рождения:",
                birthDateField
        );

        addRow(
                fieldsPanel,
                2,
                "smoking.png",
                "Лет курения:",
                smokingField
        );

        addRow(
                fieldsPanel,
                3,
                "delays.png",
                "Опозданий в неделю:",
                delaysField
        );

        addRow(
                fieldsPanel,
                4,
                "social.png",
                "Соцсети в день (часы):",
                socialField
        );

        addRow(
                fieldsPanel,
                5,
                "promises.png",
                "Нарушений обещаний в неделю:",
                promisesField
        );

        addRow(
                fieldsPanel,
                6,
                "swearing.png",
                "Ругательств в неделю:",
                swearingField
        );

        card.add(fieldsPanel, BorderLayout.CENTER);

        return card;
    }

    /**
     * Заполняем поля последними введёнными данными.
     */
    private void fillPreviousData(SinData previous) {

        if (previous == null) {
            nameField.setText("");
            birthDateField.setText("");
            smokingField.setText("0");
            delaysField.setText("0");
            socialField.setText("0.0");
            promisesField.setText("0");
            swearingField.setText("0");
            return;
        }

        nameField.setText(previous.getName());
        birthDateField.setText(previous.getBirthDate());

        smokingField.setText(
                String.valueOf(previous.getSmokingYears())
        );

        delaysField.setText(
                String.valueOf(previous.getDelaysPerWeek())
        );

        socialField.setText(
                String.valueOf(previous.getSocialHoursPerDay())
        );

        promisesField.setText(
                String.valueOf(previous.getBrokenPromisesPerWeek())
        );

        swearingField.setText(
                String.valueOf(previous.getSwearingPerWeek())
        );
    }

    /**
     * Настройка внешнего вида всех полей.
     */
    private void styleFields() {

        ((AbstractDocument) birthDateField.getDocument())
                .setDocumentFilter(new DateDocumentFilter());

        Theme.styleField(nameField);
        Theme.styleField(birthDateField);
        Theme.styleField(smokingField);
        Theme.styleField(delaysField);
        Theme.styleField(socialField);
        Theme.styleField(promisesField);
        Theme.styleField(swearingField);

        setupFieldSize(nameField);
        setupFieldSize(birthDateField);
        setupFieldSize(smokingField);
        setupFieldSize(delaysField);
        setupFieldSize(socialField);
        setupFieldSize(promisesField);
        setupFieldSize(swearingField);
    }

    /**
     * Фиксируем нормальный размер полей,
     * чтобы GridBagLayout не растягивал их на весь экран.
     */
    private void setupFieldSize(JTextField field) {

        Dimension size = new Dimension(
                FIELD_WIDTH,
                FIELD_HEIGHT
        );

        field.setPreferredSize(size);
        field.setMinimumSize(size);
        field.setMaximumSize(size);

        field.setFont(
                Theme.BODY_FONT.deriveFont(18f)
        );

        field.setBorder(new CompoundBorder(
                new LineBorder(Theme.BORDER, 2, true),
                new EmptyBorder(0, 14, 0, 14)
        ));
    }

    /**
     * Одна строка формы.
     */
    private void addRow(
            JPanel panel,
            int row,
            String icon,
            String text,
            JTextField field
    ) {

        /*
         * Иконка
         */
        GridBagConstraints iconConstraints =
                new GridBagConstraints();

        iconConstraints.gridx = 0;
        iconConstraints.gridy = row;

        iconConstraints.weightx = 0;
        iconConstraints.fill = GridBagConstraints.NONE;
        iconConstraints.anchor = GridBagConstraints.CENTER;

        iconConstraints.insets =
                new Insets(8, 2, 8, 14);

        panel.add(
                new IconLabel(icon, 34),
                iconConstraints
        );

        /*
         * Название поля
         */
        GridBagConstraints labelConstraints =
                new GridBagConstraints();

        labelConstraints.gridx = 1;
        labelConstraints.gridy = row;

        labelConstraints.weightx = 1;
        labelConstraints.fill = GridBagConstraints.HORIZONTAL;

        labelConstraints.insets =
                new Insets(8, 0, 8, 24);

        JLabel label = new JLabel(text);

        label.setForeground(Theme.PINK_SOFT);
        label.setFont(
                Theme.BODY_FONT.deriveFont(
                        Font.BOLD,
                        17f
                )
        );

        panel.add(label, labelConstraints);

        /*
         * Поле ввода
         */
        GridBagConstraints fieldConstraints =
                new GridBagConstraints();

        fieldConstraints.gridx = 2;
        fieldConstraints.gridy = row;

        fieldConstraints.weightx = 0;
        fieldConstraints.fill = GridBagConstraints.NONE;

        fieldConstraints.anchor = GridBagConstraints.CENTER;

        fieldConstraints.insets =
                new Insets(8, 0, 8, 2);

        panel.add(field, fieldConstraints);
    }

    /**
     * Кнопки.
     */
    private JPanel createButtons() {

        JPanel buttons = new JPanel(
                new FlowLayout(
                        FlowLayout.CENTER,
                        20,
                        0
                )
        );

        buttons.setOpaque(false);

        JButton calculateButton =
                new JButton("РАССЧИТАТЬ");

        JButton cancelButton =
                new JButton("ОТМЕНА");

        Theme.styleButton(calculateButton);
        styleCancel(cancelButton);

        calculateButton.setPreferredSize(
                new Dimension(230, 55)
        );

        cancelButton.setPreferredSize(
                new Dimension(180, 55)
        );

        calculateButton.addActionListener(
                event -> createResult()
        );

        cancelButton.addActionListener(
                event -> dispose()
        );

        buttons.add(calculateButton);
        buttons.add(cancelButton);

        return buttons;
    }

    /**
     * Кнопка отмены.
     */
    private void styleCancel(JButton button) {

        button.setBackground(Theme.PANEL_2);
        button.setForeground(Theme.TEXT);

        button.setFont(
                Theme.BUTTON_FONT
        );

        button.setFocusPainted(false);
        button.setOpaque(true);

        button.setBorder(
                new CompoundBorder(
                        new LineBorder(
                                Theme.BORDER,
                                2,
                                true
                        ),
                        new EmptyBorder(
                                10,
                                24,
                                10,
                                24
                        )
                )
        );
    }

    /**
     * Получение введённых данных.
     */
    private void createResult() {

        try {

            String name =
                    nameField.getText().trim();

            String date =
                    birthDateField.getText().trim();

            if (name.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Введите имя.",
                        "Ошибка ввода",
                        JOptionPane.WARNING_MESSAGE
                );

                nameField.requestFocus();
                return;
            }

            if (date.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Введите дату рождения.",
                        "Ошибка ввода",
                        JOptionPane.WARNING_MESSAGE
                );

                birthDateField.requestFocus();
                return;
            }

            int smoking =
                    Integer.parseInt(
                            smokingField
                                    .getText()
                                    .trim()
                    );

            int delays =
                    Integer.parseInt(
                            delaysField
                                    .getText()
                                    .trim()
                    );

            double social =
                    Double.parseDouble(
                            socialField
                                    .getText()
                                    .trim()
                                    .replace(',', '.')
                    );

            int promises =
                    Integer.parseInt(
                            promisesField
                                    .getText()
                                    .trim()
                    );

            int swearing =
                    Integer.parseInt(
                            swearingField
                                    .getText()
                                    .trim()
                    );

            result = new SinData(
                    name,
                    date,
                    smoking,
                    delays,
                    social,
                    promises,
                    swearing
            );

            dispose();

        } catch (NumberFormatException exception) {

            JOptionPane.showMessageDialog(
                    this,
                    "Проверьте числовые значения.",
                    "Ошибка ввода",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    public SinData getResult() {
        return result;
    }
}