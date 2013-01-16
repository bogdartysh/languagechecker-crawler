package com.ba.languagechecker.wordchecker;

import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import com.ba.languagechecker.entities.WrongSentence;

public class TextChecker {
	private Logger logger = Logger.getLogger(TextChecker.class
			.getCanonicalName());
	private WordChecker wordChecker;
	Pattern pattern = Pattern.compile(patternString);

	public WordChecker getWordChecker() {
		return wordChecker;
	}

	public void setWordChecker(WordChecker wordChecker) {
		this.wordChecker = wordChecker;
	}

	public List<WrongSentence> getErrorSentences(final String text) {
		final List<WrongSentence> wrongSentences = new LinkedList<WrongSentence>();
		
		Matcher matcher = pattern.matcher(text);

		while (matcher.find()) {
		    System.out.println(matcher.group(1));
		}
		return wrongSentences;
	}

}
