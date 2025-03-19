package database;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnection {
    public static Connection getConnection() {
        try {
            Properties props = new Properties();
            FileInputStream fis = new FileInputStream("src/database/database.properties");
            props.load(fis);

            String url = props.getProperty("db.url");
            String user = props.getProperty("db.username");
            String password = props.getProperty("db.password");

            // Load JDBC Driver
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

            return DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException e) {
            System.out.println("❌ Không tìm thấy driver JDBC.");
            e.printStackTrace();
            throw new RuntimeException("Không tìm thấy driver JDBC", e);
        } catch (IOException e) {
            System.out.println("❌ Lỗi đọc file properties: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Lỗi đọc file properties", e);
        } catch (SQLException e) {
            System.out.println("❌ Lỗi kết nối database: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Lỗi kết nối database", e);
        }
    }
}