package com.ba.languagechecker.wordchecker;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.log4j.Logger;

public class WordChecker {
	private Logger _log = Logger
			.getLogger(WordChecker.class.getCanonicalName());
	private WordSearcher shouldBeLanguageDictionary;
	private WordSearcher originalLanguageDictionary;

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

		originalLanguageDictionary = new WordSearcher(
				originalLanguage);
		shouldBeLanguageDictionary = new WordSearcher(
				souldBeLanguage);
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

}
