package com.ba.languagechecker.pagechecker;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;

import com.ba.languagechecker.entities.PageResult;
import com.ba.languagechecker.entities.SentenceResult;
import com.ba.languagechecker.properties.TaskProperties;
import com.ba.languagechecker.wordchecker.TextChecker;

public class HtmlPageChecker implements ICheckPage {

	private static Logger _log = Logger.getLogger(CheckerUrlVisitable.class
			.getCanonicalName());

	private static final HtmlPageChecker INSTANCE = new HtmlPageChecker();

	private static boolean IsPageTitleCheckable;
	private static boolean IsPageTextCheckable;
	private static boolean IsOnlyBodyCheckable;

	public static HtmlPageChecker getInstance() {
		return INSTANCE;
	}

	protected HtmlPageChecker() {
		super();
	}

	private TextChecker textChecker;

	@Override
	public void checkHtmlParsedData(final List<SentenceResult> results,
			final PageResult pageResult, final String text, final String html,
			final String title, final String url) {

		String checkedText = text;
		if (IsOnlyBodyCheckable) {
			checkedText = Jsoup.parse(html).select("body").text();
			_log.debug("parsed text is " + checkedText + " of + " + html);
		}

		if (IsPageTextCheckable)
			textChecker.addWrongSentences(results, checkedText, pageResult);
		if (IsPageTitleCheckable)
			textChecker.addWrongSentences(results, title, pageResult);

	}

	@Override
	public void uploadTaskProperties(final TaskProperties taskProperties)
			throws FileNotFoundException, IOException {
		IsPageTitleCheckable = taskProperties.IsPageTitleCheckable();
		IsPageTextCheckable = taskProperties.IsPageTextCheckable();
		IsOnlyBodyCheckable = taskProperties.IsOnlyBodyCheckable();
		textChecker = new TextChecker(taskProperties);
	}

}
