package com.ba.languagechecker.wordchecker.dictionary.languagedictionary;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import org.apache.log4j.Logger;

public abstract class AbstractLanguageDictionary implements ILanguageDictionary {
	private Logger _log = Logger.getLogger(AbstractLanguageDictionary.class
			.getCanonicalName());

	public static final String PATH_TO_DICTIONARY = "dict/";
	public static final String DICTIONARY_FILE_EXTENSION = ".dict";
	public static final int MAX_AMOUNT_OF_WORDS = 300000;

	public abstract void clearDictionaries();

	public abstract void addWord(final String word);

	@Override
	public void loadDictionaryFile(final String dictionaryFileName)
			throws FileNotFoundException, IOException {
		clearDictionaries();
		try (Reader reader = new InputStreamReader(new FileInputStream(
				dictionaryFileName), "utf-8")) {
			try (BufferedReader in = new BufferedReader(reader)) {
				String line;
				while ((line = in.readLine()) != null) {
					final String[] words = line.split(" ");
					if (words != null)
						for (String word : words) {
							addWord(word.toLowerCase().trim());
						}
				}
			}
		}
		_log.info("dictionary " + dictionaryFileName + " size is "
				+ getDictionarySize());
	}
}
