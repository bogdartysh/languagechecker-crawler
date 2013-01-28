package com.ba.languagechecker.repository;

import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;

public class CheckedUrlsRepository {

	private static Logger _log = Logger.getLogger(CheckedUrlsRepository.class
			.getCanonicalName());
	private static final CheckedUrlsRepository INSTANCE = new CheckedUrlsRepository();

	protected CheckedUrlsRepository() {
		super();
	}

	public static CheckedUrlsRepository getInstance() {
		return INSTANCE;
	}

	private Set<String> checkedNewUrls = new HashSet<String>();

	public boolean addNewUrl(final String url) {
		return checkedNewUrls.add(url);
	}

	public boolean contains(final String url) {
		return checkedNewUrls.contains(url);
	}

	public void clear() {
		_log.debug("clearing CheckedUrlsRepository");
		checkedNewUrls.clear();
	}

}
