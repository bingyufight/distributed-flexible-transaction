package com.distributed.transaction.common.util;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;




/**
 * 
 * @author yubing
 *
 */
public class ConfigUtil {
	
	private static Logger logger = LoggerFactory.getLogger(ConfigUtil.class);
	
	



	
	
	private static Map<String,Properties> proPerties = new ConcurrentHashMap<String,Properties>();
	

	public static Properties getProperties(String configFileName) {
		if (proPerties.get(configFileName) == null) {
			synchronized (ConfigUtil.class) {
				if (proPerties.get(configFileName) == null) {
					
					Properties PROPERTIES = ConfigUtil.loadProperties(configFileName, false, false);
					proPerties.put(configFileName, PROPERTIES);
					
				}
			}
		}
		return proPerties.get(configFileName);
	}

	public static String getProperty(String configFileName, String key) {
		return getProperties(configFileName).getProperty(key, null);
	}

	public static String getProperty(String configFileName,String key, String defaultValue) {
		return getProperties(configFileName).getProperty(key, defaultValue);
	}

	
	public static Properties loadProperties(String fileName,
			boolean allowMultiFile, boolean allowEmptyFile) {
		Properties properties = new Properties();
		if (fileName.startsWith("/")) {
			try {
				FileInputStream input = new FileInputStream(fileName);
				try {
					properties.load(input);
				} finally {
					input.close();
				}
			} catch (Throwable e) {
				logger.warn("Failed to load " + fileName + " file from "
						+ fileName + "(ingore this file): " + e.getMessage(), e);
			}
			return properties;
		}

		List<java.net.URL> list = new ArrayList<java.net.URL>();
		try {
			Enumeration<java.net.URL> urls = ClassHelpUtil.getClassLoader()
					.getResources(fileName);
			list = new ArrayList<java.net.URL>();
			while (urls.hasMoreElements()) {
				list.add(urls.nextElement());
			}
		} catch (Throwable t) {
			logger.warn(
					"Fail to load " + fileName + " file: " + t.getMessage(), t);
		}

		if (list.size() == 0) {
			if (!allowEmptyFile) {
				logger.warn("No " + fileName + " found on the class path.");
			}
			return properties;
		}

		if (!allowMultiFile) {
			if (list.size() > 1) {
				String errMsg = String
						.format("only 1 %s file is expected, but %d dubbo.properties files found on class path: %s",
								fileName, list.size(), list.toString());
				logger.warn(errMsg);
				// throw new IllegalStateException(errMsg); // see
				// http://code.alibabatech.com/jira/browse/DUBBO-133
			}
			try {
				properties.load(ClassHelpUtil.getClassLoader()
						.getResourceAsStream(fileName));
			} catch (Throwable e) {
				logger.warn("Failed to load " + fileName + " file from "
						+ fileName + "(ingore this file): " + e.getMessage(), e);
			}
			return properties;
		}

		logger.info("load " + fileName + " properties file from " + list);

		for (java.net.URL url : list) {
			try {
				Properties p = new Properties();
				InputStream input = url.openStream();
				if (input != null) {
					try {
						p.load(input);
						properties.putAll(p);
					} finally {
						try {
							input.close();
						} catch (Throwable t) {
						}
					}
				}
			} catch (Throwable e) {
				logger.warn("Fail to load " + fileName + " file from " + url
						+ "(ingore this file): " + e.getMessage(), e);
			}
		}

		return properties;
	}
}

