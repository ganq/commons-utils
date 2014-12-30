/**
 * Copyright mysoft Limited (c) 2014. All rights reserved.
 * This software is proprietary to and embodies the confidential
 * technology of mysoft Limited. Possession, use, or copying
 * of this software and media is authorized only pursuant to a
 * valid written license from mysoft or an authorized sublicensor.
 */
package com.mysoft.b2b.commons.scheduler;

import java.util.List;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * chengp:    Job任务工程
 * @version   Revision History
 * <pre>
 * Author     Version       Date        Changes
 * chengp    1.0           2014年8月22日     Created
 *
 * </pre>
 * @since b2b 2.0.0
 */

public class SchedulerFactory implements ApplicationContextAware, InitializingBean, DisposableBean {

    private List<MysoftJob> jobs;

    private ApplicationContext applicationContext;

    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public void setJobs(List<MysoftJob> jobs) {
        this.jobs = jobs;
    }

    public List<MysoftJob> getJobs() {
        return jobs;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (jobs != null && jobs.size() > 0) {

            SchedulerManager.initManager(new MysoftJobFactory(applicationContext));

            JobInfoModel[] jobInfoModels = new JobInfoModel[jobs.size()];
            for (int i = 0; i < jobs.size(); i++) {
                MysoftJob job = jobs.get(i);
                jobInfoModels[i] = SchedulerManager.createJobInfoModel(job.getJobName(), job.getClass(), job.getCronExpression(),
                        job.getParams());
            }
            SchedulerManager.createJobModels(jobInfoModels);
        }
    }

    @Override
    public void destroy() throws Exception {
        SchedulerManager.destoryManager();
    }

}
