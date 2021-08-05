package Model.Database.Connection;

import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;

import java.util.UUID;

public class ConnectionRedis {
    private static StatefulRedisConnection<String, String> redisCon = null;

    static {
        RedisClient redisClient = RedisClient.create("redis://localhost:6379/0");
        redisCon = redisClient.connect();
    }

    public static StatefulRedisConnection getRedisCon() {
        return redisCon;
    }

    public static boolean connectionRedisReady() {
        if (redisCon == null) {
            return false;

        } else {
            return true;
        }
    }
}
