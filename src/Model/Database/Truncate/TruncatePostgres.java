package Model.Database.Truncate;

import Model.Database.Connection.ConnectionPostgres;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class TruncatePostgres {
    static Connection postgresCon = ConnectionPostgres.getPostgresCon();
    //Statement statement = null;

    public static void TruncatePostgresTable(String tablename) {
        try {
            postgresCon.setAutoCommit(false);
            Statement statement = null;
            statement = postgresCon.createStatement();
            statement.addBatch("TRUNCATE TABLE " + tablename + " CASCADE;");
            statement.executeBatch();
            postgresCon.commit();
            System.out.println(tablename + " has beend cleared.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
