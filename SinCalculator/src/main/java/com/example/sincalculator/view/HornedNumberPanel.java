package com.example.sincalculator.view;

import javax.swing.*;
import java.awt.*;

public class HornedNumberPanel extends JPanel {
    private final JLabel numberLabel = new JLabel("0", SwingConstants.CENTER);

    public HornedNumberPanel() {
        setOpaque(false);
        numberLabel.setForeground(Theme.PINK);
        numberLabel.setFont(Theme.NUMBER_FONT);
        add(numberLabel);
    }

    public void setNumber(int number) {
        numberLabel.setText(String.valueOf(number));
    }
}
