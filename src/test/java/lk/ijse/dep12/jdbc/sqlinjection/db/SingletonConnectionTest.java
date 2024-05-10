package lk.ijse.dep12.jdbc.sqlinjection.db;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

class SingletonConnectionTest {

    @org.junit.jupiter.api.Test
    void getInstance() {
        SingletonConnection instance1 = SingletonConnection.getInstance();
        SingletonConnection instance2 = SingletonConnection.getInstance();


        assertEquals(instance1,instance2);
        System.out.println(instance1);
        System.out.println(instance2);
    }

    @org.junit.jupiter.api.Test
    void getConnection() {
        Connection connection1 = SingletonConnection.getInstance().getConnection();
        Connection connection2 = SingletonConnection.getInstance().getConnection();
        assertEquals(connection1,connection2);
        System.out.println(connection1+ " "+connection2);
    }
}