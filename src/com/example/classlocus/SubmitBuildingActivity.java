package com.example.classlocus;

import com.example.classlocus.data.Building;
import com.example.classlocus.data.BuildingsRepository;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class SubmitBuildingActivity extends Activity {
	static final int REQUEST_CODE_RECOVER_PLAY_SERVICES = 1001;
	Building building = new Building();
	BuildingsRepository database = new BuildingsRepository(this);
	double latitude;
	double longitude;
	Marker marker;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_submit_building);

		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setTitle("New Building");

		final Button submit = (Button) findViewById(R.id.submit_button);

		submit.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				final EditText buildingNameText = (EditText) findViewById(R.id.edit_text_building_name);
				final EditText buildingAbbvText = (EditText) findViewById(R.id.edit_text_abbreviation);
				final CheckBox checkBox = (CheckBox) findViewById(R.id.checkBox1);

				String buildingName = buildingNameText.getText().toString();
				String buildingAbbv = buildingAbbvText.getText().toString();
				building.setName(buildingName);
				building.setAbbreviation(buildingAbbv);
				building.setParentId(10);
				building.setLatLng(latitude, longitude);

				if (checkBox.isChecked()) {
					building.setAccessible(true);
				} else {
					building.setAccessible(false);
				}

				if ((!buildingNameText.getText().toString().matches(""))
						&& (!buildingAbbvText.getText().toString().matches(""))
						&& (latitude != 0) && (longitude != 0)) {
					if (database != null) {
						long id = database.saveBuilding(building);
						if (id > 0) {
							Toast.makeText(SubmitBuildingActivity.this,
									"Building Added (id: " + id + ")",
									Toast.LENGTH_SHORT).show();
						}
					}
				} else {
					Toast.makeText(
							SubmitBuildingActivity.this,
							"Failed to add building. Ensure you have building name and abbreviation fields filled, and place a marker for building location",
							Toast.LENGTH_SHORT).show();
				}
			}
		});

	}

	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.help_menu, menu);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			NavUtils.navigateUpFromSameTask(this);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	protected void onResume() {
		super.onResume();

		if (checkPlayServices()) {
			GoogleMap map = ((MapFragment) getFragmentManager()
					.findFragmentById(R.id.submitBuildingMap)).getMap();
			map.setMyLocationEnabled(true);
			map.setBuildingsEnabled(true);
			map.setIndoorEnabled(true);
			
			map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

				@Override
				public void onMapClick(LatLng point) {
					LatLng coordinates;

					GoogleMap map = ((MapFragment) getFragmentManager()
							.findFragmentById(R.id.submitBuildingMap)).getMap();

					if (marker != null) {
						marker.remove();
					}

					marker = map.addMarker(new MarkerOptions().position(point));

					coordinates = marker.getPosition();
					latitude = coordinates.latitude;
					longitude = coordinates.longitude;

				}
			});
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		GoogleMap map = ((MapFragment) getFragmentManager().findFragmentById(
				R.id.submitBuildingMap)).getMap();
		map.setIndoorEnabled(false);
	}

	private boolean checkPlayServices() {
		int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);

		if (status != ConnectionResult.SUCCESS) {
			if (GooglePlayServicesUtil.isUserRecoverableError(status)) {
				GooglePlayServicesUtil.getErrorDialog(status, this,
						REQUEST_CODE_RECOVER_PLAY_SERVICES).show();
			} else {
				Toast.makeText(this, "This device is not supported.",
						Toast.LENGTH_SHORT).show();
				finish();
			}
			return false;
		}
		return true;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case REQUEST_CODE_RECOVER_PLAY_SERVICES:
			if (resultCode == RESULT_CANCELED) {
				Toast.makeText(this, "Google Play Services must be installed.",
						Toast.LENGTH_SHORT).show();
				finish();
			}
			return;
		default:
			super.onActivityResult(requestCode, resultCode, data);
		}
	}

}
