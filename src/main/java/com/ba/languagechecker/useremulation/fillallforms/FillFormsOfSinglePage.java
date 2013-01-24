package com.ba.languagechecker.useremulation.fillallforms;

import java.util.concurrent.Callable;

import org.apache.log4j.Logger;

import com.ba.languagechecker.entities.TaskResult;
import com.ba.languagechecker.useremulation.fillallforms.repository.CheckedUrlsRepository;

public class FillFormsOfSinglePage implements Callable<Integer> {
	private static Logger _log = Logger.getLogger(FillFormsOfSinglePage.class
			.getCanonicalName());

	private final String url;
	private final CheckedUrlsRepository checkedUrlsRepository;
	private boolean shouldCheckDirectedNewUrls = false;
	private int numberOfRetries = 1;
	private TaskResult taskResult;

	public FillFormsOfSinglePage(String url,
			CheckedUrlsRepository checkedUrlsRepository,
			boolean shouldCheckDirectedNewUrls, int numberOfRetries,
			TaskResult taskResult) {
		super();
		this.url = url;
		this.checkedUrlsRepository = checkedUrlsRepository;
		this.shouldCheckDirectedNewUrls = shouldCheckDirectedNewUrls;
		this.numberOfRetries = numberOfRetries;
		this.taskResult = taskResult;
	}

	

	@Override
	public Integer call() throws Exception {
		_log.trace("running new singlePageTask " + url + " of task with Id="
				+ taskResult.getId());
		return 0;
	}

}
