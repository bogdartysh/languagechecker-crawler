package com.ba.languagechecker.wordchecker.typedcheck;

import org.apache.log4j.Logger;

import com.ba.languagechecker.entities.types.ResultTypeEnum;
import com.ba.languagechecker.wordchecker.dictionary.holder.IDictionaryHolder;

public class WordLanguageChecker implements ICheckWord {
	private Logger _log = Logger.getLogger(WordLanguageChecker.class
			.getCanonicalName());

	@Override
	public boolean isWordCorrect(final String word,
			final IDictionaryHolder dictionary) {
		final boolean isOriginal = dictionary.getOriginalLanguageDictionary()
				.isWordInTheDictionary(word);
		if (!isOriginal) {
			_log.debug("word=\"" + word + "\" is not of original language");
			return true;
		}
		final boolean isShouldBe = dictionary.getShouldBeLanguageDictionary()
				.isWordInTheDictionary(word);
		_log.debug("word=\"" + word + "\"" + " isShouldBe=" + isShouldBe);
		return isShouldBe;
	}

	@Override
	public ResultTypeEnum getWrongSentenceType() {
		return ResultTypeEnum.LANGUAGE;
	}

}
