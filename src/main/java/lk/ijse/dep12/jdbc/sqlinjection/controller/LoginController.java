package lk.ijse.dep12.jdbc.sqlinjection.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {
    public Button btnCancel;

    public Button btnLogin;

    public TextField txtUserName;

    public void btnCancelOnAction(ActionEvent event) {
        Platform.exit();
    }

    public void btnLoginOnAction(ActionEvent event) throws IOException {
        // Business logic
        String userName = txtUserName.getText().strip();
        if (userName.isBlank()) {
            txtUserName.requestFocus();
            txtUserName.selectAll();
            return;
        }

        System.setProperty("app.principle.userName", userName);
        Stage stage = new Stage();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/LoginPasswordView.fxml"))));
        stage.setTitle("LOGIN");
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.show();
        ((Stage) (btnLogin.getScene().getWindow())).close();

    }
}
