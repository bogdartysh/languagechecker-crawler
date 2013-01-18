package com.ba.languagechecker.crawler;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;

import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;

import org.apache.log4j.Logger;

import com.ba.languagechecker.entities.PageCheckResult;
import com.ba.languagechecker.entities.WrongSentence;
import com.ba.languagechecker.wordchecker.TextChecker;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;

public class LanguageHtmlWebCrawler extends WebCrawler {
	private static Logger _log = Logger.getLogger(LanguageHtmlWebCrawler.class
			.getCanonicalName());

	private final static Pattern FILTERS = Pattern
			.compile(".*(\\.(css|js|bmp|gif|jpe?g"
					+ "|png|tiff?|mid|mp2|mp3|mp4"
					+ "|wav|avi|mov|mpeg|ram|m4v|pdf"
					+ "|rm|smil|wmv|swf|wma|zip|rar|gz))$");
	private static String urlPatternRegexExpression;

	private static Pattern parentUrlFilterRegex;

	private static TextChecker textChecker;

	/**
	 * You should implement this function to specify whether the given url
	 * should be crawled or not (based on your crawling logic).
	 */
	@Override
	public boolean shouldVisit(final WebURL url) {
		final String href = url.getURL().toLowerCase();
		final boolean res = !FILTERS.matcher(href).matches()
				&& parentUrlFilterRegex.matcher(href).matches();
		if (!res)
			_log.debug(url.getURL() + " should not be visited");
		return res;
	}

	/**
	 * This function is called when a page is fetched and ready to be processed
	 * by your program.
	 */
	@Override
	public void visit(final Page page) {
		final String url = page.getWebURL().getURL();
		logger.info("URL: " + url);

		if (page.getParseData() instanceof HtmlParseData) {
			HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
			String title = htmlParseData.getTitle();
			String text = htmlParseData.getText();
			String html = htmlParseData.getHtml();

			final List<WebURL> links = htmlParseData.getOutgoingUrls();
			logger.debug("Title: " + title);
			logger.debug("Text length: " + text.length());
			logger.debug("Html length: " + html.length());
			logger.debug("Number of outgoing links: " + links.size());
			final PageCheckResult pageCheckResult = new PageCheckResult(url,
					true);
			final List<WrongSentence> sentences = textChecker
					.getErrorSentences(text, pageCheckResult);
			saveSentences(sentences, url);
		}
	}

	public static TextChecker getTextChecker() {
		return textChecker;
	}

	public static void setTextChecker(TextChecker theTextChecker) {
		textChecker = theTextChecker;
	}

	private void saveSentences(final List<WrongSentence> sentences,
			final String url) {
		_log.info(url);
		for (WrongSentence sentence : sentences) {
			System.out.println(sentence.toString());
		}

	}

	public static void prepareCrawler(final String originalLanguage,
			final String targetLanguage, final String urlPattern)
			throws FileNotFoundException, IOException {
		urlPatternRegexExpression = urlPattern;
		parentUrlFilterRegex = Pattern.compile(urlPatternRegexExpression);
		final TextChecker theTextChecker = new TextChecker(originalLanguage,
				targetLanguage);
		setTextChecker(theTextChecker);
	}

}
