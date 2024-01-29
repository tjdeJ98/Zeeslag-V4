package com.example.isy2zeeslagv4.model.game.commands.network;

import com.example.isy2zeeslagv4.model.game.commands.Command;
import com.example.isy2zeeslagv4.model.network.Receiver;

import static java.lang.System.out;

public class CommandRequestGameList implements Command {

    Receiver receiver;

    public CommandRequestGameList(Receiver receiver)
    {
        this.receiver = receiver;
    }

    @Override
    public void execute() {
        System.out.println("Requesting gamelist...");
        out.println("get gamelist");
    }
}