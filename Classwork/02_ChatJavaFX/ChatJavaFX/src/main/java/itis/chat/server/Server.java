package itis.chat.server;

import itis.chat.client.ClientConnection;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {

    private final static int PORT = 1234;
    private static List<ClientConnection> clients = new ArrayList<>();

    public static void main(String[] args) {
        try {
            System.out.println("Starting server...");
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("Waiting for clients...");

            while (true) {
                Socket socket = serverSocket.accept();
                ClientConnection connection = new ClientConnection(socket, clients);
                clients.add(connection);
                System.out.println("New client connected");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}