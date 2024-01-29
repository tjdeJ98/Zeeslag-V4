package com.example.isy2zeeslagv4.model.game.commands.network;
import com.example.isy2zeeslagv4.model.game.commands.Command;
import com.example.isy2zeeslagv4.model.network.Receiver;

import java.io.IOException;
import java.net.Socket;

import static java.lang.System.in;
import static java.lang.System.out;

public class CommandShutdown implements Command {

    Receiver receiver;
    private Socket client;

    public CommandShutdown(Receiver receiver){
        this.receiver = receiver;
    }

    @Override
    public void execute() {
        boolean done = true;
        try {
            receiver.quit(); // we loggen eerst netjes uit, zodat we daarna met dezelfde naam weer in kunnen loggen
            in.close();
            out.close();
            if(!client.isClosed()) {
                client.close();
            }
        } catch (IOException e) {
            //we negeren een exception omdat we toch de verbinding verbreken
        }
    }
}