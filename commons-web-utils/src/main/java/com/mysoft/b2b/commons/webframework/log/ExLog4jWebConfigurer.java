package com.mysoft.b2b.commons.webframework.log;

import java.io.FileNotFoundException;

import javax.servlet.ServletContext;

import org.springframework.util.Log4jConfigurer;
import org.springframework.util.ResourceUtils;
import org.springframework.util.SystemPropertyUtils;
import org.springframework.web.util.Log4jWebConfigurer;
import org.springframework.web.util.WebUtils;

public class ExLog4jWebConfigurer {
	/** Extension that indicates a log4j XML config file: ".xml" */
	public static final String XML_FILE_EXTENSION = ".xml";

	/**
	 * Initialize log4j, including setting the web app root system property.
	 * 
	 * @param servletContext
	 *            the current ServletContext
	 * @see WebUtils#setWebAppRootSystemProperty
	 */
	public static void initLogging(ServletContext servletContext) {
		// Expose the web app root system property.
		if (exposeWebAppRoot(servletContext)) {
			WebUtils.setWebAppRootSystemProperty(servletContext);
		}

		// Only perform custom log4j initialization in case of a config file.
		String location = servletContext.getInitParameter(Log4jWebConfigurer.CONFIG_LOCATION_PARAM);
		if (location != null) {
			// Perform actual log4j initialization; else rely on log4j's default
			// initialization.
			try {
				// Return a URL (e.g. "classpath:" or "file:") as-is;
				// consider a plain file path as relative to the web application
				// root directory.
				if (!ResourceUtils.isUrl(location)) {
					// Resolve system property placeholders before resolving
					// real path.
					location = SystemPropertyUtils.resolvePlaceholders(location);
					if (location.startsWith(ResourceUtils.FILE_URL_PREFIX)) {
						location = WebUtils.getRealPath(servletContext, location);
					}
				}

				// Write log message to server log.
				servletContext.log("Initializing log4j from [" + location + "]");

				// Check whether refresh interval was specified.
				String intervalString = servletContext.getInitParameter(Log4jWebConfigurer.REFRESH_INTERVAL_PARAM);
				if (intervalString != null) {
					// Initialize with refresh interval, i.e. with log4j's
					// watchdog thread,
					// checking the file in the background.
					try {
						long refreshInterval = Long.parseLong(intervalString);
						Log4jConfigurer.initLogging(location, refreshInterval);
					} catch (NumberFormatException ex) {
						throw new IllegalArgumentException("Invalid 'log4jRefreshInterval' parameter: "
								+ ex.getMessage());
					}
				} else {
					// Initialize without refresh check, i.e. without log4j's
					// watchdog thread.
					Log4jConfigurer.initLogging(location);
				}
			} catch (FileNotFoundException ex) {
				throw new IllegalArgumentException("Invalid 'log4jConfigLocation' parameter: " + ex.getMessage());
			}
		}
	}

	/**
	 * Return whether to expose the web app root system property, checking the
	 * corresponding ServletContext init parameter.
	 * 
	 * @see #EXPOSE_WEB_APP_ROOT_PARAM
	 */
	private static boolean exposeWebAppRoot(ServletContext servletContext) {
		String exposeWebAppRootParam = servletContext.getInitParameter(Log4jWebConfigurer.EXPOSE_WEB_APP_ROOT_PARAM);
		return (exposeWebAppRootParam == null || Boolean.valueOf(exposeWebAppRootParam));
	}
}
