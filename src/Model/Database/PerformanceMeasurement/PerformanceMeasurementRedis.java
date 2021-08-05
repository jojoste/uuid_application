package Model.Database.PerformanceMeasurement;

import Model.Database.Connection.ConnectionRedis;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;

import java.sql.Timestamp;
import java.util.*;

import static Model.Database.AuxiliaryFunctions.randomString;

public class PerformanceMeasurementRedis {

    static StatefulRedisConnection myRedisCon = ConnectionRedis.getRedisCon();


    private long totalTime = 0;
    private int insertCounter = 0;
    private int joinCounter = 0;
    private int joinAfterIndexCounter = 0;
    private int createIndexCounter = 0;
    private int dropIndexCounter = 0;
    private static int intervall = 100;
    private static int numberOfJoins = 10;

    private long[] myRedisCreation = new long[intervall];
    private long[] RedisJoinPerformance = new long[numberOfJoins];
    private long[] RedisIndexPerformance = new long[numberOfJoins];
    private long[] RedisJoinAfterIndexPerformance = new long[numberOfJoins];
    private long[] RedisDropIndexPerformance = new long[numberOfJoins];

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

    RedisCommands<String, String> myCommands = myRedisCon.sync();


    public void performanceMeasurementRedis(int amountInserts) throws Exception {
        long startTimeTotal = System.nanoTime();
        try {
            for (int i = 0; i < intervall / numberOfJoins; i++) {
                for (int j = 0; j < intervall / numberOfJoins; j++) {
                    createRandomInsertsRedis(amountInserts);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        for (int t = 0; t < myRedisCreation.length; t++) {
            int locT = t + 1;
            System.out.println("Time for " + locT + " insert :  " + myRedisCreation[t]);
        }
        long stopTimeTotal = System.nanoTime();
        totalTime = stopTimeTotal - startTimeTotal;
    }


    private void createRandomInsertsRedis(int amountInserts) throws Exception {
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


            //metaobject 1
            myCommands.lpush(myClassUUID.toString(), "metaobject");
            myCommands.lpush(myClassUUID.toString(), "Class_" + randomString());
            myCommands.lpush(myClassUUID.toString(), new Timestamp(new Date().getTime()).toString());
            myCommands.lpush(myClassUUID.toString(), new Timestamp(new Date().getTime()).toString());

            //metaobject 2
            myCommands.lpush(myRoleUUID.toString(), "metaobject");
            myCommands.lpush(myRoleUUID.toString(), "Role_" + randomString());
            myCommands.lpush(myRoleUUID.toString(), new Timestamp(new Date().getTime()).toString());
            myCommands.lpush(myRoleUUID.toString(), new Timestamp(new Date().getTime()).toString());

            //metaobject 3
            myCommands.lpush(myRole2UUID.toString(), "metaobject");
            myCommands.lpush(myRole2UUID.toString(), "Role_" + randomString());
            myCommands.lpush(myRole2UUID.toString(), new Timestamp(new Date().getTime()).toString());
            myCommands.lpush(myRole2UUID.toString(), new Timestamp(new Date().getTime()).toString());

            //metaobject4
            myCommands.lpush(mySceneTypeUUID.toString(), "metaobject");
            myCommands.lpush(mySceneTypeUUID.toString(), "Scene_Type_" + randomString());
            myCommands.lpush(mySceneTypeUUID.toString(), new Timestamp(new Date().getTime()).toString());
            myCommands.lpush(mySceneTypeUUID.toString(), new Timestamp(new Date().getTime()).toString());

            //class
            myCommands.lpush(myClassUUID.toString(), "class");
            myCommands.lpush(myClassUUID.toString(), "true");
            myCommands.lpush(myClassUUID.toString(), "true");

            //role1
            myCommands.lpush(myRoleUUID.toString(), "role");

            //role2


            System.out.println(i + 1 + " has been created");


        }
        long stopTime = System.nanoTime();
        long timeDifference = stopTime - startTime;
        myRedisCreation[insertCounter] = timeDifference;
        insertCounter++;

    }

    private void createIndexRedis() throws Exception {
        long startTime = System.nanoTime();


        long stopTime = System.nanoTime();
        long timeDifference = stopTime - startTime;
        RedisIndexPerformance[createIndexCounter] = timeDifference;
        createIndexCounter++;

    }

    public void setAllCountersToZero() {
        this.createIndexCounter = 0;
        this.dropIndexCounter = 0;
        this.insertCounter = 0;
        this.joinAfterIndexCounter = 0;
        this.joinCounter = 0;
    }

    public long[] getRedisJoinPerformance() {
        return RedisJoinPerformance;
    }

    public long[] getRedisIndexPerformance() {
        return RedisIndexPerformance;
    }

    public long[] getMyRedisCreation() {
        return myRedisCreation;
    }

    public long[] getRedisJoinAfterIndexPerformance() {
        return RedisJoinAfterIndexPerformance;
    }

    public long[] getRedisDropIndexPerformance() {
        return RedisDropIndexPerformance;
    }

    public long getTotalTime() {
        return totalTime;
    }
}

