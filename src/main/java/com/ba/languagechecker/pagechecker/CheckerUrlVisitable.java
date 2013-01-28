package com.ba.languagechecker.pagechecker;

import java.util.List;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.ba.languagechecker.properties.TaskProperties;
import com.ba.languagechecker.repository.CheckedUrlsRepository;

public class CheckerUrlVisitable {

	private static Logger _log = Logger.getLogger(CheckerUrlVisitable.class
			.getCanonicalName());

	private static final CheckerUrlVisitable INSTANCE = new CheckerUrlVisitable();

	private final static Pattern FILTERS = Pattern
			.compile(".*(\\.(css|js|bmp|gif|jpe?g"
					+ "|png|tiff?|mid|mp2|mp3|mp4"
					+ "|wav|avi|mov|mpeg|ram|m4v|pdf"
					+ "|rm|smil|wmv|swf|wma|zip|rar|gz))$");
	
	private CheckedUrlsRepository checkedUrlsRepository;

	public static CheckerUrlVisitable getInstance() {
		return INSTANCE;
	}

	protected CheckerUrlVisitable() {
		super();
	}

	private List<String> excludedUrls;

	private Pattern parentUrlFilterRegex;
	private static String urlPatternRegexExpression;

	public boolean shouldVisitPage(final String url) {

		boolean res = !FILTERS.matcher(url).matches()
				&& parentUrlFilterRegex.matcher(url).matches();
		if (excludedUrls.contains(url))
			res = false;
		if (checkedUrlsRepository.contains(url)) {
			_log.info("checkedUrlsRepository - already visited url=" + url);
			res = false;
		}
		if (!res)
			_log.debug(url + " should not be visited");
		return res;
	}

	public void uploadTaskProperties(final TaskProperties taskProperties) {
		excludedUrls = taskProperties.getExcludedUrls();
		_log.info(" url_pattern = " + taskProperties.getProperty("url_pattern"));
		urlPatternRegexExpression = taskProperties.getProperty("url_pattern");
		parentUrlFilterRegex = Pattern.compile(urlPatternRegexExpression);
	}

	public CheckedUrlsRepository getCheckedUrlsRepository() {
		return checkedUrlsRepository;
	}

	public void setCheckedUrlsRepository(
			CheckedUrlsRepository checkedUrlsRepository) {
		this.checkedUrlsRepository = checkedUrlsRepository;
	}

}
