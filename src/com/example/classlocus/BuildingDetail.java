package com.example.classlocus;

import java.util.ArrayList;
import java.util.List;

import com.example.classlocus.data.*;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.*;
import com.google.maps.android.SphericalUtil;
import android.app.ActionBar;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.support.v4.app.NavUtils;

public class BuildingDetail extends Activity {
	
	LocationManager mgr;
	Building bd;
	GoogleMap map;
	BuildingsRepository db;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_building_detail);
		// Show the Up button in the action bar.
		setupActionBar();
		
		db = new BuildingsRepository(this);
		
		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.details_map)).getMap();
		
		mgr = (LocationManager)getSystemService(LOCATION_SERVICE);
		
		double latLang[]; 
		TextView tv;
		
		if (getIntent().hasExtra("buildingID"))
			bd = populate(getIntent(), db);
		
		//populating fields
		if (bd != null){
			tv = (TextView) findViewById(R.id.detail_building_value);
			tv.setText(bd.getName());
			tv = (TextView) findViewById(R.id.detail_building_accessible_value);
			if (bd.getAccessible() == true) {
				tv.setText("Yes");
			} else {
				tv.setText("No");
			}
			latLang = bd.getLatLng();
			tv = (TextView) findViewById(R.id.detail_building_distance_value);
			tv.setText("Connecting to Satellites...");
			
			//Set map
			map.addMarker(new MarkerOptions()
	 		.title(bd.getName())
	 		.position(new LatLng(bd.getLatLng()[0], bd.getLatLng()[1])));
			updateMapPosition(new LatLng(bd.getLatLng()[0], bd.getLatLng()[1]));
		}
	}
	
	public Building populate(Intent i, BuildingsRepository helper){
		List<Building> buildings;		

		buildings = helper.searchBuilding(i.getLongExtra("buildingID", 0));
		return buildings.get(0);
	}

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {

		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		// Enable Up / Back navigation
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
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

	public double buildingDistance(LatLng coord1, LatLng coord2){
		double distance = SphericalUtil.computeDistanceBetween(coord1, coord2);
		return distance;
	}
	
	@Override
	public void onResume(){
		super.onResume();
		mgr.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, onLocationChange);
	}
	
	public void onPause(){
		super.onPause();
		mgr.removeUpdates(onLocationChange);
	}
	
	public void updateMapPosition(LatLng position) {
		
		map.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 15));
		map.animateCamera(CameraUpdateFactory.zoomIn());
		map.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);
	}
	
	public int calculateTime(double distance) {
		
		double walkingspeed = 1.78816;
		int result = (int) (distance / walkingspeed);
		return result;
	}
	
	public String formatDistance(double distance, int time){
		String distanceStr = String.valueOf(distance);
		distanceStr = distanceStr.substring(0, distanceStr.indexOf('.'));
		String result = distanceStr + "m (" + String.valueOf(time / 60) + "min, " + String.valueOf(time % 60) + "sec)";
		return result;
	}
	
	LocationListener onLocationChange = new LocationListener(){

		@Override
		public void onLocationChanged(Location location) {
			Log.i("classLocus", "Updating location");
			TextView tv = (TextView) findViewById(R.id.detail_building_distance_value);
			double distance = buildingDistance(new LatLng(bd.getLatLng()[0], bd.getLatLng()[1]), new LatLng(location.getLatitude(),location.getLongitude()));
			tv.setText(formatDistance(distance, calculateTime(distance)));
		}

		@Override
		public void onProviderDisabled(String provider) {
			//Move along!
		}

		@Override
		public void onProviderEnabled(String provider) {
			//Nobody here but us trees!
		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			// Ignore!
		}
		
	};
}
