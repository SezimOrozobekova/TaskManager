module com.example.taskmanager {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.taskmanager to javafx.fxml;
    exports com.example.taskmanager;
}