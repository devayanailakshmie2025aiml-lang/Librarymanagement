
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String URL = setting("DB_URL",
            "jdbc:mysql://localhost:3306/book_management_db");
    private static final String USERNAME = setting("DB_USERNAME", null);
    private static final String PASSWORD = setting("DB_PASSWORD", null);

    private static String setting(String name, String defaultValue) {
        String value = System.getenv(name);
        return value == null || value.isBlank() ? defaultValue : value;
    }

    public static Connection getConnection() throws SQLException {

        if (USERNAME == null || PASSWORD == null) {
            throw new SQLException("Set DB_USERNAME and DB_PASSWORD environment variables before connecting.");
        }

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQL JDBC Driver not found!", e);
        }

        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }
}
