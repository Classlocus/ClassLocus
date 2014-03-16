package com.example.classlocus;

import com.example.classlocus.data.Building;
import com.example.classlocus.data.BuildingsRepository;

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
	Building building = new Building();
	BuildingsRepository database = new BuildingsRepository(this);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_submit_building);
		
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
				building.setLatLng(44.56718, -123.27852);

				if (checkBox.isChecked()) {
					building.setAccessible(true);
				} else {
					building.setAccessible(false);
				}

				if (database != null) {
					TextView textView2 = (TextView) findViewById(R.id.textView2);
					long id = database.saveBuilding(building);
					if (id > 0) {
						textView2.setText("Building Added (id: " + id + ")");
					}
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

}
