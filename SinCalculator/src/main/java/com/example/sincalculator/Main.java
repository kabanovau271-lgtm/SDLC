package com.example.sincalculator;

import com.example.sincalculator.controller.SinController;
import com.example.sincalculator.model.SinModel;
import com.example.sincalculator.view.MainFrame;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SinModel model = new SinModel();
            MainFrame view = new MainFrame();
            new SinController(model, view);
            view.setVisible(true);
        });
    }
}
