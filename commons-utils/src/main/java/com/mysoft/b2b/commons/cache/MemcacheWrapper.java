package com.mysoft.b2b.commons.cache;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.MemcachedClientBuilder;
import net.rubyeye.xmemcached.XMemcachedClientBuilder;
import net.rubyeye.xmemcached.command.BinaryCommandFactory;
import net.rubyeye.xmemcached.exception.MemcachedException;
import net.rubyeye.xmemcached.impl.KetamaMemcachedSessionLocator;
import net.rubyeye.xmemcached.utils.AddrUtil;

import org.apache.log4j.Logger;

/**
 * 
 *
 */
public class MemcacheWrapper implements Cache {
    private static final Logger logger = Logger.getLogger(MemcacheWrapper.class);

    private MemcachedClientBuilder builder;

    private MemcachedClient memcachedClient;
    private int TimeOut = 10;


    public MemcacheWrapper(String address, Integer poolSize) throws IOException, TimeoutException, InterruptedException, MemcachedException {
        builder = new XMemcachedClientBuilder(AddrUtil.getAddressMap(address));
        builder.setCommandFactory(new BinaryCommandFactory()); // 启用二进制协议
        builder.setConnectionPoolSize(poolSize);
        builder.setSessionLocator(new KetamaMemcachedSessionLocator());
        builder.setFailureMode(true);
        memcachedClient = builder.build();
    }

    public Boolean set(String key, Object obj) {
        try {
            return setMap(key, 0, obj);
        } catch (Exception e) {
            logger.warn("Memory cache error.", e);
            return false;
        }
    }

    public Boolean setMap(String key, Integer timeOut, Object obj) {
        try {
            return memcachedClient.set(key, timeOut, obj);
        } catch (Exception e) {
            logger.warn("Memory cache error.", e);
            return false;
        }
    }

    public Boolean remove(String key) {
        try {
            return remove(key, TimeOut);
        } catch (Exception e) {
            logger.warn("Memory cache error.", e);
            return false;
        }
    }

    @SuppressWarnings("deprecation")
    public Boolean remove(String key, Integer timeOut) {
        try {
            return memcachedClient.delete(key, timeOut);
        } catch (Exception e) {
            logger.warn("Memory cache error.", e);
            return false;
        }
    }

    public void asynRemove(String key) throws InterruptedException, MemcachedException {
        asynRemove(key, TimeOut);
    }

    @SuppressWarnings("deprecation")
    public void asynRemove(String key, Integer timeOut) throws InterruptedException, MemcachedException {
        memcachedClient.deleteWithNoReply(key, timeOut);
    }

    public Boolean prependMap(String key, Object obj) {
        try {
            return memcachedClient.prepend(key, obj, TimeOut);
        } catch (Exception e) {
            logger.warn("Memory cache error.", e);
            return false;
        }
    }

    public Boolean appendMap(String key, Object obj) {
        try {
            return appendMap(key, obj, TimeOut);
        } catch (Exception e) {
            return false;
        }
    }

    public Boolean appendMap(String key, Object obj, Integer timeOut) {
        try {
            return memcachedClient.append(key, obj, timeOut);
        } catch (Exception e) {
            return false;
        }
    }

    public Boolean replaceMap(String key, Object obj) {
        try {
            return replaceMap(key, 0, obj);
        } catch (Exception e) {
            return false;
        }
    }

    public Boolean replaceMap(String key, Integer timeOut, Object obj) {
        try {
            return memcachedClient.replace(key, timeOut, obj);
        } catch (Exception e) {
            return false;
        }
    }

    public Object get(String key) {
        try {
            return getMap(key, TimeOut);
        } catch (Exception e) {
            return null;
        }
    }

    public Object getMap(String key, Integer timeOut) throws TimeoutException, InterruptedException, MemcachedException {
        return memcachedClient.get(key, timeOut);
    }
}