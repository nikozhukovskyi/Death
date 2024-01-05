package com.example.death2;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Timer;
import java.util.TimerTask;

public class Controller_battery {

    @FXML
    public ProgressBar battery_progress;

    @FXML
    private ProgressIndicator percent_ind;

    double res2, res = 0.0;
    Timer timer = new Timer();

    @FXML
    private void minimized(MouseEvent event){
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



 public void proceses(){
     String x;

     Runtime rs = Runtime.getRuntime();
     try {
         Process w = rs.exec("cmd.exe /C WMIC Path Win32_Battery Get EstimatedChargeRemaining");
         BufferedReader str  = new BufferedReader(new InputStreamReader(w.getInputStream()));
         str.readLine();//1 рядок
         str.readLine();//2 рядок
         x = str.readLine();//3 рядок число
         res = Double.parseDouble(x);
         res2 = res /100;

     }catch (IOException e) {
         throw new RuntimeException(e);
     }

    }


public void tim() {

     TimerTask timerTask =new TimerTask() {
       @Override
       public void run() {
           proceses();
           if (res > 25.0){
               proceses();
               System.out.println(res + " більше 25");
               battery_progress.setProgress(res2);
               percent_ind.setProgress(res2);
               if (res > 90.0){
                   battery_progress.setStyle("-fx-accent: green");
               }if (res > 75.0 && res < 90.0){
                   battery_progress.setStyle("-fx-accent: #90EE90");
               }if(res < 75.0 && res > 30){
                   battery_progress.setStyle("-fx-accent: #22B2D8");
               }if (res > 25.0 && res < 30.0){
                   battery_progress.setStyle("-fx-accent: #FF7D7D");
               }
               if (res < 25.0){
                   battery_progress.setStyle("-fx-accent: #ff0000");
               }else{
                 // battery_progress.setStyle("-fx-accent: #22B2D8");
               }

           }else {
               System.out.println(res + " інше");
               battery_progress.setProgress(res2);
               percent_ind.setProgress(res2);
               timer.cancel();
           }
       }
   };
     if (res2 > 0.0){
         System.out.println(res + " > 0.0");
         timer.scheduleAtFixedRate(timerTask,0,60000);
     }

    }

    @FXML
     public  void Stop_Timer(MouseEvent event){
        timer.cancel();
        iNFORM("Таймер зупенено!","Запусти таймер!");
        final Node source = (Node) event.getSource();
        final Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    @FXML
    void runable(){
        proceses();
        if (res < 27.0 ){
            System.out.println(res + " менше 25");
            battery_progress.setProgress(res2);
            percent_ind.setProgress(res2);
            iNFORM("Постав на зарядку!" + res + "%", "Таймер зупинено, якщо буде більше 25% клацни по батареї!");
            tim();
        }if (res > 85.0){
            System.out.println(res + " більше 85");
            battery_progress.setProgress(res2);
            percent_ind.setProgress(res2);
            iNFORM("Зніми з зарядки!" + res + "%", "Таймер зупинено, якщо буде менше 85% клацни по батареї!");
            tim();
        }else {
            tim();
        }
    }


}
