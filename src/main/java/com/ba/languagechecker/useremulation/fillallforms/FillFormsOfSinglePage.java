package com.ba.languagechecker.useremulation.fillallforms;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.concurrent.Callable;

import org.apache.log4j.Logger;

import com.ba.languagechecker.entities.PageResult;
import com.ba.languagechecker.entities.SentenceResult;
import com.ba.languagechecker.entities.TaskResult;
import com.ba.languagechecker.useremulation.fillallforms.repository.CheckedUrlsRepository;
import com.ba.languagechecker.wordchecker.TextChecker;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class FillFormsOfSinglePage implements Callable<Integer> {
	private static Logger _log = Logger.getLogger(FillFormsOfSinglePage.class
			.getCanonicalName());

	private final String url;
	private final CheckedUrlsRepository checkedUrlsRepository;
	private boolean shouldCheckDirectedNewUrls = false;
	private int numberOfRetries = 1;
	private TaskResult taskResult;
	private TextChecker textChecker;

	public FillFormsOfSinglePage(String url,
			CheckedUrlsRepository checkedUrlsRepository,
			boolean shouldCheckDirectedNewUrls, int numberOfRetries,
			TaskResult taskResult, TextChecker textChecker) {
		super();
		this.url = url;
		this.checkedUrlsRepository = checkedUrlsRepository;
		this.shouldCheckDirectedNewUrls = shouldCheckDirectedNewUrls;
		this.numberOfRetries = numberOfRetries;
		this.taskResult = taskResult;
		this.textChecker = textChecker;
	}

	private int getErrorsForPage(final String url, final HtmlPage page) {
		int result = 0;
		final HtmlElement body = page.getBody();
		final String text = body.asText();
		final PageResult pageCheckResult = new PageResult(url, false,
				taskResult);
		final List<SentenceResult> wrongSentences = textChecker
				.getWrongSentences(text, pageCheckResult);
		return result;
	}

	protected int processPage(final String url)
			throws FailingHttpStatusCodeException, MalformedURLException,
			IOException {
		_log.trace("processing page with url=" + url);
		final WebClient webClient = new WebClient();
		final HtmlPage page = webClient.getPage(url);
		int result = getErrorsForPage(url, page);
		webClient.closeAllWindows();
		return result;
	}

	@Override
	public Integer call() throws Exception {
		_log.trace("running new singlePageTask " + url + " of task with Id="
				+ taskResult.getId());
		int result = 0;
		result = processPage(url);
		return result;
	}

}
