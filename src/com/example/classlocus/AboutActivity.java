package com.example.classlocus;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import com.google.android.gms.common.GooglePlayServicesUtil;

public class AboutActivity extends Activity {

	static String googleDisclaimerDialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_help);

		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setTitle("About");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.help_menu, menu);
		return true;
	}

	public void goToWiki(View view) {
		Intent webIntent = new Intent(Intent.ACTION_VIEW);
		webIntent.setData(Uri.parse(this.getResources().getString(R.string.our_website)));
		startActivity(webIntent);
	}
	
	public void displayDisclaimer(View view) {
		AlertDialog.Builder LicenseDialog = new AlertDialog.Builder(AboutActivity.this);
		LicenseDialog.setTitle("Legal Notices");
		LicenseDialog.setMessage(GooglePlayServicesUtil.getOpenSourceSoftwareLicenseInfo(getApplicationContext()));
		LicenseDialog.show();
	}

}