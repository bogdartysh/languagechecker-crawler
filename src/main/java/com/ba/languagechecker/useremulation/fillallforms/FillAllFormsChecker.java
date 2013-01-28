package com.ba.languagechecker.useremulation.fillallforms;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.log4j.Logger;

import com.ba.languagechecker.entities.TaskResult;
import com.ba.languagechecker.properties.CrawlerProperties;
import com.ba.languagechecker.properties.TaskProperties;
import com.ba.languagechecker.repository.CheckedUrlsRepository;
import com.ba.languagechecker.wordchecker.TextChecker;

public class FillAllFormsChecker implements Callable<Integer> {
	private static Logger _log = Logger.getLogger(FillAllFormsChecker.class
			.getCanonicalName());

	private static final FillAllFormsChecker INSTANCE = new FillAllFormsChecker();
	private ExecutorService pool = null;

	private boolean shouldCheckDirectedNewUrls = false;
	private int numberOfRetries = 1;
	private TextChecker textChecker;
	private TaskResult taskResult;
	private Set<String> urlsToCheck = new HashSet<String>();
	private CheckedUrlsRepository checkedUrlsRepository;

	protected FillAllFormsChecker() {
		super();
	}

	public static FillAllFormsChecker getInstance() {
		return INSTANCE;
	}

	public void uploadTaskProperties(final TaskProperties taskProperties) {
		_log.trace("uploadTaskProperties ");
		shouldCheckDirectedNewUrls = taskProperties
				.getPagesToFillCheckDirectedToNewUrls();
		numberOfRetries = taskProperties.getPagesToFillNumberOfRetries();
		urlsToCheck.clear();
		urlsToCheck.addAll(taskProperties.getPagesToFillAllForms());
	}

	public void uploadCrawlerProperties(
			final CrawlerProperties crawlerProperties) {
		_log.trace("uploadCrawlerProperties ");
		if (pool != null) {
			_log.warn("pool was not empty, shutting previous tasks down and clearing the pool");
			pool.shutdown();
			pool = null;
		}
		if (crawlerProperties.getNumberOfThreadsForSeleniumTests() > 1) {
			_log.info("initializing pool of size = "
					+ crawlerProperties.getNumberOfThreadsForSeleniumTests());
			pool = Executors.newFixedThreadPool(crawlerProperties
					.getNumberOfThreadsForSeleniumTests());
		} else {
			_log.info("initializing single thread pool");
			pool = Executors.newSingleThreadExecutor();
		}
	}

	public void uploadProperties(final TaskProperties taskProperties,
			final CrawlerProperties crawlerProperties) {
		_log.trace("uploadProperties ");
		uploadTaskProperties(taskProperties);
		uploadCrawlerProperties(crawlerProperties);
	}

	@Override
	public Integer call() throws Exception {
		_log.trace("run" + " of task with Id=" + taskResult.getId());
		checkedUrlsRepository.clear();
		final List<Future<Integer>> futures = submitAllTasks();
		final int numberOfFoundErrors = getAccumulatedREsult(futures);
		_log.info("got " + numberOfFoundErrors + " erros "
				+ " of task with Id=" + taskResult.getId());
		return numberOfFoundErrors;

	}

	private List<Future<Integer>> submitAllTasks() {
		final List<Future<Integer>> futures = new LinkedList<Future<Integer>>();
		for (String url : urlsToCheck) {
			_log.info("submitting url " + url + " of task with Id="
					+ taskResult.getId());
			final FillFormsOfSinglePage fillPage = new FillFormsOfSinglePage(
					url, checkedUrlsRepository, shouldCheckDirectedNewUrls,
					numberOfRetries, taskResult, textChecker);
			futures.add(pool.submit(fillPage));
		}
		_log.info("Done: submitting all urls for task with id="
				+ taskResult.getId());
		return futures;
	}

	private static int getAccumulatedREsult(List<Future<Integer>> futures)
			throws InterruptedException, ExecutionException {
		int numberOfFoundErrors = 0;
		for (Future<Integer> future : futures) {
			final int value = future.get();
			numberOfFoundErrors += value;
		}
		return numberOfFoundErrors;
	}

	public TaskResult getTaskResult() {
		return taskResult;
	}

	public void setTaskResult(TaskResult taskResult) {
		this.taskResult = taskResult;
	}

	public TextChecker getTextChecker() {
		return textChecker;
	}

	public void setTextChecker(TextChecker textChecker) {
		this.textChecker = textChecker;
	}

}
