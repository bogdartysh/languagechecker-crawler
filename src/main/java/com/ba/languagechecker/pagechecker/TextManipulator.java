package com.ba.languagechecker.pagechecker;

public class TextManipulator {
	private static final String EMPTY_STRING = "";
	private static final String REFERENCES_PATTERN_EXPR_SIGN = "\".*\"";

	private static final String REFERENCES_PATTERN_EXPR_GL = "<<.*>>";

	public static String getTextWithoutReferences(final String text) {
		return text.replaceAll(REFERENCES_PATTERN_EXPR_SIGN, EMPTY_STRING)
				.replaceAll(REFERENCES_PATTERN_EXPR_GL, EMPTY_STRING);

	}

}
