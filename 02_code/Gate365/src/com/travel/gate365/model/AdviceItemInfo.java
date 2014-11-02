package com.travel.gate365.model;

public class AdviceItemInfo {

	private final int id;
	private String dateTime;
	private String title;
	private String detail;

	public AdviceItemInfo(int id, String dateTime, String title, String detail) {
		this.id = id;
		this.dateTime = dateTime;
		this.title = title;
		this.detail = detail;
	}

	public int getId() {
		return id;
	}

	public String getDateTime() {
		return dateTime;
	}

	public String getTitle() {
		return title;
	}

	public String getDetail() {
		return detail;
	}

	
}
