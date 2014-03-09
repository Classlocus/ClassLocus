package com.example.classlocus;

import com.example.classlocus.data.Building;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class SubmitBuildingActivity extends Activity {
	private Building building = new Building();

	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_submit_building);
		// Show the Up button in the action bar.
		setupActionBar();

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

				if (checkBox.isChecked()) {
					building.setAccessible(true);
				} else {
					building.setAccessible(false);
				}

			}
		});

	}

	private void setupActionBar() {

		getActionBar().setDisplayHomeAsUpEnabled(true);

		// Enable Up / Back navigation
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.help_menu, menu);
		return true;
	}

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

	public void createBuilding() {

		// building.setAbbreviation(getText(R.id.edit_text_abbreviation)
		// .toString());

	}

}
