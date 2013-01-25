package com.ba.languagechecker.entities;

import java.util.Collection;
import java.util.Date;
import java.util.UUID;

public class TaskResult {
	private UUID id;
	private Date started;
	private Date finished;
	private Collection<PageResult> pages;
	private int totalAmountOfParesPages = 0;
	private String externalId;

	public TaskResult(String externalId) {
		super();
		id = UUID.randomUUID();
		started = new Date();
		this.externalId = externalId;
	}

	public Date getFinished() {
		return finished;
	}

	public void setFinished(Date finished) {
		this.finished = finished;
	}

	public Date getStarted() {
		return started;
	}

	public void setStarted(Date started) {
		this.started = started;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public Collection<PageResult> getPages() {
		return pages;
	}

	public void setPages(Collection<PageResult> pages) {
		this.pages = pages;
	}

	public int getTotalAmountOfParesPages() {
		return totalAmountOfParesPages;
	}

	public void setTotalAmountOfParesPages(int totalAmountOfParesPages) {
		this.totalAmountOfParesPages = totalAmountOfParesPages;
	}

	public void incTotalAmountOfParesPages() {
		totalAmountOfParesPages++;
	}

	public String getExternalId() {
		return externalId;
	}

	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}
}
