/*
 * AboutUs
 * 
 * Version 0.1
 * 
 * January 18, 2014
 * 
 * Apache license (I think that's what we decided on)
 */

package com.example.classlocus;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.Window;

public class AboutUs extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_about_us);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.about_us, menu);
		return true;
	}

	public void goToWiki(View view){
		Intent i = new Intent(Intent.ACTION_VIEW);
		i.setData(Uri.parse(this.getResources().getString(R.string.our_website)));
		startActivity(i);
	}
}
