package com.ba.languagechecker.entities;

import java.util.UUID;

public class PageResult {

	private UUID id;
	private String url;
	private boolean hasErrors;
	private boolean isReachable;
	private TaskResult taskResult;

	public PageResult(String url, boolean isReachable, TaskResult taskResult) {
		super();
		this.url = url;
		this.isReachable = isReachable;
		this.taskResult = taskResult;
		id = UUID.randomUUID();
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public boolean isReachable() {
		return isReachable;
	}

	public void setReachable(boolean isReachable) {
		this.isReachable = isReachable;
	}

	public boolean isHasErrors() {
		return hasErrors;
	}

	public void setHasErrors(boolean hasErrors) {
		this.hasErrors = hasErrors;
	}

	public String toCSVString() {
		return new StringBuilder().append("\"").append(url).append("\", ")
				.append(hasErrors).append(", ").append(isReachable).toString();
	}

	public TaskResult getTaskResult() {
		return taskResult;
	}

	public void setTaskResult(TaskResult taskResult) {
		this.taskResult = taskResult;
	}
}
