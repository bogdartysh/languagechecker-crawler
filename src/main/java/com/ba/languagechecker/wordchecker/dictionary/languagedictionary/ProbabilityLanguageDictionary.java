package com.ba.languagechecker.wordchecker.dictionary.languagedictionary;

import java.io.FileNotFoundException;
import java.io.IOException;

import com.skjegstad.utils.BloomFilter;

public class ProbabilityLanguageDictionary extends AbstractLanguageDictionary implements ILanguageDictionary {

	private final BloomFilter<String> bloomFilter;

	public ProbabilityLanguageDictionary(final String language, final double falsePositiveProbability) throws FileNotFoundException, IOException {
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
	protected void clearDictionaries() {
		bloomFilter.clear();
	}

	@Override
	protected void addWord(final String word) {
		bloomFilter.add(word);
	}

}
