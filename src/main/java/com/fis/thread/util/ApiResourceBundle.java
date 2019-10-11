package com.fis.thread.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

/**
 * @author PhucVM3
 * @date 02/05/2018 16:30:21
 */
@Configuration
@PropertySource("file:../etc/config.properties")
public class ApiResourceBundle {
	
	@Autowired
    private Environment env;
	
	public String getProperty(String key) {
		return env.getProperty(key);
	}
	
	public String getProperty(String key, String defaultValue) {
		return env.getProperty(key, defaultValue);
	}
}
