package com.psv.ui;

public class TaskTableRow {

    private long time;
    private String task;

    public long getTime() {
        return time;
    }

    public String getTask() {
        return task;
    }

    public TaskTableRow(long time, String task) {

        this.time = time;
        this.task = task;
    }
}
