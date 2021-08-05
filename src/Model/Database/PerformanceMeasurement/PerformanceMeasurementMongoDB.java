package Model.Database.PerformanceMeasurement;

import Model.Database.Connection.ConnectionMongoDB;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Indexes;
import org.bson.BsonNull;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

import static Model.Database.AuxiliaryFunctions.randomString;
import static Model.Database.AuxiliaryFunctions.toStandardBinaryUUID;


public class PerformanceMeasurementMongoDB {
    static MongoDatabase myMongoCon = ConnectionMongoDB.getMongodbCon();

    private long totalTime = 0;
    private int insertCounter = 0;
    private int joinCounter = 0;
    private int joinAfterIndexCounter = 0;
    private int createIndexCounter = 0;
    private int dropIndexCounter = 0;
    private static int intervall = 100;
    private static int numberOfJoins = 10;


    private long[] myMongoCreation = new long[intervall];
    private long[] mongoDBJoinPerformance = new long[numberOfJoins];
    private long[] mongoDBIndexPerformance = new long[numberOfJoins];
    private long[] mongoDBJoinAfterIndexPerformance = new long[numberOfJoins];
    private long[] mongoDBDropIndexPerformance = new long[numberOfJoins];

    private Date date = new Date();
    private UUID myClassUUID = null;
    private UUID myRoleUUID = null;
    private UUID myRole2UUID = null;
    private UUID mySceneTypeUUID = null;
    private UUID myClassObject2UUID = null;
    private UUID myRoleObjectUUID = null;
    private UUID myObjectUUID = null;
    private UUID myObject2UUID = null;
    private UUID mySceneObjectUUID = null;
    private UUID mySceneObject2UUID = null;
    private UUID mySceneObject3UUID = null;


    MongoCollection<Document> metaobjectCollection = myMongoCon.getCollection("metaobject");
    MongoCollection<Document> classCollection = myMongoCon.getCollection("class");
    MongoCollection<Document> roleCollection = myMongoCon.getCollection("role");
    MongoCollection<Document> scene_typeCollection = myMongoCon.getCollection("scene_type");
    MongoCollection<Document> relationclassCollection = myMongoCon.getCollection("relationclass");
    MongoCollection<Document> instance_objectCollection = myMongoCon.getCollection("instance_object");
    MongoCollection<Document> class_instanceCollection = myMongoCon.getCollection("class_instance");
    MongoCollection<Document> scene_instanceCollection = myMongoCon.getCollection("scene_instance");
    MongoCollection<Document> role_instanceCollection = myMongoCon.getCollection("role_instance");


    public void performanceMeasurementMongoDB(int amountInserts) {

        long startTimeTotal = System.nanoTime();
        try {
            for (int i = 0; i < intervall / numberOfJoins; i++) {
                for (int j = 0; j < intervall / numberOfJoins; j++) {
                    createRandomInsertsMongoDB(amountInserts);
                }
                joinOperationMongoDB(mongoDBJoinPerformance, joinCounter);
                joinCounter++;
                createIndexMongoDB();
                joinOperationMongoDB(mongoDBJoinAfterIndexPerformance, joinAfterIndexCounter);
                joinAfterIndexCounter++;
                dropIndexesMongoDB();
            }
            for (int t = 0; t < myMongoCreation.length; t++) {
                int locT = t + 1;
                System.out.println("Time for " + locT + " insert :  " + myMongoCreation[t]);
            }
            for (int k = 0; k < mongoDBJoinPerformance.length; k++) {
                int locK = k + 1;
                System.out.println("Time for " + locK + " join : " + mongoDBJoinPerformance[k]);
            }
            for (int h = 0; h < mongoDBJoinAfterIndexPerformance.length; h++) {
                int locH = h + 1;
                System.out.println("Time for " + locH + " join after indexing : " + mongoDBJoinAfterIndexPerformance[h]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        long stopTimeTotal = System.nanoTime();
        totalTime = stopTimeTotal - startTimeTotal;
    }

    private void dropIndexesMongoDB() throws Exception {
        long startTime = System.nanoTime();
        //drops all indexes but the _id index
        metaobjectCollection.dropIndexes();
        classCollection.dropIndexes();
        roleCollection.dropIndexes();
        scene_typeCollection.dropIndexes();
        relationclassCollection.dropIndexes();
        instance_objectCollection.dropIndexes();
        class_instanceCollection.dropIndexes();
        scene_instanceCollection.dropIndexes();
        role_instanceCollection.dropIndexes();

        long stopTime = System.nanoTime();
        long timeDifference = stopTime - startTime;
        mongoDBDropIndexPerformance[dropIndexCounter] = timeDifference;

        dropIndexCounter++;
    }

    private void createIndexMongoDB() throws Exception {
        long startTime = System.nanoTime();

        metaobjectCollection.createIndex(Indexes.ascending("uuid"));
        classCollection.createIndex(Indexes.ascending("uuid_metaobject"));
        roleCollection.createIndex(Indexes.ascending("uuid_metaobject"));
        scene_typeCollection.createIndex(Indexes.ascending("uuid_metaobject"));
        relationclassCollection.createIndex(Indexes.ascending("uuid_class"));
        instance_objectCollection.createIndex(Indexes.ascending("uuid"));
        class_instanceCollection.createIndex(Indexes.ascending("uuid_instance_object", "uuid_class", "uuid_relationclass"));
        scene_instanceCollection.createIndex(Indexes.ascending("uuid_instance_object", "uuid_scene_type"));
        role_instanceCollection.createIndex(Indexes.ascending("uuid_instance_object", "uuid_role"));

        long stopTime = System.nanoTime();
        long timeDifference = stopTime - startTime;
        mongoDBIndexPerformance[createIndexCounter] = timeDifference;
        createIndexCounter++;
    }

    private void createRandomInsertsMongoDB(int amountInserts) throws Exception {

        long startTime = System.nanoTime();

        for (int i = 0; i < amountInserts / intervall; i++) {

            myClassUUID = UUID.randomUUID();
            myRoleUUID = UUID.randomUUID();
            myRole2UUID = UUID.randomUUID();
            mySceneTypeUUID = UUID.randomUUID();
            myClassObject2UUID = UUID.randomUUID();
            myRoleObjectUUID = UUID.randomUUID();
            myObjectUUID = UUID.randomUUID();
            myObject2UUID = UUID.randomUUID();
            mySceneObjectUUID = UUID.randomUUID();
            mySceneObject2UUID = UUID.randomUUID();
            mySceneObject3UUID = UUID.randomUUID();

            Document metaobjectInsert1 = new Document("_id", new ObjectId());
            metaobjectInsert1.append("uuid", toStandardBinaryUUID(myClassUUID))
                    .append("name", "Class_" + randomString())
                    .append("visualization", null)
                    .append("description", null)
                    .append("modification_time", new Timestamp(new Date().getTime()))
                    .append("creation_time", new Timestamp(new Date().getTime()));

            Document metaobjectInsert2 = new Document("_id", new ObjectId());
            metaobjectInsert2.append("uuid", toStandardBinaryUUID(myRoleUUID))
                    .append("name", "Role_" + randomString())
                    .append("visualization", null)
                    .append("description", null)
                    .append("modification_time", new Timestamp(date.getTime()))
                    .append("creation_time", new Timestamp(date.getTime()));

            Document metaobjectInsert3 = new Document("_id", new ObjectId());
            metaobjectInsert3.append("uuid", toStandardBinaryUUID(myRole2UUID))
                    .append("name", "Role_" + randomString())
                    .append("visualization", null)
                    .append("description", null)
                    .append("modification_time", new Timestamp(new Date().getTime()))
                    .append("creation_time", new Timestamp(new Date().getTime()));

            Document metaobjectInsert4 = new Document("_id", new ObjectId());
            metaobjectInsert4.append("uuid", toStandardBinaryUUID(mySceneTypeUUID))
                    .append("name", "Scene_Type_" + randomString())
                    .append("visualization", null)
                    .append("description", null)
                    .append("modification_time", new Timestamp(new Date().getTime()))
                    .append("creation_time", new Timestamp(new Date().getTime()));

            Document classInsert1 = new Document("_id", new ObjectId());
            classInsert1.append("uuid", toStandardBinaryUUID(myClassUUID))
                    .append("is_abstract", true)
                    .append("is_reusable", true);

            Document roleInsert1 = new Document("_id", new ObjectId());
            roleInsert1.append("uuid_metaobject", toStandardBinaryUUID(myRoleUUID));

            Document roleInsert2 = new Document("_id", new ObjectId());
            roleInsert2.append("uuid_metaobject", toStandardBinaryUUID(myRole2UUID));

            Document scene_typeInsert = new Document("_id", new ObjectId());
            scene_typeInsert.append("uuid_metaobject", toStandardBinaryUUID(mySceneTypeUUID));

            Document relationclassInsert = new Document("_id", new ObjectId());
            relationclassInsert.append("uuid_class", toStandardBinaryUUID(myClassUUID))
                    .append("role_from", toStandardBinaryUUID(myRoleUUID))
                    .append("role_to", toStandardBinaryUUID(myRole2UUID));

            Document instance_objectInsert1 = new Document("_id", new ObjectId());
            instance_objectInsert1.append("uuid", toStandardBinaryUUID(myObjectUUID))
                    .append("creation_time", new Timestamp(new Date().getTime()))
                    .append("modification_time", new Timestamp(new Date().getTime()));

            Document instance_objectInsert2 = new Document("_id", new ObjectId());
            instance_objectInsert2.append("uuid", toStandardBinaryUUID(myObject2UUID))
                    .append("creation_time", new Timestamp(new Date().getTime()))
                    .append("modification_time", new Timestamp(new Date().getTime()));


            Document instance_objectInsert3 = new Document("_id", new ObjectId());
            instance_objectInsert3.append("uuid", toStandardBinaryUUID(myRoleObjectUUID))
                    .append("creation_time", new Timestamp(new Date().getTime()))
                    .append("modification_time", new Timestamp(new Date().getTime()));


            Document instance_objectInsert4 = new Document("_id", new ObjectId());
            instance_objectInsert4.append("uuid", toStandardBinaryUUID(mySceneObjectUUID))
                    .append("creation_time", new Timestamp(new Date().getTime()))
                    .append("modification_time", new Timestamp(new Date().getTime()));


            Document instance_objectInsert5 = new Document("_id", new ObjectId());
            instance_objectInsert5.append("uuid", toStandardBinaryUUID(mySceneObject2UUID))
                    .append("creation_time", new Timestamp(new Date().getTime()))
                    .append("modification_time", new Timestamp(new Date().getTime()));


            Document instance_objectInsert6 = new Document("_id", new ObjectId());
            instance_objectInsert6.append("uuid", toStandardBinaryUUID(mySceneObject3UUID))
                    .append("creation_time", new Timestamp(new Date().getTime()))
                    .append("modification_time", new Timestamp(new Date().getTime()));

            Document class_instanceInsert1 = new Document("_id", new ObjectId());
            class_instanceInsert1.append("uuid_instance_object", toStandardBinaryUUID(myObjectUUID))
                    .append("uuid_class", toStandardBinaryUUID(myClassUUID))
                    .append("uuid_relationclass", toStandardBinaryUUID(myClassUUID));

            Document class_instanceInsert2 = new Document("_id", new ObjectId());
            class_instanceInsert2.append("uuid_instance_object", toStandardBinaryUUID(myObject2UUID))
                    .append("uuid_class", toStandardBinaryUUID(myClassUUID));

            Document role_instanceInsert1 = new Document("id", new ObjectId());
            role_instanceInsert1.append("uuid_instance_object", toStandardBinaryUUID(myRoleObjectUUID))
                    .append("uuid_role", toStandardBinaryUUID(myRoleUUID));

            Document scene_instanceInsert1 = new Document("_id", new ObjectId());
            scene_instanceInsert1.append("uuid_scene_type", toStandardBinaryUUID(mySceneTypeUUID))
                    .append("uuid_instance_object", toStandardBinaryUUID(mySceneObjectUUID));

            Document scene_instanceInsert2 = new Document("_id", new ObjectId());
            scene_instanceInsert2.append("uuid_scene_type", toStandardBinaryUUID(mySceneTypeUUID))
                    .append("uuid_instance_object", toStandardBinaryUUID(mySceneObject2UUID));

            Document scene_instanceInsert3 = new Document("_id", new ObjectId());
            scene_instanceInsert3.append("uuid_scene_type", toStandardBinaryUUID(mySceneTypeUUID))
                    .append("uuid_instance_object", toStandardBinaryUUID(mySceneObject3UUID));


            metaobjectCollection.insertOne(metaobjectInsert1);
            metaobjectCollection.insertOne(metaobjectInsert2);
            metaobjectCollection.insertOne(metaobjectInsert3);
            metaobjectCollection.insertOne(metaobjectInsert4);
            classCollection.insertOne(classInsert1);
            roleCollection.insertOne(roleInsert1);
            roleCollection.insertOne(roleInsert2);
            scene_typeCollection.insertOne(scene_typeInsert);
            relationclassCollection.insertOne(relationclassInsert);
            instance_objectCollection.insertOne(instance_objectInsert1);
            instance_objectCollection.insertOne(instance_objectInsert2);
            instance_objectCollection.insertOne(instance_objectInsert3);
            instance_objectCollection.insertOne(instance_objectInsert4);
            instance_objectCollection.insertOne(instance_objectInsert5);
            instance_objectCollection.insertOne(instance_objectInsert6);
            class_instanceCollection.insertOne(class_instanceInsert1);
            class_instanceCollection.insertOne(class_instanceInsert2);
            role_instanceCollection.insertOne(role_instanceInsert1);
            scene_instanceCollection.insertOne(scene_instanceInsert1);
            scene_instanceCollection.insertOne(scene_instanceInsert2);
            scene_instanceCollection.insertOne(scene_instanceInsert3);


            System.out.println(i + 1 + " has been created.");
        }
        long stopTime = System.nanoTime();
        long timeDifference = stopTime - startTime;
        myMongoCreation[insertCounter] = timeDifference;
        insertCounter++;

    }


    private void joinOperationMongoDB(long[] toSaveArray, int counter) throws Exception {
        long startTime = System.nanoTime();


        List<? extends Bson> join1 = Arrays.asList(
                new Document()
                        .append("$project", new Document()
                                .append("_id", 0)
                                .append("m", "$$ROOT")
                        ),
                new Document()
                        .append("$lookup", new Document()
                                .append("localField", "m.uuid")
                                .append("from", "instance_object")
                                .append("foreignField", "uuid")
                                .append("as", "io")
                        ),
                new Document()
                        .append("$unwind", new Document()
                                .append("path", "$io")
                                .append("preserveNullAndEmptyArrays", false)
                        ));

        metaobjectCollection.aggregate(join1)
                .allowDiskUse(true)
                .forEach(processBlock);

        List<? extends Bson> join2 = Arrays.asList(
                new Document()
                        .append("$project", new Document()
                                .append("_id", 0)
                                .append("m", "$$ROOT")
                        ),
                new Document()
                        .append("$lookup", new Document()
                                .append("localField", "m.uuid")
                                .append("from", "class")
                                .append("foreignField", "uuid_metaobject")
                                .append("as", "c")
                        ),
                new Document()
                        .append("$unwind", new Document()
                                .append("path", "$c")
                                .append("preserveNullAndEmptyArrays", false)
                        )
        );

        metaobjectCollection.aggregate(join2)
                .allowDiskUse(true)
                .forEach(processBlock);

        List<? extends Bson> join3 = Arrays.asList(
                new Document()
                        .append("$project", new Document()
                                .append("_id", 0)
                                .append("r", "$$ROOT")
                        ),
                new Document()
                        .append("$lookup", new Document()
                                .append("localField", "r.uuid_metaobject")
                                .append("from", "metaobject")
                                .append("foreignField", "uuid")
                                .append("as", "m")
                        ),
                new Document()
                        .append("$unwind", new Document()
                                .append("path", "$m")
                                .append("preserveNullAndEmptyArrays", true)
                        )
        );

        roleCollection.aggregate(join3)
                .allowDiskUse(true)
                .forEach(processBlock);


        List<? extends Bson> join4 = Arrays.asList(
                new Document()
                        .append("$project", new Document()
                                .append("_id", 0)
                                .append("st", "$$ROOT")
                        ),
                new Document()
                        .append("$lookup", new Document()
                                .append("localField", "st.uuid_metaobject")
                                .append("from", "metaobject")
                                .append("foreignField", "uuid")
                                .append("as", "m")
                        ),
                new Document()
                        .append("$unwind", new Document()
                                .append("path", "$m")
                                .append("preserveNullAndEmptyArrays", true)
                        ),
                new Document()
                        .append("$match", new Document()
                                .append("m.uuid", new BsonNull())
                        )
        );

        scene_typeCollection.aggregate(join4)
                .allowDiskUse(true)
                .forEach(processBlock);


        List<? extends Bson> join5 = Arrays.asList(
                new Document()
                        .append("$project", new Document()
                                .append("_id", 0)
                                .append("io", "$$ROOT")
                        ),
                new Document()
                        .append("$lookup", new Document()
                                .append("localField", "io.uuid")
                                .append("from", "scene_instance")
                                .append("foreignField", "uuid_instance_object")
                                .append("as", "si")
                        ),
                new Document()
                        .append("$unwind", new Document()
                                .append("path", "$si")
                                .append("preserveNullAndEmptyArrays", true)
                        )
        );

        instance_objectCollection.aggregate(join5)
                //.allowDiskUse(true)

                .forEach(processBlock);

        List<? extends Bson> join6 = Arrays.asList(
                new Document()
                        .append("$project", new Document()
                                .append("_id", 0)
                                .append("io", "$$ROOT")
                        ),
                new Document()
                        .append("$lookup", new Document()
                                .append("localField", "io.uuid")
                                .append("from", "class_instance")
                                .append("foreignField", "uuid_class")
                                .append("as", "ci")
                        ),
                new Document()
                        .append("$unwind", new Document()
                                .append("path", "$ci")
                                .append("preserveNullAndEmptyArrays", true)
                        ),
                new Document()
                        .append("$match", new Document()
                                .append("ci.uuid_class", new BsonNull())
                        )
        );

        instance_objectCollection.aggregate(join6)
                //.allowDiskUse(true)

                .forEach(processBlock);

        long stopTime = System.nanoTime();
        long timeDifference = stopTime - startTime;
        toSaveArray[counter] = timeDifference;
        System.out.println("Join Test " + counter + " has been done. ");


    }

    Consumer<Document> processBlock = new Consumer<Document>() {
        @Override
        public void accept(Document document) {
            System.out.println(document);
        }
    };

    public void setAllCountersToZero() {
        this.createIndexCounter = 0;
        this.dropIndexCounter = 0;
        this.insertCounter = 0;
        this.joinAfterIndexCounter = 0;
        this.joinCounter = 0;
    }

    public long[] getMyMongoCreation() {
        return myMongoCreation;
    }

    public long[] getMongoDBJoinPerformance() {
        return mongoDBJoinPerformance;
    }

    public long[] getMongoDBIndexPerformance() {
        return mongoDBIndexPerformance;
    }

    public long[] getMongoDBJoinAfterIndexPerformance() {
        return mongoDBJoinAfterIndexPerformance;
    }

    public long[] getMongoDBDropIndexPerformance() {
        return mongoDBDropIndexPerformance;
    }

    public long getTotalTime() {
        return totalTime;
    }
}
