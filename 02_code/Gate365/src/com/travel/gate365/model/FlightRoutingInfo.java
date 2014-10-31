package com.travel.gate365.model;

public class FlightRoutingInfo {

	private final int id;
	private final String flightNumber;
	private final String departureAirport;
	private final String departureDateTime;
	private final String arrivalAirport;
	private final String arrivalDateTime;
	private final String carrierName;

	public FlightRoutingInfo(int id, String flightNumber, String carrierName, String departureAirport, String departureDateTime, String arrivalAirport, String arrivalDateTime) {
		this.id = id;
		this.flightNumber = flightNumber;
		this.carrierName = carrierName;
		this.departureAirport = departureAirport;
		this.departureDateTime = departureDateTime;
		this.arrivalAirport = arrivalAirport;
		this.arrivalDateTime = arrivalDateTime;
	}
	
	public int getId() {
		return id;
	}

	public String getFlightNumber() {
		return flightNumber;
	}

	public String getDepartureAirport() {
		return departureAirport;
	}

	public String getDepartureDateTime() {
		return departureDateTime;
	}

	public String getArrivalAirport() {
		return arrivalAirport;
	}

	public String getArrivalDateTime() {
		return arrivalDateTime;
	}

	public String getCarrierName() {
		return carrierName;
	}

}
