/**
 * Copyright mysoft Limited (c) 2014. All rights reserved.
 * This software is proprietary to and embodies the confidential
 * technology of mysoft Limited. Possession, use, or copying
 * of this software and media is authorized only pursuant to a
 * valid written license from mysoft or an authorized sublicensor.
 */
package com.mysoft.b2b.commons.scheduler;

import java.util.Iterator;
import java.util.Map;

import org.quartz.CronTrigger;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;

/**
 * chengp:    Job任务信息
 * @version   Revision History
 * <pre>
 * Author     Version       Date        Changes
 * chengp     1.0           2014年8月22日     Created
 * </pre>
 * @since b2b 2.0.0
 */

public class JobInfoModel {
    
    public static String JOB_GROUP_NAME = "mysoft";

    private String jobName;
    private String jobGroup;
    private JobDetail jobDetail;
    private CronTrigger trigger;
    private Map<String, Object> params;

    public JobInfoModel(String jobName, JobDetail jobDetail,
            CronTrigger trigger, Map<String, Object> params) {
        this(jobName, JOB_GROUP_NAME, jobDetail, trigger, params);
    }
    
    public JobInfoModel(String jobName, String jobGroup, JobDetail jobDetail,
            CronTrigger trigger, Map<String, Object> params) {
        this.jobName = jobName;
        this.jobGroup = jobGroup;
        this.jobDetail = jobDetail;
        this.trigger = trigger;
        this.params = params;
        
        if(params!=null && !params.isEmpty()){
            JobDataMap map = this.jobDetail.getJobDataMap();
            Iterator<Map.Entry<String,Object>> iter = params.entrySet().iterator();
            if (iter.hasNext()) {
                Map.Entry<String,Object> entry = iter.next();
                map.put(entry.getKey(), entry.getValue());
            }
        }
    }
    
    @SuppressWarnings("unused")
    private JobInfoModel(){
        
    }

    public String getJobName() {
        return jobName;
    }

    public String getJobGroup() {
        return jobGroup;
    }

    public JobDetail getJobDetail() {
        return jobDetail;
    }

    public CronTrigger getTrigger() {
        return trigger;
    }

    public Map<String, Object> getParams() {
        return params;
    }
    
}
