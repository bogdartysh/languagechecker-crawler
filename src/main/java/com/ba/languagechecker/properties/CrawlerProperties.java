package com.ba.languagechecker.properties;

import java.util.Properties;

public class CrawlerProperties extends Properties {

	public String getServerName() {
		return getProperty("server_name", "TEST_DEV");
	}

}
