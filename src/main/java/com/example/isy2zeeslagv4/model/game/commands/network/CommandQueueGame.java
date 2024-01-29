package com.example.isy2zeeslagv4.model.game.commands.network;

import com.example.isy2zeeslagv4.model.game.commands.Command;
import com.example.isy2zeeslagv4.model.network.Receiver;

import static java.lang.System.out;

public class CommandQueueGame implements Command {
    Receiver receiver;
    private String gameName;

    public CommandQueueGame(Receiver receiver, String gameName)
    {
        this.receiver = receiver;
        this.gameName = gameName;
    }



    @Override
    public void execute() {
        System.out.println("Queueing for " + gameName + "...");
        out.println("subscribe " + gameName);
    }
}