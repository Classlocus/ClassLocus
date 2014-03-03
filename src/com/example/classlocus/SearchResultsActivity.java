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
	BuildingsRepository database;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_results);
		
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		
		txtQuery = (TextView) findViewById(R.id.txtQuery);
		
		database = new BuildingsRepository(this);
		
		Building a = new Building();
		a.setName("Kelley Engineering Center");
		a.setAbbreviation("KEC");
		a.setLatLng(44.5679076, -123.2783046);
		a.setParentId(10);
		a.setAccessible(true);
		
		database.saveBuilding(a);
		
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
			
			
			List<Building> buildings = database.searchBuilding(query);
			Object[] nameArray = new Object[buildings.size()];
			int i = 0;
			
			
			for (Building building : buildings) {
				Object name = building.getName();
				nameArray[i] = name;
				i++;
			}
			
			Object[] sArray = {"This", "is", 3.5, true, 2, "for", "bla"};
			
			ArrayAdapter adp = new ArrayAdapter(this, android.R.layout.simple_list_item_1, nameArray);
			
			txtQuery.setText("Results: " + query + " (count: " + buildings.size() + ")");
			
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
