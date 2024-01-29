package com.example.isy2zeeslagv4.controller;

import com.example.isy2zeeslagv4.loop.GameLoop;
import com.example.isy2zeeslagv4.model.game.games.BattleshipGame;
import com.example.isy2zeeslagv4.model.game.games.Game;
import com.example.isy2zeeslagv4.model.player.PlayerFactory;
import com.example.isy2zeeslagv4.model.player.battleship.BattleshipPlayerFactory;
import com.example.isy2zeeslagv4.other.ExcelWriteRequest;
import com.example.isy2zeeslagv4.other.ExcelWriterThread;
import com.example.isy2zeeslagv4.other.PlayerSetupType;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

public class MainMenuController {
    @FXML
    private Label playerNameLabel;

    @FXML
    public void initialize()
    {
        System.out.println("Label ding 2");
//        playerNameLabel.setText(Config.getInstance().getSetting(Setting.NAME));
    }

    @FXML
    public void playBattleShipsLokaal()
    {
        int numberOfGames = 2500;
        ExecutorService executorService = Executors.newFixedThreadPool(25);
        Thread excelWriterThread = new Thread(new ExcelWriterThread(BattleshipGame.getWriteQueue()));
        excelWriterThread.start();

        for (int i = 0; i < numberOfGames; i++) {
            executorService.submit(() -> {
                BattleshipGame battleshipGame = new BattleshipGame(false, PlayerSetupType.AI_VS_AI);
                System.out.println("Game setup and started");
                runGame(battleshipGame);
            });
        }

        executorService.shutdown();
        playerNameLabel.setText("Data Collection Finished");
    }

    private void runGame(Game game)
    {
        GameLoop gameLoop = new GameLoop(game);
        gameLoop.run();
    };
}
