package com.example.classlocus.data;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class BuildingsDataSource {

	// Database fields
	private SQLiteDatabase database;
	private BuildingDatabaseHelper dbHelper;
	private String[] allColumns = { BuildingDatabaseHelper.COLUMN_ID, 
									BuildingDatabaseHelper.COLUMN_NAME,
									BuildingDatabaseHelper.COLUMN_ABBREVIATION,
									BuildingDatabaseHelper.COLUMN_LATITUDE,
									BuildingDatabaseHelper.COLUMN_LONGITUDE,
									BuildingDatabaseHelper.COLUMN_PARENT,
									BuildingDatabaseHelper.COLUMN_ACCESSIBLE };
	
	public BuildingsDataSource(Context context) {
		dbHelper = new BuildingDatabaseHelper(context);
	}
	
	public boolean insertBuilding(Building building) {
		ContentValues values = new ContentValues();
		values.put(BuildingDatabaseHelper.COLUMN_ID, building.getId());
		values.put(BuildingDatabaseHelper.COLUMN_NAME, building.getName());
		values.put(BuildingDatabaseHelper.COLUMN_ABBREVIATION, building.getAbbreviation());
		values.put(BuildingDatabaseHelper.COLUMN_LATITUDE, building.getLatLng()[0]);
		values.put(BuildingDatabaseHelper.COLUMN_LONGITUDE, building.getLatLng()[1]);
		values.put(BuildingDatabaseHelper.COLUMN_PARENT, building.getParentId());
		values.put(BuildingDatabaseHelper.COLUMN_ACCESSIBLE, building.getAccessible());
		
		int rowsAffected = 0;
		String[] id = new String[1];
		String where = "";
		
		try {
			database = dbHelper.getWritableDatabase();
			
			if (database.isOpen()) {
				database.beginTransaction();
				
				id[0] = String.valueOf(building.getId());
				where = BuildingDatabaseHelper.COLUMN_ID + "= ?";
				rowsAffected = database.update(BuildingDatabaseHelper.TABLE_BUILDINGS, values, where, id);
				
				// insert if update resulted in 0 rows affected; since it is a new entry
				if (rowsAffected == 0) {
					database.insert(BuildingDatabaseHelper.TABLE_BUILDINGS, "", values);
				}
				
				database.setTransactionSuccessful();
				database.endTransaction();
				
				return true;
			}
			else {
				return true;
			}
		}
		catch (Exception e) {
			Log.e(BuildingDatabaseHelper.DATABASE_NAME, e.getMessage());
			return false;
		}
		finally {
			dbHelper.close();
		}
	}
	
	public boolean removeBuilding(long id) {
		try {
			database = dbHelper.getWritableDatabase();
			
			database.delete(BuildingDatabaseHelper.TABLE_BUILDINGS, 
					BuildingDatabaseHelper.COLUMN_ID + " = " + id, null);
			
			if (getBuilding(id) == null) {
				return true;
			} else {
				return false;
			}
		}
		catch (Exception e) {
			Log.e(BuildingDatabaseHelper.DATABASE_NAME, e.getMessage());
			return false;
		}
		finally {
			dbHelper.close();
		}
	}
	
	// found Building object returned, otherwise returns null
	public Building getBuilding(long id) { 
		database = dbHelper.getWritableDatabase();
		Cursor cursor = database.query(BuildingDatabaseHelper.TABLE_BUILDINGS, allColumns, 
			BuildingDatabaseHelper.COLUMN_ID + " = " + id, null, null, null, null);
		
		if (cursor.getCount() > 0) {
			cursor.moveToFirst();
			Building foundBuilding = cursorToBuilding(cursor);
			cursor.close();
			dbHelper.close();
			return foundBuilding;
		}
		dbHelper.close();
		return null;
	}
	
	public List<Building> getAllBuildings() {
		database = dbHelper.getWritableDatabase();
		List<Building> buildings = new ArrayList<Building>();
		
		Cursor cursor = database.query(BuildingDatabaseHelper.TABLE_BUILDINGS, 
				allColumns, null, null, null, null, null);
		
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Building building = cursorToBuilding(cursor);
			buildings.add(building);
			cursor.moveToNext();
		}
		
		// make sure to close the cursor
		cursor.close();
		dbHelper.close();
		return buildings;
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
