package com.ba.languagechecker.properties;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.ba.languagechecker.wordchecker.dictionary.holder.DictionaryHolder;

public class TaskProperties extends Properties {
	private static final long serialVersionUID = 352354575674356L;
	private Logger _log = Logger.getLogger(DictionaryHolder.class
			.getCanonicalName());

	public TaskProperties() {
		super();
	}

	public List<String> getStartUrls() {
		return getListedValues("start_urls");
	}

	public List<String> getExcludedUrls() {
		return getListedValues("excluded_urls");
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

	public boolean ShouldSaveWrongSentences() {
		return Boolean.valueOf(getProperty("save_wrong_sentences", "true"));
	}

	public List<String> getExcludedWordsFromChecking() {
		return getListedValues("excluded_words_from_checking");
	}

	private List<String> getListedValues(final String propertyName) {
		final String values = this.getProperty(propertyName);
		if (values == null || values.isEmpty()) {
			_log.debug(propertyName + " is empty");
			return new LinkedList<>();
		}

		final List<String> valueList = Arrays.asList(values.split(","));
		for (String value : valueList)
			_log.debug(propertyName + " is " + value);
		return valueList;
	}

	public String getOutputFileName() {
		return getProperty("output_csv_file_name", getTaskExternalId() + "_"
				+ (new Date().getTime() / 1000L) + "_out.csv");
	}

	public int getMaxCommonLanguageWords() {
		return Integer.valueOf(getProperty("max_common_language_words", "2"));
	}

	public String getTaskExternalId() {
		return getProperty("task_external_id", "unknown");
	}

	public Collection<String> getDivElementsToClear() {
		return getListedValues("skip_div_ids");
	}
	public boolean shouldSkipReferences() {
		return Boolean.valueOf(getProperty("skip_references", "false"));
	}

	public String getOriginalLanguageCode() {
		return getProperty("origin_language_code");
	}


	public String getUrlPattern() {
		return getProperty("url_pattern", ".*");
	}
}
