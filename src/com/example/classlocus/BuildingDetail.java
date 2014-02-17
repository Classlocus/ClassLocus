package com.example.classlocus;

import com.google.android.gms.maps.model.*;
import com.google.maps.android.SphericalUtil;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.support.v4.app.NavUtils;

public class BuildingDetail extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_building_detail);
		// Show the Up button in the action bar.
		setupActionBar();
		
		TextView tv = (TextView) findViewById(R.id.detail_building_distance);
		tv.setText(buildingDistance(new LatLng(2d, 2d), new LatLng(2d, 2d)));
		
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
}
