package com.ba.languagechecker.wordchecker;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.ba.languagechecker.entities.PageResult;
import com.ba.languagechecker.entities.SentenceResult;
import com.ba.languagechecker.entities.types.ResultTypeEnum;
import com.ba.languagechecker.pagechecker.TextManipulator;
import com.ba.languagechecker.properties.CrawlerProperties;
import com.ba.languagechecker.properties.TaskProperties;
import com.ba.languagechecker.wordchecker.dictionary.holder.DictionaryHolder;
import com.ba.languagechecker.wordchecker.typedcheck.WordCheckersHolder;

public class TextChecker {
	private static Logger _log = Logger.getLogger(TextChecker.class
			.getCanonicalName());
	private static final String EMPTY_STRING = "";
	private static final String WORD_PART_PATTERN_EXPR = "[\\p{L}a-zA-Z]+";
	private static final Pattern WORD_PART_PATTERN = Pattern
			.compile(WORD_PART_PATTERN_EXPR);

	private static final String SPACE_BEFORE_WORD = "[\\p{Blank}\\s.,\"\']+";
	private static final String WORD_PATTERN_EXPR = SPACE_BEFORE_WORD
			+ WORD_PART_PATTERN_EXPR + SPACE_BEFORE_WORD;
	private static final Pattern WORD_PATTERN = Pattern
			.compile(WORD_PATTERN_EXPR);
	private boolean shouldSkipReferences = false;

	private List<String> excludedWords;
	private int maxCommonLanguageWords = 0;

	public TextChecker(final TaskProperties taskProperties,
			final CrawlerProperties crawlerProperties)
			throws FileNotFoundException, IOException {
		super();

		ResultTypeEnum.LANGUAGE.setMimumSentenceLength(Integer
				.valueOf(taskProperties.getProperty(
						"minimum_length_of_sentence_in_words", "2")));
		DictionaryHolder.getInstance().loadDictionaries(taskProperties,
				crawlerProperties);
		excludedWords = taskProperties.getExcludedWordsFromChecking();
		maxCommonLanguageWords = taskProperties.getMaxCommonLanguageWords();
		shouldSkipReferences = taskProperties.shouldSkipReferences();
		_log.info("origin - "
				+ taskProperties.getProperty("origin_language_code")
				+ " dest = "
				+ taskProperties.getProperty("shouldbe_language_code")
				+ " depth = "
				+ taskProperties.getProperty("max_depth")
				+ " distance_between_sentences_in_characters = "
				+ taskProperties
						.getProperty("distance_between_sentences_in_characters"));

	}

	public void addWrongSentences(final List<SentenceResult> wrongSentences,
			final String theText, final PageResult pageCheckResult) {

		final String text = ((shouldSkipReferences) ? TextManipulator
				.getTextWithoutReferences(theText) : theText).toLowerCase();
		final Matcher matcher = WORD_PATTERN.matcher(text);
		SentenceResult currentSentense = null;
		int numberOfSkippedWords = 0;

		while (matcher.find()) {
			final String found = matcher.group();
			final String word = getWordValue(found);
			if (EMPTY_STRING.equals(word) || word == null) {
				_log.debug(word + " is skipped");
				continue;
			}
			final ResultTypeEnum wordCheckedResult = WordCheckersHolder
					.getInstance().checkWord(word);
			if (wordCheckedResult != ResultTypeEnum.OK) {
				_log.info(word + " is wrong");
				if (currentSentense == null) {
					_log.info(word + " started a new sentence");
					currentSentense = getCurrentSentence(matcher, word,
							pageCheckResult, wordCheckedResult);
					numberOfSkippedWords = 0;
				} else {
					addNewWordToSentence(matcher, word, currentSentense);
				}
			} else {
				_log.debug(word + " is correct");
				if (currentSentense != null) {
					if (currentSentense.getSentenceType() == ResultTypeEnum.LANGUAGE
							&& numberOfSkippedWords < maxCommonLanguageWords) {
						_log.debug(word + " is correct, but will continue");
						numberOfSkippedWords++;
						continue;

					}
					closeSentenceAndAddNewWrongSentence(wrongSentences,
							currentSentense, text);
					_log.info("word=" + word + " stopped a wrong sentence="
							+ currentSentense.getSentence());

					currentSentense = null;
				}
			}
		}
		pageCheckResult.setHasErrors(!wrongSentences.isEmpty());
	}

	public List<SentenceResult> getWrongSentences(final String text,
			final PageResult pageCheckResult) {
		final List<SentenceResult> wrongSentences = new LinkedList<SentenceResult>();
		addWrongSentences(wrongSentences, text, pageCheckResult);
		return wrongSentences;
	}

	private String getWordValue(final String foundWord) {
		final Matcher innermatcher = WORD_PART_PATTERN.matcher(foundWord);
		final boolean isWordInIt = innermatcher.find();
		_log.debug("found " + foundWord + " " + isWordInIt);
		if (!isWordInIt)
			return EMPTY_STRING;
		final String word = innermatcher.group();
		if (!WordCheckersHolder.getWordiscanonicalchecker().isWordCorrect(word,
				DictionaryHolder.getInstance())) {
			_log.debug(word + " is not a correct word");
			return EMPTY_STRING;
		}
		_log.debug("trying word " + word);
		if (excludedWords.contains(word)) {
			_log.debug(word + " is excluded");
			return EMPTY_STRING;
		}
		return word;
	}

	private SentenceResult getCurrentSentence(final Matcher matcher,
			final String word, final PageResult pageCheckResul,
			final ResultTypeEnum sentenceType) {
		final SentenceResult sentence = new SentenceResult(sentenceType);
		sentence.setBeginningIndex(matcher.start());
		sentence.setEndingIndex(sentence.getBeginningIndex() + word.length()
				+ 1);
		sentence.setParentPage(pageCheckResul);
		sentence.incAmountOfAddedWords();
		return sentence;
	}

	private void addNewWordToSentence(final Matcher matcher, final String word,
			final SentenceResult sentence) {
		_log.debug(word + "continued a wrong sentence which started at"
				+ sentence.getBeginningIndex());
		sentence.setEndingIndex(matcher.start() + word.length());
		sentence.incAmountOfAddedWords();
	}

	private void closeSentenceAndAddNewWrongSentence(
			final List<SentenceResult> sentences,
			final SentenceResult sentence, final String text) {
		sentence.setSentenceByText(text);
		if (sentence.isSentenceLongEnaugh()) {
			sentences.add(sentence);
			_log.info("\"" + sentence.getSentence() + "\" added to url "
					+ sentence.getParentPage().getUrl());
		} else {
			_log.info("\"" + sentence.getSentence()
					+ "\" is too short to be considered");
		}
	}
}
