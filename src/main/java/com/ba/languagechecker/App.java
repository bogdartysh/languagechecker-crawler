package com.ba.languagechecker;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.ba.languagechecker.crawler.LanguageCheckerCrawlController;
import com.ba.languagechecker.crawler.LanguageHtmlWebCrawler;
import com.ba.languagechecker.pagechecker.output.CVSCrawlerOutputStream;
import com.ba.languagechecker.pagechecker.output.ICrawlerOutputStream;
import com.ba.languagechecker.properties.TaskProperties;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;

public class App {
	private final static String TASK_PROPERTIES_FILE_NAME = "task.properties";
	private final static String CRAWLER_PROPERTIES_FILE_NAME = "crawler.properties";
	private static Logger _log = Logger.getLogger(App.class.getCanonicalName());

	private final TaskProperties taskProperties = new TaskProperties();
	private final Properties crawlerProperties = new Properties();

	public static void main(String[] args) throws FileNotFoundException,
			IOException {
		final App app = new App();
		try (final FileInputStream crawlerPropertiesFileStream = new FileInputStream(
				CRAWLER_PROPERTIES_FILE_NAME)) {
			try (final ICrawlerOutputStream crawlerOutputStream = new CVSCrawlerOutputStream()) {
				app.crawlerProperties.load(crawlerPropertiesFileStream);
				final String taskPropertiesFileName = (args.length > 0) ? args[0] : TASK_PROPERTIES_FILE_NAME;
					
				try (final FileInputStream faskPropertiesFileStream = new FileInputStream(
						taskPropertiesFileName)) {

					app.taskProperties.load(faskPropertiesFileStream);
					crawlerOutputStream
							.uploadTaskProperties(app.taskProperties);

					LanguageHtmlWebCrawler.uploadProperties(app.taskProperties,
							app.crawlerProperties);
					LanguageHtmlWebCrawler.outputStream = crawlerOutputStream;

					/*
					 * crawlStorageFolder is a folder where intermediate crawl
					 * data is stored.
					 */

					final CrawlConfig config = new CrawlConfig();

					config.setCrawlStorageFolder(app.crawlerProperties
							.getProperty("crawlerProperties", "tmp"));
					/*
					 * Be polite: Make sure that we don't send more than 1
					 * request per second (1000 milliseconds between requests).
					 */
					config.setPolitenessDelay(Integer
							.valueOf(app.crawlerProperties
									.getProperty("politeness_delay")));

					/*
					 * You can set the maximum crawl depth here. The default
					 * value is -1 for unlimited depth
					 */
					config.setMaxDepthOfCrawling(Integer
							.valueOf(app.taskProperties
									.getProperty("max_depth")));

					/*
					 * You can set the maximum number of pages to crawl. The
					 * default value is -1 for unlimited number of pages
					 */
					config.setMaxPagesToFetch(Integer
							.valueOf(app.taskProperties
									.getProperty("max_pages_to_check")));
					/*
					 * This config parameter can be used to set your crawl to be
					 * resumable (meaning that you can resume the crawl from a
					 * previously interrupted/crashed crawl). Note: if you
					 * enable resuming feature and want to start a fresh crawl,
					 * you need to delete the contents of rootFolder manually.
					 */
					config.setResumableCrawling(false);
					config.setIncludeHttpsPages(true);
					final PageFetcher pageFetcher = new PageFetcher(config);
					final RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
					final RobotstxtServer robotstxtServer = new RobotstxtServer(
							robotstxtConfig, pageFetcher);
					final LanguageCheckerCrawlController controller;
					try {
						controller = new LanguageCheckerCrawlController(config,
								pageFetcher, robotstxtServer);
						/*
						 * For each crawl, you need to add some seed urls. These
						 * are the first URLs that are fetched and then the
						 * crawler starts following links which are found in
						 * these pages
						 */
						controller.addSeeds(app.taskProperties);

						/*
						 * Start the crawl. This is a blocking operation,
						 * meaning that your code will reach the line after this
						 * only when crawling is finished.
						 */
						controller.start(LanguageHtmlWebCrawler.class, Integer
								.valueOf(app.crawlerProperties
										.getProperty("number_of_crawlers")));
					} catch (Exception e) {
						_log.error(e, e);
					}
				}
			} catch (Exception e1) {
				_log.error(e1, e1);
			}
		}
	}
}
