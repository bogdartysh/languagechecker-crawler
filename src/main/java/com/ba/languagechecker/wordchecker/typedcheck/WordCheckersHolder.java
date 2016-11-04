package com.ba.languagechecker.wordchecker.typedcheck;

import java.util.LinkedList;

import java.util.List;

import org.apache.log4j.Logger;

import com.ba.languagechecker.entities.types.ResultTypeEnum;
import com.ba.languagechecker.properties.TaskProperties;
import com.ba.languagechecker.wordchecker.dictionary.holder.DictionaryHolder;

public class WordCheckersHolder {

	public final static ICheckWord wordIsCanonicalChecker = new WordIsCanonicalChecker();
	public final static ICheckWord wordLanguageChecker = new WordLanguageChecker();
	public final static ICheckWord wordLexicChecker = new WordLexicChecker();
	public static Logger _log = Logger.getLogger(WordCheckersHolder.class
			.getCanonicalName());

	public List<ICheckWord> checkers;

	public static final WordCheckersHolder HOLDER_INSTANCE = new WordCheckersHolder();

	public static WordCheckersHolder getInstance() {
		return WordCheckersHolder.HOLDER_INSTANCE;
	}

	public void setProperties(final TaskProperties taskProperties) {
		checkers = new LinkedList<ICheckWord>();
		if (taskProperties.IsLanguageCheckable()) {
			_log.info("language will be checked");
			checkers.add(wordLanguageChecker);
		}

		if (taskProperties.IsLexicCheckable()) {
			_log.info("lexic will be checked");
			checkers.add(wordLexicChecker);
		}

	}

	public ResultTypeEnum checkWord(final String word) {
		for (ICheckWord checker : checkers) {
			if (!checker.isWordCorrect(word, DictionaryHolder.getInstance())) {
				return checker.getWrongSentenceType();
			}
		}
		return ResultTypeEnum.OK;
	}

}
