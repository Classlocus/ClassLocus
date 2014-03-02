package com.example.classlocus;

import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

import android.app.ActionBar;
import android.app.ListActivity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.ArrayAdapter;

import com.example.classlocus.data.*;

public class SearchResultsActivity extends ListActivity {

	private TextView txtQuery;
	BuildingsDataSource database;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_results);
		
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		
		txtQuery = (TextView) findViewById(R.id.txtQuery);
		
		database = new BuildingsDataSource(this);
		
		Building ex = new Building();
		ex.setId(34);
		ex.setName("Kelley Engineering Center");
		ex.setAbbreviation("KEC");
		ex.setLatLng(44.5679076, -123.2783046);
		ex.setParentId(10);
		ex.setAccessible(true);
		
		database.insertBuilding(ex);
		
		handleIntent(getIntent());
	}
	
	@Override
	protected void onNewIntent(Intent intent) {
		setIntent(intent);
		handleIntent(intent);
	}
	
	private void handleIntent(Intent intent) {
		if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
			String query = intent.getStringExtra(SearchManager.QUERY);
			
			/*
			List<Building> allBuildings = database.getAllBuildings();
			List<String> names = new ArrayList<String>();
			
			for (Building building : allBuildings) {
				names.add(building.toString());
			}
			*/
			
			Object[] sArray = {"This", "is", 3.5, true, 2, "for", "bla"};
			
			ArrayAdapter adp = new ArrayAdapter(this, android.R.layout.simple_list_item_1, sArray);
			
			txtQuery.setText("Results: " + query);
			
			/*
			if (names.size() > 0) {
				txtQuery.setText("Results: " + names.get(0));
			} else {
				txtQuery.setText("Results: names is empty");
			}
			*/
			
			setListAdapter(adp);
		}
	}
}
