package lk.ijse.dep12.jdbc.sqlinjection.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lk.ijse.dep12.jdbc.sqlinjection.db.SingletonConnection;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class LoginRegistrationController {
    public Button btnCancel;

    public Button btnRegister;

    public PasswordField txtConfirmPassword;

    public PasswordField txtPswd;

    public TextField txtUsername;

    public TextField txtFullName;
    public Button btnSignUp;

    public void btnCancelOnAction(ActionEvent event) {
        Platform.exit();
    }

    public void btnRegisterOnAction(ActionEvent event) throws IOException {
        TextField textField = isValidation();
        if (textField != null) {
            textField.requestFocus();
            textField.selectAll();
            new Alert(Alert.AlertType.ERROR, "Registration Failed").showAndWait();
            return;
        }
// store data in data base
        Connection connection = SingletonConnection.getInstance().getConnection();
        boolean isRegistered = false;
        while (!isRegistered) {
            try {
                PreparedStatement pstm = connection.prepareStatement("""
                                              INSERT INTO users (full_name,user_name,password)
                                                  VALUES (?,?,?);
                              
                        """);
                String fullName = txtFullName.getText().
                        strip();
                String userName =
                        txtUsername.getText().strip();
                String password = DigestUtils.
                        sha256Hex(txtPswd.getText().strip
                                ());
                pstm.
                        setString(1, fullName);
                pstm.setString(2, userName);
                pstm.setString(3, password)
                ;
                pstm.executeUpdate();

            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "Registration Faild").showAndWait();
                txtUsername.requestFocus();
                txtUsername.selectAll();
                return;
            }
            isRegistered = true;
        }
        new Alert(Alert.AlertType.CONFIRMATION, "Successfully Registered. ").showAndWait();

// Load the login user window
        loadLoginStage();

    }

    private TextField isValidation() {
        // business logic
        String fullName = txtFullName.getText();
        String userName = txtUsername.getText();
        String password = txtPswd.getText();
        String confirmPwd = txtConfirmPassword.getText();

        if (fullName.isBlank() || !fullName.strip().contains(" ") || fullName.strip().length() < 3 || fullName.matches(".*\\d.*")) {
            return txtFullName;
        }
        if (userName.isBlank() || userName.strip().length() < 3) {
            return txtUsername;
        }
        if (password.isBlank() || password.strip().length() < 3 || !password.matches(".*\\d.*")) {
            return txtPswd;
        }
        if (!password.strip().equals(confirmPwd.strip())) {
            return txtConfirmPassword;
        }

        return null;
    }

    private void loadLoginStage() throws IOException {
        Stage stage = new Stage();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/LoginUserView.fxml"))));
        stage.setTitle("LOGIN");
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.show();
        ((Stage) (btnRegister.getScene().getWindow())).close();
    }


    public void btnSignUpOnaction(ActionEvent actionEvent) throws IOException {
        loadLoginStage();
    }
}
