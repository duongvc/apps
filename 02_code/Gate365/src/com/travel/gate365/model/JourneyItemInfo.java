package com.travel.gate365.model;

public class JourneyItemInfo {

	private final int id;
	private String date;
	private String leavingTime;
	private String pNRCode;
	private PlaceInfo src;
	private PlaceInfo des;
	private FlightRoutingInfo[] flightRoutings;
	
	public JourneyItemInfo(int id) {
		this.id = id;
	}

	public JourneyItemInfo(int id, String date, String leavingTime, String PNRCode, PlaceInfo src, PlaceInfo des) {
		this(id);
		this.date = date;
		this.leavingTime = leavingTime;
		pNRCode = PNRCode;
		this.src = src;
		this.des = des;
		
	}
	
	public int getId() {
		return id;
	}

	public FlightRoutingInfo[] getFlightRoutings() {
		return flightRoutings;
	}

	public void setFlightRoutings(FlightRoutingInfo[] flightRoutings) {
		this.flightRoutings = flightRoutings;
	}

	public String getDate() {
		return date;
	}

	public String getLeavingTime() {
		return leavingTime;
	}

	public String getpNRCode() {
		return pNRCode;
	}

	public PlaceInfo getSrc() {
		return src;
	}

	public PlaceInfo getDes() {
		return des;
	}

	
}
