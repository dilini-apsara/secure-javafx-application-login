package lk.ijse.dep12.jdbc.sqlinjection.controller;

import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DashBoardController {
    public Button btnLogOut;
    public Label lblFullName;
    public Label lblDateTime;

    public void initialize() {
        String fullName = System.getProperty("app.principle.fullName");
        lblFullName.setText(fullName);

        Task<String> dateTimeTask = new Task<>() {

            @Override
            protected String call() throws Exception {
                while (true) {
                    updateValue(LocalDateTime.now()
                            .format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss")));
                    Thread.sleep(1000);
                }
            }
        };

        lblDateTime.textProperty().bind(dateTimeTask.valueProperty());
        new Thread(dateTimeTask).start();
    }

    public void btnLogOutOnAction(ActionEvent event) {
        System.exit(0);
    }

}
