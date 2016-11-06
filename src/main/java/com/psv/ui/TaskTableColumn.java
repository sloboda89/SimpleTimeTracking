package com.psv.ui;

import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

public class TaskTableColumn<T> extends TableColumn {
    public TaskTableColumn(String text, String item, int width) {
        super(text);
        setCellValueFactory(
                new PropertyValueFactory<TaskTableRow, T>(item));
        setPrefWidth(width);
    }
}
