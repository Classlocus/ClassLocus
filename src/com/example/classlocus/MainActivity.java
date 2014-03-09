package com.example.classlocus;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.SearchableInfo;
import android.os.Bundle;
import android.provider.SearchRecentSuggestions;
import android.content.Intent;
import android.content.Context;
import android.widget.Toast;
import android.view.*;

import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.*;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

import com.example.classlocus.search.*;

import android.app.SearchManager;
import android.widget.SearchView;

public class MainActivity extends Activity {
	
	static final int REQUEST_CODE_RECOVER_PLAY_SERVICES = 1001;
	private Intent buildingIntent;
	private Intent helpIntent;
	
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
			buildingIntent = new Intent(MainActivity.this, BuildingDetail.class);
			startActivity(buildingIntent);
			return true;
		} else if (itemId == R.id.help) {
			helpIntent = new Intent(MainActivity.this, Help.class);
			startActivity(helpIntent);
			return true;
		} else if (itemId == R.id.legalnotices) {
			String LicenseInfo = GooglePlayServicesUtil.getOpenSourceSoftwareLicenseInfo(
			        getApplicationContext());
			AlertDialog.Builder LicenseDialog = new AlertDialog.Builder(MainActivity.this);
			LicenseDialog.setTitle("Legal Notices");
			LicenseDialog.setMessage(LicenseInfo);
			LicenseDialog.show();
		}
		else {
			return super.onOptionsItemSelected(item);
		}
		return false;
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
