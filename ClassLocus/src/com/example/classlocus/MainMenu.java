/*
 * MainMenu
 * 
 * Version 0.1
 * 
 * January 18, 2014
 * 
 * Apache license (I think that's what we decided on)
 */

package com.example.classlocus;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.Window;


public class MainMenu extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main_menu);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_menu, menu);
		return true;
	}

	public void aboutUsButton(View view){
		Intent i = new Intent(this, AboutUs.class);
		startActivity(i);
	}
}
