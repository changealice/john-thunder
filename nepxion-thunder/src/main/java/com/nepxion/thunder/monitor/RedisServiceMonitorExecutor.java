package com.nepxion.thunder.monitor;

/**
 * <p>Title: Nepxion Thunder</p>
 * <p>Description: Nepxion Thunder For Distribution</p>
 * <p>Copyright: Copyright (c) 2015</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @email 1394997@qq.com
 * @version 1.0
 */

import java.util.UUID;
import java.util.concurrent.Callable;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;

import com.nepxion.thunder.common.constant.ThunderConstants;
import com.nepxion.thunder.common.entity.MonitorStat;
import com.nepxion.thunder.common.serialization.SerializerExecutor;
import com.nepxion.thunder.common.thread.ThreadPoolFactory;
import com.nepxion.thunder.protocol.redis.cluster.RedisClusterFactory;
import com.nepxion.thunder.protocol.redis.sentinel.RedisSentinelPoolFactory;

public class RedisServiceMonitorExecutor extends AbstractMonitorExecutor {
    private static final Logger LOG = LoggerFactory.getLogger(RedisServiceMonitorExecutor.class);

    @Override
    public void execute(final MonitorStat monitorStat) throws Exception {
        final String traceId = monitorStat.getTraceId();
        if (StringUtils.isEmpty(traceId)) {
            LOG.error("Trace ID is null, the monitor stat can't be put into redis");

            return;
        }

        if (RedisSentinelPoolFactory.enabled()) {
            executeToSentinel(traceId, monitorStat);
        } else if (RedisClusterFactory.enabled()) {
            executeToCluster(traceId, monitorStat);
        }
    }

    public void executeToSentinel(final String traceId, final MonitorStat monitorStat) throws Exception {
        final Jedis jedis = RedisSentinelPoolFactory.getResource();
        if (jedis == null) {
            LOG.error("No redis sentinel resource found, execute failed");

            return;
        }

        ThreadPoolFactory.createThreadPoolDefaultExecutor().submit(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                try {
                    jedis.hset(traceId, UUID.randomUUID().toString(), SerializerExecutor.toJson(monitorStat));
                    jedis.pexpire(traceId, properties.getLong(ThunderConstants.REDIS_DATA_EXPIRATION_ATTRIBUTE_NAME));
                } catch (Exception e) {
                    LOG.error("Execute failed", e);
                } finally {
                    if (jedis != null) {
                        jedis.close();
                    }
                }

                return null;
            }
        });
    }

    public void executeToCluster(final String traceId, final MonitorStat monitorStat) throws Exception {
        final JedisCluster cluster = RedisClusterFactory.getCluster();
        if (cluster == null) {
            LOG.error("No redis cluster found, execute failed");

            return;
        }

        ThreadPoolFactory.createThreadPoolDefaultExecutor().submit(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                try {
                    cluster.hset(traceId, UUID.randomUUID().toString(), SerializerExecutor.toJson(monitorStat));
                    cluster.pexpire(traceId, properties.getLong(ThunderConstants.REDIS_DATA_EXPIRATION_ATTRIBUTE_NAME));
                } catch (Exception e) {
                    LOG.error("Execute failed", e);
                } finally {
                    if (cluster != null) {
                        cluster.close();
                    }
                }

                return null;
            }
        });
    }
}