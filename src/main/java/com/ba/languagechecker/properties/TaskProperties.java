package com.ba.languagechecker.properties;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class TaskProperties extends Properties{
	public TaskProperties() {
		super();
	}
	public List<String> getStartUrls() {		
		final String urlProperty = this.getProperty("start_url");
		return Arrays.asList(urlProperty.split(","));		
	}

}
