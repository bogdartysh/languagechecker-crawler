package com.ba.languagechecker.properties;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.ba.languagechecker.wordchecker.dictionary.DictionaryHolder;

public class TaskProperties extends Properties {
	private Logger _log = Logger.getLogger(DictionaryHolder.class
			.getCanonicalName());

	public TaskProperties() {
		super();
	}

	public List<String> getStartUrls() {
		return getListedValues("start_url");
	}

	public boolean IsPageTitleCheckable() {
		return Boolean.valueOf(getProperty("check_page_title", "true"));
	}

	public boolean IsPageTextCheckable() {
		return Boolean.valueOf(getProperty("check_page_text", "true"));
	}

	public boolean IsLexicCheckable() {
		return Boolean.valueOf(getProperty("check_page_lexic", "true"));
	}

	public boolean IsLanguageCheckable() {
		return Boolean.valueOf(getProperty("check_page_language", "true"));
	}
	public boolean IsOnlyBodyCheckable() {
		return Boolean.valueOf(getProperty("check_only_page_body", "false"));
	}


	public List<String> getExcludedWordsFromChecking() {
		return getListedValues("excluded_words_from_checking");
	}

	private List<String> getListedValues(final String propertyName) {
		final String values = this.getProperty(propertyName);
		if (values == null || values.isEmpty()) {
			_log.debug(propertyName + " is empty");
			return new LinkedList<String>();
		}

		final List<String> valueList = Arrays.asList(values.split(","));
		for (String value : valueList)
			_log.debug(propertyName + " is " + value);
		return valueList;
	}

}
