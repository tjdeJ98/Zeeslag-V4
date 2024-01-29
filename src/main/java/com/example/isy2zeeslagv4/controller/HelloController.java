package com.example.isy2zeeslagv4.controller;

import com.example.isy2zeeslagv4.config.Config;
import com.example.isy2zeeslagv4.config.Setting;
import com.example.isy2zeeslagv4.model.ViewManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class HelloController {
    @FXML
    private Label label;
    @FXML private TextField helloViewNameBox;
    @FXML private CheckBox rememberMeCheckBox;

    @FXML
    public void initialize()
    {
        label.setText("");
        helloViewNameBox.setText(Config.getInstance().getSetting(Setting.NAME));
        helloViewNameBox.setText("Welkom");
        boolean textBoxEmpty = helloViewNameBox.getText().isEmpty();
        rememberMeCheckBox.setSelected(!textBoxEmpty);
    }

    @FXML
    private void handleStartButtonAction(ActionEvent event) {
        String name = helloViewNameBox.getText();
        if (name.isEmpty()) {
            label.setText("A username is required");
            return;
        }

        if (rememberMeCheckBox.isSelected()) {
            System.out.println("config ding");
            Config.getInstance().setSetting(Setting.NAME, name);
        }

        ViewManager.setScene("mainMenu");
    }
}
