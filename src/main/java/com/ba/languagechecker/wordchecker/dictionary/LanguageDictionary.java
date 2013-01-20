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
	public final Set<String> dictionary = new HashSet<String>(MAX);

	public LanguageDictionary(final String language)
			throws FileNotFoundException, IOException {
		super();
		loadDictionaryFile(PATH_TO_DICTIONARY + language
				+ DICTIONARY_FILE_EXTENSION);
	}

	public void loadDictionaryFile(final String dictionaryFileName)
			throws FileNotFoundException, IOException {
		dictionary.clear();
		try (Reader reader = new InputStreamReader(new FileInputStream(
				dictionaryFileName), "utf-8")) {
			try (BufferedReader in = new BufferedReader(reader)) {
				String line;
				while ((line = in.readLine()) != null) {
					final String[] words = line.split(" ");
					if (words != null)
						for (String word : words) {
							dictionary.add(word.toLowerCase().trim());
						}
				}
			}
		}
		_log.info("dictionary " + dictionaryFileName + " size is "
				+ dictionary.size());
	}

	public boolean isWordInTheDictionary(final String word) {
		return dictionary.contains(word);
	}

}
