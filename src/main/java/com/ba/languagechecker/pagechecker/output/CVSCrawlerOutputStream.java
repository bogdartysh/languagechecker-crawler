package com.ba.languagechecker.pagechecker.output;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.List;

import com.ba.languagechecker.entities.PageResult;
import com.ba.languagechecker.entities.SentenceResult;
import com.ba.languagechecker.entities.TaskResult;
import com.ba.languagechecker.properties.TaskProperties;

public class CVSCrawlerOutputStream implements ICrawlerOutputStream {
	private static final String RESULT_FOLDER = "results/";
	private PrintStream printStream = null;

	private boolean ShouldOutPutWrongSentences = true;

	@Override
	public void saveSentences(final PageResult pageCheckResult,
			final List<SentenceResult> sentences) {
		if (ShouldOutPutWrongSentences) {
			for (SentenceResult sentence : sentences) {
				printStream.println(sentence.toCSVString());
			}
		} else
			printStream.println(pageCheckResult.toCSVString());

	}

	@Override
	public void close() {
		if (printStream != null)
			printStream.close();
	}

	@Override
	public void uploadTaskProperties(final TaskProperties taskProperties)
			throws FileNotFoundException, UnsupportedEncodingException {
		ShouldOutPutWrongSentences = taskProperties.ShouldSaveWrongSentences();
		printStream = new PrintStream(new FileOutputStream(
				RESULT_FOLDER + taskProperties.getOutputFileName()), true, "utf-8");
		

	}

	@Override
	public void saveTaskResult(TaskResult taskResult) {
		if (ShouldOutPutWrongSentences) {
			printStream
					.println("\"URL\", \"sentence\",\"ERROR_TYPE\", \"POSITION_START_INDEX\",\"POSITION_END_INDEX\"");
		}		
	}

}
