package com.example.classlocus;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;

class coordinates {
	public double lat;
	public double lng;
	public coordinates(double latitude, double longitude) {
		lat = latitude;
		lng = longitude;
	}
}

public class Building {
	private String name;
	private String abrv;
	private coordinates GPS;
	private int buildingID;
	private int parentLocation;
	private boolean accessible;
	
	public Building(String buildingName, String abreviation, coordinates buildingGPS, int BID) {
		name = buildingName;
		abrv = abreviation;
		GPS = buildingGPS;
		buildingID = BID;
	}
	
}
