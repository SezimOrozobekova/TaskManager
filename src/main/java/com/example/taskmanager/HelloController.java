package com.example.taskmanager;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.ListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.DatePicker;

import java.util.Date;


public class HelloController {
    @FXML
    private ListView<Task> listView;
    ObservableList<Task> tasks = FXCollections.observableArrayList();

    public void initialize() {
        listView.setItems(tasks);
    }
    @FXML
    private DatePicker datePicker = new DatePicker();

    @FXML
    private Label title = new Label();

    @FXML
    private Label desc = new Label();

    @FXML
    private RadioButton completed = new RadioButton();

    @FXML
    private RadioButton hard;
    @FXML
    private RadioButton middle;
    @FXML
    private RadioButton easy;


    @FXML
    protected void onSaveButtonClick(){
        HomeWork ht = new HomeWork();
        ht.setTitle(title.getText());
        ht.setDescription(desc.getText());



    }


    @FXML
    protected void onListViewSelected(){
        int i = listView.getSelectionModel().getSelectedIndex();

        title.setText("Task: " + tasks.get(i).getTaskName());
        desc.setText("Description: " + tasks.get(i).getTaskDescription());
        information.setVisible(true);

    }

    @FXML
    protected void onCompletedClicked(){
        int i = listView.getSelectionModel().getSelectedIndex();

        tasks.remove(i);
        title.setText("");
        desc.setText("");
        completed.setSelected(false);
    }


    @FXML
    private BorderPane hview;

    @FXML
    private AnchorPane creat;

    @FXML
    private AnchorPane edit;

    @FXML
    private AnchorPane information;


    @FXML
    private TextField inputName;

    @FXML
    private TextField inputDescription;


    @FXML
    protected void onEditButton(){
        information.setVisible(false);
        edit.setVisible(true);
    }
    @FXML
    protected void onSaveButton(){
        information.setVisible(true);
        edit.setVisible(false);
    }
    @FXML
    protected void onCreatButton(){
        creat.setVisible(true);
        hview.setVisible(false);
    }

    @FXML
    protected void onHviewButton(){
        hview.setVisible(true);
        creat.setVisible(false);
    }


}