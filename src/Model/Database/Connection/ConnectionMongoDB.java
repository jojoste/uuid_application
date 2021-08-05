package Model.Database.Connection;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;


public class ConnectionMongoDB {
    private static MongoDatabase mongodbCon = null;

    static {
        MongoClient myMongoClient = MongoClients.create();
        mongodbCon = myMongoClient.getDatabase("public");
    }

    public static MongoDatabase getMongodbCon() {
        return mongodbCon;
    }

    public static boolean connectionMongoDBReady() {
        if (mongodbCon == null) {
            return false;
        } else {
            return true;
        }
    }

}
