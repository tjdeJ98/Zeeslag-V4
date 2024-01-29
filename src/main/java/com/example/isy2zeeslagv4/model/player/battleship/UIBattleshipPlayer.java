package com.example.isy2zeeslagv4.model.player.battleship;

import com.example.isy2zeeslagv4.model.game.commands.board.Board;
import com.example.isy2zeeslagv4.model.game.commands.board.battleship.Battleshipboard;
import com.example.isy2zeeslagv4.model.game.games.BattleshipGame;
import com.example.isy2zeeslagv4.model.player.Player;

public class UIBattleshipPlayer implements Player<BattleshipGame> {
    private String name;
    private BattleshipGame game;
    private Battleshipboard board;

    public UIBattleshipPlayer(String name, BattleshipGame game)
    {
        this.name = name;
        this.game = game;
        this.board = new Battleshipboard(8);
    }

    @Override
    public void setupPhase() {

    }

    @Override
    public void initialize() {

    }

    @Override
    public void makeMove() {

    }

    @Override
    public void moveOnBoard(int coordinate) {

    }

    @Override
    public Board getBoard() {
        return null;
    }

    @Override
    public boolean isGameOver() {
        return false;
    }

    @Override
    public int[] getMatchData() {
        return board.countHitsAndMisses();
    }

    @Override
    public void endGame() {

    }

    @Override
    public String getName() {
        return null;
    }
}
