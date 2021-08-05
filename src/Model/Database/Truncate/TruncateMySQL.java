package Model.Database.Truncate;

import Model.Database.Connection.ConnectionMySQL;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class TruncateMySQL {
    static Connection mySQLcon = ConnectionMySQL.getMysqlCon();
    //Statement statement = null;

    public static void TruncateMySQLTable(String tablename) {
        try {
            mySQLcon.setAutoCommit(false);
            Statement statement = null;
            statement = mySQLcon.createStatement();
            statement.addBatch("SET FOREIGN_KEY_CHECKS=0;");
            statement.addBatch("TRUNCATE TABLE " + tablename+";");
            statement.addBatch("SET FOREIGN_KEY_CHECKS =1;");
            statement.executeBatch();
            mySQLcon.commit();
            System.out.println(tablename + " has been cleared. ");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
