package com.ba.languagechecker.crawler;

import java.util.List;

import org.apache.log4j.Logger;

import com.ba.languagechecker.properties.TaskProperties;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;

public class LanguageCheckerCrawlController extends CrawlController {
	private static Logger _log = Logger
			.getLogger(LanguageCheckerCrawlController.class.getCanonicalName());

	public LanguageCheckerCrawlController(CrawlConfig config,
			PageFetcher pageFetcher, RobotstxtServer robotstxtServer)
			throws Exception {
		super(config, pageFetcher, robotstxtServer);
	}

	public void addSeeds(final TaskProperties properties) {
		final List<String> urls = properties.getStartUrls();
		for (String url : urls) {
			_log.info("adding url seed " + url);
			addSeed(url);
		}
	}
}
