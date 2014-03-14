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
	
	public static final String TABLE_BUILDINGS = "buildings";
	public static final String TABLE_FAVORITES = "favorites";
	public static final String TABLE_BUILDINGS_FTS = "buildings_fts";
	
	public static final String COLUMN_ID = BaseColumns._ID;
	public static final String COLUMN_NAME = "name";
	public static final String COLUMN_ABBR = "abbreviation";
	public static final String COLUMN_LAT = "latitude";
	public static final String COLUMN_LONG = "longitude";
	public static final String COLUMN_PARENT = "parentLocation";
	public static final String COLUMN_ACCESS = "accessible";
	
	public static final String FAVORITES_ID = "fid";
	public static final String FAVORITES_BUILDING = "bid";
	
	// Singleton function
	public static DatabaseHelper getInstance(Context context) {
		if (sInstance == null) {
			sInstance = new DatabaseHelper(context.getApplicationContext());
		}
		return sInstance;
	}
	
	private DatabaseHelper(Context context) {
		super(context, DatabaseContract.DATABASE_NAME, null, DatabaseContract.DATABASE_VERSION);
		database = getWritableDatabase();
	}
	
	@Override
	public void onConfigure(SQLiteDatabase db) {
		// Enable foreign key constraints (requires API 16 or higher)
		db.setForeignKeyConstraintsEnabled(true);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.w(DatabaseHelper.class.getName(), "Updating " + DatabaseContract.DATABASE_TAG + " schema");
		
		db.execSQL(DatabaseContract.BuildingsTable.CREATE_TABLE);
		db.execSQL(DatabaseContract.BuildingsTable.CREATE_FTS_TABLE);
		db.execSQL(DatabaseContract.BuildingsTable.CREATE_INSERT_TRIGGER);
		db.execSQL(DatabaseContract.BuildingsTable.CREATE_DELETE_TRIGGER);
		db.execSQL(DatabaseContract.FavoritesTable.CREATE_TABLE);
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(DatabaseHelper.class.getName(), "Upgrading " + DatabaseContract.DATABASE_TAG + " database from version " 
				+ oldVersion + " to " + newVersion + ", which will destroy all old data");
		
		db.execSQL(DatabaseContract.BuildingsTable.DELETE_TABLE);
		db.execSQL(DatabaseContract.BuildingsTable.DELETE_FTS_TABLE);
		db.execSQL(DatabaseContract.FavoritesTable.DELETE_TABLE);
		onCreate(db);
	}
	
	@Override
	public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		onUpgrade(db, oldVersion, newVersion);
	}
	
	public void wipe() {
		database.execSQL(DatabaseContract.BuildingsTable.DELETE_TABLE);
		database.execSQL(DatabaseContract.BuildingsTable.DELETE_FTS_TABLE);
		database.execSQL(DatabaseContract.FavoritesTable.DELETE_TABLE);
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

	public long insert(long id) {
		ContentValues values = new ContentValues();
		values.putNull(FAVORITES_ID);
		values.put(FAVORITES_BUILDING, id);
		
		return database.insertWithOnConflict(TABLE_FAVORITES, null, values, SQLiteDatabase.CONFLICT_IGNORE);
	}
	
	// SCRUD-compliant method; delete
	public boolean remove(long id) {
		String whereClause = COLUMN_ID + " == " + id;
		int rowsAffected = 0;
		
		rowsAffected = database.delete(TABLE_BUILDINGS, whereClause, null);
		
		if (rowsAffected == 0) {
			return false;
		}
		return true;
	}
	
	// SCRUD-compliant method; read
	public Cursor read(long id) {
		String[] columns = { COLUMN_ID, COLUMN_NAME, COLUMN_ABBR, COLUMN_LAT,
							COLUMN_LONG, COLUMN_PARENT, COLUMN_ACCESS };
		String selection = COLUMN_ID + " = " + id;
					
		return database.query(TABLE_BUILDINGS, columns, selection, null, null, null, null);
	}
	
	public Cursor exists(long id) {
		String[] columns = { FAVORITES_BUILDING };
		String selection = FAVORITES_BUILDING + " = " + id;
		
		return database.query(TABLE_FAVORITES, columns, selection, null, null, null, null);
	}
	
	// SCRUD-compliant method; search
	public Cursor search(String query) {
		Cursor cursor = null;
		
		if (!TextUtils.isEmpty(query)) {
			String[] columns = { COLUMN_ID, COLUMN_NAME, COLUMN_ABBR, COLUMN_LAT, COLUMN_LONG, COLUMN_PARENT, COLUMN_ACCESS };
			String selection = DatabaseHelper.TABLE_BUILDINGS_FTS + " MATCH ?";
			String[] selectionArgs = { String.format(DatabaseHelper.COLUMN_NAME + ":%s* OR " 
					+ DatabaseHelper.COLUMN_ABBR + ":%s*", query.toString(), query.toString()) }; 
			
			cursor = database.query(DatabaseHelper.TABLE_BUILDINGS_FTS, columns, selection, selectionArgs, null, null, null);
		}
		
		return cursor;
	}
}
