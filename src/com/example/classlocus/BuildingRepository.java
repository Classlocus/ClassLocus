package com.example.classlocus;

import android.content.Context;
import java.util.List;
import java.sql.SQLException;

import com.example.classlocus.database.Building;
import com.j256.ormlite.dao.Dao;

public class BuildingRepository {

	private DatabaseHelper db;
	Dao<Building, Integer> buildingDao;
	
	public BuildingRepository(Context ctx) {
		try {
			DatabaseManager dbManager = new DatabaseManager();
			db = dbManager.getHelper(ctx);
			buildingDao = db.getBuildingDao();
		} catch (SQLException e) {
			// TODO: Exception Handling
			e.printStackTrace();
		}
	}
	
	public int create(Building building) {
		try {
			return buildingDao.create(building);
		} catch (SQLException e) {
			// TODO: Exception Handling
			e.printStackTrace();
		}
		return 0;
	}
	
	public int update(Building building) {
		try {
			return buildingDao.update(building);
		} catch (SQLException e) {
			// TODO: Exception Handling
			e.printStackTrace();
		}
		return 0;
	}
	
	public int delete(Building building) {
		try {
			return buildingDao.delete(building);
		} catch (SQLException e) {
			// TODO: Exception Handling
			e.printStackTrace();
		}
		return 0;
	}
	
	public List getAll() {
		try {
			return buildingDao.queryForAll();
		} catch (SQLException e) {
			// TODO: Exception Handling
			e.printStackTrace();
		}
		return null;
	}

}
