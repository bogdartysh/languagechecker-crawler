package com.ba.languagechecker.entities;

import java.util.UUID;

public class WrongSentence {

	private UUID id;
	private String sentence;
	private int beginningIndex = -1;
	private int endingIndex = -1;
	private PageCheckResult parentPage;
	private int amountOfAddedWords = 0;
	private WrongSentenceType sentenceType;

	public WrongSentence(final WrongSentenceType sentenceType) {
		super();
		id = UUID.randomUUID();
		this.sentenceType = sentenceType;
	}

	public PageCheckResult getParentPage() {
		return parentPage;
	}

	public void setParentPage(PageCheckResult parentSentence) {
		this.parentPage = parentSentence;
	}

	public String getSentence() {
		return sentence;
	}

	public void setSentence(String sentence) {
		this.sentence = sentence;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public int getBeginningIndex() {
		return beginningIndex;
	}

	public void setBeginningIndex(int beginningIndex) {
		this.beginningIndex = beginningIndex;
	}

	public int getEndingIndex() {
		return endingIndex;
	}

	public void setEndingIndex(int endingIndex) {
		this.endingIndex = endingIndex;
	}

	public void setSentenceByText(final String text) {
		setSentence(text.substring(beginningIndex, endingIndex));
	}

	@Override
	public String toString() {
		return new StringBuilder().append("\"").append(parentPage.getUrl())
				.append("\", \"").append(sentence).append("\", \"")
				.append(sentenceType).append("\", ").append(beginningIndex)
				.append(", ").append(endingIndex).toString();
	}

	public int getAmountOfAddedWords() {
		return amountOfAddedWords;
	}

	public void setAmountOfAddedWords(int amountOfAddedWords) {
		this.amountOfAddedWords = amountOfAddedWords;
	}

	public void incAmountOfAddedWords() {
		amountOfAddedWords++;
	}

	public boolean isSentenceLongEnaugh() {
		return getAmountOfAddedWords() >= getSentenceType()
				.getMimumSentenceLength();
	}

	public WrongSentenceType getSentenceType() {
		return sentenceType;
	}

	public void setSentenceType(WrongSentenceType sentenceType) {
		this.sentenceType = sentenceType;
	}

}
