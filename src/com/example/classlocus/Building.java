package com.example.classlocus;

//import android.content.Context;
//import android.database.sqlite.SQLiteDatabase.CursorFactory;
//import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;

import com.j256.ormlite.table.DatabaseTable;
import com.j256.ormlite.field.DatabaseField;

@DatabaseTable(tableName = "building")
public class Building {
	
	@DatabaseField(generatedId=true)
	private int id;
	
	@DatabaseField(unique=true)
	private String name;
	
	@DatabaseField(unique=true)
	private String abbreviation;
	
	@DatabaseField
	private double latitude;
	
	@DatabaseField
	private double longitude;
	
	@DatabaseField(unique=true)
	private int buildingID;
	
	@DatabaseField
	private int parentLocation;
	
	@DatabaseField
	private boolean accessible;
	
	public Building() {
		//Needed for ORM
	}
	
	public Building(String buildingName, String buildingAbbreviation, double latitude, double longitude, int buildingId) {
		this.name = buildingName;
		this.abbreviation = buildingAbbreviation;
		this.latitude = latitude;
		this.longitude = longitude;
		this.buildingID = buildingId;
	}
	
	public int getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public String getAbbreviation() {
		return abbreviation;
	}
	
	public double[] getLatLng() {
		double[] latLng = {latitude, longitude}; 
		return latLng;
	}
}
