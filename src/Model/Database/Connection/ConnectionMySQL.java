package Model.Database.Connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionMySQL {
    private static Connection mysqlCon = null;

    static {
        String host = "localhost";
        String port = "3306";
        String db_name = "test";
        String username = "root";
        String password = "GelbeBlume21";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            mysqlCon = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + db_name, username, password);

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static Connection getMysqlCon() {
        return mysqlCon;
    }

    public static boolean connectionMySQLReady (){
        if(mysqlCon == null){
            return false;
        }else{
            return true;
        }
    }

}
