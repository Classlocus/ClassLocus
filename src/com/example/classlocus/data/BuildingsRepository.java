package com.example.classlocus.data;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import android.database.Cursor;

public class BuildingsRepository {

	private DatabaseHelper dbHelper;
	
	public BuildingsRepository(Context context) {
		dbHelper = DatabaseHelper.getInstance(context);
	}

	// deletes all buildings from the database
	public void cleanBuilding() {
		dbHelper.wipe();
	}
	
	// create/update available through this method
	public long saveBuilding(Building building) {
		String name = building.getName();
		String abbreviation = building.getAbbreviation();
		double[] latLng = building.getLatLng();
		long parentId = building.getParentId();
		boolean accessible = building.getAccessible();
		
		return dbHelper.insert(name, abbreviation, latLng, parentId, accessible);
	}
	
	// deletes from database
	public boolean deleteBuilding(Building building) {
		Cursor cursor = dbHelper.read(building.getId()); 
		if (cursor.getCount() > 0) {
			return dbHelper.remove(building.getId());
		}
		
		cursor = dbHelper.search(building.getName());
		if (cursor.getCount() > 0) {
			cursor.moveToFirst();
			Building target = cursorToBuilding(cursor);
			return dbHelper.remove(target.getId());
		}
		
		return false;
	}
	
	public long saveFavorite(Building building) {
		
		return dbHelper.insert(building.getId());
	}
	
	public boolean isFavorite(Building building) {
		Cursor cursor = dbHelper.exists(building.getId());
		if (cursor.getCount() == 1) {
			return true;
		}
		return false;
	}
	
	// returns null List object if no valid records exist in database
	public List<Building> searchBuilding(long id) {
		List<Building> buildings = new ArrayList<Building>();
		Cursor cursor = null;
		
		cursor = dbHelper.read(id);
		
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Building building = cursorToBuilding(cursor);
			buildings.add(building);
			cursor.moveToNext();
		}
		
		cursor.close();
		return buildings;
	}
	
	// returns null List object if no valid records exist in database
	public List<Building> searchBuilding(String query) {
		List<Building> buildings = new ArrayList<Building>();
		Cursor cursor = null;
		
		cursor = dbHelper.search(query);
		
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Building building = cursorToBuilding(cursor);
			buildings.add(building);
			cursor.moveToNext();
		}
		
		cursor.close();
		return buildings;
	}
	
	private Building cursorToBuilding(Cursor cursor) {
		Building building = new Building();
		building.setId(cursor.getInt(0));
		building.setName(cursor.getString(1));
		building.setAbbreviation(cursor.getString(2));
		building.setLatLng(cursor.getDouble(3), cursor.getDouble(4));
		building.setParentId(cursor.getLong(5));
		boolean value = (cursor.getString(6)).equals("1");
		building.setAccessible(value);
		
		return building;
	}
}
