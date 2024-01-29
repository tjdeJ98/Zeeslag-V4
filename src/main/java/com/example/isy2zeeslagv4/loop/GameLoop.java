package com.example.isy2zeeslagv4.loop;

import com.example.isy2zeeslagv4.controller.GameController;
import com.example.isy2zeeslagv4.model.game.games.Game;
import javafx.application.Platform;

public class GameLoop {
    private boolean running = false;
    private Game game;

    public GameLoop(Game game)
    {
        this.game = game;
    }

    public void run()
    {
        this.running = true;

        Thread gameThread = new Thread(() -> {
            while (running) {
                game.initialize();
                game.setupPhase();
                game.update(); // just game logic goes here, what needs to be done
                if (game.isGameOver()) {
                    game.endGame();
                    stopGame();
                    System.out.println("-------------------------End Game------------------------");
                } else {
                    game.switchPlayer();
                    System.out.println("-------------------------Next Turn------------------------");
                    Platform.runLater(() -> {
                        // Update UI and stuff like controller.render()
                    });
                }


                try {
                    Thread.sleep(150);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        gameThread.start();
    };

    public void stopGame() {
        running = false;
    }
}
