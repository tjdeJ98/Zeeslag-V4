package com.example.isy2zeeslagv4.model.game.games;

public interface Game {
    void setupPhase();
    void initialize();
    void update();
    boolean isGameOver();
    void endGame();
    void switchPlayer();
}
