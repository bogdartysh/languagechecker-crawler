package com.ba.languagechecker;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.log4j.Logger;

import com.ba.languagechecker.properties.CrawlerProperties;
import com.ba.languagechecker.properties.TaskProperties;

public class App {
	private final static String TASK_PROPERTIES_FILE_NAME = "task.properties";
	private final static String CRAWLER_PROPERTIES_FILE_NAME = "crawler.properties";

	private static Logger _log = Logger.getLogger(App.class.getCanonicalName());

	private final TaskProperties taskProperties = new TaskProperties();
	private final CrawlerProperties crawlerProperties = new CrawlerProperties();

	public static void main(String[] args) throws FileNotFoundException,
			IOException {
		final App app = new App();
		try (final FileInputStream crawlerPropertiesFileStream = new FileInputStream(
				CRAWLER_PROPERTIES_FILE_NAME)) {
			app.crawlerProperties.load(crawlerPropertiesFileStream);
			final String taskPropertiesFileName = (args.length > 0) ? args[0]
					: TASK_PROPERTIES_FILE_NAME;
			try (final FileInputStream faskPropertiesFileStream = new FileInputStream(
					taskPropertiesFileName)) {

				LanguageCheckerCrawlerRunner languageCheckerCrawlerRunner = new LanguageCheckerCrawlerRunner(
						app.taskProperties, app.crawlerProperties,
						new java.io.InputStreamReader(faskPropertiesFileStream));
				languageCheckerCrawlerRunner.run();

			} catch (Exception e) {
				_log.error(e, e);
			}
		} catch (Exception e1) {
			_log.error(e1, e1);
		}
	}
}
