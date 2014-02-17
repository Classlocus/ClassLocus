package com.example.classlocus;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.maps.model.*;
import com.google.maps.android.SphericalUtil;

import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v4.app.NavUtils;

public class BuildingDetail extends Activity implements 
GooglePlayServicesClient.ConnectionCallbacks, 
GooglePlayServicesClient.OnConnectionFailedListener{
	
	LocationClient mLocationClient;
	boolean isConnected = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_building_detail);
		// Show the Up button in the action bar.
		setupActionBar();
		mLocationClient = new LocationClient(this, this, this);
		
	}

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {

		getActionBar().setDisplayHomeAsUpEnabled(true);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.building_detail, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public String buildingDistance(LatLng coord1, LatLng coord2){
		double distance = SphericalUtil.computeDistanceBetween(coord1, coord2);
		String dString = String.valueOf(distance);
		dString = dString + "m";
		return dString;
	}
	
	protected void onStart(){
		super.onStart();
		mLocationClient.connect();
		while(!isConnected);
		LatLng test = getCurrentLocation();
		//TextView tv = (TextView) findViewById(R.id.detail_building_distance);
		//tv.setText(buildingDistance(getCurrentLocation(), new LatLng(2d, 2d)));
	}
	
	protected void onStop(){
		super.onStop();
		mLocationClient.disconnect();
	}
	
	public LatLng getCurrentLocation(){
		int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
		switch (status){
		case ConnectionResult.SUCCESS:
			Location l = mLocationClient.getLastLocation();
			return new LatLng(l.getLatitude(), l.getLongitude());
		case ConnectionResult.SERVICE_MISSING:
			Toast.makeText(this, "This device is not supported.", Toast.LENGTH_SHORT).show();
	    	finish();
			break;
		case ConnectionResult.SERVICE_VERSION_UPDATE_REQUIRED:
			Toast.makeText(this, "This device is not supported.", Toast.LENGTH_SHORT).show();
	    	finish();
			break;
		case ConnectionResult.SERVICE_DISABLED:
			Toast.makeText(this, "This device is not supported.", Toast.LENGTH_SHORT).show();
	    	finish();
			break;
		case ConnectionResult.SERVICE_INVALID:
			Toast.makeText(this, "This device is not supported.", Toast.LENGTH_SHORT).show();
	    	finish();
			break;
		case ConnectionResult.DATE_INVALID:
			Toast.makeText(this, "This device is not supported.", Toast.LENGTH_SHORT).show();
	    	finish();
			break;
		}
		return null;
	}

	@Override
	public void onConnectionFailed(ConnectionResult arg0) {
		Toast.makeText(this, "Connection Failed", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onConnected(Bundle arg0) {
		isConnected = true;
	}

	@Override
	public void onDisconnected() {
		isConnected = false;
	}
}
