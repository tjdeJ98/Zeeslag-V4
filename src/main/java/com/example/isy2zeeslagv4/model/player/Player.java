package com.example.isy2zeeslagv4.model.player;

import com.example.isy2zeeslagv4.model.game.commands.board.Board;
import com.example.isy2zeeslagv4.model.game.games.Game;

public interface Player<T extends Game>{
    void setupPhase();
    void initialize();
    void makeMove();
    void moveOnBoard(int coordinate);
    Board getBoard();
    boolean isGameOver();
    int[] getMatchData();
    void endGame();
    String getName();
}
