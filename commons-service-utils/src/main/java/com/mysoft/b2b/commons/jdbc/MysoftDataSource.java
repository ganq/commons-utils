/**
 * Copyright mysoft Limited (c) 2014. All rights reserved.
 * This software is proprietary to and embodies the confidential
 * technology of mysoft Limited. Possession, use, or copying
 * of this software and media is authorized only pursuant to a
 * valid written license from mysoft or an authorized sublicensor.
 */
package com.mysoft.b2b.commons.jdbc;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import com.jolbox.bonecp.BoneCPDataSource;

/**
 * chengp: Change to the actual description of this class
 * @version   Revision History
 * <pre>
 * Author     Version       Date        Changes
 * chengp    1.0           2014年8月25日     Created
 *
 * </pre>
 * @since b2b 2.0.0
 */

@SuppressWarnings("serial")
public class MysoftDataSource extends BoneCPDataSource implements InitializingBean, DisposableBean{

    private static final Logger logger = Logger.getLogger(MysoftDataSource.class);
    
    /**
     * 仅仅启动报错就可以，不用让这个原因引起其他所有类都创建不成功，最理想是直接停止启动 
     * 不用重复打印错误
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        try{
            getConnection();
        }catch(Exception e){
            logger.error("数据库配置错误："+e.getMessage(), e.getCause());
            System.exit(1);
        }
    }
    
    @Override
    public Connection getConnection() throws SQLException {
        try{
            return super.getConnection();
        }catch(SQLException e){
            logger.error("数据库连接获取错误："+e.getMessage(), e.getCause());
            throw e;
        }
    }

    @Override
    public void close() {
        super.close();
    }

    @Override
    public void destroy() throws Exception {
        close();
    }
    
}
