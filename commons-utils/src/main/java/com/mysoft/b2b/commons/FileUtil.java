package com.mysoft.b2b.commons;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

/**
 * 文件读取工具类
 * 
 * @author ganq
 * 
 */
public final class FileUtil {

	private static final Logger logger = Logger.getLogger(FileUtil.class);
	// Linux 下公用文件路径
	private static final String COMMON_CONFIG_LINUX = "/www/target/images/common/";
	// Windows 下公用文件路径
	private static final String COMMON_CONFIG_WINDOWS = "E:\\www\\target\\images\\common\\";
	private static final String COMMON_CONFIG_KEY = "common_page";

	private static Map<String, String> map = new HashMap<String, String>();

	static {
		File directory = null;
		directory = new File(COMMON_CONFIG_LINUX);
		if (directory.exists()) {
			// find common html config file path in linux.
			map.put(COMMON_CONFIG_KEY, COMMON_CONFIG_LINUX);
		} else {
			logger.info("Not Found common html directory at : [" + COMMON_CONFIG_LINUX + "]");
			logger.info("Start loading common html directory at: [" + COMMON_CONFIG_WINDOWS + "]");
			
			directory = new File(COMMON_CONFIG_WINDOWS);
			if (directory.exists()) {
				// find common html config file path in window.
				map.put(COMMON_CONFIG_KEY, COMMON_CONFIG_WINDOWS);
			} else {
				map.put(COMMON_CONFIG_KEY, "");
				logger.error("Load common html config file error!");
			}
		}
	}

	/**
	 * 读取文本文件内容
	 * 
	 * @param path
	 * @return
	 */
	public static String readTxtFileContent(String path) {

		if (!new File(path).exists()) {
			logger.error("file :'" + path + "' not found!");
			return "";
		}

		InputStreamReader inputReader = null;
		BufferedReader bufferReader = null;
		StringBuilder strBuider = new StringBuilder();
		try {
			InputStream inputStream = new FileInputStream(path);
			inputReader = new InputStreamReader(inputStream);
			bufferReader = new BufferedReader(inputReader);

			// 读取一行
			String line = null;
			while ((line = bufferReader.readLine()) != null) {
				strBuider.append(line);
			}

		} catch (IOException e) {
			logger.error("Read file error :" + e.getMessage());
		} finally {
			try {
				inputReader.close();

				if (bufferReader != null) {
					bufferReader.close();
				}
				if (inputReader != null) {
					inputReader.close();
				}
			} catch (IOException e) {
				logger.error("close file stream error :" + e.getMessage());
			}
		}
		return strBuider.toString();
	}

	/**
	 * 获取磁盘文件内容
	 * 
	 * @param filePath
	 * @param breakLine
	 */
	public static String getFileContentFromDisk(String page, boolean breakLine) {
		BufferedReader reader = null;
		InputStreamReader inputer = null;
		String filePath = map.get(COMMON_CONFIG_KEY) + page + ".html";
		String readStr = "";
		try {
			File file = new File(filePath);
			if (!file.exists()) {
				logger.error("file :'" + filePath + "' not found!");
				return "";
			}
			inputer = new InputStreamReader(new FileInputStream(file), "UTF-8");
			reader = new BufferedReader(inputer);

			String line = "";
			String breakStr = breakLine ? "\r\n" : "";
			while ((line = reader.readLine()) != null) {
				readStr = readStr + line + breakStr;
			}
		} catch (IOException e) {
			logger.error("read " + filePath + " file error :" + e.getMessage());
		} finally {
			if (inputer != null) {
				try {
					inputer.close();
				} catch (IOException e) {
					
				}
			}
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					
				}
			}
		}
		return readStr;
	}

	/**
	 * Test Case
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(readTxtFileContent("c:\\index.dvm"));

		System.out.println(getFileContentFromDisk("c:\\index.dvm", true));
	}
}
