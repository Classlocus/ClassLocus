package com.example.classlocus.database;

import java.util.ArrayList;

public interface DatabaseTable {

	ArrayList<ColumnInstance> GetColumns();
	
	void UpdateColumns(ArrayList<ColumnInstance> columns);
	
	
}
