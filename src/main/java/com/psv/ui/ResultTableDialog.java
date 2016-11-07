package com.psv.ui;

import com.psv.utils.LogParser;
import com.psv.utils.TableUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

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

    private long totalDuration = 0;

    public ResultTableDialog() throws IOException {
        super();
        setTitle(TITLE);

        table.setPrefWidth(WIDTH);
        List<TaskTableColumn> taskTableColumns = Arrays.asList(
                new TaskTableColumn<String>("Time", "time", 60),
                new TaskTableColumn<String>("Task", "task", 500)
        );
        table.getColumns().addAll(taskTableColumns);
        table.setPadding(new Insets(5, 5, 0, 5));
        table.getSelectionModel().setCellSelectionEnabled(true);

        TableUtils.installCopyPasteHandler(table);

        fillTable();

        table.setItems(data);

        Label totalLabel = new Label("Total: " + LogParser.convertToJiraFormat(totalDuration));
        totalLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));

        VBox vBox = new VBox(10);

        vBox.getChildren().addAll(table, totalLabel);

        getDialogPane().setContent(vBox);

        getDialogPane().getScene().getWindow().setOnCloseRequest(event -> this.getDialogPane().getScene().getWindow().hide());
    }

    private void fillTable() throws IOException {
        Map<String, Long> log = LogParser.parse("./SimpleTimeTracking/logs/log-" + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")) + ".log");
        totalDuration = log.entrySet().stream().filter(item -> !item.getKey().equals("Lunch")).mapToLong(Map.Entry::getValue).sum();
        log.forEach((k,v) -> data.add(new TaskTableRow(LogParser.convertToJiraFormat(v), k)));
    }
}
