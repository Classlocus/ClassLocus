package com.example.classlocus;

import android.os.Bundle;
import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;
import android.provider.SearchRecentSuggestions;

public class MainActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_main);
			
		Intent searchIntent = getIntent();
		if(Intent.ACTION_SEARCH.equals(searchIntent.getAction())) {
			String query = searchIntent.getStringExtra(SearchManager.QUERY);
			
			SearchRecentSuggestions suggestions = new SearchRecentSuggestions(this,
	                SearchSuggestionProvider.AUTHORITY, SearchSuggestionProvider.MODE);
	        
			suggestions.saveRecentQuery(query, null);
			
			//runSearch(query);
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu from XML
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.options_menu, menu);
		
		//Get the SearchView and set the searchable configuration
		SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
		SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
		
		//Assumes current activity is the searchable activity
		searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
		searchView.setIconifiedByDefault(false); // Do not iconify the widget; expand it by default
		
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle item selection
	    switch (item.getItemId()) {
	    	case R.id.clear_history:
				ClearSearchHistory PopupAlert = new ClearSearchHistory();
				PopupAlert.clearSearchHistory(this);
	    		return true;
	    	case R.id.settings:
	    		return true;
	    	case R.id.help:
	    		//showHelp();
	    		return true;
	    	case R.id.about:
	    		Intent aboutIntent = new Intent(MainActivity.this, BuildingDetail.class);
	    		startActivity(aboutIntent);
	    		return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	/*
	private void setUpMapIfNeeded() {
    // Do a null check to confirm that we have not already instantiated the map.
    	if (mMap == null) {
        	mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
                            .getMap();
        	// Check if we were successful in obtaining the map.
        	if (mMap != null) {
            	// The Map is verified. It is now safe to manipulate the map.
        	}
    	}
	}
	*/
}
