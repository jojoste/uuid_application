package Model.Database.PerformanceMeasurement;

import Model.Database.Connection.ConnectionMySQL;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.UUID;

import static Model.Database.AuxiliaryFunctions.randomString;

public class PerformanceMeasurementMySQL {
    static Connection mySQLcon = ConnectionMySQL.getMysqlCon();
    Statement statement = null;

    private long totalTime = 0;
    private int insertCounter = 0;
    private int joinCounter = 0;
    private int joinAfterIndexCounter = 0;
    private int createIndexCounter = 0;
    private int dropIndexCounter = 0;
    private static int intervall = 100;
    private static int numberOfJoins = 10;


    private long[] mySQLCreation = new long[intervall];
    private long[] mySQLJoinPerformance = new long[numberOfJoins];
    private long[] mySQLIndexPerformance = new long[numberOfJoins];
    private long[] mySQLJoinAfterIndexPerformance = new long[numberOfJoins];
    private long[] mySQLDropIndexPerformance = new long[numberOfJoins];

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

    public void performanceMeasurementMySQL(int amountInserts) {
        long startTimeTotal = System.nanoTime();
        try {
            for (int i = 0; i < intervall / numberOfJoins; i++) {
                for (int j = 0; j < intervall / numberOfJoins; j++) {
                    createRandomInsertsMySQL(amountInserts);
                }
                joinOperationsMySQL(mySQLJoinPerformance, joinCounter);
                joinCounter++;
                createIndexMySQL();
                joinOperationsMySQL(mySQLJoinAfterIndexPerformance, joinAfterIndexCounter);
                joinAfterIndexCounter++;
                dropIndexesMySQL();
            }
            for (int t = 0; t < mySQLCreation.length; t++) {
                int tloc = t + 1;
                System.out.println("Time for " + tloc + " insert :  " + mySQLCreation[t]);
            }
            for (int k = 0; k < mySQLJoinPerformance.length; k++) {
                int kloc = k + 1;
                System.out.println("Time for " + kloc + " join : " + mySQLJoinPerformance[k]);
            }
            for (int h = 0; h < mySQLJoinAfterIndexPerformance.length; h++) {
                int locH = h + 1;
                System.out.println("Time for " + locH + " join after indexing : " + mySQLJoinAfterIndexPerformance[h]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        long stopTimeTotal = System.nanoTime();
        totalTime = stopTimeTotal - startTimeTotal;
    }

    private void dropIndexesMySQL() throws Exception {
        mySQLcon.setAutoCommit(false);
        long startTime = System.nanoTime();
        statement = mySQLcon.createStatement();
        statement.addBatch("drop index  metaobject_performance_idx on test.metaobject ;");
        statement.addBatch("drop index   class_performance_idx on test.class;");
        statement.addBatch("drop index   role_performance_idx on test.role;");
        statement.addBatch("drop index   scene_type_performance_idx on test.scene_type ;");
        statement.addBatch("drop index   relationclass_performance_idx on test.relationclass ;");
        statement.addBatch("drop index   instance_object_performance_idx on test.instance_object ;");
        statement.addBatch("drop index   class_instance1_performance_idx on test.class_instance ;");
        statement.addBatch("-- drop index   class_instance2_performance_idx on test.class_instance ;");
        statement.addBatch("drop index   class_instance3_performance_idx on test.class_instance ;");
        statement.addBatch("drop index    scene_instance1_performance_idx on test.scene_instance ;");
        statement.addBatch("-- drop index   scene_instance2_performance_idx on test.scene_instance ;");
        statement.addBatch("drop index   role_instance_performance_idx on test.role_instance ;");

        statement.executeBatch();
        mySQLcon.commit();
        long stopTime = System.nanoTime();
        long timeDifference = stopTime - startTime;
        mySQLDropIndexPerformance[dropIndexCounter] = timeDifference;

        dropIndexCounter++;

    }

    private void createIndexMySQL() throws Exception {
        mySQLcon.setAutoCommit(false);
        long startTime = System.nanoTime();
        statement = mySQLcon.createStatement();
        statement.addBatch("create index  metaobject_performance_idx on test.metaobject(uuid)  ;");
        statement.addBatch("create index class_performance_idx on test.class(uuid_metaobject)  ;");
        statement.addBatch("create index role_performance_idx on test.role(uuid_metaobject)  ;");
        statement.addBatch("create index scene_type_performance_idx on test.scene_type (uuid_metaobject)  ;");
        statement.addBatch("create index relationclass_performance_idx on test.relationclass (uuid_class)  ;");
        statement.addBatch("create index instance_object_performance_idx on test.instance_object (uuid)  ;");
        statement.addBatch("create index class_instance1_performance_idx on test.class_instance (uuid_instance_object)  ;");
        statement.addBatch("-- create index class_instance2_performance_idx on test.class_instance (uuid_class)  ;");
        statement.addBatch("create index class_instance3_performance_idx on test.class_instance (uuid_relationclass)  ;");
        statement.addBatch("create index scene_instance1_performance_idx on test.scene_instance (uuid_instance_object)  ;");
        statement.addBatch("-- create index scene_instance2_performance_idx on test.scene_instance (uuid_scene_type)  ;");
        statement.addBatch("create index role_instance_performance_idx on test.role_instance (uuid_instance_object)  ;");


        statement.executeBatch();
        mySQLcon.commit();
        long stopTime = System.nanoTime();
        long timeDifference = stopTime - startTime;
        mySQLIndexPerformance[createIndexCounter] = timeDifference;

        createIndexCounter++;
    }

    private void joinOperationsMySQL(long[] toSaveArray, int counter) throws Exception {
        //mySQLcon.setAutoCommit(false);
        long startTime = System.nanoTime();

        statement = mySQLcon.createStatement();

        ResultSet rs1 = statement.executeQuery("select * from test.metaobject as m\n" +
                "inner join test.instance_object as io \n" +
                "on m.uuid = io.uuid;");
        ResultSet rs2 = statement.executeQuery("select * from test.metaobject as m\n" +
                "inner join test.class as c\n" +
                "on m.uuid = c.uuid_metaobject;");
        ResultSet rs3 = statement.executeQuery("select * from test.metaobject as m\n" +
                "right join test.`role` as r \n" +
                "on m.uuid = r.uuid_metaobject;");
        ResultSet rs4 = statement.executeQuery("select * from test.metaobject as m\n" +
                "right join test.scene_type as st \n" +
                "on m.uuid = st.uuid_metaobject\n" +
                "where m.uuid is NULL;");
        ResultSet rs5 = statement.executeQuery("select * from test.instance_object as io\n" +
                "left join test.scene_instance as si \n" +
                "on io.uuid = si.uuid_instance_object;");
        ResultSet rs6 = statement.executeQuery("select * from test.instance_object as io\n" +
                "left join test.class_instance as ci\n" +
                "on io.uuid = ci.uuid_class \n" +
                "where ci.uuid_class is null;");

        long stopTime = System.nanoTime();
        long timeDifference = stopTime - startTime;
        toSaveArray[counter] = timeDifference;
        System.out.println("Join Test " + counter + " has been done.");


    }

    private void createRandomInsertsMySQL(int amountInserts) throws Exception {
        mySQLcon.setAutoCommit(false);
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

            statement = mySQLcon.createStatement();
            statement.addBatch("SET sql_mode=PIPES_AS_CONCAT;");
            statement.addBatch("INSERT  INTO  test.metaobject (uuid, name, visualization, description, modification_time, creation_time)  values ('" + myClassUUID + "', 'Class_' || '" + randomString() + "', null, null, now(), now());");
            statement.addBatch("insert into test.metaobject (uuid, name, visualization, description,modification_time,creation_time) values ('" + myRoleUUID + "', 'Role_' ||  '" + randomString() + "'  , null, null, now(), now());");
            statement.addBatch("insert into test.metaobject (uuid, name, visualization, description,modification_time,creation_time)  values ('" + myRole2UUID + "', 'Role_' ||  '" + randomString() + "'  , null, null, now(), now());");
            statement.addBatch("insert into test.metaobject (uuid, name, visualization, description, modification_time, creation_time) values ('" + mySceneTypeUUID + "','Scene_Type_'||  '" + randomString() + "' , null, null, now(), now());");
            statement.addBatch("insert into test.class (uuid_metaobject, is_abstract, is_reusable) values ('" + myClassUUID + "', true, true);");
            statement.addBatch(" insert into test.role (uuid_metaobject)  values ('" + myRoleUUID + "');  ");
            statement.addBatch("insert into test.role (uuid_metaobject) values ('" + myRole2UUID + "');  ");
            statement.addBatch(" insert into test.scene_type (uuid_metaobject) values ('" + mySceneTypeUUID + "');  ");
            statement.addBatch("insert into test.relationclass(uuid_class, role_from, role_to)  values ('" + myClassUUID + "', '" + myRoleUUID + "', '" + myRole2UUID + "');  ");
            statement.addBatch("insert into test.instance_object(uuid, creation_time, modification_time) values ('" + myObjectUUID + "', now(), now());  ");
            statement.addBatch("insert into test.instance_object(uuid, creation_time, modification_time)  values ('" + myObject2UUID + "', now(), now());  ");
            statement.addBatch("insert into test.instance_object (uuid, creation_time, modification_time) values ('" + myRoleObjectUUID + "', now(), now());  ");
            statement.addBatch("insert into test.instance_object(uuid, creation_time, modification_time)  values ('" + mySceneObjectUUID + "', now(), now());  ");
            statement.addBatch("insert into test.instance_object (uuid, creation_time, modification_time)  values ('" + mySceneObject2UUID + "', now(), now());  ");
            statement.addBatch("insert into test.instance_object(uuid, creation_time, modification_time) values ('" + mySceneObject3UUID + "', now(), now());  ");
            statement.addBatch("insert into test.class_instance (uuid_instance_object, uuid_class, uuid_relationclass)  values ('" + myObjectUUID + "', '" + myClassUUID + "', '" + myClassUUID + "');  ");
            statement.addBatch("insert into test.class_instance (uuid_instance_object, uuid_class) values ('" + myObject2UUID + "', '" + myClassUUID + "' );  ");
            statement.addBatch("insert into test.role_instance (uuid_instance_object, uuid_role)  values( '" + myRoleObjectUUID + "' , '" + myRoleUUID + "');  ");
            statement.addBatch("insert into test.scene_instance (uuid_scene_type , uuid_instance_object ) values ('" + mySceneTypeUUID + "', '" + mySceneObjectUUID + "');  ");
            statement.addBatch("insert into test.scene_instance (uuid_scene_type , uuid_instance_object)  values ('" + mySceneTypeUUID + "', '" + mySceneObject2UUID + "');  ");
            statement.addBatch("insert into test.scene_instance (uuid_scene_type , uuid_instance_object) values ('" + mySceneTypeUUID + "', '" + mySceneObject3UUID + "');  ");
            statement.executeBatch();
            mySQLcon.commit();
            System.out.println(i + 1 + " has been created");
        }
        long stopTime = System.nanoTime();
        long timeDifference = stopTime - startTime;
        mySQLCreation[insertCounter] = timeDifference;
        insertCounter++;
    }

    public void setAllCountersToZero() {
        this.createIndexCounter = 0;
        this.dropIndexCounter = 0;
        this.insertCounter = 0;
        this.joinAfterIndexCounter = 0;
        this.joinCounter = 0;
    }


    public long[] getMySQLCreation() {
        return mySQLCreation;
    }

    public long[] getMySQLJoinPerformance() {
        return mySQLJoinPerformance;
    }

    public long[] getMySQLIndexPerformance() {
        return mySQLIndexPerformance;
    }

    public long[] getMySQLJoinAfterIndexPerformance() {
        return mySQLJoinAfterIndexPerformance;
    }

    public long[] getMySQLDropIndexPerformance() {
        return mySQLDropIndexPerformance;
    }

    public long getTotalTime() {
        return totalTime;
    }
}
