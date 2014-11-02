package com.travel.gate365.model;

public class AlertItemInfo {

	private final int id;
	private String datetime;
	private String title;
	private String detail;
	private PlaceInfo place;

	public AlertItemInfo(int id, String datetime, String title, String detail, PlaceInfo place) {
		this.id = id;
		this.datetime = datetime;
		this.title = title;
		this.detail = detail;
		this.place = place;
		
	}

	public int getId() {
		return id;
	}

	public String getDatetime() {
		return datetime;
	}

	public String getTitle() {
		return title;
	}

	public String getDetail() {
		return detail;
	}

	public PlaceInfo getPlace() {
		return place;
	}
	
	

}
