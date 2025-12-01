package pkg.vms.DBconnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBconnection {

    private static final String URL = "jdbc:postgresql://postgresql-yesh.alwaysdata.net:5432/yesh_julien_vms";
    private static final String USER = "yesh_vamos";
    private static final String PASS = "iTXzXa@hsXmYZ8x";
    private static Connection conn;

    // Open a connection (only once)
    public static Connection getConnection() {

        if (conn != null) {
            return conn;
        }

        try {
            conn = DriverManager.getConnection(URL, USER, PASS);
            System.out.println("Connected to PostgreSQL database.");
        } catch (SQLException e) {
            System.out.println("Database connection failed!");
            e.printStackTrace();
        }
        return conn;
    }
}
