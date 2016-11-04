package com.ba.languagechecker.wordchecker.dictionary.holder;

import java.io.IOException;

import com.ba.languagechecker.properties.CrawlerProperties;
import com.ba.languagechecker.properties.TaskProperties;
import com.ba.languagechecker.wordchecker.dictionary.languagedictionary.ILanguageDictionary;

public interface IDictionaryHolder {
	void loadDictionaries(final TaskProperties taskProperties,
			final CrawlerProperties crawlerProperties)
			throws IOException;

	ILanguageDictionary getShouldBeLanguageDictionary();

	ILanguageDictionary getOriginalLanguageDictionary();
}
