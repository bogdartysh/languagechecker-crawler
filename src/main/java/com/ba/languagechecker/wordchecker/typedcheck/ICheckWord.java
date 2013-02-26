package com.ba.languagechecker.wordchecker.typedcheck;

import com.ba.languagechecker.entities.types.ResultTypeEnum;
import com.ba.languagechecker.wordchecker.dictionary.holder.IDictionaryHolder;

public interface ICheckWord {
	public boolean isWordCorrect(final String word,
			final IDictionaryHolder checker);

	public ResultTypeEnum getWrongSentenceType();


}
