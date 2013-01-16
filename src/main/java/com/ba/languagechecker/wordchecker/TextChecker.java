package com.ba.languagechecker.wordchecker;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.ba.languagechecker.entities.WrongSentence;

public class TextChecker {
	private Logger _log = Logger
			.getLogger(TextChecker.class.getCanonicalName());
	private WordChecker wordChecker;
	private static final String WORD_PATTERN_EXPR = "[.,\"\'\\p{Blank}\\s][\\p{L}\\w]+[\\p{Blank}\\s.,\"\']";
	private static final Pattern WORD_PATTERN = Pattern
			.compile(WORD_PATTERN_EXPR);

	public WordChecker getWordChecker() {
		return wordChecker;
	}

	public void setWordChecker(WordChecker wordChecker) {
		this.wordChecker = wordChecker;
	}

	public List<WrongSentence> getErrorSentences(final String text) {
		final List<WrongSentence> wrongSentences = new LinkedList<WrongSentence>();
		final Matcher matcher = WORD_PATTERN.matcher(text);

		WrongSentence currentSentense = null;

		while (matcher.find()) {
			final String word = matcher.group();
			_log.info("trying word " + matcher.group());
			if (getWordChecker().isWordOfOriginalLanguage(word)) {
				if (currentSentense == null) {
					currentSentense = getCurrentSentence(matcher, word);
				} else {
					addNewWordToSentenc(matcher, word, currentSentense);
				}
			} else {
				if (currentSentense != null) {
					storeSentence(matcher, word, currentSentense);
					currentSentense = null;
				}
			}

		}
		return wrongSentences;
	}

	private WrongSentence getCurrentSentence(final Matcher matcher,
			final String word) {
		final WrongSentence result = new WrongSentence();
		result.setBeginningIndex(matcher.start());
		result.setEndingIndex(result.getBeginningIndex() + word.length());
		return result;
	}

	private void addNewWordToSentenc(final Matcher matcher, final String word,
			final WrongSentence currentSentense) {
		currentSentense.setEndingIndex(matcher.start() + word.length());
	}

	private void storeSentence(final Matcher matcher, final String word,
			final WrongSentence currentSentense) {
		_log.warn("new sentence  " + currentSentense.getSentence() + " at "
				+ currentSentense.getBeginningIndex() + " ... "
				+ currentSentense.getEndingIndex());

	}
}
