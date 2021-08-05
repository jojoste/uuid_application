import Model.Database.PerformanceMeasurement.PerformanceMeasurementMongoDB;
import Model.Database.PerformanceMeasurement.PerformanceMeasurementMySQL;
import Model.Database.PerformanceMeasurement.PerformanceMeasurementPostgres;
import Model.Database.PerformanceMeasurement.PerformanceMeasurementRedis;
import Model.Database.Truncate.TruncateRedis;

import java.util.UUID;
//import View.Graph;

public class TestClass {
    public static void main(String[] args) throws Exception {
        PerformanceMeasurementMySQL mySQLCreat = new PerformanceMeasurementMySQL();
        PerformanceMeasurementMongoDB myMongoDBCreat = new PerformanceMeasurementMongoDB();
        PerformanceMeasurementPostgres myPostgresCreat = new PerformanceMeasurementPostgres();
        PerformanceMeasurementRedis myRedisCreat = new PerformanceMeasurementRedis();



        System.out.println(UUID.randomUUID());

        //myPostgresCreat.dropIndexesPostgres();


        //TruncatePostgres.TruncatePostgresTable("role_instance");
        //TruncateMongoDB.TruncateMongoDB();
        //myMongoDBCreat.performanceMeasurementMongoDB(1000);

        //myPostgresCreat.createRandomInsertsPostgres(1000);

        //TruncateMongoDB.TruncateMongoDB();

       //mySQLCreat.createRandomInsertsmySQL(100000);
        //myPostgresCreat.performanceMeasurementPostgres(1000);
        //mySQLCreat.performanceMeasurementMySQL(1000);
       //myPostgresJoin.joinPostgresOperation();

        /*TruncateMySQL.TruncateMySQLTable("test.instance_object");
        TruncateMySQL.TruncateMySQLTable("test.relationclass");
        TruncateMySQL.TruncateMySQLTable("test.class");
        TruncateMySQL.TruncateMySQLTable("test.scene_type");
        TruncateMySQL.TruncateMySQLTable("test.role");
        TruncateMySQL.TruncateMySQLTable("test.metaobject");
        TruncateMySQL.TruncateMySQLTable("test.class_instance");
        TruncateMySQL.TruncateMySQLTable("test.role_instance");
        TruncateMySQL.TruncateMySQLTable("test.scene_instance");*/






        //mySQLCreat.createRandomInsertsmySQL(1000);


    }
}
