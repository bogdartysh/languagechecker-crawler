package com.ba.languagechecker.useremulation.fillallforms.output;

import java.util.List;

import org.apache.log4j.Logger;

import com.ba.languagechecker.entities.SentenceResult;
import com.ba.languagechecker.entities.TaskResult;

public class CSVFillAllFormsCheckerFileOutput implements ICSVFillAllFormsCheckerOutput{
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

	@Override
	public void outputPageResults(final List<SentenceResult> wrongSentences) {
		if (wrongSentences.isEmpty()) {
			return;
		}
			
		for (SentenceResult sentence: wrongSentences) {
			System.out.println(sentence.toCSVString());
		}		
	}
	
	
	

}
