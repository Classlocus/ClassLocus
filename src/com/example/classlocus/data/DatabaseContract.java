package com.example.classlocus.data;

import android.provider.BaseColumns;

public final class DatabaseContract {

	public static final String DATABASE_TAG = "LocusDatabase";
	public static final String DATABASE_NAME = "locus.db";
	public static final int DATABASE_VERSION = 1;
	
	public static final String TABLE_BUILDINGS = "buildings";
	public static final String TABLE_BUILDINGS_FTS = "buildings_fts";
	public static final String TABLE_FAVORITES = "favorites";
	
	// contract class should not be instantiated, therefore empty constructor
	public DatabaseContract() {}

	public static abstract class BuildingsTable implements BaseColumns {
		public static final String COLUMN_ID = BaseColumns._ID;
		public static final String COLUMN_NAME = "name";
		public static final String COLUMN_ABBR = "abbreviation";
		public static final String COLUMN_LAT = "latitude";
		public static final String COLUMN_LONG = "longitude";
		public static final String COLUMN_PARENT = "parentLocation";
		public static final String COLUMN_ACCESS = "accessible";
		
		private static final String BUILDINGS_INSERT_TRIGGER = "buildings_insert";
		private static final String BUILDINGS_DELETE_TRIGGER = "buildings_delete";
		
		public static final String CREATE_TABLE = "CREATE TABLE " 
				+ TABLE_BUILDINGS + " ("
					+ COLUMN_ID + " integer primary key autoincrement, "
					+ COLUMN_NAME + " text not null unique, " 
					+ COLUMN_ABBR + " text, " 
					+ COLUMN_LAT + " float, " 
					+ COLUMN_LONG + " float, " 
					+ COLUMN_PARENT + " integer, "
					+ COLUMN_ACCESS + " bit not null" + ");";
		
		public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_BUILDINGS;
		
		public static final String CREATE_FTS_TABLE = "CREATE VIRTUAL TABLE " 
				+ TABLE_BUILDINGS_FTS + " USING fts3("
					+ COLUMN_ID + ", " 
					+ COLUMN_NAME + ", "
					+ COLUMN_ABBR + ", " 
					+ COLUMN_LAT + ", " 
					+ COLUMN_LONG + ", " 
					+ COLUMN_PARENT + ", " 
					+ COLUMN_ACCESS + ");";
		
		public static final String DELETE_FTS_TABLE = "DROP TABLE IF EXISTS " + TABLE_BUILDINGS_FTS;
		
		public static final String CREATE_INSERT_TRIGGER = "CREATE TRIGGER " + BUILDINGS_INSERT_TRIGGER
				+ " AFTER INSERT ON " + TABLE_BUILDINGS
				+ " BEGIN "
					+ "INSERT INTO " + TABLE_BUILDINGS_FTS + "("
						+ "rowid, " 
						+ COLUMN_ID + ", " 
						+ COLUMN_NAME + ", " 
						+ COLUMN_ABBR + ", " 
						+ COLUMN_LAT + ", "
						+ COLUMN_LONG + ", " 
						+ COLUMN_PARENT + ", " 
						+ COLUMN_ACCESS + ") "
					+ "VALUES ("
						+ "last_insert_rowid(), " 
						+ "last_insert_rowid(), "
						+ "NEW." + COLUMN_NAME + ", " 
						+ "NEW." + COLUMN_ABBR + ", "
						+ "NEW." + COLUMN_LAT + ", " 
						+ "NEW." + COLUMN_LONG + ", "
						+ "NEW." + COLUMN_PARENT + ", " 
						+ "NEW." + COLUMN_ACCESS + "); "
				+ " END ";
		
		public static final String CREATE_DELETE_TRIGGER = "CREATE TRIGGER " + BUILDINGS_DELETE_TRIGGER
				+ " AFTER DELETE ON " + TABLE_BUILDINGS
				+ " BEGIN "
					+ "DELETE FROM " + TABLE_BUILDINGS_FTS + " WHERE rowid = OLD.rowid;"
				+ " END ";
	}
	
	public static abstract class FavoritesTable implements BaseColumns {
		public static final String COLUMN_ID = "fid";
		public static final String COLUMN_BUILDING = "bid";
		
		public static final String CREATE_TABLE = "CREATE TABLE "
				+ TABLE_FAVORITES + " ("
					+ COLUMN_ID + " integer primary key autoincrement, "
					+ COLUMN_BUILDING + " integer, "
					+ "FOREIGN KEY(" + COLUMN_BUILDING + ") REFERENCES " 
						+ TABLE_BUILDINGS + "(" + BuildingsTable.COLUMN_ID + ") ON DELETE CASCADE);";
		
		public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_FAVORITES;
	}
}
