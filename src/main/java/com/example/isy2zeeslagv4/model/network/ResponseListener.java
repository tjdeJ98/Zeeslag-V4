package com.example.isy2zeeslagv4.model.network;

import com.example.isy2zeeslagv4.model.game.commands.Command;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class ResponseListener implements Observer {

    private ArrayList<Command> commands = new ArrayList<>();
    private ArrayList<String> labels  = new ArrayList<>();

    @Override
    public void update(Observable o, Object arg) {
        ServerConnection2 serverConnection2 = (ServerConnection2) o; // we casten Observable op de Singleton connection
        String response = serverConnection2.getResponse(); // we halen het laatste bericht op (in de toekomst misschien een queue en na elke update de eerste index verwijderen
        System.out.println(response); // we printen de repsonse van de server
        //TODO we evoken de bijpassende command
    }

    public void addCommand(Command command, String label) {
        commands.add(command);
        labels.add(label);
    }

    public void start() {

        //Command command = commands.get();

    }

}