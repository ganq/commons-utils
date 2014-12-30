/**
 * Copyright ecVision Limited (c) 2012. All rights reserved.
 * This software is proprietary to and embodies the confidential
 * technology of ecVision Limited.  Possession, use, or copying
 * of this software and media is authorized only pursuant to a
 * valid written license from Uet or an authorized sublicensor.
 */
package com.mysoft.b2b.commons;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * CGP:       Properties帮助类
 * @version   Revision History
 * <pre>
 * Author     Version       Date        Changes
 * CGP        1.0           2012-11-9     Created
 *
 * </pre>
 * @since B2B 2.0.0
 */
public class PropertiesUtils {

    public static final String CLASSPATH = "classpath:";

    public static final String SMART_SCANNING_CLASSPATH = "classpath*:";

    private PropertiesUtils() {
        
    }

    @SuppressWarnings({ "rawtypes" })
    public static Map<String, String> loadPropertiesMap(String filepath) {
        Map<String, String> map = new HashMap<String, String>();
        Properties prop = loadProperties(PropertiesUtils.class.getClassLoader(), filepath);
        Enumeration en = prop.propertyNames();
        while (en.hasMoreElements()) {
            String key = (String) en.nextElement();
            map.put(key, prop.getProperty(key));
        }
        return map;
    }

    public static Properties loadProperties(String filepath) {
        return loadProperties(PropertiesUtils.class.getClassLoader(), filepath);
    }

    public static Properties loadProperties(ClassLoader cl, String filepath) {
        InputStream is = null;
        Properties prop = null;
        boolean flag = false;
        try {
            String filePath = "";
            if (filepath.endsWith(".properties")) {
                int begin = filepath.indexOf(CLASSPATH);
                if (begin > -1) {
                    filePath = filepath.substring(begin + CLASSPATH.length());
                } else {
                    filePath = filepath;
                }

                URL url = cl.getResource(filePath);
                if (url != null) {
                    is = url.openStream();
                    prop = new Properties();
                    prop.load(is);
                } else {
                    flag = true;
                }
            } else {
                flag = true;
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("Could not load properties from " + filepath + " properties file", e);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                }
            }
            if (flag) {
                throw new IllegalArgumentException("Could not find properties from " + filepath + " properties file", null);
            }
        }
        return prop;
    }
    
}
