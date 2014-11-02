package com.travel.gate365.model;

public class PlaceInfo {

	private String countryName;
	private String locationName;
	private String securityRisk;
	private String countryId;
	private String countryISOCode;

	public PlaceInfo(String countryName, String locationName, String securityRisk) {
		this.countryName = countryName;
		this.locationName = locationName;
		this.securityRisk = securityRisk;
	}

	public PlaceInfo(String CountryId, String CountryISOCode, String countryName, String locationName, String securityRisk) {
		this(countryName, locationName, securityRisk);
		countryId = CountryId;
		countryISOCode = CountryISOCode;
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

	public String getCountryId() {
		return countryId;
	}

	public String getCountryISOCode() {
		return countryISOCode;
	}

	
}
