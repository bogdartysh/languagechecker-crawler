package com.ba.languagechecker.wordchecker.dictionary;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.ba.languagechecker.properties.TaskProperties;

public class DictionaryHolder {
	private Logger _log = Logger.getLogger(DictionaryHolder.class
			.getCanonicalName());
	private Map<String, LanguageDictionary> availableDictionaries = new HashMap<String, LanguageDictionary>(
			2);
	private LanguageDictionary shouldBeLanguageDictionary;
	private LanguageDictionary originalLanguageDictionary;
	private static final DictionaryHolder DICTIONARY_HOLDER_INSTANCE = new DictionaryHolder();

	public static DictionaryHolder getInstance() {
		return DICTIONARY_HOLDER_INSTANCE;
	}

	protected DictionaryHolder() {
		super();
	}

	public void loadDictionaries(final TaskProperties taskProperties)
			throws FileNotFoundException, IOException {
		originalLanguageDictionary = getDictionary(taskProperties
				.getProperty("origin_language_code"),
					taskProperties.couldWordsBeLongerThen31Letters());
		shouldBeLanguageDictionary = getDictionary(taskProperties
				.getProperty("shouldbe_language_code"), 
					taskProperties.couldWordsBeLongerThen31Letters());
		_log.debug("dictionaries loaded");
	}

	private LanguageDictionary getDictionary(final String languageCode, 
			final boolean couldWordsBeLongerThen31Letters)
			throws FileNotFoundException, IOException {
		if (availableDictionaries.containsKey(languageCode)) {
			_log.info(languageCode + " dictionary already loaded");
			return availableDictionaries.get(languageCode);
		} else {
			_log.info("loading dictionary " + languageCode + " dictionary");
			final LanguageDictionary dict = new LanguageDictionary(languageCode, 
				couldWordsBeLongerThen31Letters);
			availableDictionaries.put(languageCode, dict);
			return dict;
		}
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
