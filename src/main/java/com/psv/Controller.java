package com.psv;

import com.psv.ui.NewTaskDialog;
import com.psv.ui.ResultTableDialog;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Optional;

public class Controller {

    private final Logger log = LoggerFactory.getLogger(Controller.class);

    private Status lastState = Status.stopped;
    private String taskInProgress = "";

    private enum Status {started, stopped}

    private final NewTaskDialog dialog = new NewTaskDialog();

    @FXML
    protected void handleBigRedButtonAction(ActionEvent event) {
        switch (lastState) {
            case started: {
                lastState = Status.stopped;
                log.info("[" + lastState + "] " + taskInProgress);
            }
            case stopped: {
                Optional<String> result = dialog.showDialog();
                lastState = Status.started;
                result.ifPresent(task -> taskInProgress = task);
                log.info("[" + lastState + "] " + taskInProgress);
            }
        }
    }

    @FXML
    protected void handleSummaryButtonAction(ActionEvent event) throws IOException {

        ResultTableDialog dialog = new ResultTableDialog();

        dialog.showAndWait();
    }
}
