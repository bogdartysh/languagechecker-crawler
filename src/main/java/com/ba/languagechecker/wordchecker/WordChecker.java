package com.ba.languagechecker.wordchecker;

public class WordChecker {
	private WordSearcher shouldBeLanguageDictionary;
	private WordSearcher originalLanguageDictionary;

	public boolean isWordOfOriginalLanguage(final String word) {
		return originalLanguageDictionary.isWordInTheDictionary(word)
				&& !shouldBeLanguageDictionary.isWordInTheDictionary(word);
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
