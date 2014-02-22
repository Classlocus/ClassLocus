package com.example.classlocus.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class BuildingDatabaseHelper extends SQLiteOpenHelper {
	
	public static final int DATABASE_VERSION = 1;
	public static final String DATABASE_NAME = "locus.db";
	
	public static final String TABLE_BUILDINGS = "buildings";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_NAME = "name";
	public static final String COLUMN_ABBREVIATION = "abbreviation";
	public static final String COLUMN_LATITUDE = "latitude";
	public static final String COLUMN_LONGITUDE = "longitude";
	public static final String COLUMN_BUILDING_ID = "buildingID";
	public static final String COLUMN_PARENT = "parentLocation";
	public static final String COLUMN_ACCESSIBLE = "accessible";
	
	private static final String DATABASE_CREATE = "create table "
			+ TABLE_BUILDINGS + "(" + COLUMN_ID + " integer primary key autoincrement, " 
			+ COLUMN_NAME + " text not null, " + COLUMN_ABBREVIATION + ", " + COLUMN_LATITUDE
			+ ", " + COLUMN_LONGITUDE + ", " + COLUMN_BUILDING_ID + ", " + COLUMN_PARENT + ", "
			+ COLUMN_ACCESSIBLE + ");";
	
	BuildingDatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(DATABASE_CREATE);
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(BuildingDatabaseHelper.class.getName(), "Upgrading database from version " 
				+ oldVersion + " to " + newVersion + ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_BUILDINGS);
		onCreate(db);
	}
	

}