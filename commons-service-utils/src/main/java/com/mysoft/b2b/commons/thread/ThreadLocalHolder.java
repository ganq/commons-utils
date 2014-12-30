/**
 * Copyright ecVision Limited (c) 2012. All rights reserved.
 * This software is proprietary to and embodies the confidential
 * technology of ecVision Limited.  Possession, use, or copying
 * of this software and media is authorized only pursuant to a
 * valid written license from Uet or an authorized sublicensor.
 */
package com.mysoft.b2b.commons.thread;

import java.util.Properties;

/**
 * CGP:       线程共享
 * @version   Revision History
 * <pre>
 * Author     Version       Date        Changes
 * CGP        1.0           2012-10-29     Created
 *
 * </pre>
 * @since B2B 2.0.0
 */

public class ThreadLocalHolder {

    private static final ThreadLocal<Properties> holder = new ThreadLocal<Properties>();

    public static <T> void setObject(String key, T t) {
        Properties pro = holder.get();
        if(pro==null){
            holder.set(new Properties());
            pro = holder.get();
        }
        holder.get().put(key, t);
    }

    @SuppressWarnings("unchecked")
    public static <T> T getObject(String key) {
        Properties pro = holder.get();
        if(pro==null){
            holder.set(new Properties());
        }
        return (T)(holder.get().get(key));
    }

    public static void clear() {
        holder.remove();
    }
    
}
