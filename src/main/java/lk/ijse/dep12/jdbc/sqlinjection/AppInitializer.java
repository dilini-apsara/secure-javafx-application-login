package lk.ijse.dep12.jdbc.sqlinjection;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lk.ijse.dep12.jdbc.sqlinjection.db.SingletonConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class AppInitializer extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {

        Connection connection = SingletonConnection.getInstance().getConnection();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                if (connection != null && !connection.isClosed()) connection.close();
            } catch (SQLException e) {
                System.out.println("connection");
                throw new RuntimeException(e);
            }
        }));

        primaryStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/RegistrationView.fxml"))));
        primaryStage.setTitle("REGISTRATION WINDOW");
        primaryStage.setResizable(false);
        primaryStage.show();
        primaryStage.centerOnScreen();


    }
}
