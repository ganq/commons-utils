/**
 * Copyright mysoft Limited (c) 2014. All rights reserved.
 * This software is proprietary to and embodies the confidential
 * technology of mysoft Limited. Possession, use, or copying
 * of this software and media is authorized only pursuant to a
 * valid written license from mysoft or an authorized sublicensor.
 */
package com.mysoft.b2b.commons.scheduler;

import org.quartz.Job;
import org.quartz.SchedulerException;
import org.quartz.spi.JobFactory;
import org.quartz.spi.TriggerFiredBundle;
import org.springframework.context.ApplicationContext;

/**
 * chengp: Change to the actual description of this class
 * @version   Revision History
 * <pre>
 * Author     Version       Date        Changes
 * chengp    1.0           2014年9月4日     Created
 *
 * </pre>
 * @since b2b 2.0.0
 */

public class MysoftJobFactory implements JobFactory{
    
    private ApplicationContext applicationContext;
    
    public MysoftJobFactory(ApplicationContext applicationContext){
        this.applicationContext = applicationContext;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public Job newJob(TriggerFiredBundle bundle) throws SchedulerException {
        return (Job)applicationContext.getBean(bundle.getJobDetail().getJobClass());
    }
    
}
