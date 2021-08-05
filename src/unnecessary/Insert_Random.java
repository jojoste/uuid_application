package unnecessary;

import unnecessary.ConnectDB;

import java.sql.*;

public class Insert_Random {
    public static void main(String[] args) {
        Connection connection = null;
        Statement statement = null;
        ConnectDB obj_ConnectDB = new ConnectDB();
        connection = obj_ConnectDB.ConnectDB();

        int counter = 0;

        try {
            long startTime = System.nanoTime();


            for (int i = 0; i < 10; i++) {
                String query = "insert into public.metaobject (uuid, \"name\", description, visualization, creation_time, modification_time) select uuid_generate_v4(), random() , null, null, now(), now()";
                statement = connection.createStatement();
                statement.executeUpdate(query);
                System.out.println(i);
                counter++;
            }

            long stopTime = System.nanoTime();
            long timeDifference = stopTime - startTime;
            System.out.println("All " + counter + " queries have been executed.\n" +
                    "It took " + timeDifference+ " nanoseconds.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*protected static String getRandomString() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 18) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;

    }*/

}


