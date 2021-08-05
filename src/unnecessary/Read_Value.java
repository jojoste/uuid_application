package unnecessary;

import unnecessary.ConnectDB;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class Read_Value {

    public static void main(String[] args) {
        String queryType = "Select";

        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;

        ConnectDB obj_ConnectDB = new ConnectDB();
        connection = obj_ConnectDB.ConnectDB();

        try {
            String query = "select * from metaobject";
            statement = connection.createStatement();
            rs = statement.executeQuery(query);

            long startTime = System.nanoTime();

            while (rs.next()) {
                System.out.print(rs.getString("uuid"));
                System.out.print(" - ");
                System.out.print(rs.getString("name"));
                System.out.print(" - ");
                System.out.println(rs.getString("description"));
            }

            long stopTime = System.nanoTime();
            long timeDifference = stopTime - startTime;

            System.out.println("It took " + timeDifference + " nanoseconds to execute the " + queryType + " query.");


        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
