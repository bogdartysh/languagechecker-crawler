package com.ba.languagechecker;

import java.io.FileNotFoundException;
import java.io.IOException;

import com.ba.languagechecker.crawler.LanguageHtmlWebCrawler;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;

/**
 * Hello world!
 * 
 */
public class App {
	private final static String crawlStorageFolder = "tmp";

	public static void main(String[] args) throws FileNotFoundException,
			IOException {
		System.out.println("origin - " + args[0] + " dest = " + args[1]
				+ " patter = " + args[2] + " url =" + args[3]);
		LanguageHtmlWebCrawler.prepareCrawler(args[0], args[1], args[2]);

		/*
		 * crawlStorageFolder is a folder where intermediate crawl data is
		 * stored.
		 */

		final CrawlConfig config = new CrawlConfig();

		config.setCrawlStorageFolder(crawlStorageFolder);
		/*
		 * Be polite: Make sure that we don't send more than 1 request per
		 * second (1000 milliseconds between requests).
		 */
		config.setPolitenessDelay(1000);

		/*
		 * You can set the maximum crawl depth here. The default value is -1 for
		 * unlimited depth
		 */
		config.setMaxDepthOfCrawling(5);

		/*
		 * You can set the maximum number of pages to crawl. The default value
		 * is -1 for unlimited number of pages
		 */
		config.setMaxPagesToFetch(-1);
		/*
		 * This config parameter can be used to set your crawl to be resumable
		 * (meaning that you can resume the crawl from a previously
		 * interrupted/crashed crawl). Note: if you enable resuming feature and
		 * want to start a fresh crawl, you need to delete the contents of
		 * rootFolder manually.
		 */
		config.setResumableCrawling(false);
		config.setIncludeHttpsPages(true);
		final PageFetcher pageFetcher = new PageFetcher(config);
		final RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
		final RobotstxtServer robotstxtServer = new RobotstxtServer(
				robotstxtConfig, pageFetcher);
		final CrawlController controller;
		try {
			controller = new CrawlController(config, pageFetcher,
					robotstxtServer);
			/*
			 * For each crawl, you need to add some seed urls. These are the
			 * first URLs that are fetched and then the crawler starts following
			 * links which are found in these pages
			 */
			controller.addSeed(args[3]);

			/*
			 * Start the crawl. This is a blocking operation, meaning that your
			 * code will reach the line after this only when crawling is
			 * finished.
			 */
			controller.start(LanguageHtmlWebCrawler.class, 2);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
