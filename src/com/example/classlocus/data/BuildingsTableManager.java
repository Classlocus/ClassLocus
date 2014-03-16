package com.example.classlocus.data;

import android.text.TextUtils;

import android.content.Context;
import android.content.ContentValues;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class BuildingsTableManager {

	private SQLiteDatabase database;
	private DatabaseHelper dbHelper;
	
	public BuildingsTableManager(Context context) {
		// non-Singleton explicit model
		// dbHelper = new DatabaseHelper(context);
		
		dbHelper = DatabaseHelper.getInstance(context);
		database = dbHelper.getWritableDatabase();
	}
	
	// SCRUD-compliant method; create and update
	public long insert(String name, String abbreviation, double[] latLng, long parentId, boolean accessible) {
		ContentValues values = new ContentValues();
		values.putNull(DatabaseContract.BuildingsTable.COLUMN_ID);
		values.put(DatabaseContract.BuildingsTable.COLUMN_NAME, name);
		values.put(DatabaseContract.BuildingsTable.COLUMN_ABBR, abbreviation);
		values.put(DatabaseContract.BuildingsTable.COLUMN_LAT, latLng[0]);
		values.put(DatabaseContract.BuildingsTable.COLUMN_LONG, latLng[1]);
		values.put(DatabaseContract.BuildingsTable.COLUMN_PARENT, parentId);
		values.put(DatabaseContract.BuildingsTable.COLUMN_ACCESS, accessible);
			
		return database.insertWithOnConflict(DatabaseContract.TABLE_BUILDINGS, null, values, SQLiteDatabase.CONFLICT_IGNORE);
	}

	// SCRUD-compliant method; delete
	public boolean remove(long id) {
		String whereClause = DatabaseContract.BuildingsTable.COLUMN_ID + " == " + id;
		int rowsAffected = 0;
			
		rowsAffected = database.delete(DatabaseContract.TABLE_BUILDINGS, whereClause, null);
			
		if (rowsAffected == 0) {
			return false;
		}
		return true;
	}
		
	// SCRUD-compliant method; read
	public Cursor read(long id) {
		String[] columns = { DatabaseContract.BuildingsTable.COLUMN_ID, 
							 DatabaseContract.BuildingsTable.COLUMN_NAME, 
							 DatabaseContract.BuildingsTable.COLUMN_ABBR, 
							 DatabaseContract.BuildingsTable.COLUMN_LAT,
							 DatabaseContract.BuildingsTable.COLUMN_LONG, 
							 DatabaseContract.BuildingsTable.COLUMN_PARENT, 
							 DatabaseContract.BuildingsTable.COLUMN_ACCESS };
		String selection = DatabaseContract.BuildingsTable.COLUMN_ID + " = " + id;
		if (id == 0) {
			selection = null;
		}
		return database.query(DatabaseContract.TABLE_BUILDINGS, columns, selection, null, null, null, null);
	}
		
	// SCRUD-compliant method; search
	public Cursor search(String query) {
		Cursor cursor = null;
			
		if (!TextUtils.isEmpty(query)) {
			String[] columns = { DatabaseContract.BuildingsTable.COLUMN_ID, 
								 DatabaseContract.BuildingsTable.COLUMN_NAME, 
								 DatabaseContract.BuildingsTable.COLUMN_ABBR, 
								 DatabaseContract.BuildingsTable.COLUMN_LAT, 
								 DatabaseContract.BuildingsTable.COLUMN_LONG, 
								 DatabaseContract.BuildingsTable.COLUMN_PARENT, 
								 DatabaseContract.BuildingsTable.COLUMN_ACCESS };
			String selection = DatabaseContract.TABLE_BUILDINGS_FTS + " MATCH ?";
			String[] selectionArgs = { String.format(DatabaseContract.BuildingsTable.COLUMN_NAME + ":%s* OR " 
					+ DatabaseContract.BuildingsTable.COLUMN_ABBR + ":%s*", query.toString(), query.toString()) }; 
				
			cursor = database.query(DatabaseContract.TABLE_BUILDINGS_FTS, columns, selection, selectionArgs, null, null, null);
		}
			
		return cursor;
	}	

}
