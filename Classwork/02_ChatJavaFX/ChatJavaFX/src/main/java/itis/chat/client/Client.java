package itis.chat.client;

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

            //Поток для получения сообщений от других клиентов на стороне КЛИЕНТА. Он постоянно ждет, что нам что-то придет в инпут стрим.
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
            out.println(name); //записывем имя клиента
            System.out.println("Input the message: ");

            //ввод сообщений производится в мейн-потоке
            while (true) {
                String message = sc.nextLine();
                out.println(message); //уходит на сервер, где работает поток ClientConnection. Там сообщение рассылается всем другим клиентам.
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}