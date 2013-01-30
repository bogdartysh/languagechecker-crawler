package com.ba.languagechecker.properties;

import java.util.Properties;

public class CrawlerProperties extends Properties {

	private static final long serialVersionUID = 1132154879L;

	public String getServerName() {
		return getProperty("server_name", "TEST_DEV");
	}

	public int getNumberOfThreadsForSeleniumTests() {
		return Integer.valueOf(getProperty("selenium_tests_pool_size", "2"));
	}

}
