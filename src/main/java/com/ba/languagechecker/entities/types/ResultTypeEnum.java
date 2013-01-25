package com.ba.languagechecker.entities.types;

public enum ResultTypeEnum {
	LEXIC, LANGUAGE, IS_NOT_A_WORD, MULTIPLE, OK;
	private int mimumSentenceLength = 0;

	public int getMimumSentenceLength() {
		return mimumSentenceLength;
	}

	public void setMimumSentenceLength(int mimumSentenceLength) {
		this.mimumSentenceLength = mimumSentenceLength;
	}
}
