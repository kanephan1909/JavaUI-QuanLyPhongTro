package DAL;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnection {
    private static Connection connection = null;

    private static Properties loadProperties() throws IOException {
        Properties props = new Properties();
        try (FileInputStream fis = new FileInputStream("src/DAL/database.properties")) {
            props.load(fis);
        }
        return props;
    }

    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                Properties props = loadProperties();
                String url = props.getProperty("db.url");
                String user = props.getProperty("db.username");
                String password = props.getProperty("db.password");

                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(url, user, password);
            }
        } catch (ClassNotFoundException e) {
            System.out.println("❌ Không tìm thấy driver JDBC.");
            throw new RuntimeException(e);
        } catch (IOException e) {
            System.out.println("❌ Lỗi đọc file cấu hình.");
            throw new RuntimeException(e);
        } catch (SQLException e) {
            System.out.println("❌ Không thể kết nối database.");
            throw new RuntimeException(e);
        }

        return connection;
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
                System.out.println("✅ Đã đóng kết nối database!");
            } catch (SQLException e) {
                System.out.println("❌ Lỗi khi đóng kết nối database.");
                e.printStackTrace();
            }
        }
    }
}
