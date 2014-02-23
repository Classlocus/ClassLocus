package com.example.classlocus.data;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class BuildingsDataSource {

	// Database fields
	private SQLiteDatabase database;
	private BuildingDatabaseHelper dbHelper;
	private String[] allColumns = { BuildingDatabaseHelper.COLUMN_ID, BuildingDatabaseHelper.COLUMN_NAME };
	
	public BuildingsDataSource(Context context) {
		dbHelper = new BuildingDatabaseHelper(context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}
	
	public void close() {
		dbHelper.close();
	}
	
	public Building createBuilding(String name, String abbreviation) {
		ContentValues values = new ContentValues();
		values.put(BuildingDatabaseHelper.COLUMN_NAME, name);
		values.put(BuildingDatabaseHelper.COLUMN_ABBREVIATION, abbreviation);
		
		long insertId = database.insert(BuildingDatabaseHelper.TABLE_BUILDINGS, null, values);
		Cursor cursor = database.query(BuildingDatabaseHelper.TABLE_BUILDINGS, allColumns, 
				BuildingDatabaseHelper.COLUMN_ID + " = " + insertId, null, null, null, null);
		
		cursor.moveToFirst();
		Building newBuilding = cursorToBuilding(cursor);
		cursor.close();
		return newBuilding;
	}
	
	public void deleteBuilding(Building building) {
		long id = building.getId();
		System.out.println("Building deleted with id: " + id);
		database.delete(BuildingDatabaseHelper.TABLE_BUILDINGS, 
				BuildingDatabaseHelper.COLUMN_ID + " = " + id, null);
	}
	
	public Building getBuilding(long buildingId) { 
		Cursor cursor = database.query(BuildingDatabaseHelper.TABLE_BUILDINGS, allColumns, 
			BuildingDatabaseHelper.COLUMN_ID + " = " + buildingId, null, null, null, null);
		
		Building foundBuilding = cursorToBuilding(cursor);
		cursor.close();
		
		return foundBuilding; 
	}
	
	public List<Building> getAllBuildings() {
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
		return buildings;
	}
	
	private Building cursorToBuilding(Cursor cursor) {
		Building building = new Building();
		building.setId(cursor.getLong(0));
		building.setName(cursor.getString(1));
		return building;
	}
}
