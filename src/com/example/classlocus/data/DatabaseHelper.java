package com.example.classlocus.data;

import android.text.TextUtils;
import android.util.Log;

import android.provider.BaseColumns;

import android.content.ContentValues;
import android.content.Context;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
	
	private static DatabaseHelper sInstance;
	private SQLiteDatabase database;
	
	private static final String TAG = "LocusDatabase";
	public static final String DATABASE_NAME = "locus.db";
	private static final int DATABASE_VERSION = 2;
	
	public static final String TABLE_BUILDINGS = "buildings";
	public static final String COLUMN_ID = BaseColumns._ID;
	public static final String COLUMN_NAME = "name";
	public static final String COLUMN_ABBR = "abbreviation";
	public static final String COLUMN_LAT = "latitude";
	public static final String COLUMN_LONG = "longitude";
	public static final String COLUMN_PARENT = "parentLocation";
	public static final String COLUMN_ACCESS = "accessible";
	
	public static DatabaseHelper getInstance(Context context) {
		if (sInstance == null) {
			sInstance = new DatabaseHelper(context.getApplicationContext());
		}
		return sInstance;
	}
	
	private DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		database = getWritableDatabase();
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.w(DatabaseHelper.class.getName(), "Updating " + TAG + " schema");
		
		db.execSQL("CREATE VIRTUAL TABLE " + TABLE_BUILDINGS + " USING fts3 ("
				+ COLUMN_ID + " integer primary key autoincrement, "
				+ COLUMN_NAME + " unique, " 
				+ COLUMN_ABBR + ", " + COLUMN_LAT + ", " 
				+ COLUMN_LONG + ", " + COLUMN_PARENT + ", "
				+ COLUMN_ACCESS + ");");
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(DatabaseHelper.class.getName(), "Upgrading " + TAG + " database from version " 
				+ oldVersion + " to " + newVersion + ", which will destroy all old data");
		
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_BUILDINGS);
		onCreate(db);
	}
	
	public void wipe() {
		database.execSQL("DROP TABLE IF EXISTS " + TABLE_BUILDINGS);
		onCreate(database);
	}
	
	// SCRUD-compliant method; create and update
	public long insert(String name, String abbreviation, double[] latLng, long parentId, boolean accessible) {
		ContentValues values = new ContentValues();
		values.putNull(COLUMN_ID);
		values.put(COLUMN_NAME, name);
		values.put(COLUMN_ABBR, abbreviation);
		values.put(COLUMN_LAT, latLng[0]);
		values.put(COLUMN_LONG, latLng[1]);
		values.put(COLUMN_PARENT, parentId);
		values.put(COLUMN_ACCESS, accessible);
		
		return database.insertWithOnConflict(TABLE_BUILDINGS, null, values, SQLiteDatabase.CONFLICT_IGNORE);
	}
	
	// SCRUD-compliant method; delete
	public boolean remove(String name) {
		String whereClause = COLUMN_NAME + " LIKE " + name;
		int rowsAffected = 0;
			
		rowsAffected = database.delete(TABLE_BUILDINGS, whereClause, null);
			
		if (rowsAffected == 0) {
			return false;
		}
		return true;
	}
	
	// SCRUD-compliant method; read
	public Cursor read(long id) {
		Cursor cursor = null;
			
		String[] columns = { COLUMN_ID, COLUMN_NAME, COLUMN_ABBR, COLUMN_LAT,
							COLUMN_LONG, COLUMN_PARENT, COLUMN_ACCESS };
		String selection = COLUMN_ID + " = " + id;
					
		cursor = database.query(TABLE_BUILDINGS, columns, selection, null, null, null, null);
			
		return cursor;
	}
	
	// SCRUD-compliant method; search
	public Cursor search(String query) {
		Cursor cursor = null;
		
		if (!TextUtils.isEmpty(query)) {
			
			String[] columns = { COLUMN_ID, COLUMN_NAME, COLUMN_ABBR, COLUMN_LAT,
					COLUMN_LONG, COLUMN_PARENT, COLUMN_ACCESS };
			String selection = DatabaseHelper.TABLE_BUILDINGS + " MATCH ?";
			String[] selectionArgs = { DatabaseHelper.COLUMN_NAME + ": " + query.toString() + "*" };
			
			cursor = database.query(DatabaseHelper.TABLE_BUILDINGS, columns, selection, selectionArgs, null, null, null);
		}
		
		return cursor;
	}
}
