package com.app.tests;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Log4jDemo {
	
	
public static final Logger logger = LogManager.getLogger();
	
	public static void main(String[] args) {
		logger.info("This is info log step");
		logger.warn("This is a warning");
		logger.debug("This is a debug message");
		logger.error("This is a error step");
	}

}
