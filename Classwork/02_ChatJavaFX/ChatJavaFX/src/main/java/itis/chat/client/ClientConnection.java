package itis.chat.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

public class ClientConnection implements Runnable {

    private Socket socket;
    private Thread thread;
    private PrintWriter out;
    private BufferedReader in;
    private List<ClientConnection> clients;

    public ClientConnection(Socket socket, List<ClientConnection> clients) throws IOException {
        this.socket = socket;
        this.clients = clients;
        this.out = new PrintWriter(socket.getOutputStream(), true);
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        try {
            String message;
            while ((message = in.readLine()) != null) {
                for (ClientConnection client : clients) {
                    if (client != this) { // Не отправляем сообщение себе
                        client.out.println("Other client: " + message);
                    } else {
                        client.out.println("Your message: " + message);
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}