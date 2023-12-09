module com.example.taskmanager {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.taskmanager to javafx.fxml;
    exports com.example.taskmanager;
}