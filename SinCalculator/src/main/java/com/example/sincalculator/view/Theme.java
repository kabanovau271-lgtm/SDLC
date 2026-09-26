package com.example.sincalculator.view;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

public final class Theme {
    public static final Color BG = new Color(7, 5, 14);
    public static final Color PANEL = new Color(17, 11, 27);
    public static final Color PANEL_2 = new Color(25, 15, 38);
    public static final Color FIELD = new Color(9, 7, 17);
    public static final Color PINK = new Color(255, 42, 112);
    public static final Color PINK_SOFT = new Color(255, 142, 190);
    public static final Color PINK_DARK = new Color(181, 12, 75);
    public static final Color TEXT = new Color(250, 242, 249);
    public static final Color MUTED = new Color(185, 160, 190);
    public static final Color BORDER = new Color(125, 25, 92);

    public static final Font TITLE_FONT = new Font("Segoe UI", Font.BOLD, 34);
    public static final Font SECTION_FONT = new Font("Segoe UI", Font.BOLD, 19);
    public static final Font BODY_FONT = new Font("Segoe UI", Font.PLAIN, 16);
    public static final Font SMALL_FONT = new Font("Segoe UI", Font.PLAIN, 14);
    public static final Font BUTTON_FONT = new Font("Segoe UI", Font.BOLD, 17);
    public static final Font NUMBER_FONT = new Font("Segoe UI", Font.BOLD, 88);

    private Theme() { }

    public static void apply() {
        UIManager.put("Panel.background", BG);
        UIManager.put("OptionPane.background", PANEL);
        UIManager.put("OptionPane.messageForeground", TEXT);
        UIManager.put("Label.foreground", TEXT);
        UIManager.put("TextField.background", FIELD);
        UIManager.put("TextField.foreground", TEXT);
        UIManager.put("TextField.caretForeground", PINK);
        UIManager.put("TextField.selectionBackground", PINK_DARK);
        UIManager.put("TextField.selectionForeground", TEXT);
        UIManager.put("ScrollPane.background", BG);
        UIManager.put("Viewport.background", BG);
    }

    public static Border neonBorder() {
        return new CompoundBorder(
                new LineBorder(BORDER, 1, true),
                new EmptyBorder(16, 18, 16, 18));
    }

    public static void styleButton(JButton button) {
        button.setBackground(PINK_DARK);
        button.setForeground(Color.WHITE);
        button.setFont(BUTTON_FONT);
        button.setFocusPainted(false);
        button.setBorder(new CompoundBorder(
                new LineBorder(PINK, 1, true),
                new EmptyBorder(12, 28, 12, 28)));
        button.setOpaque(true);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    public static void styleField(JTextField field) {
        field.setFont(BODY_FONT);
        field.setBackground(FIELD);
        field.setForeground(TEXT);
        field.setCaretColor(PINK);
        field.setBorder(new CompoundBorder(
                new LineBorder(BORDER, 2, true),
                new EmptyBorder(10, 13, 10, 13)));
        field.setPreferredSize(new Dimension(380, 48));
    }
}
