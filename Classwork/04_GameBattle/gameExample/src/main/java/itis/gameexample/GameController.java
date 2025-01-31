package itis.gameexample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;

public class GameController {

    @FXML
    public Slider slider1;

    @FXML
    public ProgressBar progressBar1;

    @FXML
    public Button btnKick1;

    @FXML
    public Slider slider2;

    @FXML
    public ProgressBar progressBar2;

    @FXML
    public Button btnKick2;

    @FXML
    public Label HP1;

    @FXML
    public Label HP2;

    @FXML
    public void initialize() {
        // Устанавливаем начальные значения для слайдеров и прогресс-баров
        slider1.setMin(1);
        slider1.setMax(5);
        slider1.setValue(1); // начальное значение

        slider2.setMin(1);
        slider2.setMax(5);
        slider2.setValue(1); // начальное значение

        progressBar1.setProgress(1.0); // 100%
        progressBar2.setProgress(1.0); // 100%

        // Устанавливаем обработчики для кнопок
        btnKick1.setOnAction(event -> kickPlayer1());
        btnKick2.setOnAction(event -> kickPlayer2());

        // Обновляем цвет прогресс баров
        updateProgressBarColor(progressBar1);
        updateProgressBarColor(progressBar2);
    }

    private void kickPlayer1() {
        // Получаем значение из слайдера и уменьшаем прогресс бар 2
        int damage = (int) slider1.getValue();
        double newProgress = progressBar2.getProgress() - (damage / 100.0); // Преобразуем в диапазон от 0 до 1
        HP2.setText("HP: " + Math.max(Math.round(newProgress * 100), 0));
        progressBar2.setProgress(Math.max(newProgress, 0)); // на случай если получится отриц значение
        updateProgressBarColor(progressBar2);
    }

    private void kickPlayer2() {
        // Получаем значение из слайдера и уменьшаем прогресс бар 1
        int damage = (int) slider2.getValue();
        double newProgress = progressBar1.getProgress() - (damage / 100.0);
        HP1.setText("HP: " + Math.max(Math.round(newProgress * 100), 0));
        progressBar1.setProgress(Math.max(newProgress, 0));
        updateProgressBarColor(progressBar1);
    }

    private void updateProgressBarColor(ProgressBar progressBar) {
        double progress = progressBar.getProgress();
        if (progress > 0.7) {
            progressBar.setStyle("-fx-accent: green;");
        } else if (progress > 0.3) {
            progressBar.setStyle("-fx-accent: yellow;");
        } else {
            progressBar.setStyle("-fx-accent: red;");
        }
    }
}
