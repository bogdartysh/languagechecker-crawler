package com.ba.languagechecker.wordchecker.dictionary.languagedictionary;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Set;
import java.util.HashSet;


public class HashSetLanguageDictionary extends AbstractLanguageDictionary implements
		ILanguageDictionary {

	public final Set<String> wordDictionary = new HashSet<String>(
			MAX_AMOUNT_OF_WORDS);

	public HashSetLanguageDictionary(final String language)
			throws FileNotFoundException, IOException {
		super();

		loadDictionaryFile(PATH_TO_DICTIONARY + language
				+ DICTIONARY_FILE_EXTENSION);
	}

	@Override
	protected void clearDictionaries() {
		wordDictionary.clear();
	}

	@Override
	public int getDictionarySize() {
		return wordDictionary.size();
	}

	@Override
	public boolean isWordInTheDictionary(final String word) {
		return wordDictionary.contains(word);
	}

	@Override
	protected void addWord(final String word) {
		wordDictionary.add(word);
	}

}
