import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseChecker {
    public static void main(String[] args) throws Exception {
        String url = "jdbc:mysql://localhost:3306/jobportal?useSSL=false&serverTimezone=UTC";
        String user = "Harsh";
        String pass = "Harsh@2003";

        try (Connection conn = DriverManager.getConnection(url, user, pass)) {
            System.out.println(conn != null ? "✅ DB Connected" : "❌ DB Failed");
        }
    }
}
