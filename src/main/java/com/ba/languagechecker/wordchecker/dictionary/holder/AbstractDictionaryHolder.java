package com.ba.languagechecker.wordchecker.dictionary.holder;

import com.ba.languagechecker.wordchecker.dictionary.languagedictionary.ILanguageDictionary;

public abstract class AbstractDictionaryHolder implements IDictionaryHolder {
	protected ILanguageDictionary shouldBeLanguageDictionary;
	protected ILanguageDictionary originalLanguageDictionary;

	public ILanguageDictionary getShouldBeLanguageDictionary() {
		return shouldBeLanguageDictionary;
	}

	public ILanguageDictionary getOriginalLanguageDictionary() {
		return originalLanguageDictionary;
	}
}
