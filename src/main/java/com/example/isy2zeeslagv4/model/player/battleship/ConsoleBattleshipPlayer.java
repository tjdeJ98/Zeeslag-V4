package com.example.isy2zeeslagv4.model.player.battleship;

import com.example.isy2zeeslagv4.model.game.commands.battleship.CommandMakeMoveBattleships;
import com.example.isy2zeeslagv4.model.game.commands.battleship.CommandPlaceShipBattleships;
import com.example.isy2zeeslagv4.model.game.commands.board.battleship.Battleshipboard;
import com.example.isy2zeeslagv4.model.game.games.BattleshipGame;
import com.example.isy2zeeslagv4.model.player.Player;
import com.example.isy2zeeslagv4.other.Ship;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ConsoleBattleshipPlayer implements Player<BattleshipGame> {
    private String name;
    private BattleshipGame game;
    private Battleshipboard board;
    private List<Ship> ships;
    private boolean setupTodo;

    public ConsoleBattleshipPlayer(String name, BattleshipGame game)
    {
        this.name = name;
        this.game = game;
        this.ships = new ArrayList<>();
        this.board = new Battleshipboard(8);
        this.setupTodo = true;
    }

    @Override
    public void initialize() {
        if (ships.isEmpty()) {
            createdListOfShips();
        }
    }

    @Override
    public void setupPhase() {
        int count = 0;
        while (setupTodo) {
            printShips();
            int shipid = Integer.parseInt(getInput("Give shipid"));
            Ship ship = selectShip(shipid);
            List<Integer> coordinates = new ArrayList<>();
            boolean coordinatesInvalid = true;

            do {
                coordinates.add(board.convertCoordinateToIndex(getInput("Enter the first coordinate (e.g., A1):")));
                coordinates.add(board.convertCoordinateToIndex(getInput("Enter the second coordinate (e.g., A3):")));

                if (board.setupValidation(coordinates.get(0), coordinates.get(1), ship)) {
                    coordinatesInvalid = false;
                    CommandPlaceShipBattleships placeShipCommand = new CommandPlaceShipBattleships(
                            coordinates.get(0),
                            coordinates.get(1),
                            board,
                            ship);
                    placeShipCommand.execute();
                    count++;
                } else {
                    System.out.println("Invalid coordinates or move. Please enter different coordinates.");
                }
            } while (coordinatesInvalid);

            if (count == ships.size()) {
                this.setupTodo = false;
            }
            board.printBoard();
        }
    }

    @Override
    public void makeMove() {
        Player<BattleshipGame> otherPlayer = game.getOtherPlayer();
        Battleshipboard otherPlayerBoard = (Battleshipboard) otherPlayer.getBoard();
        otherPlayerBoard.printOnlyHitsBoard();
        int coordinate;
        boolean shotToCheck;

            do {
                coordinate = board.convertCoordinateToIndex(getInput("Give coordinate to shoot: "));
                shotToCheck = otherPlayerBoard.checkIfCellAlreadyShot(coordinate) && !otherPlayerBoard.checkIfCoordinateExists(coordinate);
            } while (shotToCheck);

        CommandMakeMoveBattleships makeMoveCommand = new CommandMakeMoveBattleships(coordinate, game.getOtherPlayer());
        makeMoveCommand.execute();
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
    public void moveOnBoard(int coordinate)
    {
        board.shotOnBoard(coordinate);
        Ship ship = getShipByCoordinate(coordinate);
        if (ship != null)
            ship.takeHit();
    }

    @Override
    public String getName() {
        return name;
    }

    private void printShips()
    {
        for (Ship ship : ships){
            System.out.println(ship.getId() + " - " + ship.getSize());
        }
    }
    private boolean checkSetupDone()
    {
        return ships.size() == game.getShipSizes().length;
    }

    private void createdListOfShips()
    {
        // TODO get this from a config file
        int shipId = 0;
        for (int shipSize : game.getShipSizes()) {
            ships.add(new Ship(shipId, shipSize));
            shipId++;
        }
    }

    public int[] getHitMisData()
    {
        return board.countHitsAndMisses();
    }


    private Ship selectShip(int shipid)
    {
        boolean shipSelect = true;
        Ship selectedShip = null;

        while (shipSelect) {
            int shipId = shipid;
            for (Ship ship : ships) {
                if (ship.getId() == shipId) {
                    selectedShip = ship;
                    shipSelect = false;
                }
            }
        }
        return selectedShip;
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

    private String getInput(String message)
    {
        System.out.println(message);
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
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
