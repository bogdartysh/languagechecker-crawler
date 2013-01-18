package com.ba.languagechecker.wordchecker;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.ba.languagechecker.entities.PageCheckResult;
import com.ba.languagechecker.entities.WrongSentence;

public class TextChecker {
	private Logger _log = Logger
			.getLogger(TextChecker.class.getCanonicalName());
	private static final String WORD_PATTERN_EXPR = "[.,\"\'\\p{Blank}\\s][\\p{L}a-zA-Z]+[\\p{Blank}\\s.,\"\']";
	private static final Pattern WORD_PATTERN = Pattern
			.compile(WORD_PATTERN_EXPR);

	private static final int DISTANCE_BETWEEN_SENTENCES = 20;

	private WordChecker wordChecker;

	public TextChecker(final String originalLanguage,
			final String souldBeLanguage) throws FileNotFoundException,
			IOException {
		super();
		wordChecker = new WordChecker(originalLanguage, souldBeLanguage);
		_log.info(originalLanguage
				+ " size = "
				+ wordChecker.getOriginalLanguageDictionary().dictionary.size()
				+ " "
				+ souldBeLanguage
				+ " size = "
				+ +wordChecker.getShouldBeLanguageDictionary().dictionary
						.size());
	}

	public WordChecker getWordChecker() {
		return wordChecker;
	}

	public void setWordChecker(WordChecker wordChecker) {
		this.wordChecker = wordChecker;
	}

	public List<WrongSentence> getErrorSentences(final String text,
			final PageCheckResult pageCheckResul) {
		final List<WrongSentence> wrongSentences = new LinkedList<WrongSentence>();
		final Matcher matcher = WORD_PATTERN.matcher(text);
		WrongSentence currentSentense = null;

		while (matcher.find()) {
			final String word = matcher.group().toLowerCase().trim();
			if (!WordChecker.isAWord(word)) {

				if (currentSentense != null) {
					_log.debug(word
							+ " is not a word I wanna check, but would be added to existed sentence id="
							+ currentSentense.getId());
				}
				continue;
			}
			_log.info("trying word " + matcher.group());
			if (getWordChecker().isWordOfOriginalLanguage(word)) {
				_log.info(word + "of wrong language");
				if (currentSentense == null) {
					_log.info(word + " started a new sentence");
					currentSentense = getCurrentSentence(matcher, word,
							pageCheckResul);
				} else {
					addNewWordToSentenc(matcher, word, currentSentense);
				}
			} else {
				_log.debug(word + " of correct language");
				if (currentSentense != null) {
					if (isFarFromPreviousSentence(matcher, currentSentense)) {
						_log.info(word + " stopped a wrong sentence");
						closeSentenceAndAddNewWrongSentence(wrongSentences,
								currentSentense, text);

						currentSentense = null;
					}
				}
			}
		}
		pageCheckResul.setLanguageCorrect(wrongSentences.isEmpty());
		return wrongSentences;
	}

	private boolean isFarFromPreviousSentence(final Matcher matcher,
			final WrongSentence sentence) {
		return matcher.start() - sentence.getEndingIndex() > DISTANCE_BETWEEN_SENTENCES;
	}

	private WrongSentence getCurrentSentence(final Matcher matcher,
			final String word, final PageCheckResult pageCheckResul) {
		final WrongSentence sentence = new WrongSentence();
		sentence.setBeginningIndex(matcher.start());
		sentence.setEndingIndex(sentence.getBeginningIndex() + word.length());
		sentence.setParentPage(pageCheckResul);
		sentence.incAmountOfAddedWords();
		return sentence;
	}

	private void addNewWordToSentenc(final Matcher matcher, final String word,
			final WrongSentence sentence) {
		_log.debug(word + "continued a wrong sentence which started at"
				+ sentence.getBeginningIndex());
		sentence.setEndingIndex(matcher.start() + word.length());
		sentence.incAmountOfAddedWords();
	}

	private void closeSentenceAndAddNewWrongSentence(
			final List<WrongSentence> sentences, final WrongSentence sentence,
			final String text) {
		sentence.setSentenceByText(text);
		if (sentence.isSentenceLongEnaugh()) {
			sentences.add(sentence);
			_log.info(sentence.getSentence() + " added to previously found on "
					+ sentence.getParentPage().getUrl());
		}
		_log.info(sentence.getSentence() + " is too short to be considered");

	}

}
