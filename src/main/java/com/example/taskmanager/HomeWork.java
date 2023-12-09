package com.example.taskmanager;

import com.example.taskmanager.Priority;
import javafx.scene.control.DatePicker;
import java.time.format.DateTimeFormatter;

public class HomeWork {
    private int id; // Assuming an ID is needed for database operations

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public DatePicker getDeadline() {
        return deadline;
    }

    public void setDeadline(DatePicker deadline) {
        this.deadline = deadline;
    }

    private String title;
    private String description;
    private boolean completed;
    private Priority priority;
    private DatePicker deadline;

    // Constructor for creating tasks without an ID (e.g., for new tasks)





}
