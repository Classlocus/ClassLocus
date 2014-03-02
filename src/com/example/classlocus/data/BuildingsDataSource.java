package com.example.classlocus.data;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.text.TextUtils;

public class BuildingsDataSource {

	private SQLiteDatabase database;
	private DatabaseHelper dbHelper;
	private String[] allColumns = { DatabaseHelper.COLUMN_ID, 
									DatabaseHelper.COLUMN_NAME,
									DatabaseHelper.COLUMN_ABBR,
									DatabaseHelper.COLUMN_LAT,
									DatabaseHelper.COLUMN_LONG,
									DatabaseHelper.COLUMN_PARENT,
									DatabaseHelper.COLUMN_ACCESS };
	
	public BuildingsDataSource(Context context) {
		dbHelper = DatabaseHelper.getInstance(context);
	}
	
	public boolean insertBuilding(Building building) {
		ContentValues values = new ContentValues();
		values.put(DatabaseHelper.COLUMN_ID, building.getId());
		values.put(DatabaseHelper.COLUMN_NAME, building.getName());
		values.put(DatabaseHelper.COLUMN_ABBR, building.getAbbreviation());
		values.put(DatabaseHelper.COLUMN_LAT, building.getLatLng()[0]);
		values.put(DatabaseHelper.COLUMN_LONG, building.getLatLng()[1]);
		values.put(DatabaseHelper.COLUMN_PARENT, building.getParentId());
		values.put(DatabaseHelper.COLUMN_ACCESS, building.getAccessible());
		
		int rowsAffected = 0;
		String[] id = new String[1];
		String where = "";
		
		try {
			database = dbHelper.getWritableDatabase();
			
			if (database.isOpen()) {
				database.beginTransaction();
				
				id[0] = String.valueOf(building.getId());
				where = DatabaseHelper.COLUMN_ID + "= ?";
				rowsAffected = database.update(DatabaseHelper.TABLE_BUILDINGS, values, where, id);
				
				// insert if update resulted in 0 rows affected (0 means it is a new entry)
				if (rowsAffected == 0) {
					database.insert(DatabaseHelper.TABLE_BUILDINGS, "", values);
				}
				database.setTransactionSuccessful();
				database.endTransaction();
			}
			return true;
		}
		catch (Exception e) {
			Log.e(DatabaseHelper.DATABASE_NAME, e.getMessage());
			return false;
		}
		finally {
			dbHelper.close();
		}
	}
	
	public boolean removeBuilding(long id) {
		try {
			database = dbHelper.getWritableDatabase();
			database.delete(DatabaseHelper.TABLE_BUILDINGS, 
					DatabaseHelper.COLUMN_ID + " = " + id, null);
			
			if (getBuilding(id) == null) {
				return true;
			} else {
				return false;
			}
		}
		catch (Exception e) {
			Log.e(DatabaseHelper.DATABASE_NAME, e.getMessage());
			return false;
		}
		finally {
			dbHelper.close();
		}
	}
	
	// returns Building object on query, returns null if not found
	public Building getBuilding(long id) {
		Cursor cursor;
		
		try {
			database = dbHelper.getReadableDatabase();
			cursor = database.query(DatabaseHelper.TABLE_BUILDINGS, allColumns, 
					DatabaseHelper.COLUMN_ID + " = " + id, null, null, null, null);
			
			if (cursor.getCount() > 0) {
				cursor.moveToFirst();
				Building foundBuilding = cursorToBuilding(cursor);
				cursor.close();
				return foundBuilding;
			} else {
				cursor.close();
				return null;
			}
		}
		catch (Exception e) {
			Log.e(DatabaseHelper.DATABASE_NAME, e.getMessage());
			return null;
		}
		finally {
			dbHelper.close();
		}
	}
	
	// returns List<Building> object on query, returns empty list if empty database
	public List<Building> getAllBuildings() {
		List<Building> buildings = new ArrayList<Building>();
		Cursor cursor;
		
		try {
			database = dbHelper.getReadableDatabase();
			cursor = database.query(DatabaseHelper.TABLE_BUILDINGS, 
					allColumns, null, null, null, null, null);
			
			cursor.moveToFirst();
			while (!cursor.isAfterLast()) {
				Building building = cursorToBuilding(cursor);
				buildings.add(building);
				cursor.moveToNext();
			}
			cursor.close();
			return buildings;
		}
		catch (Exception e) {
			Log.e(DatabaseHelper.DATABASE_NAME, e.getMessage());
			return null;
		}
		finally {
			dbHelper.close();
		}
	}
	
	public int selectAll() {
		final String selection = DatabaseHelper.TABLE_BUILDINGS + " MATCH ?";
		database = dbHelper.getReadableDatabase();
		Cursor cursor = database.query(DatabaseHelper.TABLE_BUILDINGS, allColumns, selection, null, null, null, null);
		
		return cursor.getCount();
	}
	
	// returns List<Building> object on query, returns empty list if no matches exist
	public List<Building> getMatchedBuildings(String query) {
		List<Building> buildings = new ArrayList<Building>();
		Cursor cursor = null;
		
		try {
			database = dbHelper.getReadableDatabase();
			
			if (!TextUtils.isEmpty(query)) {
				final String selection = DatabaseHelper.TABLE_BUILDINGS + " MATCH ?";
				final String[] selectionArgs = { appendWildcard(query) + " " + DatabaseHelper.COLUMN_NAME + ": " + query.toString() };
				
				cursor = database.query(DatabaseHelper.TABLE_BUILDINGS, allColumns, selection, selectionArgs, null, null, null);
			}
			
			cursor.moveToFirst();
			while (!cursor.isAfterLast()) {
				Building building = cursorToBuilding(cursor);
				buildings.add(building);
				cursor.moveToNext();
			}
			cursor.close();
			return buildings;
		}
		catch (Exception e) {
			Log.e(DatabaseHelper.TABLE_BUILDINGS, e.getMessage());
			return null;
		}
		finally {
			dbHelper.close();
		}
	}
	
	private String appendWildcard(String query) {
		if(TextUtils.isEmpty(query)) {
			return query;
		}
		
		final StringBuilder builder = new StringBuilder();
		final String[] splits = TextUtils.split(query, " ");
		
		for (String split : splits) {
			builder.append(split).append("*").append(" ");
		}
		
		return builder.toString().trim();
	}
	
	private Building cursorToBuilding(Cursor cursor) {
		Building building = new Building();
		building.setId(cursor.getLong(0));
		building.setName(cursor.getString(1));
		building.setAbbreviation(cursor.getString(2));
		building.setLatLng(cursor.getDouble(3), cursor.getDouble(4));
		building.setParentId(cursor.getLong(5));
		boolean value = (cursor.getString(6)).equals("1");
		building.setAccessible(value);
		
		return building;
	}
}
