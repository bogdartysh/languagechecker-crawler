package com.ba.languagechecker.useremulation.fillallforms.output;

import org.apache.log4j.Logger;

public class CSVFillAllFormsCheckerFileOutput {
	private static Logger _log = Logger
			.getLogger(CSVFillAllFormsCheckerFileOutput.class);
	private static final CSVFillAllFormsCheckerFileOutput INSTANCE = new CSVFillAllFormsCheckerFileOutput();
	private static final String DIRECTORY = "";

	public CSVFillAllFormsCheckerFileOutput getInstance() {
		return INSTANCE;
	}

	protected CSVFillAllFormsCheckerFileOutput() {
		super();
	}
	
	
	

}
