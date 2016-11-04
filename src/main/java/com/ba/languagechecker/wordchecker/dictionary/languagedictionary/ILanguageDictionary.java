package com.ba.languagechecker.wordchecker.dictionary.languagedictionary;

import java.io.IOException;

public interface ILanguageDictionary {
	void loadDictionaryFile(final String dictionaryFileName)
			throws IOException;

	int getDictionarySize();

	boolean isWordInTheDictionary(final String word);

}
