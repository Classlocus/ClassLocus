package com.example.classlocus;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;

import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.*;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import android.app.SearchManager;
import android.widget.SearchView;
import android.widget.Toast;
import android.content.Context;
import android.provider.SearchRecentSuggestions;

public class MainActivity extends Activity {
	
	static final int REQUEST_CODE_RECOVER_PLAY_SERVICES = 1001;
	private Intent searchIntent;
	private Intent bld_detailIntent;
	private Intent settingsIntent;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_main);
		
		GoogleMap map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
		//map.setMyLocationEnabled(true);
		map.setBuildingsEnabled(true);
		
		LatLng oregonstate = new LatLng(44.5657285, -123.2788689);
		map.addMarker(new MarkerOptions()
     		.title("Oregon State University")
     		.snippet("A land-, sea-, and space-grant university.")
     		.position(oregonstate));
		
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
	protected void onResume() {
		super.onResume();
		
		if (checkPlayServices()) {
			// Google Play Services are installed and GoogleMap object can be loaded
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
			case REQUEST_CODE_RECOVER_PLAY_SERVICES:
				if (resultCode == RESULT_CANCELED) {
					Toast.makeText(this, "Google Play Services must be installed.", Toast.LENGTH_SHORT).show();
					finish();
				}
				return;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    int itemId = item.getItemId();
	    
		if (itemId == R.id.clear_history) {
			ClearSearchHistoryDialog PopupAlert = new ClearSearchHistoryDialog();
			PopupAlert.clearSearchHistory(this);
			return true;
		} else if (itemId == R.id.building_detail) {
			bld_detailIntent = new Intent(MainActivity.this, BuildingDetail.class);
			startActivity(bld_detailIntent);
			return true;
		} else if (itemId == R.id.help) {
			//helpscreen();
			return true;
		} else if (itemId == R.id.settings) {
			//settingsIntent = new Intent(MainActivity.this, Settings.class);
			//startActivity(settingsIntent);
			return true;
		} else {
			return super.onOptionsItemSelected(item);
		}
	}
	
	private boolean checkPlayServices() {
		int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
		
		if (status != ConnectionResult.SUCCESS) {
			if (GooglePlayServicesUtil.isUserRecoverableError(status)) {
				showErrorDialog(status);
		    } else {
		    	Toast.makeText(this, "This device is not supported.", Toast.LENGTH_SHORT).show();
		    	finish();
		    }
		    return false;
		}
		
		return true;
	} 

	void showErrorDialog(int code) {
		GooglePlayServicesUtil.getErrorDialog(code, this, REQUEST_CODE_RECOVER_PLAY_SERVICES).show();
	}
}
