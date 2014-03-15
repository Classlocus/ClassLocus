package com.example.classlocus;

import java.util.List;

import com.example.classlocus.data.*;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class FavoriteListActivity extends ListActivity {

	BuildingsRepository database;
	List<Building> buildings;
	long[] idArray;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_favorite_list);
		
		database = new BuildingsRepository(this);
		
		buildings = database.getAllFavorites();
		
		Object[] buildingNames = new Object[buildings.size()];
		idArray = new long[buildings.size()];
		int i = 0;
		
		for (Building building : buildings) {
			buildingNames[i] = (Object) building.getName();
			idArray[i] = building.getId();
			i++;
		}
		
		ArrayAdapter adp = new ArrayAdapter(this, android.R.layout.simple_list_item_1, buildingNames);
		
		setListAdapter(adp);
	}
	
	
	


	
	
}
