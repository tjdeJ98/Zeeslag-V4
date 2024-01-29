package com.example.isy2zeeslagv4.model.game.commands.battleship;

import com.example.isy2zeeslagv4.model.game.commands.Command;
import com.example.isy2zeeslagv4.model.game.commands.board.battleship.Battleshipboard;
import com.example.isy2zeeslagv4.model.game.games.BattleshipGame;
import com.example.isy2zeeslagv4.model.network.Receiver;
import com.example.isy2zeeslagv4.other.Ship;

import java.util.List;

public class CommandPlaceShipBattleships implements Command {
    Receiver receiver;
    private int beginIndex;
    private int endIndex;
    private Battleshipboard board;
    private Ship ship;

    public CommandPlaceShipBattleships(Receiver receiver, int beginIndex, int endIndex, Battleshipboard board)
    {
        this.receiver = receiver;
        this.beginIndex = beginIndex;
        this.endIndex = endIndex;
        this.board = board;
    }

    public CommandPlaceShipBattleships(int beginIndex, int endIndex, Battleshipboard board, Ship ship)
    {
        this.beginIndex = beginIndex;
        this.endIndex = endIndex;
        this.board = board;
        this.ship = ship;
    }

    @Override
    public void execute() {
        if (receiver != null) {
            // Handle online game logic
            System.out.println("Placing ship for on " + beginIndex + " / " + endIndex);
            receiver.placeShip(beginIndex, endIndex);
        } else {
            // Handle local game logic
            System.out.println("Placing ship for on " + beginIndex + " / " + endIndex);
            List<Integer> allCoordinates = board.getAllCoordinatesShipWillCover(beginIndex, endIndex);
            board.placeShip(allCoordinates, ship);
            // Local game logic here
        }

    }
}
