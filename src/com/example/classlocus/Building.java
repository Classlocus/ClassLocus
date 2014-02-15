package com.example.classlocus;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "building")
public class Building {
	@DatabaseField(id=true)
	private int id;
	@DatabaseField(unique=true)
	private String name;
	@DatabaseField(unique=true)
	private String abrv;
	@DatabaseField
	private int lat;
	@DatabaseField
	private int lng;
	@DatabaseField(unique=true)
	private int buildingID;
	@DatabaseField
	private int parentLocation;
	@DatabaseField
	private boolean accessible;
	
	public Building() {
		//Needed for ORM
	}
	
	public Building(String buildingName, String abreviation, int lt, int lg, int BID) {
		name = buildingName;
		abrv = abreviation;
		lat = lt;
		lng = lg;
		buildingID = BID;
	}
}
