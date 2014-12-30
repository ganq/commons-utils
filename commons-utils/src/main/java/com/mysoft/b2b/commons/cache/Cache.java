package com.mysoft.b2b.commons.cache;


/**
 * Created with IntelliJ IDEA.
 * User: Think
 * Date: 14-1-14
 * Time: 下午4:59
 * To change this template use File | Settings | File Templates.
 */
public interface Cache {
    Boolean set(String key, Object obj);

    Boolean remove(String key);

    Object get(String key);
}
