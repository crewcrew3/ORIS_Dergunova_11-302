package itis;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    private static final String HOST = "localhost";
    private final static int PORT = 1234;

    public static void main(String[] args) {
        try {
            Socket socket = new Socket(InetAddress.getByName(HOST), PORT);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            Scanner sc = new Scanner(System.in);

            new Thread(() -> {
                String messageFromOtherClient;
                try {
                    while ((messageFromOtherClient = in.readLine()) != null) {
                        System.out.println(messageFromOtherClient);
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }).start();

            System.out.println("Input your name: ");
            String name = sc.nextLine();
            out.println(name);
            System.out.println("Input the message: ");

            while (true) {
                String message = sc.nextLine();
                out.println(message);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
