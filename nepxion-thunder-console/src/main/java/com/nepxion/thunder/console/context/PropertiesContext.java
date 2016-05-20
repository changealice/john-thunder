package com.nepxion.thunder.console.context;

/**
 * <p>Title: Nepxion Thunder</p>
 * <p>Description: Nepxion Thunder For Distribution</p>
 * <p>Copyright: Copyright (c) 2015</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @email 1394997@qq.com
 * @version 1.0
 */

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.nepxion.thunder.common.constant.ThunderConstants;
import com.nepxion.thunder.common.object.ObjectPoolFactory;
import com.nepxion.thunder.common.property.ThunderProperties;
import com.nepxion.thunder.common.property.ThunderPropertiesManager;
import com.nepxion.thunder.common.serialization.SerializerFactory;
import com.nepxion.thunder.common.thread.ThreadPoolFactory;

public class PropertiesContext {
    public static final String LOG_FILE_PATH_ATTRIBUTE_NAME = "logFilePath";

    private static ThunderProperties properties = ThunderPropertiesManager.getProperties();

    public static void initialize() {
        ObjectPoolFactory.initialize(properties);
        ThreadPoolFactory.initialize(properties);
        SerializerFactory.initialize(properties);
    }

    public static String getRegistryAddress() {
        try {
            return properties.getString(ThunderConstants.ZOOKEEPER_ADDRESS_ATTRIBUTE_NAME);
        } catch (Exception e) {
            return null;
        }
    }

    public static void setRegistryAddress(String registryAddress) {
        registryAddress = registryAddress.replaceAll(";", ",");
        properties.put(ThunderConstants.ZOOKEEPER_ADDRESS_ATTRIBUTE_NAME, registryAddress);
    }

    public static String getRedisSentinel() {
        try {
            return properties.getString(ThunderConstants.REDIS_SENTINEL_ATTRIBUTE_NAME);
        } catch (Exception e) {
            return null;
        }
    }

    public static String getRedisMasterName() {
        try {
            return properties.getString(ThunderConstants.REDIS_MASTER_NAME_ATTRIBUTE_NAME);
        } catch (Exception e) {
            return null;
        }
    }

    public static String getRedisClientName() {
        try {
            return properties.getString(ThunderConstants.REDIS_CLIENT_NAME_ATTRIBUTE_NAME);
        } catch (Exception e) {
            return null;
        }
    }

    public static String getRedisPassword() {
        try {
            return properties.getString(ThunderConstants.REDIS_PASSWORD_ATTRIBUTE_NAME);
        } catch (Exception e) {
            return null;
        }
    }

    public static String getRedisCluster() {
        try {
            return properties.getString(ThunderConstants.REDIS_CLUSTER_ATTRIBUTE_NAME);
        } catch (Exception e) {
            return null;
        }
    }
    
    public static String getLoggerHost() {
        try {
            return properties.getString(ThunderConstants.SPLUNK_HOST_ATTRIBUTE_NAME);
        } catch (Exception e) {
            return null;
        }
    }
    
    public static int getLoggerPort() {
        try {
            return properties.getInteger(ThunderConstants.SPLUNK_PORT_ATTRIBUTE_NAME);
        } catch (Exception e) {
            return -1;
        }
    }
    
    public static String getLoggerUserName() {
        try {
            return properties.getString(ThunderConstants.SPLUNK_USER_NAME_ATTRIBUTE_NAME);
        } catch (Exception e) {
            return null;
        }
    }
    
    public static String getLoggerPassword() {
        try {
            return properties.getString(ThunderConstants.SPLUNK_PASSWORD_ATTRIBUTE_NAME);
        } catch (Exception e) {
            return null;
        }
    }
    
    public static String getLoggerFilePath() {
        String loggerFilePath = null;
        try {
            loggerFilePath = properties.getString(LOG_FILE_PATH_ATTRIBUTE_NAME);
        } catch (Exception e) {
            return null;
        }
        
        if (StringUtils.isNotEmpty(loggerFilePath)) {
            loggerFilePath = loggerFilePath.replace("//", "\\");
        }
        
        return loggerFilePath;
    }
    
    public static void addPropertiesMap(Map<String, Object> value) {
        for (Map.Entry<String, Object> entry : value.entrySet()) {
            properties.put(entry.getKey(), entry.getValue());
        }
    }

    public static ThunderProperties getProperties() {
        return properties;
    }
}