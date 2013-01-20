package com.ba.languagechecker.entities;

import java.util.UUID;

public class PageCheckResult {

	private UUID id;
	private String url;
	private boolean hasNoErrors;
	private boolean isReachable;
	
	
	
	public PageCheckResult(String url, boolean isReachable) {
		super();
		this.url = url;
		this.isReachable = isReachable;
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
	public boolean isHasNoErrors() {
		return hasNoErrors;
	}
	public void setHasNoErrors(boolean hasNoErrors) {
		this.hasNoErrors = hasNoErrors;
	}
}
