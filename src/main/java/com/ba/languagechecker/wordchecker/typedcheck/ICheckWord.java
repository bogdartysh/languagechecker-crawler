package com.ba.languagechecker.wordchecker.typedcheck;

import com.ba.languagechecker.entities.WrongSentenceType;
import com.ba.languagechecker.wordchecker.dictionary.DictionaryHolder;

public interface ICheckWord {
	public boolean isWordCorrect(final String word,
			final DictionaryHolder checker);

	public WrongSentenceType getWrongSentenceType();


}
