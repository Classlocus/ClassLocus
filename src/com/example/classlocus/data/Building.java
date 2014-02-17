package com.example.classlocus.data;

public class Building {
	private long id;
	private String name;
	private String abbreviation;
	private double latitude;
	private double longitude;
	private long buildingId;
	private long parentId;
	private boolean accessible;
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getAbbreviation() {
		return name;
	}
	
	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}
	
	public double[] getLatLng() {
		double[] latLng = {latitude, longitude}; 
		return latLng;
	}
	
	public void setLatLng(double latitude, double longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	public long getBuildingId() {
		return buildingId;
	}
	
	public void setBuildingId(long buildingId) {
		this.buildingId = buildingId;
	}
	
	public long getParentId() {
		return parentId;
	}
	
	public void setParentId(long parentId) {
		this.parentId = parentId;
	}
	
	public boolean getAccessible() {
		return accessible;
	}
	
	public void setAccessible(boolean accessible) {
		this.accessible = accessible;
	}
	
	// will be used by the ArrayAdapter in the ListView
	@Override
	public String toString() {
		return name;
	}
}