package com.ba.languagechecker;

import java.io.IOException;
import java.io.Reader;

import org.apache.log4j.Logger;

import com.ba.languagechecker.crawler.LanguageCheckerCrawlController;
import com.ba.languagechecker.crawler.LanguageHtmlWebCrawler;
import com.ba.languagechecker.entities.TaskResult;
import com.ba.languagechecker.pagechecker.output.CVSCrawlerOutputStream;
import com.ba.languagechecker.pagechecker.output.ICrawlerOutputStream;
import com.ba.languagechecker.properties.CrawlerProperties;
import com.ba.languagechecker.properties.TaskProperties;
import com.ba.languagechecker.wordchecker.typedcheck.WordCheckersHolder;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;

public class LanguageCheckerCrawlerRunner implements Runnable {
	private static Logger _log = Logger
			.getLogger(LanguageCheckerCrawlerRunner.class.getCanonicalName());
	private final TaskProperties taskProperties;
	private final CrawlerProperties crawlerProperties;
	private Reader taskPropertiesFileStream = null;
	private Reader crawlerPropertiesFileStream = null;

	public LanguageCheckerCrawlerRunner(final TaskProperties taskProperties,
			final CrawlerProperties crawlerProperties) {
		super();
		this.taskProperties = taskProperties;
		this.crawlerProperties = crawlerProperties;

	}

	public LanguageCheckerCrawlerRunner(final TaskProperties taskProperties,
			final CrawlerProperties crawlerProperties,
			final Reader taskPropertiesFileStream) {
		this(taskProperties, crawlerProperties);
		this.taskPropertiesFileStream = taskPropertiesFileStream;
	}

	public LanguageCheckerCrawlerRunner(final TaskProperties taskProperties,
			final CrawlerProperties crawlerProperties,
			final Reader taskPropertiesFileStream,
			final Reader crawlerPropertiesFileStream) {
		this(taskProperties, crawlerProperties, taskPropertiesFileStream);
		this.crawlerPropertiesFileStream = crawlerPropertiesFileStream;
	}

	private void loadPropertiesFromReaders() throws IOException {
		if (crawlerPropertiesFileStream != null) {
			_log.info("loading crawler properties");
			crawlerProperties.load(crawlerPropertiesFileStream);
		}

		if (taskPropertiesFileStream != null) {
			_log.info("loading task properties");
			taskProperties.load(taskPropertiesFileStream);
		}

		WordCheckersHolder.getInstance().setProperties(taskProperties);
		LanguageHtmlWebCrawler.uploadProperties(taskProperties,
				crawlerProperties);

	}

	private TaskResult loadTaskResult() throws IOException {
		loadPropertiesFromReaders();
		final String taskExternalId = taskProperties.getTaskExternalId();
		return new TaskResult(taskExternalId);
	}

	private void setTaskResult(final TaskResult taskResult,
			final ICrawlerOutputStream crawlerOutputStream) {
		LanguageHtmlWebCrawler.setTaskResult(taskResult);
		crawlerOutputStream.saveTaskResult(taskResult);
		LanguageHtmlWebCrawler.outputStream = crawlerOutputStream;
	}

	private CrawlConfig getCrawlConfig() {
		/*
		 * crawlStorageFolder is a folder where intermediate crawl data is
		 * stored.
		 */

		final CrawlConfig config = new CrawlConfig();

		config.setCrawlStorageFolder(crawlerProperties.getProperty(
				"crawlerProperties", "tmp"));
		/*
		 * Be polite: Make sure that we don't send more than 1 request per
		 * second (1000 milliseconds between requests).
		 */
		config.setPolitenessDelay(Integer.valueOf(crawlerProperties
				.getProperty("politeness_delay")));

		/*
		 * You can set the maximum crawl depth here. The default value is -1 for
		 * unlimited depth
		 */
		config.setMaxDepthOfCrawling(Integer.valueOf(taskProperties
				.getProperty("max_depth")));

		/*
		 * You can set the maximum number of pages to crawl. The default value
		 * is -1 for unlimited number of pages
		 */
		config.setMaxPagesToFetch(Integer.valueOf(taskProperties
				.getProperty("max_pages_to_check")));
		/*
		 * This config parameter can be used to set your crawl to be resumable
		 * (meaning that you can resume the crawl from a previously
		 * interrupted/crashed crawl). Note: if you enable resuming feature and
		 * want to start a fresh crawl, you need to delete the contents of
		 * rootFolder manually.
		 */
		config.setResumableCrawling(false);
		config.setIncludeHttpsPages(true);
		return config;
	}

	private LanguageCheckerCrawlController getLanguageCheckerCrawlController()
			throws Exception {
		final CrawlConfig config = getCrawlConfig();
		final PageFetcher pageFetcher = new PageFetcher(config);
		final RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
		final RobotstxtServer robotstxtServer = new RobotstxtServer(
				robotstxtConfig, pageFetcher);
		return new LanguageCheckerCrawlController(config, pageFetcher,
				robotstxtServer);
	}

	@Override
	public void run() {
		try (final ICrawlerOutputStream crawlerOutputStream = new CVSCrawlerOutputStream()) {
			final TaskResult taskResult = loadTaskResult();
			crawlerOutputStream.uploadTaskProperties(taskProperties);
			setTaskResult(taskResult, crawlerOutputStream);

			try {
				final LanguageCheckerCrawlController controller = getLanguageCheckerCrawlController();

				/*
				 * For each crawl, you need to add some seed urls. These are the
				 * first URLs that are fetched and then the crawler starts
				 * following links which are found in these pages
				 */
				controller.addSeeds(taskProperties);

				/*
				 * Start the crawl. This is a blocking operation, meaning that
				 * your code will reach the line after this only when crawling
				 * is finished.
				 */
				_log.info("started crawl for task with ExternalId="
						+ taskResult.getExternalId());
				controller.start(LanguageHtmlWebCrawler.class, Integer
						.valueOf(crawlerProperties
								.getProperty("number_of_crawlers")));
			} catch (Exception e) {
				_log.error(e, e);
			}
		} catch (Exception e3) {
			_log.error(e3, e3);
		}

	}

}
