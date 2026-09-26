package com.example.sincalculator.view;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class CatImageLabel extends JLabel {
    public CatImageLabel(int width, int height) {
        setPreferredSize(new Dimension(width, height));
        setHorizontalAlignment(SwingConstants.CENTER);
        setVerticalAlignment(SwingConstants.CENTER);
        setOpaque(false);

        try {
            ImageIcon original = new ImageIcon(
                    CatImageLabel.class.getResource("/cat.png"));
            Image scaled = original.getImage().getScaledInstance(
                    width, height, Image.SCALE_SMOOTH);
            setIcon(new ImageIcon(scaled));
        } catch (Exception exception) {
            setText("😈");
            setFont(Theme.TITLE_FONT);
            setForeground(Theme.PINK);
        }
    }
}
