/**
 * Copyright mysoft Limited (c) 2014. All rights reserved.
 * This software is proprietary to and embodies the confidential
 * technology of mysoft Limited. Possession, use, or copying
 * of this software and media is authorized only pursuant to a
 * valid written license from mysoft or an authorized sublicensor.
 */
package com.mysoft.b2b.commons.scheduler;

import java.text.ParseException;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.locks.ReentrantLock;

import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.spi.JobFactory;

import com.mysoft.b2b.commons.exception.PlatformUncheckException;
/**
 * chengp: Change to the actual description of this class
 * @version   Revision History
 * <pre>
 * Author     Version       Date        Changes
 * chengp    1.0           2014年8月22日     Created
 *
 * </pre>
 * @since b2b 2.0.0
 */

public final class SchedulerManager {

    public static String CREATE_SCHEDULER_ERR = "Failed to create scheduler!";
    public static String STOP_SCHEDULER_ERR = "Failed to stop scheduler!";
    public static String CREATE_JOB_ERR = "Failed to create job!";
    public static String STOP_JOB_ERR = "Failed to stop job!";
    
    public static final String LOCK_OBJECT_KEY = "__lockObj";
    
    private static Scheduler scheduler;
    private static Map<String, JobInfoModel> jobsMap = new Hashtable<String, JobInfoModel>();
    
    static void createJobModels(JobInfoModel... models) {
        if (models != null && models.length > 0) {
            try {
                for (JobInfoModel jobInfoModel : models) {
                    if (jobInfoModel != null) {
                        if(jobInfoModel.getTrigger() == null || jobInfoModel.getJobDetail() == null){
                            continue;
                        }
                        jobsMap.put(jobInfoModel.getJobName(), jobInfoModel);
                        scheduler.scheduleJob(jobInfoModel.getJobDetail(),
                                jobInfoModel.getTrigger());
                    }
                }
            } catch (SchedulerException e) {
                throw new PlatformUncheckException(CREATE_JOB_ERR + e.getMessage(),
                        null, e.getCause());
            }
        }
    }

    static void deleteJobModels(JobInfoModel... models) {
        if (models != null && models.length > 0) {
            try {
                String jobName = null;
                for (JobInfoModel jobInfoModel : models) {
                    if (jobInfoModel != null) {
                        jobName = jobInfoModel.getJobName();
                        if (jobsMap.containsKey(jobName)) {
                            jobsMap.remove(jobName);
                            scheduler.deleteJob(jobName,
                                    JobInfoModel.JOB_GROUP_NAME);
                        }
                    }
                }
            } catch (SchedulerException e) {
                throw new PlatformUncheckException(STOP_JOB_ERR + e.getMessage(), null,
                        e.getCause());
            }
        }
    }

    static void updateJobModels(JobInfoModel... models) {
        if (models != null && models.length > 0) {
            try {
                String jobName = null;
                for (JobInfoModel jobInfoModel : models) {
                    if (jobInfoModel != null) {
                        jobName = jobInfoModel.getJobName();
                        if (jobsMap.containsKey(jobName)) {
                            scheduler.deleteJob(jobName,
                                    JobInfoModel.JOB_GROUP_NAME);
                            jobsMap.remove(jobName);
                        }
                        
                        if(jobInfoModel.getTrigger() == null || jobInfoModel.getJobDetail() == null){
                            continue;
                        }
                        jobsMap.put(jobName, jobInfoModel);
                        scheduler.scheduleJob(jobInfoModel.getJobDetail(),
                                jobInfoModel.getTrigger());

                    }

                }
            } catch (SchedulerException e) {
                throw new PlatformUncheckException(CREATE_JOB_ERR + " OR " + STOP_JOB_ERR
                                + e.getMessage(), null, e.getCause());
            }
        }
    }

    static Scheduler getScheduler() {
        return scheduler;
    }

    static void initManager(JobFactory jobFactory) {
        try {
            SchedulerFactory sf = new StdSchedulerFactory();
            scheduler = sf.getScheduler();
            scheduler.setJobFactory(jobFactory);
            scheduler.start();
        } catch (SchedulerException e) {
            throw new PlatformUncheckException(
                    STOP_SCHEDULER_ERR + e.getMessage(),
                    null, e.getCause());
        }
    }

    static void destoryManager() {
        if (jobsMap != null && !jobsMap.isEmpty()) {
            Iterator<String> iter = jobsMap.keySet().iterator();
            if (iter.hasNext()) {
                jobsMap.remove(iter.next());
            }
        }
        if (scheduler != null) {
            try {
                scheduler.shutdown();
            } catch (SchedulerException e) {
                throw new PlatformUncheckException(
                        STOP_SCHEDULER_ERR + e.getMessage(),
                        null, e.getCause());
            }
            scheduler = null;
        }
    }

    public static int getJobNumber() {
        try {
            String[] arr = scheduler
                    .getTriggerNames(JobInfoModel.JOB_GROUP_NAME);
            if (arr == null) {
                return 0;
            }
            return arr.length;
        } catch (SchedulerException e) {
            throw new PlatformUncheckException(
                    CREATE_SCHEDULER_ERR + e.getMessage(),
                    null, e.getCause());
        }
    }
    
    @SuppressWarnings("rawtypes")
    public static JobInfoModel createJobInfoModel(String jobName, Class clazz, String cronExpression, Map<String, Object> params){
        try{
            JobDetail jobDetail = new JobDetail(jobName, JobInfoModel.JOB_GROUP_NAME, clazz);
            jobDetail.getJobDataMap().put(SchedulerManager.LOCK_OBJECT_KEY, new ReentrantLock());
    
            CronTrigger trigger = new CronTrigger(jobName, JobInfoModel.JOB_GROUP_NAME);
            //设置使用服务器时区
            trigger.setTimeZone(TimeZone.getDefault());
            trigger.setCronExpression(cronExpression);
            return new JobInfoModel(jobName, jobDetail, trigger, params);
        } catch (ParseException e) {
            throw new PlatformUncheckException(CREATE_JOB_ERR + e.getMessage(), null, e.getCause());
        }
    }
    
}
