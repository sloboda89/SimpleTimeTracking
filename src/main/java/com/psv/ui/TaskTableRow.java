package com.psv.ui;

public class TaskTableRow {

    private String time;
    private String task;

    public String getTime() {
        return time;
    }

    public String getTask() {
        return task;
    }

    public TaskTableRow(String time, String task) {

        this.time = time;
        this.task = task;
    }
}
