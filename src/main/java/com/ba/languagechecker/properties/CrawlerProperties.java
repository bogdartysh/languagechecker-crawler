package com.ba.languagechecker.properties;

import java.util.Properties;

public class CrawlerProperties extends Properties {
	public double getFalsePositiveProbability() {
		return Double
				.valueOf(getProperty("false_positive_probability", "0.001"));
	}

}
