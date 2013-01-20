package com.ba.languagechecker.wordchecker.dictionary;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.log4j.Logger;

import com.ba.languagechecker.properties.TaskProperties;

public class DictionaryHolder {
	private Logger _log = Logger.getLogger(DictionaryHolder.class
			.getCanonicalName());
	private LanguageDictionary shouldBeLanguageDictionary;
	private LanguageDictionary originalLanguageDictionary;
	private static final DictionaryHolder DICTIONARY_HOLDER_INSTANCE = new DictionaryHolder();

	public static DictionaryHolder getInstance() {
		return DICTIONARY_HOLDER_INSTANCE;
	}

	public void loadDictionaries(final TaskProperties taskProperties)
			throws FileNotFoundException, IOException {
		originalLanguageDictionary = new LanguageDictionary(
				taskProperties.getProperty("origin_language_code"));
		shouldBeLanguageDictionary = new LanguageDictionary(
				taskProperties.getProperty("shouldbe_language_code"));
		_log.debug("dictionaries loaded");
	}

	public LanguageDictionary getShouldBeLanguageDictionary() {
		return shouldBeLanguageDictionary;
	}

	public void setShouldBeLanguageDictionary(
			LanguageDictionary shouldBeLanguageDictionary) {
		this.shouldBeLanguageDictionary = shouldBeLanguageDictionary;
	}

	public LanguageDictionary getOriginalLanguageDictionary() {
		return originalLanguageDictionary;
	}

	public void setOriginalLanguageDictionary(
			LanguageDictionary originalLanguageDictionary) {
		this.originalLanguageDictionary = originalLanguageDictionary;
	}
}
