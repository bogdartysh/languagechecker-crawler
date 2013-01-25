package com.ba.languagechecker.wordchecker.typedcheck;

import com.ba.languagechecker.entities.types.ResultTypeEnum;
import com.ba.languagechecker.wordchecker.dictionary.DictionaryHolder;

public class WordLexicChecker implements ICheckWord {

	@Override
	public boolean isWordCorrect(String word, DictionaryHolder checker) {
		return checker.getShouldBeLanguageDictionary().isWordInTheDictionary(
				word);
	}

	@Override
	public ResultTypeEnum getWrongSentenceType() {
		return ResultTypeEnum.LEXIC;
	}



}
