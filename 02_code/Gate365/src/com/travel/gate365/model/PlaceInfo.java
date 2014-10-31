package com.travel.gate365.model;

public class PlaceInfo {

	private String countryName;
	private String locationName;
	private String securityRisk;

	public PlaceInfo(String countryName, String locationName, String securityRisk) {
		this.countryName = countryName;
		this.locationName = locationName;
		this.securityRisk = securityRisk;
	}

	public String getCountryName() {
		return countryName;
	}

	public String getLocationName() {
		return locationName;
	}

	public String getSecurityRisk() {
		return securityRisk;
	}

}
