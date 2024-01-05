module com.example.death2 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.death2 to javafx.fxml;
    exports com.example.death2;
}