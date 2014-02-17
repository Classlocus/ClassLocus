package com.example.classlocus;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import java.sql.SQLException;

import com.example.classlocus.database.Building;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

	private static final String DATABASE_NAME = "locus.db";
	private static final int DATABASE_VERSION = 1;
	private Dao<Building, Integer> buildingDao = null;
	private RuntimeExceptionDao<Building, Integer> buildingRuntimeDao = null;
	
	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
		try {
			Log.i(DatabaseHelper.class.getName(), "onCreate");
			TableUtils.createTable(connectionSource, Building.class);
		} catch (SQLException e) {
			Log.e(DatabaseHelper.class.getName(), "Can't create database", e);
			throw new RuntimeException(e);
		}
		
		// test to verify that data insertion works
		RuntimeExceptionDao<Building, Integer> dao = getBuildingDataDao();
		Building building = new Building("Kelley Engineering Center", "KEC", 44.5671987, -123.278715, 1);
		dao.create(building);
		Log.i(DatabaseHelper.class.getName(), "created new entries in onCreate");
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {
		try {
			Log.i(DatabaseHelper.class.getName(), "onUpgrade");
			TableUtils.dropTable(connectionSource, Building.class, true);
			onCreate(db, connectionSource);
		} catch (SQLException e) {
			Log.e(DatabaseHelper.class.getName(), "Can't drop databases", e);
			throw new RuntimeException(e);
		}
	}
	
	public RuntimeExceptionDao<Building, Integer> getBuildingDataDao() {
		if (buildingRuntimeDao == null) {
			buildingRuntimeDao = getRuntimeExceptionDao(Building.class);
		}
		return buildingRuntimeDao;
	}
	
	public Dao<Building, Integer> getBuildingDao() throws SQLException {
		if (buildingDao == null) {
			buildingDao = getDao(Building.class);
		}
		return buildingDao;
	}
	
	@Override
	public void close() {
		super.close();
		buildingDao = null;
	}
}
