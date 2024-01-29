package com.example.isy2zeeslagv4.model.game.commands.board;

public interface Board {
    void initialize();
    Object[][] getBoard();
    void printBoard();
    int getSize();
}
