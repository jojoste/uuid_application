package Model.Database.Truncate;

import Model.Database.Connection.ConnectionRedis;
import io.lettuce.core.api.StatefulRedisConnection;

public class TruncateRedis {
    static StatefulRedisConnection myRedisCon = ConnectionRedis.getRedisCon();

    public static void TruncateRedis(){
        myRedisCon.sync().flushall();
    }
}
