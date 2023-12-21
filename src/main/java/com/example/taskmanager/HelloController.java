package com.example.taskmanager;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class HelloController {

    @FXML
    private CheckBox Completed;
    @FXML
    private RadioButton Homework;
    @FXML
    private RadioButton Meeting;
    @FXML
    private RadioButton Shopping;
    @FXML
    private RadioButton Low;
    @FXML
    private RadioButton Medium;
    @FXML
    private RadioButton High;
    @FXML
    private DatePicker Deadline;

    @FXML
    private ListView<Task> listView;
    ObservableList<Task> tasks = FXCollections.observableArrayList();

    @FXML
    private TextField inputName;

    @FXML
    private TextField inputDesc;

    @FXML
    private Label label;



    private TaskDAO taskDAO;

    @FXML
    public void initialize() {

        listView.setItems(tasks);
        taskDAO = new TaskDAO();
        fetchTasksFromDatabase();
    }

    private void fetchTasksFromDatabase() {
        try {

            List<Task> tasksFromDB = taskDAO.getAllTasks();
            tasks.setAll(tasksFromDB);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void onListViewSelected() {
        int selectedIndex = listView.getSelectionModel().getSelectedIndex();


        if (selectedIndex >= 0) {
            Task selectedTask = tasks.get(selectedIndex);
            inputName.setText(selectedTask.getTaskName());
            inputDesc.setText(selectedTask.getTaskDescription());
            Deadline.setValue(selectedTask.getDeadline());


            String taskType = selectedTask.getTaskType();
            switch (taskType) {
                case "Homework":
                    Homework.setSelected(true);
                    break;
                case "Meeting":
                    Meeting.setSelected(true);
                    break;
                case "Shopping":
                    Shopping.setSelected(true);
                    break;
                default:
                    Homework.setSelected(false);
                    Meeting.setSelected(false);
                    Shopping.setSelected(false);
            }

            Boolean completed = selectedTask.getCompleted();
            Completed.setSelected(Boolean.TRUE.equals(completed));
            Completed.setVisible(true);

            Priority priority = selectedTask.getPriority();
            if (priority != null) {
                switch (priority) {
                    case LOW:
                        Low.setSelected(true);
                        break;
                    case MEDIUM:
                        Medium.setSelected(true);
                        break;
                    case HIGH:
                        High.setSelected(true);
                        break;
                    default:
                        Low.setSelected(false);
                        Medium.setSelected(false);
                        High.setSelected(false);
                }
            }

        } else {
            clearInputFields();

        }
    }


    @FXML
    protected void onSaveButtonClick() {
        int selectedIndex = listView.getSelectionModel().getSelectedIndex();

        if (selectedIndex >= 0) {
            updateTask(selectedIndex);
        } else {
            createNewTask();
        }
    }

    private void createNewTask() {
        Task newTask = new Task();
        newTask.setTaskName(inputName.getText());
        newTask.setTaskDescription(inputDesc.getText());
        newTask.setDeadline(Deadline.getValue());

        String taskType = "";

        if (Homework.isSelected()) {
            taskType = "Homework";
        } else if (Meeting.isSelected()) {
            taskType = "Meeting";
        } else if (Shopping.isSelected()) {
            taskType = "Shopping";
        }

        newTask.setTaskType(taskType);

        newTask.setCompleted(Completed.isSelected());

        if (Low.isSelected()) {
            newTask.setPriority(Priority.LOW);
        } else if (Medium.isSelected()) {
            newTask.setPriority(Priority.MEDIUM);
        } else if (High.isSelected()) {
            newTask.setPriority(Priority.HIGH);
        }

        try {

            taskDAO.createTask(newTask);
            tasks.add(newTask);
            clearInputFields();
        } catch (SQLException e) {
            e.printStackTrace();

        }
    }


    private void updateTask(int selectedIndex) {
        Task selectedTask = tasks.get(selectedIndex);

        selectedTask.setTaskName(inputName.getText());
        selectedTask.setTaskDescription(inputDesc.getText());
        selectedTask.setDeadline(Deadline.getValue());

        String taskType = "";

        if (Homework.isSelected()) {
            taskType = "Homework";
        } else if (Meeting.isSelected()) {
            taskType = "Meeting";
        } else if (Shopping.isSelected()) {
            taskType = "Shopping";
        }

        selectedTask.setTaskType(taskType);

        selectedTask.setCompleted(Completed.isSelected());

        // Update Priority
        if (Low.isSelected()) {
            selectedTask.setPriority(Priority.LOW);
        } else if (Medium.isSelected()) {
            selectedTask.setPriority(Priority.MEDIUM);
        } else if (High.isSelected()) {
            selectedTask.setPriority(Priority.HIGH);
        }

        try {

            taskDAO.updateTask(selectedTask);

            listView.refresh();


            listView.getSelectionModel().clearSelection();
        } catch (SQLException e) {
            e.printStackTrace();

        }
    }


    @FXML
    protected void onDeleteButtonClick() {
        int selectedIndex = listView.getSelectionModel().getSelectedIndex();

        if (selectedIndex >= 0) {
            Task selectedTask = tasks.get(selectedIndex);

            try {

                taskDAO.deleteTask(selectedTask.getTaskID());

                tasks.remove(selectedTask);

                listView.refresh();
                Completed.setVisible(false);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void clearInputFields() {
        inputName.clear();
        inputDesc.clear();
        Deadline.setValue(null);
        Homework.setSelected(false);
        Meeting.setSelected(false);
        Shopping.setSelected(false);
        Completed.setVisible(false);
        Low.setSelected(false);
        Medium.setSelected(false);
        High.setSelected(false);
    }
}
