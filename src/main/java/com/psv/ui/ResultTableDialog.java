package com.psv.ui;

import com.psv.utils.LogParser;
import com.psv.utils.TableUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Dialog;
import javafx.scene.control.TableView;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ResultTableDialog extends Dialog<Boolean> {

    private final String TITLE = "Summary";

    private final TableView table = new TableView();

    private final ObservableList<TaskTableRow> data = FXCollections.observableArrayList();

    private final int WIDTH = 600;

    public ResultTableDialog() throws IOException {
        super();
        setTitle(TITLE);

        table.setPrefWidth(WIDTH);
        List<TaskTableColumn> taskTableColumns = Arrays.asList(
                new TaskTableColumn<Long>("Time", "time", 50),
                new TaskTableColumn<String>("Task", "task", 500)
        );
        table.getColumns().addAll(taskTableColumns);
        table.setPadding(new Insets(5, 5, 0, 5));
        table.getSelectionModel().setCellSelectionEnabled(true);

        TableUtils.installCopyPasteHandler(table);

        fillTable();

        table.setItems(data);

        getDialogPane().setContent(table);

        getDialogPane().getScene().getWindow().setOnCloseRequest(event -> this.getDialogPane().getScene().getWindow().hide());
    }

    private void fillTable() throws IOException {
        Map<String, Long> log = LogParser.parse("./SimpleTimeTracking/logs/log-" + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")) + ".log");
        log.forEach((k,v) -> data.add(new TaskTableRow(v, k)));
    }
}
