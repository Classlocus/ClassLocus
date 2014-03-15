package com.example.classlocus.data;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import android.database.Cursor;

public class BuildingsRepository {

	private BuildingsTableManager buildingsManager;
	private FavoritesTableManager favoritesManager;
	
	public BuildingsRepository(Context context) {
		buildingsManager = new BuildingsTableManager(context);
		favoritesManager = new FavoritesTableManager(context);
	}

	// deletes all buildings from the database
	public void cleanRepository() {
		Cursor cursor = null;
		cursor = buildingsManager.search("");
		
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Building building = cursorToBuilding(cursor);
			buildingsManager.remove(building.getId());
			cursor.moveToNext();
		}
		
		cursor.close();
	}
	
	// create/update available through this method
	public long saveBuilding(Building building) {
		String name = building.getName();
		String abbreviation = building.getAbbreviation();
		double[] latLng = building.getLatLng();
		long parentId = building.getParentId();
		boolean accessible = building.getAccessible();
		
		return buildingsManager.insert(name, abbreviation, latLng, parentId, accessible);
	}
	
	// deletes from database
	public boolean deleteBuilding(Building building) {
		Cursor cursor = buildingsManager.read(building.getId()); 
		if (cursor.getCount() > 0) {
			return buildingsManager.remove(building.getId());
		}
		
		cursor = buildingsManager.search(building.getName());
		if (cursor.getCount() > 0) {
			cursor.moveToFirst();
			Building target = cursorToBuilding(cursor);
			return buildingsManager.remove(target.getId());
		}
		
		return false;
	}
	
	// returns null List object if no valid records exist in database
	public List<Building> searchBuilding(long id) {
		List<Building> buildings = new ArrayList<Building>();
		Cursor cursor = null;
		
		cursor = buildingsManager.read(id);
		
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
		
		cursor = buildingsManager.search(query);
		
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Building building = cursorToBuilding(cursor);
			buildings.add(building);
			cursor.moveToNext();
		}
		
		cursor.close();
		return buildings;
	}
	
	public long saveFavorite(Building building) {
		return favoritesManager.insert(building.getId());
	}
	
	public boolean deleteFavorite(int fid) {
		return favoritesManager.remove(fid);
	}
	
	public Building searchFavorites(int fid) {
		Cursor cursor = null;
		Building building = null;
		
		cursor = favoritesManager.read(fid);
		
		if (cursor.getCount() > 0) {
			cursor.moveToFirst();
			int bid = cursor.getInt(1);
			
			cursor = buildingsManager.read(bid);
			building = cursorToBuilding(cursor);
		}
		
		cursor.close();
		return building;
	}
	
	public int searchFavorites(Building building) {
		Cursor cursor = favoritesManager.search(building.getId());
		
		cursor.moveToFirst();
		if (cursor.getCount() > 0) { 
			return cursor.getInt(0);
		}
		return 0;
	}
	
	public List<Building> getAllFavorites() {
		List<Building> buildings = new ArrayList<Building>();
		Cursor cursor = buildingsManager.read(0);
		
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Building building = cursorToBuilding(cursor);
			if (searchFavorites(building) > 0) {
				buildings.add(building);
			}
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
