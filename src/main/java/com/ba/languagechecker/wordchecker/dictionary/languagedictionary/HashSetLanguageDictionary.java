package com.ba.languagechecker.wordchecker.dictionary.languagedictionary;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class HashSetLanguageDictionary extends AbstractLanguageDictionary
		implements
			ILanguageDictionary {

	public final Set<String> wordDictionary = new HashSet<String>(
			MAX_AMOUNT_OF_WORDS);

	public HashSetLanguageDictionary(final String language)
			throws  IOException {
		super();

		loadDictionaryFile(PATH_TO_DICTIONARY + language
				+ DICTIONARY_FILE_EXTENSION);
	}

	@Override
	public void clearDictionaries() {
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
	public void addWord(final String word) {
		wordDictionary.add(word);
	}

}
