package itis.chat.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class ChatController {

    private static final String HOST = "localhost";
    private final static int PORT = 1234;
    private PrintWriter out;
    private BufferedReader in;

    public void initialize() {
        Platform.runLater(() -> taMessage.requestFocus()); //когда все загрузится, нужно выполнить requestFocus(). Метод requestFocus() устанавливает фокус на указанном элементе управления (в данном случае на текстовой области), что позволяет пользователю сразу начать вводить текст в это поле, как только интерфейс загружен и отображен.

        // Поток для получения сообщений от сервера
        new Thread(() -> {
            try {
                Socket socket = new Socket(InetAddress.getByName(HOST), PORT);
                out = new PrintWriter(socket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                String messageFromServer;
                while ((messageFromServer = in.readLine()) != null) {
                    displayMessage(messageFromServer);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void displayMessage(String message) {
        Platform.runLater(() -> {
            Label messageContainer = new Label(message);
            chat.getChildren().add(messageContainer);
        });
    }

    @FXML
    public TextArea taMessage;
    @FXML
    public Button btnSend;
    @FXML
    public VBox chat;

    @FXML
    protected void onSendButtonClick() {
        String message = taMessage.getText();
        if (!message.isBlank()) {
            out.println(message); // отправляем соо на сервер
            taMessage.clear();
            taMessage.requestFocus();
        }
    }

    @FXML
    protected void onKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            taMessage.setText(taMessage.getText().substring(0, taMessage.getText().length() - 1)); //уберем символ переноса
            onSendButtonClick();
        }
    }
}
