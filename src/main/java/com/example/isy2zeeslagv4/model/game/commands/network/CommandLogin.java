package com.example.isy2zeeslagv4.model.game.commands.network;

import com.example.isy2zeeslagv4.model.game.commands.Command;
import com.example.isy2zeeslagv4.model.network.ServerConnection2;

public class CommandLogin implements Command {

    ServerConnection2 server = ServerConnection2.getInstance();

    @Override
    public void execute() {
        server.login("poep"); //TODO argumenten verwerken van de login
    }
}