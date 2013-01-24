package com.ba.languagechecker.crawler;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;

import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;

import org.apache.log4j.Logger;

import com.ba.languagechecker.entities.PageResult;
import com.ba.languagechecker.entities.SentenceResult;
import com.ba.languagechecker.pagechecker.CheckerUrlVisitable;
import com.ba.languagechecker.pagechecker.HtmlPageChecker;
import com.ba.languagechecker.pagechecker.output.ICrawlerOutputStream;
import com.ba.languagechecker.properties.CrawlerProperties;
import com.ba.languagechecker.properties.TaskProperties;
import com.ba.languagechecker.wordchecker.typedcheck.WordCheckersHolder;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

public class LanguageHtmlWebCrawler extends WebCrawler {
	private static Logger _log = Logger.getLogger(LanguageHtmlWebCrawler.class
			.getCanonicalName());

	private static CheckerUrlVisitable crawlerHtmlPageChecker;

	private static HtmlPageChecker htmlPageChecker;
	
	public static ICrawlerOutputStream outputStream;


	/**
	 * You should implement this function to specify whether the given url
	 * should be crawled or not (based on your crawling logic).
	 */
	@Override
	public boolean shouldVisit(final WebURL url) {
		return crawlerHtmlPageChecker.shouldVisitPage(url.getURL());
	}

	/**
	 * This function is called when a page is fetched and ready to be processed
	 * by your program.
	 */
	@Override
	public void visit(final Page page) {
		final String url = page.getWebURL().getURL();
		logger.info("URL: " + url);
		final PageResult pageCheckResult = new PageResult(url, true);
		final List<SentenceResult> sentences = new LinkedList<SentenceResult>();
		
		if (page.getParseData() instanceof HtmlParseData) {
			final HtmlParseData htmlParseData = (HtmlParseData) page
					.getParseData();
			final String title = htmlParseData.getTitle();
			final String text = htmlParseData.getText();
			final String html = htmlParseData.getHtml();		
			
			htmlPageChecker.checkHtmlParsedData(sentences, pageCheckResult,
					text, html, title, url);

		}
		outputStream.saveSentences(pageCheckResult, sentences);
	}



	public static void uploadProperties(final TaskProperties taskProperties,
			final CrawlerProperties crawlerProperties) throws FileNotFoundException,
			IOException {

		crawlerHtmlPageChecker = CheckerUrlVisitable.getInstance();
		crawlerHtmlPageChecker.uploadTaskProperties(taskProperties);

		htmlPageChecker = HtmlPageChecker.getInstance();
		htmlPageChecker.uploadTaskProperties(taskProperties);
	
		

	}
}
