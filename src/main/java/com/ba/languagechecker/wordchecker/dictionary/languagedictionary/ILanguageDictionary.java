package com.ba.languagechecker.wordchecker.dictionary.languagedictionary;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface ILanguageDictionary {
	public void loadDictionaryFile(final String dictionaryFileName)
			throws FileNotFoundException, IOException;

	public int getDictionarySize();
	
	public boolean isWordInTheDictionary(final String word);

}
