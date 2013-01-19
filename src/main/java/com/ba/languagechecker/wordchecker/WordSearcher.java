package com.ba.languagechecker.wordchecker;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Set;
import java.util.HashSet;
import org.apache.log4j.Logger;

public class WordSearcher {
	private static final String PATH_TO_DICTIONARY = "dict/";
	private static final String DICTIONARY_FILE_EXTENSION = ".dict";

	private Logger _log = Logger
			.getLogger(TextChecker.class.getCanonicalName());
	private static final int MAX = 300000;
	public final Set<String> dictionary = new HashSet<String>(MAX);

	public WordSearcher(final String language) throws FileNotFoundException,
			IOException {
		super();
		loadDictionaryFile(PATH_TO_DICTIONARY + language
				+ DICTIONARY_FILE_EXTENSION);
	}

	public void loadDictionaryFile(final String dictionaryFileName)
			throws FileNotFoundException, IOException {
		dictionary.clear();
		final FileReader file = new FileReader(dictionaryFileName);
		try (BufferedReader in = new BufferedReader(file)) {
			String word;
			while ((word = in.readLine()) != null) {
				dictionary.add(word.toLowerCase().trim());
			}
		}
		_log.info("dictionary " + dictionaryFileName + " size is "
				+ dictionary.size());
	}

	public boolean isWordInTheDictionary(final String word) {
		return dictionary.contains(word);
	}

}
