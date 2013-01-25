package com.ba.languagechecker.wordchecker.typedcheck;

import java.util.regex.Pattern;

import com.ba.languagechecker.entities.types.ResultTypeEnum;
import com.ba.languagechecker.wordchecker.dictionary.DictionaryHolder;

public class WordIsCanonicalChecker implements ICheckWord {
	
	private final static Pattern ROMAN_DIGIT_FILTERS = Pattern
			.compile("\\p{Space}?x?(i|ii|iii|iv|v|vi|vii|vii|viii|ix)x?\\p{Space}?");

	@Override
	public boolean isWordCorrect(String word, DictionaryHolder checker) {
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

	@Override
	public ResultTypeEnum getWrongSentenceType() {
		return ResultTypeEnum.IS_NOT_A_WORD;
	}



}
