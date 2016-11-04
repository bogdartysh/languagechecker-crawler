package com.ba.languagechecker.wordchecker.dictionary.languagedictionary;

import java.io.IOException;

import com.skjegstad.utils.BloomFilter;

public class ProbabilityLanguageDictionary extends AbstractLanguageDictionary
		implements
		ILanguageDictionary {

	public final BloomFilter<String> bloomFilter;

	public ProbabilityLanguageDictionary(final String language, final double falsePositiveProbability) throws IOException {
		super();

		bloomFilter = new BloomFilter<>(falsePositiveProbability, MAX_AMOUNT_OF_WORDS);
		loadDictionaryFile(PATH_TO_DICTIONARY + language + DICTIONARY_FILE_EXTENSION);
	}

	@Override
	public int getDictionarySize() {
		return bloomFilter.size();
	}

	@Override
	public boolean isWordInTheDictionary(final String word) {
		return bloomFilter.contains(word);
	}

	@Override
	public void clearDictionaries() {
		bloomFilter.clear();
	}

	@Override
	public void addWord(final String word) {
		bloomFilter.add(word);
	}

}
