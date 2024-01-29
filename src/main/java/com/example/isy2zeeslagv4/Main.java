package com.example.isy2zeeslagv4;

import com.example.isy2zeeslagv4.model.ViewManager;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stageStart) throws IOException {
        ViewManager.initialScene(stageStart);
    }

    public static void main(String[] args) {
        launch();
    }


}