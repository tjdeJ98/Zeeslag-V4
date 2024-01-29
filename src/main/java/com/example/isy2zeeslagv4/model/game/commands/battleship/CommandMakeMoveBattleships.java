package com.example.isy2zeeslagv4.model.game.commands.battleship;

import com.example.isy2zeeslagv4.model.game.commands.Command;
import com.example.isy2zeeslagv4.model.game.games.BattleshipGame;
import com.example.isy2zeeslagv4.model.network.Receiver;
import com.example.isy2zeeslagv4.model.player.Player;

public class CommandMakeMoveBattleships implements Command {
    Receiver receiver;
    private int index;
    private Player<BattleshipGame> otherPlayer;

    public CommandMakeMoveBattleships(int index, Player<BattleshipGame> otherPlayer)
    {
        this.index = index;
        this.otherPlayer = otherPlayer;
    }

    public CommandMakeMoveBattleships(Receiver receiver, int index)
    {
        this.receiver = receiver;
        this.index = index;
    }

    @Override
    public void execute() {
        if (receiver != null) {
            // Handle online game logic
            System.out.println("Online: Hitting " + index + "...");
            receiver.makeMove(index);
        } else {
            // Handle local game logic
            System.out.println("Local: Hitting " + index + "...");
            otherPlayer.moveOnBoard(index);
            // Local game logic here
        }
    }
}
