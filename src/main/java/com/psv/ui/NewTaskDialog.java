package com.psv.ui;

import javafx.application.Platform;
import javafx.scene.control.TextInputDialog;

import java.util.Optional;

public class NewTaskDialog extends TextInputDialog {

    private final String TITLE = "New task";
    private final String HEADER = "Describe your new task";
    private final String CONTENT = "Description";
    private final int WIDTH = 50;

    public NewTaskDialog() {
        super();
        setTitle(TITLE);
        setHeaderText(HEADER);
        setContentText(CONTENT);
        getEditor().setPrefColumnCount(WIDTH);
    }

    public Optional<String> showDialog() {
        Platform.runLater(() -> getEditor().requestFocus());
        return super.showAndWait();
    }
}
