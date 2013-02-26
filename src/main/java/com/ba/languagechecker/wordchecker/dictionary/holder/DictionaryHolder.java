package com.ba.languagechecker.wordchecker.dictionary.holder;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.ba.languagechecker.properties.CrawlerProperties;
import com.ba.languagechecker.properties.TaskProperties;
import com.ba.languagechecker.wordchecker.dictionary.languagedictionary.ILanguageDictionary;
import com.ba.languagechecker.wordchecker.dictionary.languagedictionary.HashSetLanguageDictionary;
import com.ba.languagechecker.wordchecker.dictionary.languagedictionary.ProbabilityLanguageDictionary;

public class DictionaryHolder extends AbstractDictionaryHolder {
	
	private Logger _log = Logger.getLogger(DictionaryHolder.class
			.getCanonicalName());
	private Map<String, ILanguageDictionary> availableDictionaries = new HashMap<String, ILanguageDictionary>(
			2);

	private static final DictionaryHolder DICTIONARY_HOLDER_INSTANCE = new DictionaryHolder();

	public static IDictionaryHolder getInstance() {
		return DICTIONARY_HOLDER_INSTANCE;
	}

	protected DictionaryHolder() {
		super();
	}

	public void loadDictionaries(final TaskProperties taskProperties,
			final CrawlerProperties crawlerProperties)
			throws FileNotFoundException, IOException {
		originalLanguageDictionary = getDictionary(
				taskProperties.getOriginalLanguageCode(),
				crawlerProperties.getFalsePositiveProbability());
		shouldBeLanguageDictionary = getDictionary(
				taskProperties.getProperty("shouldbe_language_code"),
				crawlerProperties.getFalsePositiveProbability());
		_log.debug("dictionaries loaded");
	}

	private ILanguageDictionary getDictionary(final String languageCode,
			final double falsePositiveProbability)
			throws FileNotFoundException, IOException {
		if (availableDictionaries.containsKey(languageCode)) {
			_log.info(languageCode + " dictionary already loaded");
			return availableDictionaries.get(languageCode);
		} else {
			_log.info("loading dictionary " + languageCode + " dictionary");

			final ILanguageDictionary dict = (falsePositiveProbability<=0)?
					new HashSetLanguageDictionary(languageCode):
						new ProbabilityLanguageDictionary(languageCode, falsePositiveProbability);
			availableDictionaries.put(languageCode, dict);
			return dict;
		}
	}


}
