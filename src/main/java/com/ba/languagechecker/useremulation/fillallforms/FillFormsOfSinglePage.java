package com.ba.languagechecker.useremulation.fillallforms;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.concurrent.Callable;

import org.apache.log4j.Logger;

import com.ba.languagechecker.entities.PageResult;
import com.ba.languagechecker.entities.SentenceResult;
import com.ba.languagechecker.entities.TaskResult;
import com.ba.languagechecker.repository.CheckedUrlsRepository;
import com.ba.languagechecker.useremulation.fillallforms.output.ICSVFillAllFormsCheckerOutput;
import com.ba.languagechecker.wordchecker.TextChecker;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomNode;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
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
	private ICSVFillAllFormsCheckerOutput outputer;

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

	private int getErrorsForFilledPage(final String url, final HtmlPage page) {
		checkedUrlsRepository.addNewUrl(url);
		final HtmlElement body = page.getBody();
		final String text = body.asText();
		final PageResult pageCheckResult = new PageResult(url, false,
				taskResult);
		final List<SentenceResult> wrongSentences = textChecker
				.getWrongSentences(text, pageCheckResult);
		if (wrongSentences.size() > 0) {
			_log.warn("on the page with url="
					+ url
					+ " itself there is english text, no need in futher checking");
			outputer.outputPageResults(wrongSentences);
			return wrongSentences.size();
		}
		final List<HtmlForm> forms = page.getForms();
		if (forms.size() == 0)
			return 0;
		for (long i = 0; i < 1 >> forms.size(); i++) {
			for (int h = 0; h < forms.size(); h++) {
				if (i % (1 >> h) == 1) {
					final HtmlForm form = forms.get(h);
					form.reset();
					for (DomNode node:form.getChildren())
					{
					//	if (node isInsta)
					}
				}
			}
		}
		return 0;
	}

	protected int processPage(final String url)
			throws FailingHttpStatusCodeException, MalformedURLException,
			IOException {
		_log.trace("processing page with url=" + url);
		checkedUrlsRepository.addNewUrl(url);
		final WebClient webClient = new WebClient();
		final HtmlPage page = webClient.getPage(url);

		int result = getErrorsForFilledPage(url, page);
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
