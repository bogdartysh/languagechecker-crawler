package com.ba.languagechecker.properties;

import java.util.Properties;

public class CrawlerProperties extends Properties {

	public String getServerName() {
		return getProperty("server_name", "TEST_DEV");
	}

	public int getNumberOfThreadsForSeleniumTests() {
		return Integer.valueOf(getProperty("selenium_tests_pool_size", "2"));
	}
	
	public double getFalsePositiveProbability() {
		return Double.valueOf(getProperty("false_positive_probability", "0.001"));
	}

}
