/**
 * 
 */
package com.mysoft.b2b.commons.config.properties;

import java.lang.reflect.Field;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.util.ReflectionUtils;

/**
 * 
 * chengp:    通过注解的方式注入配置属性
 * @version   Revision History
 * <pre>
 * Author     Version       Date        Changes
 * chengp    1.0           2014年8月30日     Created
 *
 * </pre>
 * @since 8.
 */
public class AnnotationBeanPostProcessor extends PropertyPlaceholderConfigurer implements BeanPostProcessor,
        InitializingBean {

    private static final Logger logger = Logger.getLogger(AnnotationBeanPostProcessor.class);
    
    private static java.util.Properties pros;

    @SuppressWarnings("rawtypes")
    private Class[] enableClassList = { String.class };
    @SuppressWarnings("rawtypes")
    public void setEnableClassList(Class[] enableClassList) {
        this.enableClassList = enableClassList;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String arg1) throws BeansException {
        Field[] fields = bean.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(Property.class)) {
                if (filterType(field.getType().toString())) {
                    Property p = field.getAnnotation(Property.class);
                    try {
                        ReflectionUtils.makeAccessible(field);
                        field.set(bean, pros.getProperty(p.name()));
                    } catch (Exception e) {
                        logger.error(e.getMessage(), e);
                    }
                }
            }
        }
        return bean;
    }

    @SuppressWarnings("rawtypes")
    private boolean filterType(String type) {
        if (type != null) {
            for (Class c : enableClassList) {
                if (c.toString().equals(type)) {
                    return true;
                }
            }
            return false;
        } else {
            return true;
        }
    }

    public Object postProcessBeforeInitialization(Object bean, String arg1) throws BeansException {
        return bean;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        pros = mergeProperties();
    }

    
    public static String getProperties(String key){
    	return pros.getProperty(key);
    }
}
