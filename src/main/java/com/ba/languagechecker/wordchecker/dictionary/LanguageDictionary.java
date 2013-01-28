package com.ba.languagechecker.wordchecker.dictionary;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Set;
import java.util.HashSet;
import org.apache.log4j.Logger;

import com.ba.languagechecker.wordchecker.TextChecker;

public class LanguageDictionary {
	private static final String PATH_TO_DICTIONARY = "dict/";
	private static final String DICTIONARY_FILE_EXTENSION = ".dict";

	private Logger _log = Logger
			.getLogger(TextChecker.class.getCanonicalName());
	private static final int MAX = 300000;
	public final Set<Integer> hashedDictionary = new HashSet<Integer>(MAX);
	public final Set<String> wordDictionary = new HashSet<String> (MAX);
	public final boolean couldWordsBeLongerThen31Letters;

	public LanguageDictionary(final String language, final boolean couldWordsBeLongerThen31Letters)
			throws FileNotFoundException, IOException {
		super();
		this.couldWordsBeLongerThen31Letters = couldWordsBeLongerThen31Letters;
		loadDictionaryFile(PATH_TO_DICTIONARY + language
				+ DICTIONARY_FILE_EXTENSION);		
	}

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
							if (couldWordsBeLongerThen31Letters)
								wordDictionary.add(word.toLowerCase().trim());
							else
								hashedDictionary.add(word.toLowerCase().trim().hashCode());
	
						}
				}
			}
		}
		_log.info("dictionary " + dictionaryFileName + " size is "
				+ getDictionarySize());
	}
	private void clearDictionaries() {
		wordDictionary.clear();
		hashedDictionary.clear();
	}
	public int getDictionarySize() {
		return (couldWordsBeLongerThen31Letters)?
			wordDictionary.size():hashedDictionary.size();
	}

	public boolean isWordInTheDictionary(final String word) {
		return (couldWordsBeLongerThen31Letters)?
			wordDictionary.contains(word):
			hashedDictionary.contains(word.hashCode());
	}

}
