package pkg.vms.DBconnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBconnection {

    private static final String URL = "jdbc:postgresql://postgresql-yesh.alwaysdata.net:5432/yesh_julien_vms";
    private static final String USER = "yesh_vamos";
    private static final String PASS = "iTXzXa@hsXmYZ8x";
    private static Connection conn;

    // Open a fresh connection each time
    public static Connection getConnection() {
        Connection conn = null;
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