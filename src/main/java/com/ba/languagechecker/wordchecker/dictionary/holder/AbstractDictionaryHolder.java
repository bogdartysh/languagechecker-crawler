package com.ba.languagechecker.wordchecker.dictionary.holder;

import com.ba.languagechecker.wordchecker.dictionary.languagedictionary.ILanguageDictionary;

public abstract class AbstractDictionaryHolder implements IDictionaryHolder {
	protected ILanguageDictionary shouldBeLanguageDictionary;
	protected ILanguageDictionary originalLanguageDictionary;


	public ILanguageDictionary getShouldBeLanguageDictionary() {
		return shouldBeLanguageDictionary;
	}

	public void setShouldBeLanguageDictionary(
			final ILanguageDictionary shouldBeLanguageDictionary) {
		this.shouldBeLanguageDictionary = shouldBeLanguageDictionary;
	}

	public ILanguageDictionary getOriginalLanguageDictionary() {
		return originalLanguageDictionary;
	}

	public void setOriginalLanguageDictionary(
			ILanguageDictionary originalLanguageDictionary) {
		this.originalLanguageDictionary = originalLanguageDictionary;
	}
}
