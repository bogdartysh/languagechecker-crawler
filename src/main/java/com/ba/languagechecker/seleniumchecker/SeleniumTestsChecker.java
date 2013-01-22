package com.ba.languagechecker.seleniumchecker;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;

import com.ba.languagechecker.properties.CrawlerProperties;
import com.ba.languagechecker.properties.TaskProperties;

public class SeleniumTestsChecker {
	private Logger _log = Logger.getLogger(SeleniumTestsChecker.class
			.getCanonicalName());
	private final List<String> seleniumFiles;
	private ExecutorService pool;
	private final int poolSize;

	public SeleniumTestsChecker(final List<String> seleniumFiles,
			final TaskProperties taskProperties,
			final CrawlerProperties crawlerProperties) {
		super();
		this.seleniumFiles = seleniumFiles;
		poolSize = crawlerProperties.getNumberOfThreadsForSeleniumTests();
	}

	public void checkSeleniumTestFiles() {
		if (this.seleniumFiles.size() > 0) {
			_log.info("starting selenium tests");
			pool = Executors.newFixedThreadPool(poolSize);
			for (String seleniumTestTile : seleniumFiles) {
				_log.info("submitting selenium test with fileName="
						+ seleniumTestTile);
				final SingleSeleniumtTestChecker checker = new SingleSeleniumtTestChecker(
						seleniumTestTile);
				pool.execute(checker);
			}
			shutDownThreads();
		} else {
			_log.debug("no selenium tests");
		}
	}

	private void shutDownThreads() {
		_log.debug("shut down SeleniumTestChecker threads");
		pool.shutdown();
	}
}
