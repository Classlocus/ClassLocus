package com.example.classlocus.database;

import android.animation.ArgbEvaluator;

public class Building {

	private int id;
	private String name;
	private String abbreviation;
	private double latitude;
	private double longitude;
	private int buildingID;

	private int parentLocation;

	private boolean accessible;

	public Building(String buildingName, String buildingAbbreviation,
			double latitude, double longitude, int buildingId) {
		this.name = buildingName;
		this.abbreviation = buildingAbbreviation;
		this.latitude = latitude;
		this.longitude = longitude;
		this.buildingID = buildingId;
	}
	
	public void setId(int id) {
		buildingID = id;
	}

	public int getId() {
		return buildingID;
	}

	public void setName(String nm) {
		name = nm;
	}
	
	public String getName() {
		return name;
	}
	

	public String getAbbreviation() {
		return abbreviation;
	}
	
	public void setAbbreviation(String ab) {
		abbreviation = ab;
	}

	public double[] getLatLng() {
		double[] latLng = { latitude, longitude };
		return latLng;
	}
	
	public void setLatLng(double[] coord ) {
		if(coord.length != 2) {
			throw new IllegalArgumentException("Coordinate array not right length"); 
		} 
		
		latitude = coord[0];
		longitude = coord[1];
	}
}
