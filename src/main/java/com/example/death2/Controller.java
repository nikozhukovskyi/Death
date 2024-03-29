package com.example.death2;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.Timer;


public class Controller{

    Timer timer = new Timer();
    @FXML
    private void minimized(MouseEvent event) {
        final Node source = (Node) event.getSource();
        final Stage stage = (Stage) source.getScene().getWindow();
        stage.setIconified(true);

    }

    @FXML
    private void close(MouseEvent event){
        Stop_Timer(event);
        final Node source = (Node) event.getSource();
        final Stage stage = (Stage) source.getScene().getWindow();
        stage.close();

    }
    public void iNFORM(String informes, String context){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(informes + " ");
        alert.setContentText(context + " ");
        alert.showAndWait().ifPresent(wait -> {

        });
    }
    @FXML
    public void Stop_Timer(MouseEvent event){
        timer.cancel();
        iNFORM("Таймер зупенено!","Запусти таймер!");
        final Node source = (Node) event.getSource();
        final Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    @FXML
    void off() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Information");
        alert.setHeaderText("Вимкнутись!?");
        alert.setContentText("Питаю чи мені вимкнутись!?");
        alert.showAndWait().ifPresent(wait ->{
        if (wait == ButtonType.OK){
            Runtime rs = Runtime.getRuntime();
            try {
                rs.exec("cmd.exe /C C:\\Windows\\System32\\shutdown.exe /s /t 10 /c \"PC wil die in 10 seconds\"");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    });
    }

    @FXML
    void reload() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Information");
        alert.setHeaderText("Перезаргурзитись!?");
        alert.setContentText("Питаю чи мені перезаргурзитись!?");
        alert.showAndWait().ifPresent(wait ->{
            if (wait == ButtonType.OK){
                Runtime rs = Runtime.getRuntime();
                try {
                    rs.exec("cmd.exe /C C:\\Windows\\System32\\shutdown.exe -r /t 10 /c \"PC will restart in 10 seconds\"");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
    });
    }

    @FXML
    void sleep() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Information");
        alert.setHeaderText("Заснути!?");
        alert.setContentText("Питаю чи піти спати!?");
        alert.showAndWait().ifPresent(wait ->{
            if (wait == ButtonType.OK){
                Runtime rs = Runtime.getRuntime();

                try {
                    rs.exec("cmd.exe /C %windir%\\System32\\rundll32.exe powrprof.dll,SetSuspendState 0,1,0");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

    }

    private double xOffset;
    private double yOffset;
    @FXML
    void open_battery()  {


        Image ico = new Image(String.valueOf(getClass().getResource("img/ico.png")));
        try {
            Parent root = FXMLLoader.load(getClass().getResource("batter.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(root);//розміри панелі в сценБілдері
            stage.setTitle("!Death");
            stage.setResizable(false);//не можна змінювати розміри панелі
            stage.getIcons().add(ico);
            stage.initStyle(StageStyle.UNDECORATED);//приховує віндовську панель close and mini\maxi mized
            stage.initStyle(StageStyle.TRANSPARENT);
            scene.setFill(Color.TRANSPARENT);


            scene.setOnMousePressed(event -> {
                xOffset = stage.getX() - event.getScreenX();
                yOffset = stage.getY() - event.getScreenY();
            });
            //Lambda mouse event handler
            scene.setOnMouseDragged(event -> {
                stage.setX(event.getScreenX() + xOffset);
                stage.setY(event.getScreenY() + yOffset);
            });
            //тут код, всьо..

            stage.setScene(scene);
            stage.show();
        }catch (Exception e){
            System.out.println(e.getMessage() + " sukaa");
        }

    }
}