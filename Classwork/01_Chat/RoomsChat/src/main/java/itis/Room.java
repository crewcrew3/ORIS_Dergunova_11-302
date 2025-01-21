package itis;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Room implements Runnable {

    private ClientConnection connection1, connection2;
    private Thread thread;
    private List<ClientConnection> clients = new ArrayList<>();

    public Room(Socket s1) throws IOException {
        connection1 = new ClientConnection(s1, clients);
        clients.add(connection1);
        thread = new Thread(this);
    }

    public void addAndStart(Socket s2) throws IOException {
        connection2 = new ClientConnection(s2, clients);
        clients.add(connection2);
        thread.start();
    }

    @Override
    public void run() {
        
    }
}

