package Model.Database.PerformanceMeasurement;

import Model.Database.Connection.ConnectionPostgres;


import java.sql.*;
import java.util.UUID;

import static Model.Database.AuxiliaryFunctions.randomString;


public class PerformanceMeasurementPostgres {
    static Connection postgresCon = ConnectionPostgres.getPostgresCon();
    Statement statement = null;

    private long totalTime = 0;
    private int insertCounter = 0;
    private int joinCounter = 0;
    private int joinAfterIndexCounter = 0;
    private int createIndexCounter = 0;
    private int dropIndexCounter = 0;
    private static int intervall = 100;
    private static int numberOfJoins = 10;


    private long[] postgresCreation = new long[intervall];
    private long[] postgresJoinPerformance = new long[numberOfJoins];
    private long[] postgresIndexPerformance = new long[numberOfJoins];
    private long[] postgresJoinAfterIndexPerformance = new long[numberOfJoins];
    private long[] postgresDropIndexPerformance = new long[numberOfJoins];

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

    public void performanceMeasurementPostgres(int amountInserts) {
        long startTimeTotal = System.nanoTime();
        try {
            for (int i = 0; i < intervall / numberOfJoins; i++) {
                for (int j = 0; j < intervall / numberOfJoins; j++) {
                    createRandomInsertsPostgres(amountInserts);
                }
                joinOperationsPostgres(postgresJoinPerformance, joinCounter);
                joinCounter++;
                createIndexPostgres();
                joinOperationsPostgres(postgresJoinAfterIndexPerformance, joinAfterIndexCounter);
                joinAfterIndexCounter++;
                dropIndexesPostgres();
            }
            for (int t = 0; t < postgresCreation.length; t++) {
                int locT = t + 1;
                System.out.println("Time for " + locT + " insert :  " + postgresCreation[t]);
            }
            for (int k = 0; k < postgresJoinPerformance.length; k++) {
                int locK = k + 1;
                System.out.println("Time for " + locK + " join : " + postgresJoinPerformance[k]);
            }
            for (int h = 0; h < postgresJoinAfterIndexPerformance.length; h++) {
                int locH = h + 1;
                System.out.println("Time for " + locH + " join after indexing : " + postgresJoinAfterIndexPerformance[h]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        long stopTimeTotal = System.nanoTime();
        totalTime = stopTimeTotal - startTimeTotal;
    }

    public void dropIndexesPostgres() throws Exception {
        postgresCon.setAutoCommit(false);
        long startTime = System.nanoTime();
        statement = postgresCon.createStatement();
        statement.addBatch("drop index  if exists metaobject_performance_idx;");
        statement.addBatch("drop index  if exists class_performance_idx ;");
        statement.addBatch("drop index  if exists role_performance_idx ;");
        statement.addBatch("drop index  if exists scene_type_performance_idx ;");
        statement.addBatch("drop index  if exists relationclass_performance_idx ;");
        statement.addBatch("drop index  if exists instance_object_performance_idx;");
        statement.addBatch("drop index  if exists class_instance1_performance_idx ;");
        statement.addBatch("drop index  if exists class_instance2_performance_idx ;");
        statement.addBatch("drop index  if exists class_instance3_performance_idx;");
        statement.addBatch("drop index  if exists  scene_instance1_performance_idx ;");
        statement.addBatch("drop index  if exists scene_instance2_performance_idx ;");
        statement.addBatch("drop index  if exists role_instance_performance_idx ;");

        statement.executeBatch();
        postgresCon.commit();
        long stopTime = System.nanoTime();
        long timeDifference = stopTime - startTime;
        postgresDropIndexPerformance[dropIndexCounter] = timeDifference;

        dropIndexCounter++;

    }

    private void createIndexPostgres() throws Exception {
        postgresCon.setAutoCommit(false);
        long startTime = System.nanoTime();
        statement = postgresCon.createStatement();

        statement.addBatch("create index  metaobject_performance_idx on public.metaobject(uuid);");
        statement.addBatch("create index class_performance_idx on public.\"class\"(uuid_metaobject);");
        statement.addBatch("create index role_performance_idx on public.\"role\"(uuid_metaobject);");
        statement.addBatch("create index scene_type_performance_idx on public.scene_type (uuid_metaobject);");
        statement.addBatch("create index relationclass_performance_idx on public.relationclass (uuid_class);");
        statement.addBatch("create index instance_object_performance_idx on public.instance_object (uuid);");
        statement.addBatch("create index class_instance1_performance_idx on public.class_instance (uuid_instance_object);");
        statement.addBatch("create index class_instance2_performance_idx on public.class_instance (uuid_class);");
        statement.addBatch("create index class_instance3_performance_idx on public.class_instance (uuid_relationclass);");
        statement.addBatch("create index scene_instance1_performance_idx on public.scene_instance (uuid_instance_object);");
        statement.addBatch("create index scene_instance2_performance_idx on public.scene_instance (uuid_scene_type);");
        statement.addBatch("create index role_instance_performance_idx on public.role_instance (uuid_instance_object);");

        statement.executeBatch();
        postgresCon.commit();
        long stopTime = System.nanoTime();
        long timeDifference = stopTime - startTime;
        postgresIndexPerformance[createIndexCounter] = timeDifference;
        createIndexCounter++;


    }

    private void joinOperationsPostgres(long[] toSaveArray, int counter) throws Exception {
        //postgresCon.setAutoCommit(false);
        long startTime = System.nanoTime();
        statement = postgresCon.createStatement();

        ResultSet rs1 = statement.executeQuery("select * from public.metaobject as m\n" +
                "inner join public.instance_object as io \n" +
                "on m.uuid = io.uuid;");
        ResultSet rs2 = statement.executeQuery("select * from public.metaobject as m\n" +
                "inner join public.\"class\" as c \n" +
                "on m.uuid = c.uuid_metaobject ;");
        ResultSet rs3 = statement.executeQuery("select * from public.metaobject as m\n" +
                "right join public.\"role\" as r \n" +
                "on m.uuid = r.uuid_metaobject;");
        ResultSet rs4 = statement.executeQuery("select * from public.metaobject as m\n" +
                "right join public.scene_type as st\n" +
                "on m.uuid = st.uuid_metaobject\n" +
                "where m.uuid is null;");
        ResultSet rs5 = statement.executeQuery("select * from instance_object as io\n" +
                "left join public.scene_instance as si\n" +
                "on io.uuid = si.uuid_instance_object;");
        ResultSet rs6 = statement.executeQuery("select * from instance_object as io\n" +
                "left join public.class_instance as ci\n" +
                "on io.uuid  = ci.uuid_class\n" +
                "where ci.uuid_class is null;");

        long stopTime = System.nanoTime();
        long timeDifference = stopTime - startTime;
        toSaveArray[counter] = timeDifference;
        System.out.println("Join Test " + counter + " has been done.");

    }

    public void printResultSet(ResultSet rs) throws SQLException {
        ResultSetMetaData rsmd = rs.getMetaData();
        int columnsNumber = rsmd.getColumnCount();
        while (rs.next()) {
            for (int i = 1; i <= columnsNumber; i++) {
                if (i > 1) System.out.print(" | ");
                System.out.print(rs.getString(i));
            }
            System.out.println("");
        }

    }

    private void createRandomInsertsPostgres(int amountInserts) throws Exception {
        postgresCon.setAutoCommit(false);
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

            statement = postgresCon.createStatement();
            statement.addBatch("CREATE EXTENSION IF NOT EXISTS \"uuid-ossp\";");
            statement.addBatch("insert into metaobject (uuid, \"name\", visualization,description,modification_time,creation_time) values ('" + myClassUUID + "', 'Class_' || '" + randomString() + "' , null, null, now(), now());");
            statement.addBatch("insert into metaobject (uuid, \"name\", visualization, description,modification_time,creation_time) values ('" + myRoleUUID + "', 'Role_' ||  '" + randomString() + "'  , null, null, now(), now());");
            statement.addBatch("insert into metaobject (uuid, \"name\", visualization, description,modification_time,creation_time) values ('" + myRole2UUID + "', 'Role_' ||  '" + randomString() + "'  , null, null, now(), now());");
            statement.addBatch("insert into metaobject (uuid, \"name\", visualization, description, modification_time, creation_time) values ('" + mySceneTypeUUID + "','Scene_Type_'||  '" + randomString() + "' , null, null, now(), now());");
            statement.addBatch("insert into public.class (uuid_metaobject, is_abstract, is_reusable) values ('" + myClassUUID + "', true, true) ;");
            statement.addBatch("insert into public.role (uuid_metaobject) values ('" + myRoleUUID + "');");
            statement.addBatch("insert into public.role (uuid_metaobject) values ('" + myRole2UUID + "');");
            statement.addBatch("insert into public.scene_type (uuid_metaobject) values ('" + mySceneTypeUUID + "');");
            statement.addBatch("insert into public.relationclass(uuid_class, role_from, role_to) values ('" + myClassUUID + "', '" + myRoleUUID + "', '" + myRole2UUID + "');");
            statement.addBatch("insert into public.instance_object(uuid, creation_time, modification_time) values ('" + myObjectUUID + "', now(), now());");
            statement.addBatch("insert into public.instance_object(uuid, creation_time, modification_time) values ('" + myObject2UUID + "', now(), now());");
            statement.addBatch("insert into public.instance_object (uuid, creation_time, modification_time) values ('" + myRoleObjectUUID + "', now(), now());");
            statement.addBatch("insert into public.instance_object(uuid, creation_time, modification_time) values ('" + mySceneObjectUUID + "', now(), now());");
            statement.addBatch("insert into public.instance_object (uuid, creation_time, modification_time)values ('" + mySceneObject2UUID + "', now(), now());");
            statement.addBatch("insert into public.instance_object(uuid, creation_time, modification_time) values ('" + mySceneObject3UUID + "', now(), now());");
            statement.addBatch("insert into public.class_instance (uuid_instance_object, uuid_class, uuid_relationclass) values ('" + myObjectUUID + "', '" + myClassUUID + "', '" + myClassUUID + "');");
            statement.addBatch("insert into public.class_instance (uuid_instance_object, uuid_class) values ('" + myObject2UUID + "', '" + myClassUUID + "' );");
            statement.addBatch("insert into public.role_instance (uuid_instance_object, uuid_role) values( '" + myRoleObjectUUID + "' , '" + myRoleUUID + "');");
            statement.addBatch("insert into public.scene_instance (uuid_scene_type , uuid_instance_object ) values ('" + mySceneTypeUUID + "', '" + mySceneObjectUUID + "');");
            statement.addBatch("insert into public.scene_instance (uuid_scene_type , uuid_instance_object) values ('" + mySceneTypeUUID + "', '" + mySceneObject2UUID + "');");
            statement.addBatch("insert into public.scene_instance (uuid_scene_type , uuid_instance_object) values ('" + mySceneTypeUUID + "', '" + mySceneObject3UUID + "');");

            statement.executeBatch();
            postgresCon.commit();

            System.out.println(i + 1 + " has been created");
        }
        long stopTime = System.nanoTime();
        long timeDifference = stopTime - startTime;
        postgresCreation[insertCounter] = timeDifference;
        insertCounter++;
    }

    public void setAllCountersToZero() {
        this.createIndexCounter = 0;
        this.dropIndexCounter = 0;
        this.insertCounter = 0;
        this.joinAfterIndexCounter = 0;
        this.joinCounter = 0;
    }

    public long[] getPostgresCreation() {
        return postgresCreation;
    }

    public long[] getPostgresJoinPerformance() {
        return postgresJoinPerformance;
    }

    public long[] getPostgresIndexPerformance() {
        return postgresIndexPerformance;
    }

    public long[] getPostgresJoinAfterIndexPerformance() {
        return postgresJoinAfterIndexPerformance;
    }

    public static int getintervall() {
        return intervall;
    }

    public long[] getPostgresDropIndexPerformance() {
        return postgresDropIndexPerformance;
    }

    public long getTotalTime() {
        return totalTime;
    }
}



