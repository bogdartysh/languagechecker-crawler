package com.ba.languagechecker.wordchecker.typedcheck;

import com.ba.languagechecker.entities.ResultTypeEnum;
import com.ba.languagechecker.wordchecker.dictionary.DictionaryHolder;

public interface ICheckWord {
	public boolean isWordCorrect(final String word,
			final DictionaryHolder checker);

	public ResultTypeEnum getWrongSentenceType();


}
