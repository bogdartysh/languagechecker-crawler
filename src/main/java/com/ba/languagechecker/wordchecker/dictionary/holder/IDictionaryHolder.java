package com.ba.languagechecker.wordchecker.dictionary.holder;

import java.io.FileNotFoundException;
import java.io.IOException;

import com.ba.languagechecker.properties.CrawlerProperties;
import com.ba.languagechecker.properties.TaskProperties;
import com.ba.languagechecker.wordchecker.dictionary.languagedictionary.ILanguageDictionary;

public interface IDictionaryHolder {
	public void loadDictionaries(final TaskProperties taskProperties,
			final CrawlerProperties crawlerProperties)
			throws FileNotFoundException, IOException;

	public ILanguageDictionary getShouldBeLanguageDictionary();

	public void setShouldBeLanguageDictionary(
			final ILanguageDictionary shouldBeLanguageDictionary);

	public ILanguageDictionary getOriginalLanguageDictionary();

	public void setOriginalLanguageDictionary(
			final ILanguageDictionary originalLanguageDictionary);

}
