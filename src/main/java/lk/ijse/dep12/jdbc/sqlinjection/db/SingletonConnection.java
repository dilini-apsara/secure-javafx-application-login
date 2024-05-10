package lk.ijse.dep12.jdbc.sqlinjection.db;

import javafx.scene.control.Alert;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class SingletonConnection {

    private final static SingletonConnection INSTANCE=new SingletonConnection();
    private static Connection CONNECTION;

    private SingletonConnection() {
        Properties properties = new Properties();
        try {
            properties.load(getClass().getResourceAsStream("/Application.properties"));
            String url = properties.getProperty("app.db.url");
            String user =properties.getProperty("app.db.username");
            String pwd =properties.getProperty("app.db.password");
            CONNECTION=DriverManager.getConnection(url,user,pwd);
        } catch (IOException | SQLException e) {
           e.printStackTrace();
           new Alert(Alert.AlertType.ERROR, "Connection failed, try again").showAndWait();
           System.exit(1);
        }
    }

   public static SingletonConnection getInstance(){
        return INSTANCE;
   }
   public Connection getConnection(){
        return CONNECTION;
   }




}
