package itis;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private final static int PORT = 1234;

    public static void main(String[] args) {
        try {
            System.out.println("Starting server...");
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("Waiting for clients...");

            int roomCount = 0;
            while (true) {
                roomCount++;
                System.out.println("Room number " + roomCount + " is open");
                Socket socket1 = serverSocket.accept();
                System.out.println("First client is available. Waiting for second client...");

                Room room = new Room(socket1);

                Socket socket2 = serverSocket.accept();
                System.out.println("Second client is available!");

                room.addAndStart(socket2);
                System.out.println("Room number " + roomCount + " is closed");
                System.out.println();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}



