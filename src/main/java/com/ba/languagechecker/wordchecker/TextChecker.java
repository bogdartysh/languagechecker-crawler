package com.ba.languagechecker.wordchecker;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.ba.languagechecker.entities.PageCheckResult;
import com.ba.languagechecker.entities.WrongSentence;
import com.ba.languagechecker.entities.WrongSentenceType;
import com.ba.languagechecker.properties.TaskProperties;
import com.ba.languagechecker.wordchecker.dictionary.DictionaryHolder;
import com.ba.languagechecker.wordchecker.typedcheck.WordCheckersHolder;

public class TextChecker {
	private static Logger _log = Logger
			.getLogger(TextChecker.class.getCanonicalName());
	private static final String EMPTY_STRING = "";
	private static final String WORD_PART_PATTERN_EXPR = "[\\p{L}a-zA-Z]+";
	private static final Pattern WORD_PART_PATTERN = Pattern
			.compile(WORD_PART_PATTERN_EXPR);

	private static final String SPACE_BEFORE_WORD = "[\\p{Blank}\\s.,\"\']+";
	private static final String WORD_PATTERN_EXPR = SPACE_BEFORE_WORD
			+ WORD_PART_PATTERN_EXPR + SPACE_BEFORE_WORD;
	private static final Pattern WORD_PATTERN = Pattern
			.compile(WORD_PATTERN_EXPR);

	private List<String> excludedWords;

	public TextChecker(final TaskProperties taskProperties)
			throws FileNotFoundException, IOException {
		super();
		WrongSentenceType.LANGUAGE.setMimumSentenceLength(Integer
				.valueOf(taskProperties.getProperty(
						"minimum_length_of_sentence_in_words", "2")));
		DictionaryHolder.getInstance().loadDictionaries(taskProperties);
		excludedWords = taskProperties.getExcludedWordsFromChecking();
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

	public void addWrongSentences(List<WrongSentence> wrongSentences,
			final String text, final PageCheckResult pageCheckResult) {

		final Matcher matcher = WORD_PATTERN.matcher(text);
		WrongSentence currentSentense = null;

		while (matcher.find()) {
			final String found = matcher.group();
			final String word = getWordValue(found);
			if (EMPTY_STRING.equals(word) || word == null) {
				_log.debug(word + " is skipped");
				continue;
			}
			final WrongSentenceType wordCheckedResult = WordCheckersHolder
					.getInstance().checkWord(word);
			if (wordCheckedResult != WrongSentenceType.OK) {
				_log.info(word + " is wrong");
				if (currentSentense == null) {
					_log.info(word + " started a new sentence");
					currentSentense = getCurrentSentence(matcher, word,
							pageCheckResult, wordCheckedResult);
				} else {
					addNewWordToSentence(matcher, word, currentSentense);
				}
			} else {
				_log.debug(word + " is correct");
				if (currentSentense != null) {
					closeSentenceAndAddNewWrongSentence(wrongSentences,
							currentSentense, text);
					_log.info("word=" + word + " stopped a wrong sentence="
							+ currentSentense.getSentence());

					currentSentense = null;
				}
			}
		}
		pageCheckResult.setHasNoErrors(wrongSentences.isEmpty());
	}

	private String getWordValue(final String foundWord) {
		final Matcher innermatcher = WORD_PART_PATTERN.matcher(foundWord);
		final boolean isWordInIt = innermatcher.find();
		_log.info("found " + foundWord + " " + isWordInIt);
		final String possibleWord = innermatcher.group();
		if (!WordCheckersHolder.getWordiscanonicalchecker().isWordCorrect(
				possibleWord, DictionaryHolder.getInstance())) {
			_log.info(possibleWord + " is not a correct word");
			return EMPTY_STRING;
		}
		final String word = possibleWord.toLowerCase();
		_log.info("trying word " + word);
		if (excludedWords.contains(word)) {
			_log.debug(word + " is excluded");
			return EMPTY_STRING;
		}
		return word;
	}

	private WrongSentence getCurrentSentence(final Matcher matcher,
			final String word, final PageCheckResult pageCheckResul,
			final WrongSentenceType sentenceType) {
		final WrongSentence sentence = new WrongSentence(sentenceType);
		sentence.setBeginningIndex(matcher.start());
		sentence.setEndingIndex(sentence.getBeginningIndex() + word.length());
		sentence.setParentPage(pageCheckResul);
		sentence.incAmountOfAddedWords();
		return sentence;
	}

	private void addNewWordToSentence(final Matcher matcher, final String word,
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
			_log.info("\"" + sentence.getSentence() + "\" added to url "
					+ sentence.getParentPage().getUrl());
		} else {
			_log.info("\"" + sentence.getSentence()
					+ "\" is too short to be considered");
		}
	}

}
