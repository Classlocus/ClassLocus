package com.example.classlocus;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;

public class BuildingFound extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_building_found);
		
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
	}

}
