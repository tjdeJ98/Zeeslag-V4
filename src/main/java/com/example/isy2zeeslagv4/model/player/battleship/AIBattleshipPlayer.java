package com.example.isy2zeeslagv4.model.player.battleship;

import com.example.isy2zeeslagv4.model.game.commands.battleship.CommandMakeMoveBattleships;
import com.example.isy2zeeslagv4.model.game.commands.battleship.CommandPlaceShipBattleships;
import com.example.isy2zeeslagv4.model.game.commands.board.Board;
import com.example.isy2zeeslagv4.model.game.commands.board.battleship.Battleshipboard;
import com.example.isy2zeeslagv4.model.game.games.BattleshipGame;
import com.example.isy2zeeslagv4.model.player.Player;
import com.example.isy2zeeslagv4.model.player.battleship.strategy.AIStrategy;
import com.example.isy2zeeslagv4.other.ExcelExporter;
import com.example.isy2zeeslagv4.other.Ship;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class AIBattleshipPlayer implements Player<BattleshipGame> {
    private String name;
    private BattleshipGame game;
    private Battleshipboard board;
    private List<Ship> ships;
    private boolean setupTodo;
    private Random random;
    private int boardSize;
    private AIStrategy strategy;

    public AIBattleshipPlayer(String name, BattleshipGame game, AIStrategy strategy)
    {
        this.name = name;
        this.game = game;
        this.strategy = strategy;
        this.ships = new ArrayList<>();
        this.boardSize = 8;
        this.board = new Battleshipboard(boardSize);
        this.setupTodo = true;
        this.random = new Random();
    }

    @Override
    public void initialize() {
        if (ships.isEmpty()) {
            createdListOfShips();
            strategy.initialize();
        }
    }

    @Override
    public void setupPhase() {
        if (setupTodo)
            placeShipsOnBoard();
    }

    @Override
    public void makeMove() {
        Player<BattleshipGame> otherPlayer = game.getOtherPlayer();
        Battleshipboard otherPlayerBoard = (Battleshipboard) otherPlayer.getBoard();
        otherPlayerBoard.printOnlyHitsBoard();
        int coordinate = strategy.takeShot();
        CommandMakeMoveBattleships makeMoveCommand = new CommandMakeMoveBattleships(coordinate, game.getOtherPlayer());
        makeMoveCommand.execute();
    }

    @Override
    public void moveOnBoard(int coordinate) {
        board.shotOnBoard(coordinate);
        Ship ship = getShipByCoordinate(coordinate);
        if (ship != null)
            ship.takeHit();
    }

    @Override
    public Battleshipboard getBoard() {
        return board;
    }

    @Override
    public boolean isGameOver() {
        return checkAllShipsSunk();
    }

    @Override
    public int[] getMatchData() {
        return board.countHitsAndMisses();
    }

    @Override
    public void endGame() {
        System.out.println(name + " has won!");
    }

    @Override
    public String getName() {
        return name;
    }

    private void placeShipsOnBoard()
    {
        for (Ship ship : ships) {
            printShips();
            boolean placed = false;
            while (!placed) {
                int orientation = random.nextInt(2); // 0 for horizontal, 1 for vertical
                int start = random.nextInt(boardSize * boardSize);
                int end = calculateEndCoordinate(start, ship.getSize(), orientation);

                if (end != -1 && board.setupValidation(start, end, ship)) {
                    CommandPlaceShipBattleships placeShipBattleship = new CommandPlaceShipBattleships(start, end, board, ship);
                    placeShipBattleship.execute();
                    placed = true;
                    board.printBoard();
                }
            }
        }
        this.setupTodo = false;
    }

    public int[] getHitMisData()
    {
        return board.countHitsAndMisses();
    }

    private int calculateEndCoordinate(int start, int size, int orientation) {
        int row = start / boardSize;
        int col = start % boardSize;

        if (orientation == 0 && col + size <= boardSize) { // Horizontal
            return start + size - 1;
        } else if (orientation == 1 && row + size <= boardSize) { // Vertical
            return start + (size - 1) * boardSize;
        } else {
            return -1; // Invalid end coordinate
        }
    }

    private void printShips()
    {
        for (Ship ship : ships){
            System.out.println(ship.getId() + " - " + ship.getSize());
        }
    }

    private void createdListOfShips()
    {
        int shipId = 0;
        for (int shipSize : game.getShipSizes()) {
            ships.add(new Ship(shipId, shipSize));
            shipId++;
        }
    }

    private boolean checkAllShipsSunk()
    {
        if (ships.isEmpty())
            return false;

        for (Ship ship : ships) {
            if (ship.getHealth() != 0)
                return false;
        }

        return true;
    }

    private Ship getShipByCoordinate(int coordinate)
    {
        for (Ship ship : ships) {
            if (ship.getCoordinates().contains(coordinate))
                return ship;
        }
        return null;
    }
}
