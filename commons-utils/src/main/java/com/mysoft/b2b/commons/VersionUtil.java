package com.mysoft.b2b.commons;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * 获取版本工具类
 * 
 * @author ganq
 * 
 */
public class VersionUtil {
	private static final String VERSION_CONFIG = "/version.conf";

	private static final Logger logger = Logger.getLogger(VersionUtil.class);

	private static Properties prop = new Properties();

	static {
		InputStream fis = null;
		try {
			fis = VersionUtil.class.getResourceAsStream(VERSION_CONFIG);
			prop.load(fis);
		} catch (FileNotFoundException lfex) {
			logger.info("Not Found version config file path: [" + VERSION_CONFIG + "]");
			
		} catch (Exception e) {
			logger.info("Exception version config file path: [" + VERSION_CONFIG + "]:" + e.getMessage());
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (Exception ea) {
					// e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 根据key获取版本配置
	 * 
	 * @param key
	 * @return
	 */
	public static String getVersion(String key) {
		return prop.getProperty(key,"");
	}

	/**
	 * Test Case
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(Thread.currentThread() + ":login==>" + getVersion("login"));
	}
}
