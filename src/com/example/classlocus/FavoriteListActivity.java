package com.example.classlocus;

import java.util.List;

import com.example.classlocus.data.*;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class FavoriteListActivity extends ListActivity {

	private Intent detailsIntent;
	BuildingsRepository database;
	private TextView infoText;
	List<Building> buildings;
	long[] idArray;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_favorite_list);
		
		infoText = (TextView) findViewById(R.id.infoText);
		
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
		
		if (buildingNames.length <= 0) {
			infoText.setText("No favorites found");
		}
		
		ArrayAdapter adp = new ArrayAdapter(this, android.R.layout.simple_list_item_1, buildingNames);
		
		setListAdapter(adp);
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id){
		super.onListItemClick(l, v, position, id);
		
		//OnClick for a given button item, given the index in the array.
		detailsIntent = new Intent(this, BuildingDetail.class);
		long buildingID = idArray[position];
		detailsIntent.putExtra("buildingID", buildingID);
		startActivity(detailsIntent);
	}
}
