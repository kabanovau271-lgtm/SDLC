package com.example.sincalculator.view;

import javax.swing.*;
import java.awt.*;

public class IconLabel extends JLabel {
    public IconLabel(String resourceName, int size) {
        setPreferredSize(new Dimension(size, size));
        setHorizontalAlignment(SwingConstants.CENTER);
        setVerticalAlignment(SwingConstants.CENTER);
        setOpaque(false);
        try {
            ImageIcon source = new ImageIcon(
                    IconLabel.class.getResource("/icons/" + resourceName));
            Image image = source.getImage().getScaledInstance(size, size, Image.SCALE_SMOOTH);
            setIcon(new ImageIcon(image));
        } catch (Exception exception) {
            setText("•");
            setForeground(Theme.PINK);
            setFont(Theme.SECTION_FONT);
        }
    }
}
