package com.example.classlocus;

import com.example.classlocus.data.*;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.*;
import com.google.maps.android.SphericalUtil;
import android.app.ActionBar;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.support.v4.app.NavUtils;

public class BuildingDetail extends Activity {

	private static final boolean True = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_building_detail);
		// Show the Up button in the action bar.
		setupActionBar();
		
		GoogleMap map = ((MapFragment) getFragmentManager().findFragmentById(R.id.details_map)).getMap();
		
		BuildingsDataSource db = new BuildingsDataSource(this); 
		
		Intent passedIn = getIntent();
		//if (passedIn.hasExtra("buildingID")){
			double latLang[]; 
			Building bd; 
			TextView tv;
			bd = new Building();
			bd.setName("william");
			bd.setAccessible(True);
			bd.setLatLng(23.45, 23.23);
			//bd = populate(getIntent(), db);
			tv = (TextView) findViewById(R.id.detail_building_value);
			tv.setText(bd.getName());
			tv = (TextView) findViewById(R.id.detail_building_accessible_value);
			if (bd.getAccessible() == true) {
				tv.setText("Yes");
			} else {
				tv.setText("No");
			}
			latLang = bd.getLatLng();
			tv = (TextView) findViewById(R.id.detail_building_long_value);
			tv.setText(String.valueOf(latLang[0]));
			tv = (TextView) findViewById(R.id.detail_building_lat_value);
			tv.setText(String.valueOf(latLang[1]));
			//tv.findViewById(R.id.detail_building_distance_value);
			//tv.setText(buildingDistance(bd.getLatLng(), );
			map.addMarker(new MarkerOptions()
	 		.title(bd.getName())
	 		.position(new LatLng(bd.getLatLng()[0], bd.getLatLng()[1])));
			
		//}
		//TextView tv = (TextView) findViewById(R.id.detail_building_value);
		//tv.setText(text)
		//tv.setText(buildingDistance(new LatLng(2d, 2d), new LatLng(2d, 2d)));
		
	}
	
	public Building populate(Intent i, BuildingsDataSource helper){
		
		Building tmp = helper.getBuilding(i.getLongExtra("buildingID", 0));
		
		if (tmp == null) {
			this.finish();
		}
			
		return tmp;
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

	public String buildingDistance(LatLng coord1, LatLng coord2){
		double distance = SphericalUtil.computeDistanceBetween(coord1, coord2);
		String dString = String.valueOf(distance);
		dString = dString + "m";
		return dString;
	}
}
