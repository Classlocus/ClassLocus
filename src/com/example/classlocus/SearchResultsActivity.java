package com.example.classlocus;

import java.util.List;

import android.app.ActionBar;
import android.app.ListActivity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.provider.SearchRecentSuggestions;
import android.view.View;
import android.widget.ListView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.ArrayAdapter;

import com.example.classlocus.data.*;
import com.example.classlocus.search.SearchSuggestionProvider;

public class SearchResultsActivity extends ListActivity {
	
	private TextView textQuery;
	private Intent submitIntent;
	private Intent detailsIntent;
	BuildingsRepository database;
	List<Building> buildings;
	long[] idArray;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_results);
		
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		
		textQuery = (TextView) findViewById(R.id.textQuery);
		
		database = new BuildingsRepository(this);
		
		handleIntent(getIntent());
		
		Intent intent = getIntent();
		if (Intent.ACTION_SEARCH.equals(intent.getAction())){
			String query = intent.getStringExtra(SearchManager.QUERY);
			SearchRecentSuggestions suggestions = new SearchRecentSuggestions(this, SearchSuggestionProvider.AUTHORITY, SearchSuggestionProvider.MODE);
			suggestions.saveRecentQuery(query, null);
		}
	}
	
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu from XML
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.search_results_menu, menu);
		
		return super.onCreateOptionsMenu(menu);		
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.submit_building:
			submitIntent = new Intent(SearchResultsActivity.this, SubmitBuildingActivity.class);
			this.finish();
			startActivity(submitIntent);
			return true;
		default:
			return super.onOptionsItemSelected(item);
	    }
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
	
	@Override
	protected void onNewIntent(Intent intent) {
		setIntent(intent);
		handleIntent(intent);
	}
	
	private void handleIntent(Intent intent) {
		if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
			String query = intent.getStringExtra(SearchManager.QUERY);
			buildings = database.searchBuilding(query);
			
			Object[] buildingNames = new Object[buildings.size()];
			idArray = new long[buildings.size()];
			int i = 0;
			
			for (Building building : buildings) {
				buildingNames[i] = (Object) building.getName();
				idArray[i] = building.getId();
				i++;
			}
			
			textQuery.setText(buildings.size() + " results found for '" + query + "'");
			ArrayAdapter adp = new ArrayAdapter(this, android.R.layout.simple_list_item_1, buildingNames);
			
			setListAdapter(adp);
		}
	}
}
