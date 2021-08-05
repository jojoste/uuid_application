package Model.Database.Connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionPostgres {
    private static Connection postgresCon = null;

    static {
        String host = "localhost";
        String port = "5432";
        String db_name = "postgres";
        String username = "";
        String password = "";

        try {
            Class.forName("org.postgresql.Driver");
            postgresCon = DriverManager.getConnection("jdbc:postgresql://" + host + ":" + port + "/" + db_name);

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static Connection getPostgresCon() {
        return postgresCon;
    }

    public static boolean connectionPostgresReady() {
        if (postgresCon == null) {
            return false;
        } else {
            return true;
        }
    }
}
