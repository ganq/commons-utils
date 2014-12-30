/**
 * Copyright mysoft Limited (c) 2014. All rights reserved.
 * This software is proprietary to and embodies the confidential
 * technology of mysoft Limited. Possession, use, or copying
 * of this software and media is authorized only pursuant to a
 * valid written license from mysoft or an authorized sublicensor.
 */
package com.mysoft.b2b.commons.thread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * chengp:    线程池工厂
 * @version   Revision History
 * <pre>
 * Author     Version       Date        Changes
 * chengp    1.0           2014年8月15日     Created
 * </pre>
 * @since b2b 2.0.0
 */
public class ThreadPoolFactory implements InitializingBean, DisposableBean {

    /**
     * 
     */
    private int corePoolSize;
    /**
     * 
     */
    private int maximumPoolSize;
    /**
     * 
     */
    private long keepAliveTime;

    protected ThreadPoolExecutor pool;

    @Override
    public void afterPropertiesSet() throws Exception {
        pool = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(100), new ThreadPoolExecutor.CallerRunsPolicy());
    }

    @Override
    public void destroy() throws Exception {
        try{
            pool.shutdownNow();
        }catch(Exception e){
        }
    }
    
    /**
     * 添加线程任务至线程池
     * @param run
     */
    public void addTask(Runnable run){
        pool.execute(run);
    }

    public void setCorePoolSize(int corePoolSize) {
        this.corePoolSize = corePoolSize;
    }

    public void setMaximumPoolSize(int maximumPoolSize) {
        this.maximumPoolSize = maximumPoolSize;
    }

    public void setKeepAliveTime(long keepAliveTime) {
        this.keepAliveTime = keepAliveTime;
    }

}
