package com.example.classlocus.data;

public class Building {
	private long id;
	private String name;
	private String abbreviation;
	private double latitude;
	private double longitude;
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
		return abbreviation;
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
	
	@Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }

        Building b = (Building) obj;
        
        if (this.id == b.id && 
        	(this.name.compareTo(b.name) == 0) && 
        	(this.abbreviation.compareTo(b.abbreviation) == 0) &&
        	this.latitude == b.latitude &&
        	this.longitude == b.longitude &&
        	this.parentId == b.parentId &&
        	this.accessible == b.accessible) {
        	return true;
        }
        return false;
    }
}