package lk.ijse.dep12.jdbc.sqlinjection.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import lk.ijse.dep12.jdbc.sqlinjection.db.SingletonConnection;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginPasswordController {

    public Button btnCancel;

    public Button btnLogin;

    public PasswordField txtpwd;

    public void btnCancelOnAction(ActionEvent event) {
        System.exit(1);

    }

    public void btnLoginOnAction(ActionEvent event) {
        if (txtpwd.getText().isEmpty()) {
            txtpwd.requestFocus();
            txtpwd.selectAll();
            new Alert(Alert.AlertType.ERROR, "Please enter password").showAndWait();
            return;
        }

        String userName = System.getProperty("app.principle.userName");
        Connection connection = SingletonConnection.getInstance().getConnection();
        try {
            PreparedStatement pstm = connection.prepareStatement("""
                        SELECT * FROM users WHERE user_name=?
                    """);
            pstm.setString(1, userName);
            ResultSet rst = pstm.executeQuery();
            rst.next();
            String password = rst.getString("password");
            String fullName = rst.getString("full_name");


            if (!password.equals(DigestUtils.sha256Hex(txtpwd.getText().strip()))) {
                new Alert(Alert.AlertType.ERROR, "Invalid user name or password!").showAndWait();
                return;
            }
            System.setProperty("app.principle.fullName", fullName);
            loadNewStage();
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Something went wrong, try again !").showAndWait();
            return;
        }

    }

    private void loadNewStage() {
        Stage stage = new Stage();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/DashBoard.fxml"))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage.setTitle("DASH BOARD WIDOW");
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.show();
        ((Stage) (btnLogin.getScene().getWindow())).close();
    }
}
