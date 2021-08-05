package Model.Database.Truncate;

import Model.Database.Connection.ConnectionMongoDB;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class TruncateMongoDB {

    static MongoDatabase myMongoCon = ConnectionMongoDB.getMongodbCon();

    public static void TruncateMongoDB(){
        MongoCollection<Document> metaobjectCollection = myMongoCon.getCollection("metaobject");
        MongoCollection<Document> classCollection = myMongoCon.getCollection("class");
        MongoCollection<Document> roleCollection = myMongoCon.getCollection("role");
        MongoCollection<Document> scene_typeCollection = myMongoCon.getCollection("scene_type");
        MongoCollection<Document> relationclassCollection = myMongoCon.getCollection("relationclass");
        MongoCollection<Document> instance_objectCollection = myMongoCon.getCollection("instance_object");
        MongoCollection<Document> class_instanceCollection = myMongoCon.getCollection("class_instance");
        MongoCollection<Document> scene_instanceCollection = myMongoCon.getCollection("scene_instance");
        MongoCollection<Document> role_instanceCollection = myMongoCon.getCollection("role_instance");

        metaobjectCollection.drop();
        classCollection.drop();
        roleCollection.drop();
        scene_typeCollection.drop();
        relationclassCollection.drop();
        instance_objectCollection.drop();
        class_instanceCollection.drop();
        scene_instanceCollection.drop();
        role_instanceCollection.drop();

    }

}

