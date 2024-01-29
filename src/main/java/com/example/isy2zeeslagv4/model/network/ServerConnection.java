package com.example.isy2zeeslagv4.model.network;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerConnection {

    private String hostName = "127.0.0.1"; // localhost address TODO config
    private int portNumber = 7789; // port nummer van de server TODO config
    private Socket client; // aanmaken van de socket
    private BufferedReader in; // aanmaken van input reader
    private PrintWriter out; // aanmaken van output writer TODO observer pattern, apparte thread
    private boolean done = false; // boolean die aangeeft of we nog verbinding hebben (voor de UI)

    private volatile static ServerConnection instance;

    private ServerConnection()
    {
    }

    public static ServerConnection getInstance()
    {

        if (instance == null) {
            synchronized (ServerConnection.class) {
                if (instance == null) {
                    instance = new ServerConnection();
                }
            }
        }
        return instance;
    }

    /**
     * Deze methode verbreekt de verbinding met de server en logt onze user uit
     */
//    public void shutdown()
//    {
//        done = true;
//        try {
//            quit(); // we loggen eerst netjes uit, zodat we daarna met dezelfde naam weer in kunnen loggen
//            in.close();
//            out.close();
//            if(!client.isClosed()) {
//                client.close();
//            }
//        } catch (IOException e) {
//            //we negeren een exception omdat we toch de verbinding verbreken
//        }
//    }

}