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
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
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
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setTitle("Building Detail");
		
		db = new BuildingsRepository(this);
		
		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.details_map)).getMap();
		map.setMyLocationEnabled(true);
		map.setBuildingsEnabled(true);
		
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
	
	@Override
	public void onResume(){
		super.onResume();
		mgr.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, onLocationChange);
		map.setIndoorEnabled(true);
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		mgr.removeUpdates(onLocationChange);
		map.setIndoorEnabled(false);
	}
	
	public Building populate(Intent i, BuildingsRepository helper){
		List<Building> buildings;		

		buildings = helper.searchBuilding(i.getLongExtra("buildingID", 0));
		return buildings.get(0);
	}

	@Override
    public boolean onPrepareOptionsMenu (Menu menu){
		MenuItem fav;
		getMenuInflater().inflate(R.menu.building_detail, menu);
	    fav = menu.findItem(R.id.action_favorites);
	    if(db.isFavorite(bd)){
	    	fav.setIcon(getResources().getDrawable(R.drawable.ic_action_important));
	    }
	    return true;
    }

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			NavUtils.navigateUpFromSameTask(this);
			return true;
		case R.id.action_favorites:
			item.setIcon(getResources().getDrawable(R.drawable.ic_action_important));
			addToFavorites();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public double buildingDistance(LatLng coord1, LatLng coord2){
		double distance = SphericalUtil.computeDistanceBetween(coord1, coord2);
		return distance;
	}
	
	public void addToFavorites() {	
		db.saveFavorite(bd);
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
