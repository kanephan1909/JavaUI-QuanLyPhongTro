package DTO;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnection {
    private static Connection connection = null;

    // Phương thức lấy thông tin từ file properties
    private static Properties loadProperties() throws IOException {
        Properties props = new Properties();
        try (FileInputStream fis = new FileInputStream("src/database/database.properties")) {
            props.load(fis);
        }
        return props;
    }

    // Phương thức lấy kết nối duy nhất (Singleton)
    public static Connection getConnection() {
        if (connection == null) {
            synchronized (DatabaseConnection.class) {
                if (connection == null) {
                    try {
                        Properties props = loadProperties();

                        String url = props.getProperty("db.url");
                        String user = props.getProperty("db.username");
                        String password = props.getProperty("db.password");

                        // Load JDBC Driver
                        Class.forName("com.mysql.cj.jdbc.Driver");

                        connection = DriverManager.getConnection(url, user, password);
                        System.out.println("✅ Kết nối database thành công!");

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
        }
        return connection;
    }

    // Đóng kết nối khi không cần nữa
    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
                System.out.println("✅ Đóng kết nối database thành công!");
            } catch (SQLException e) {
                System.out.println("❌ Lỗi khi đóng kết nối database: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
