package com.example.classlocus;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
	//Database Name
	private static final String DB_NAME = "classlocus";
	//Database version (We may have to update when we update the schema)
	private static final int DB_Version = 1;
	
	//ORM instance
	private  Dao<Building, Integer> buildingDao = "classlocus";
	
	public DatabaseHelper(Context context, String databaseName,
			CursorFactory factory, int databaseVersion) {
		super(context, databaseName, factory, databaseVersion);
		// TODO Auto-generated constructor stub
	}
	
	
}
