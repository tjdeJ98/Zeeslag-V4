package com.example.isy2zeeslagv4.model.game.commands.network;

import com.example.isy2zeeslagv4.model.game.commands.Command;
import com.example.isy2zeeslagv4.model.network.Receiver;

public class CommandQuit implements Command {

    Receiver receiver;

    public CommandQuit(Receiver receiver)
    {
        this.receiver = receiver;
    }

    @Override
    public void execute() {
        receiver.quit();
    }
}