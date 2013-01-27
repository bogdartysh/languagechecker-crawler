package com.ba.languagechecker.pagechecker;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class TextManipulatorTest extends TestCase {

	private static final String BEFORE = "before ";
	private static final String AFTER = " after";
	private static final String REFENCED_TEXT = " referenced text";

	public TextManipulatorTest(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(TextManipulatorTest.class);
	}

	public void testShould_Remove_SingleSigned_Reference() {
		TestCase.assertEquals(
				BEFORE + AFTER,
				TextManipulator.getTextWithoutReferences(BEFORE + "\""
						+ REFENCED_TEXT + "\"" + AFTER));
	}

	public void testShould_Remove_LessGreater_Reference() {
		TestCase.assertEquals(
				BEFORE + AFTER,
				TextManipulator.getTextWithoutReferences(BEFORE + "<<"
						+ REFENCED_TEXT + ">>" + AFTER));

	}

}
