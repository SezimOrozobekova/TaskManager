package com.example.taskmanager;

import javafx.scene.control.DatePicker;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TaskDao {

    private static final String JDBC_URL = "jdbc:postgresql://localhost:5432/task";
    private static final String JDBC_USER = "postgres";
    private static final String JDBC_PASSWORD = "1234";

    public TaskDao() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public List<HomeWork> getAllTasks() throws SQLException {
        List<HomeWork> tasks = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
            String sql = "SELECT * FROM tasks";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        tasks.add(getTaskFromResultSet(resultSet));
                    }
                }
            }
        }
        return tasks;
    }

    public HomeWork getTaskById(int taskId) throws SQLException {
        HomeWork task = null;
        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
            String sql = "SELECT * FROM tasks WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, taskId);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        task = getTaskFromResultSet(resultSet);
                    }
                }
            }
        }
        return task;
    }

    public static void insertTask(HomeWork task) throws SQLException {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
            String sql = "INSERT INTO task(title, description, deadline, complete) VALUES (?, ?, ?, ?) RETURNING id";
            try (PreparedStatement insertTaskStatement = connection.prepareStatement(sql)) {
                insertTaskStatement.setString(1, task.getTitle());
                insertTaskStatement.setString(2, task.getDescription());
                insertTaskStatement.setDate(3, Date.valueOf(task.getDeadline().getValue()));
                insertTaskStatement.setBoolean(4, task.isCompleted());

                try (ResultSet resultSet = insertTaskStatement.executeQuery()) {
                    if (resultSet.next()) {
                        int taskId = resultSet.getInt("id");
                        task.setId(taskId);
                    }
                }
            }
        }
    }

    public void updateTask(HomeWork task) throws SQLException {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
            String sql = "UPDATE tasks SET task_name=?, task_description=?, completed=?, priority=?, deadline=? WHERE id=?";
            try (PreparedStatement updateTaskStatement = connection.prepareStatement(sql)) {
                updateTaskStatement.setString(1, task.getTitle());
                updateTaskStatement.setString(2, task.getDescription());
                updateTaskStatement.setBoolean(3, task.isCompleted());
                updateTaskStatement.setString(4, task.getPriority().name());
                updateTaskStatement.setDate(5, Date.valueOf(task.getDeadline().getValue()));
                updateTaskStatement.setInt(6, task.getId());

                updateTaskStatement.executeUpdate();
            }
        }
    }

    public void deleteTask(int taskId) throws SQLException {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
            String sql = "DELETE FROM tasks WHERE id = ?";
            try (PreparedStatement deleteTaskStatement = connection.prepareStatement(sql)) {
                deleteTaskStatement.setInt(1, taskId);
                deleteTaskStatement.executeUpdate();
            }
        }
    }

    private HomeWork getTaskFromResultSet(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String taskName = resultSet.getString("task_name");
        String taskDescription = resultSet.getString("task_description");
        boolean completed = resultSet.getBoolean("completed");
        Priority priority = Priority.valueOf(resultSet.getString("priority"));
        DatePicker deadline = new DatePicker(resultSet.getDate("deadline").toLocalDate());

        HomeWork task = new HomeWork();
        task.setId(id);
        task.setCompleted(completed);
        task.setTitle(taskName);
        task.setDescription(taskDescription);
        task.setPriority(priority);

        return task;
    }
}
