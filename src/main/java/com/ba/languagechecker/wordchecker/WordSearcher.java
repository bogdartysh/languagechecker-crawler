package com.ba.languagechecker.wordchecker;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;

public class WordSearcher {
	private static final int MAX = 25000000; 
	private static Set<String> dictionary;

	public void loadDictionaryFile(final String dictionaryFileName)
			throws FileNotFoundException, IOException {
		dictionary = new HashSet(MAX);
		final File file = new File(dictionaryFileName);
		try (BufferedReader in = new BufferedReader(new InputStreamReader(
				new FileInputStream(file)))) {
			String word;
			while ((word = in.readLine()) != null) {
				dictionary.add(word);
			}
		}
		System.out.println("dictionary " + dictionaryFileName + " size is " + dictionary.size()); 
	}


	public boolean isWordInTheDictionary(final String word) {
		return dictionary.contains(word);
	}


/*
	private final Map<String, Set<String>> map = new HashMap<String, Set<String>>(
			MAX);

	public void loadWord(final String word) {

		String sorted = sort(word);
		Set<String> words = map.get(sorted);
		if (words == null) {
			words = new TreeSet<String>();
			words.add(word);
			map.put(sorted, words);
		} else {
			words.add(word);
		}

	}

	public void loadDictionaryFile(final String dictionaryFileName)
			throws FileNotFoundException, IOException {
		final File file = new File(dictionaryFileName);
		try (BufferedReader in = new BufferedReader(new InputStreamReader(
				new FileInputStream(file)))) {
			String word;
			while ((word = in.readLine()) != null) {
				loadWord(word);
			}
		}
		System.out.println("dictionary " + dictionaryFileName + " size is " + map.size()); 
	}

	// Sort the letters of a word
	private String sort(final String s) {
		byte[] ba = s.toLowerCase().getBytes();
		Arrays.sort(ba);
		return new String(ba);
	}

	public boolean isWordInTheDictionary(final String word) {
		return map.containsKey(sort(word));
	}    */
}
