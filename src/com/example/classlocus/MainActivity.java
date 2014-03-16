package com.example.classlocus;

import android.app.Activity;
import android.app.SearchableInfo;
import android.os.Bundle;
import android.content.Intent;
import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.view.*;

import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.*;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

import com.example.classlocus.data.BuildingGenerator;
import com.example.classlocus.search.*;

import android.app.SearchManager;
import android.widget.SearchView;

public class MainActivity extends Activity {
	
	static final int REQUEST_CODE_RECOVER_PLAY_SERVICES = 1001;
	private Intent aboutIntent;
	private Intent favoriteIntent;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		BuildingGenerator.initialDbState(this);
		this.setContentView(R.layout.activity_main);
		
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu from XML
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.options_menu, menu);
		
		//Get the SearchView and set the searchable configuration
		SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
		SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
		
		//Assumes current activity is the searchable activity
		SearchableInfo si = searchManager.getSearchableInfo(getComponentName());
		searchView.setSearchableInfo(si);
		searchView.setIconifiedByDefault(false); // Do not iconify the widget; expand it by default
		
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
		if (checkPlayServices()) {
			GoogleMap map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
			map.setMyLocationEnabled(true);
			map.setBuildingsEnabled(true);
			map.setIndoorEnabled(true);
			
			LatLng oregonstate = new LatLng(44.5657285, -123.2788689);
			map.addMarker(new MarkerOptions()
	     		.title("Oregon State University")
	     		.snippet("A land-, sea-, and space-grant university.")
	     		.position(oregonstate));
		}
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		GoogleMap map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
		map.setIndoorEnabled(false);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.clear_history:
			ClearSearchHistoryDialog PopupAlert = new ClearSearchHistoryDialog();
			PopupAlert.clearSearchHistory(this);
			return true;
		case R.id.about:
			aboutIntent = new Intent(MainActivity.this, AboutActivity.class);
			startActivity(aboutIntent);
			return true;
		case R.id.favorite_list:
			favoriteIntent = new Intent(MainActivity.this, FavoriteListActivity.class);
			startActivity(favoriteIntent);
			return true;
		default:
			return super.onOptionsItemSelected(item);
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
			default:
				super.onActivityResult(requestCode, resultCode, data);
		}
	}
	
	private boolean checkPlayServices() {
		int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
		
		if (status != ConnectionResult.SUCCESS) {
			if (GooglePlayServicesUtil.isUserRecoverableError(status)) {
				GooglePlayServicesUtil.getErrorDialog(status, this, REQUEST_CODE_RECOVER_PLAY_SERVICES).show();
		    } else {
		    	Toast.makeText(this, "This device is not supported.", Toast.LENGTH_SHORT).show();
		    	finish();
		    }
		    return false;
		}
		return true;
	} 
}
