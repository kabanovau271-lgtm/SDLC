package com.example.sincalculator.controller;

import com.example.sincalculator.model.SinData;
import com.example.sincalculator.model.SinModel;
import com.example.sincalculator.view.InputDialog;
import com.example.sincalculator.view.MainFrame;

public class SinController {
    private final SinModel model;
    private final MainFrame view;
    private final AudioPlayer audioPlayer = new AudioPlayer("/sounds/ispoved.wav");

    public SinController(SinModel model, MainFrame view) {
        this.model = model;
        this.view = view;
        model.addListener(this::updateView);
        view.setCalculateRequest(this::openInputDialog);
        updateView();
    }

    private void openInputDialog() {
        InputDialog dialog = new InputDialog(view, model.getData());
        dialog.setVisible(true);
        SinData data = dialog.getResult();
        if (data != null) {
            try {
                model.calculate(data);
                if (model.getResult() > 1000) {
                    audioPlayer.play();
                }
            } catch (IllegalArgumentException exception) {
                view.showError(exception.getMessage());
            }
        }
    }

    private void updateView() {
        if (!model.getData().getName().isBlank()) {
            view.updateResult(model.getData(), model.getResult());
        }
    }
}
