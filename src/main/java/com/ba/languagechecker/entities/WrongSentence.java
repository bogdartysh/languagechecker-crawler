package com.ba.languagechecker.entities;

import java.util.UUID;

public class WrongSentence {

	private UUID id;
	private String sentence;
	private int beginningIndex = -1;
	private int endingIndex = -1;
	private PageCheckResult parentPage;

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

}
