package com.ba.languagechecker.pagechecker.output;

import java.io.FileNotFoundException;
import java.util.List;

import com.ba.languagechecker.entities.PageResult;
import com.ba.languagechecker.entities.SentenceResult;
import com.ba.languagechecker.properties.TaskProperties;

public interface ICrawlerOutputStream extends AutoCloseable {

	public void saveSentences(final PageResult pageCheckResult, final List<SentenceResult> sentences);
	
	public void uploadTaskProperties(final TaskProperties taskProperties) throws FileNotFoundException;
}
