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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.ArrayAdapter;

import com.example.classlocus.data.*;

public class SearchResultsActivity extends ListActivity {

	private Intent submitIntent;
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
		database.cleanBuilding();
		
		Building a = new Building();
		a.setName("Reser Stadium");
		a.setAbbreviation("RES");
		a.setLatLng(44.59701, -123.281609);
		a.setParentId(10);
		a.setAccessible(true);
		
		database.saveBuilding(a);
		
		handleIntent(getIntent());
	}
	
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu from XML
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.search_results_menu, menu);
		
		return super.onCreateOptionsMenu(menu);
				
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
	    int itemId = item.getItemId();
	    
	    if (itemId == R.id.submit_building) {
			submitIntent = new Intent(SearchResultsActivity.this, SubmitBuildingActivity.class);
			startActivity(submitIntent);
			return true;
	    }
	    
		return false;
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
