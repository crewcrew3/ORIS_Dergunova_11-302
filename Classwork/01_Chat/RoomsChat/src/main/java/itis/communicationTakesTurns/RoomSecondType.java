package itis.communicationTakesTurns;

import itis.parallelСommunication.ClientConnection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class RoomSecondType implements Runnable {

    private Socket socket1, socket2;
    private Thread thread;
    private String clientName1, clientName2;
    private PrintWriter out1, out2;
    private BufferedReader in1, in2;

    public RoomSecondType(Socket s1) throws IOException {
        socket1 = s1;
        out1 = new PrintWriter(s1.getOutputStream(), true);
        in1 = new BufferedReader(new InputStreamReader(s1.getInputStream()));
        thread = new Thread(this);
    }

    public void addAndStart(Socket s2) throws IOException {
        socket2 = s2;
        out2 = new PrintWriter(s2.getOutputStream(), true);
        in2 = new BufferedReader(new InputStreamReader(s2.getInputStream()));
        thread.start();
    }

    @Override
    public void run() {
        try {
            clientName1 = in1.readLine();
            clientName2 = in2.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //пусть тот кто первый подключился - начинает общение
        while (true) {
            try {
                String message1, message2;
                if ((message1 = in1.readLine()) != null) {
                    out2.println(clientName1 + ": " + message1);
                    out1.println("Your message: " + message1);
                }

                if ((message2 = in2.readLine()) != null) {
                    out1.println(clientName2 + ": " + message2);
                    out2.println("Your message: " + message2);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
