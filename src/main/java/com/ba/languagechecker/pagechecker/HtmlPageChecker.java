package com.ba.languagechecker.pagechecker;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;

import com.ba.languagechecker.entities.PageResult;
import com.ba.languagechecker.entities.SentenceResult;
import com.ba.languagechecker.properties.CrawlerProperties;
import com.ba.languagechecker.properties.TaskProperties;
import com.ba.languagechecker.wordchecker.TextChecker;

public class HtmlPageChecker implements ICheckPage {

	private static Logger _log = Logger.getLogger(CheckerUrlVisitable.class
			.getCanonicalName());

	private static final HtmlPageChecker INSTANCE = new HtmlPageChecker();

	public boolean IsPageTitleCheckable;
	public boolean IsPageTextCheckable;
	public boolean IsOnlyBodyCheckable;
	public Collection<String> divElementsToClear;

	public static HtmlPageChecker getInstance() {
		return INSTANCE;
	}

	protected HtmlPageChecker() {
		super();
	}

	public TextChecker textChecker;

	@Override
	public void checkHtmlParsedData(final List<SentenceResult> results,
			final PageResult pageResult, final String text, final String html,
			final String title, final String url) {
		_log.info("checking url=" + url);
		final String checkedText = (IsOnlyBodyCheckable) ? TextManipulator
				.getBodyOfHtml(html, divElementsToClear) : text;

		if (IsPageTextCheckable)
			textChecker.addWrongSentences(results, checkedText, pageResult);
		if (IsPageTitleCheckable)
			textChecker.addWrongSentences(results, title, pageResult);
		_log.info("checked url=" + url);

	}

	@Override
	public void uploadTaskProperties(final TaskProperties taskProperties,
			final CrawlerProperties crawlerProperties)
			throws IOException {
		IsPageTitleCheckable = taskProperties.IsPageTitleCheckable();
		IsPageTextCheckable = taskProperties.IsPageTextCheckable();
		IsOnlyBodyCheckable = taskProperties.IsOnlyBodyCheckable();
		divElementsToClear = taskProperties.getDivElementsToClear();
		textChecker = new TextChecker(taskProperties, crawlerProperties);
	}

}
