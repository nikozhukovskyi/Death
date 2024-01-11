package com.example.death2;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.Timer;
import java.util.TimerTask;

public class Controller_battery {

    @FXML
    public ProgressBar battery_progress;

    @FXML
    private ProgressIndicator percent_ind;

    @FXML
    private Label percent;

    double result_to_progress, percent_battery = 0.0;
    String getPercentBatteryCommand;
    DecimalFormat df = new DecimalFormat("#");
    ProcessBuilder build_cmd = new ProcessBuilder();
    PauseTransition pause = new PauseTransition(Duration.seconds(1));
    Timer timer = new Timer();
    TimerTask timerTask;


    @FXML
    private void minimized(MouseEvent event) {
        final Node source = (Node) event.getSource();
        final Stage stage = (Stage) source.getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    private void close(MouseEvent event) {
        Stop_Timer(event);
        final Node source = (Node) event.getSource();
        final Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    public void iNFORM(String informes, String context) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(informes + " ");
        alert.setContentText(context + " ");
        alert.showAndWait().ifPresent(wait -> {
        });
    }

    public void getPercentBattery() {
        getPercentBatteryCommand = "WMIC Path Win32_Battery Get EstimatedChargeRemaining";
        try {
            build_cmd.command("cmd.exe", "/C", getPercentBatteryCommand);
            Process process = build_cmd.start();
            BufferedReader str = new BufferedReader(new InputStreamReader(process.getInputStream()));
            str.readLine();//1 рядок
            str.readLine();//2 рядок
            String x = str.readLine();//3 рядок число
            percent_battery = Double.parseDouble(x);
            result_to_progress = percent_battery / 100;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void tim() {
        timerTask = new TimerTask() {
            @Override
            public void run() {
                getPercentBattery();
                if (percent_battery > 1.0) {
                    getPercentBattery();
                    battery_progress.setProgress(result_to_progress);
                    percent_ind.setProgress(result_to_progress);
                    percent.setAlignment(Pos.CENTER);

                    if (percent_battery > 90.0) {
                        battery_progress.setProgress(result_to_progress);
                        percent_ind.setProgress(result_to_progress);
                        battery_progress.setStyle("-fx-accent: green");
                        pause.setOnFinished((e) -> percent.setText(df.format(percent_battery) + "%"));
                        pause.play();
                    }
                    if (percent_battery > 75.0 && percent_battery < 90.0) {
                        battery_progress.setProgress(result_to_progress);
                        percent_ind.setProgress(result_to_progress);
                        battery_progress.setStyle("-fx-accent: #90EE90");
                        pause.setOnFinished((e) -> percent.setText(df.format(percent_battery) + "%"));
                        pause.play();
                    }
                    if (percent_battery < 75.0 && percent_battery > 30) {
                        battery_progress.setProgress(result_to_progress);
                        percent_ind.setProgress(result_to_progress);
                        battery_progress.setStyle("-fx-accent: #22B2D8");
                        pause.setOnFinished((e) -> percent.setText(df.format(percent_battery) + "%"));
                        pause.play();
                    }
                    if (percent_battery > 25.0 && percent_battery < 30.0) {
                        battery_progress.setProgress(result_to_progress);
                        percent_ind.setProgress(result_to_progress);
                        battery_progress.setStyle("-fx-accent: #FF7D7D");
                        pause.setOnFinished((e) -> percent.setText(df.format(percent_battery) + "%"));
                        pause.play();
                    }
                    if (percent_battery < 25.0) {
                        battery_progress.setProgress(result_to_progress);
                        percent_ind.setProgress(result_to_progress);
                        battery_progress.setStyle("-fx-accent: #ff0000");
                        pause.setOnFinished((e) -> percent.setText(df.format(percent_battery) + "%"));
                        pause.play();

                    }else {
                        battery_progress.setProgress(result_to_progress);
                        percent_ind.setProgress(result_to_progress);
                        System.out.println("хуєта");
                        pause.setOnFinished((e) -> percent.setText(df.format(percent_battery) + "%"));
                        pause.play();
                    }


                } else {
                    iNFORM("таймер здох","хз-хз");
                    battery_progress.setProgress(result_to_progress);
                    percent_ind.setProgress(result_to_progress);
                    timer.cancel();
                }
            }
        };
        if (result_to_progress > 0.0) {
            timer.scheduleAtFixedRate(timerTask, 0, 10000);
        }

    }

    @FXML
    private void Stop_Timer(MouseEvent event) {
        timer.cancel();
        iNFORM("Таймер зупенено!", "Запусти таймер!");
        final Node source = (Node) event.getSource();
        final Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    @FXML
    void runable() {
        getPercentBattery();
        if (percent_battery < 27.0) {
            battery_progress.setProgress(result_to_progress);
            percent_ind.setProgress(result_to_progress);
            iNFORM("Постав на зарядку! " + percent_battery + " %", "Таймер зупинено, якщо буде більше 27% клацни по батареї!");
            tim();
        }
        if (percent_battery > 85.0) {
            battery_progress.setProgress(result_to_progress);
            percent_ind.setProgress(result_to_progress);
            iNFORM("Зніми з зарядки! " + percent_battery + " %", "Таймер зупинено, якщо буде менше 85% клацни по батареї!");
            tim();
        } else {
            tim();
        }
    }


}
