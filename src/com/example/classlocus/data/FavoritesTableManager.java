package com.example.classlocus.data;

import android.content.ContentValues;
import android.content.Context;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class FavoritesTableManager {

	private SQLiteDatabase database;
	private DatabaseHelper dbHelper;
	
	public FavoritesTableManager(Context context) {
		dbHelper = DatabaseHelper.getInstance(context);
		database = dbHelper.getWritableDatabase();
	}
	
	// SCRUD-compliant method; create and update
	public long insert(long bid) {
		ContentValues values = new ContentValues();
		values.putNull(DatabaseContract.FavoritesTable.COLUMN_ID);
		values.put(DatabaseContract.FavoritesTable.COLUMN_BUILDING, bid);
		
		return database.insertWithOnConflict(DatabaseContract.TABLE_FAVORITES, null, values, SQLiteDatabase.CONFLICT_IGNORE);
	}
	
	// SCRUD-compliant method; delete
	public boolean remove(long fid) {
		String whereClause = DatabaseContract.FavoritesTable.COLUMN_ID + " == " + fid;
		int rowsAffected = 0;
				
		rowsAffected = database.delete(DatabaseContract.TABLE_FAVORITES, whereClause, null);
				
		if (rowsAffected == 0) {
			return false;
		}
		return true;
	}
	
	// SCRUD-compliant method; read
	public Cursor read(long fid) {
		String[] columns = { DatabaseContract.FavoritesTable.COLUMN_ID, 
							 DatabaseContract.FavoritesTable.COLUMN_BUILDING };
		String selection = DatabaseContract.FavoritesTable.COLUMN_ID + " = " + fid;
						
		return database.query(DatabaseContract.TABLE_FAVORITES, columns, selection, null, null, null, null);
	}
	
	// SCRUD-compliant method; search
	public Cursor search(long bid) {
		String[] columns = { DatabaseContract.FavoritesTable.COLUMN_ID, 
				 			 DatabaseContract.FavoritesTable.COLUMN_BUILDING };
		String selection = DatabaseContract.FavoritesTable.COLUMN_BUILDING + " = " + bid;
			
		return database.query(DatabaseContract.TABLE_FAVORITES, columns, selection, null, null, null, null);
	}
	
}
