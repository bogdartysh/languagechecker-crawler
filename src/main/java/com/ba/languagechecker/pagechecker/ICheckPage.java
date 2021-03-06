package com.ba.languagechecker.pagechecker;

import java.io.IOException;
import java.util.List;

import com.ba.languagechecker.entities.PageResult;
import com.ba.languagechecker.entities.SentenceResult;
import com.ba.languagechecker.properties.CrawlerProperties;
import com.ba.languagechecker.properties.TaskProperties;

public interface ICheckPage {
	void uploadTaskProperties(final TaskProperties taskProperties,
			final CrawlerProperties crawlerProperties)
			throws IOException;

	void checkHtmlParsedData(final List<SentenceResult> results,
			final PageResult pageResult, final String text, final String html,
			final String title, final String url);
}
