package com.example.classlocus;

import com.example.classlocus.data.Building;
import com.example.classlocus.data.BuildingsRepository;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class SubmitBuildingActivity extends Activity {
	
	BuildingsRepository database;
	Button button;
	Marker marker;
	LatLng coordinates;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_submit_building);
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setTitle("New Building");
		
		database = new BuildingsRepository(this);
		coordinates = new LatLng(44.56718, -123.27852);
		
		button = (Button) findViewById(R.id.submit_button);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
		GoogleMap map = ((MapFragment) getFragmentManager().findFragmentById(R.id.submit_map)).getMap();
		map.setMyLocationEnabled(true);
		map.setBuildingsEnabled(true);
		map.setIndoorEnabled(true);
		
		map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
			@Override
			public void onMapClick(LatLng point) {
				GoogleMap map = ((MapFragment) getFragmentManager().findFragmentById(R.id.submit_map)).getMap();
				
				if (marker != null) {
					marker.remove();
				}
				
				marker = map.addMarker(new MarkerOptions().position(point));
				coordinates = marker.getPosition();
			}
		});
		
		button.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				final EditText buildingNameText = (EditText) findViewById(R.id.edit_text_building_name);
				final EditText buildingAbbvText = (EditText) findViewById(R.id.edit_text_abbreviation);
				final CheckBox checkBox = (CheckBox) findViewById(R.id.checkBox1);
				
				Building building = new Building();
				building.setName(buildingNameText.getText().toString());
				building.setAbbreviation(buildingAbbvText.getText().toString());
				building.setParentId(10);
				building.setLatLng(coordinates.latitude, coordinates.longitude);
				
				if (checkBox.isChecked()) {
					building.setAccessible(true);
				} else {
					building.setAccessible(false);
				}
				
				if ( (building.getName().length() != 0) && (building.getAbbreviation().length() != 0) && (building.getLatLng().length != 0) ) {
					long id = database.saveBuilding(building);
					
					if (id > 0) {
						Toast.makeText(SubmitBuildingActivity.this, "Added buiding " + building.getName(), Toast.LENGTH_SHORT).show();
					} else {
						Toast.makeText(SubmitBuildingActivity.this, "Failed to add building. Ensure you have building name and abbreviation fields filled, and place a marker for building location", Toast.LENGTH_SHORT).show();
					}
				}
			}
		});
		
		map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
			@Override
			public void onMapClick(LatLng point) {
				GoogleMap map = ((MapFragment) getFragmentManager().findFragmentById(R.id.submit_map)).getMap();
				
				if (marker != null) {
					marker.remove();
				}

				marker = map.addMarker(new MarkerOptions().position(point));
				coordinates = marker.getPosition();
			}
		});
	}

	@Override
	protected void onPause() {
		super.onPause();
		GoogleMap map = ((MapFragment) getFragmentManager().findFragmentById(R.id.submit_map)).getMap();
		map.setIndoorEnabled(false);
	}
}
