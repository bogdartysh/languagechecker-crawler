package com.ba.languagechecker.wordchecker;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

public class WordChecker {
	private Logger _log = Logger
			.getLogger(WordChecker.class.getCanonicalName());
	private WordSearcher shouldBeLanguageDictionary;
	private WordSearcher originalLanguageDictionary;
	
	private final static Pattern ROMAN_DIGIT_FILTERS = Pattern
			.compile("\\p{Space}?x?(i|ii|iii|iv|v|vi|vii|vii|viii|ix)x?\\p{Space}?");

	public boolean isWordOfOriginalLanguage(final String word) {
		final boolean isOriginal = originalLanguageDictionary
				.isWordInTheDictionary(word);
		final boolean isShouldBe = shouldBeLanguageDictionary
				.isWordInTheDictionary(word);
		_log.info("\"" + word + "\"" + " " + isOriginal + " " + isShouldBe);
		return isOriginal && !isShouldBe;
	}

	public WordChecker(final String originalLanguage,
			final String souldBeLanguage) throws FileNotFoundException,
			IOException {
		super();

		originalLanguageDictionary = new WordSearcher(originalLanguage);
		shouldBeLanguageDictionary = new WordSearcher(souldBeLanguage);
	}

	public WordSearcher getShouldBeLanguageDictionary() {
		return shouldBeLanguageDictionary;
	}

	public void setShouldBeLanguageDictionary(
			WordSearcher shouldBeLanguageDictionary) {
		this.shouldBeLanguageDictionary = shouldBeLanguageDictionary;
	}

	public WordSearcher getOriginalLanguageDictionary() {
		return originalLanguageDictionary;
	}

	public void setOriginalLanguageDictionary(
			WordSearcher originalLanguageDictionary) {
		this.originalLanguageDictionary = originalLanguageDictionary;
	}

	public static boolean isAWord(final String word) {
		if (word == null)
			return false;
		if (word.isEmpty())
			return false;
		if (word.equals(word.toUpperCase()))
			return false;
		if (ROMAN_DIGIT_FILTERS.matcher(word).matches())
			return false;
		return true;
	}

}
