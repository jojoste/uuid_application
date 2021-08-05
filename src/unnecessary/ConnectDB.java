package unnecessary;

import java.sql.Connection;
import java.sql.DriverManager;


public class ConnectDB {

    public static void main(String[] args) {
        ConnectDB obj_ConnectDB = new ConnectDB();
        System.out.println(obj_ConnectDB.ConnectDB());
    }

    /*public unnecessary.ConnectDB() {
        unnecessary.ConnectDB obj_ConnectDB = new unnecessary.ConnectDB();
        System.out.println(obj_ConnectDB.unnecessary.ConnectDB());
    }*/

    public Connection ConnectDB() {
        Connection connection = null; //initiate
        String host = "localhost";
        String port = "5432";
        String db_name = "postgres";
        String username = "";
        String password = "";

        try {
            Class.forName("org.postgresql.Driver"); //will load the driver from the jar file
            connection = DriverManager.getConnection("jdbc:postgresql://"+host+":"+port+"/"+db_name);

            if (connection != null) {
                System.out.println("Connection OK");
            } else {
                System.out.println("Connection failed");
            }


        } catch (Exception e) {
            System.out.println(e);
        }

        return connection;


    }
}
