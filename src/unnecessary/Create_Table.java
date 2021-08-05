package unnecessary;

import unnecessary.ConnectDB;

import java.sql.Connection;
import java.sql.Statement;

public class Create_Table {

    public static void main(String[] args) {

        Connection connection = null;
        Statement statement = null;

        ConnectDB obj_ConnectDB = new ConnectDB();
        connection = obj_ConnectDB.ConnectDB();

        try {

            String query = "";
            statement = connection.createStatement();
            statement.executeUpdate(query);
            System.out.println("finished");


        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
