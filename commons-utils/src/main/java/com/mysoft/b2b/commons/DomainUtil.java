package com.mysoft.b2b.commons;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * 获取域名工具类
 * 
 * @author subh
 * 
 */
public class DomainUtil {
	// Linux 下domian配置文件路径
	private static final String DOMAIN_CONFIG_LINUX = "/etc/b2bdomain.conf";
	// Windows 下domain配置文件路径
	private static final String DOMAIN_CONFIG_WINDOWS = "c:/b2bdomain.conf";

	private static final Logger logger = Logger.getLogger(DomainUtil.class);

	private static Properties prop = new Properties();

	static {
		FileInputStream fis = null;
		try {
			// 首先加载linux下域名配置文件
			fis = new FileInputStream(DOMAIN_CONFIG_LINUX);
			prop.load(fis);
		} catch (Exception lfex) {
			logger.info("Not Found domain config file path: [" + DOMAIN_CONFIG_LINUX + "]");
			logger.info("Start loading domain config file path: [" + DOMAIN_CONFIG_WINDOWS + "]");

			try {
				// 然后查找windows下域名配置文件
				fis = new FileInputStream(DOMAIN_CONFIG_WINDOWS);
				prop.load(fis);
			} catch (FileNotFoundException wfex) {
				logger.error("Not Found domain config file, path: [" + DOMAIN_CONFIG_LINUX + ", "
						+ DOMAIN_CONFIG_WINDOWS + "]");
			} catch (Exception ex) {
				logger.error("Load domain config file error!");
			}
		} finally {
			// close fis
			if (fis != null) {
				try {
					fis.close();
				} catch (Exception e) {
					// e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 根据key获取域名配置
	 * 
	 * @param key
	 * @return
	 */
	public static String getDomain(String key) {
        String domainPath = prop.getProperty(key, "");
        if(!domainPath.startsWith("http://") && !domainPath.startsWith("https://")) {
            domainPath = "http://" +domainPath;
        }
        return domainPath;
	}

	/**
	 * Test Case
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(Thread.currentThread() + ":login==>" + getDomain("login"));
	}
}
