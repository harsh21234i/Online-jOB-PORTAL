import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseChecker {

    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/jobportal";
        String username ="root" ;
        String password = "Harsh@2003";

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            if (connection != null) {
                System.out.println("Database is up and running.");
            }
        } catch (SQLException e) {
            System.out.println("Database is not reachable. Error: " + e.getMessage());
        }
    }
}
