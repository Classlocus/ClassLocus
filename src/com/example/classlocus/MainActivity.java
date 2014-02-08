package com.example.classlocus;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import android.app.SearchManager;
import android.widget.SearchView;
import android.content.Context;
import android.provider.SearchRecentSuggestions;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	private GoogleMap googleMap;
	private Intent searchIntent;
	private Intent bld_detailIntent;
	private Intent settingsIntent;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_main);
			
		try {
			initializeMap();
        } catch (Exception e) {
            e.printStackTrace();
        }
		
		searchIntent = getIntent();
		if(Intent.ACTION_SEARCH.equals(searchIntent.getAction())) {
			String query = searchIntent.getStringExtra(SearchManager.QUERY);
			
			SearchRecentSuggestions suggestions = new SearchRecentSuggestions(this, SearchSuggestionProvider.AUTHORITY, SearchSuggestionProvider.MODE);
	        
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
				ClearSearchHistoryDialog PopupAlert = new ClearSearchHistoryDialog();
				PopupAlert.clearSearchHistory(this);
	    		return true;
	    	case R.id.building_detail:
	    		bld_detailIntent = new Intent(MainActivity.this, BuildingDetail.class);
	    		startActivity(bld_detailIntent);
	    		return true;
	    	case R.id.help:
	    		//helpscreen();
	    		return true;
	    	case R.id.settings:
	    		settingsIntent = new Intent(MainActivity.this, MapPane.class);
	    		startActivity(settingsIntent);
	    		return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	private void initializeMap() {		
        if (googleMap == null) {
        	googleMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
            // check if map is created successfully or not
            if (googleMap != null) {
            	LatLng oregonStateUniversity = new LatLng(44.5598247, -123.2820478);
         		
        		googleMap.setMyLocationEnabled(true);
        		googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(oregonStateUniversity, 13));
        		googleMap.addMarker(new MarkerOptions()
             		.title("Oregon State University")
             		.snippet("A land-, sea-, and space-grant university.")
             		.position(oregonStateUniversity));
            } else {
            	Toast.makeText(getApplicationContext(), "Failed to initialize Google Maps", Toast.LENGTH_SHORT).show();
            }
        }
    }	
}
