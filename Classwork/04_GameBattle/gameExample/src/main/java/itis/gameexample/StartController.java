package itis.gameexample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class StartController {

    @FXML
    private Label welcomeText;

    @FXML
    protected void onStartButtonClick() throws IOException {
        switchScene();
    }

    private void switchScene() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("game.fxml"));
        Stage stage = (Stage) welcomeText.getScene().getWindow();
        Scene newScene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setScene(newScene);
        stage.setTitle("Fighting!");
        stage.setResizable(false);
        stage.sizeToScene();
        stage.centerOnScreen();
        stage.show();
    }
}