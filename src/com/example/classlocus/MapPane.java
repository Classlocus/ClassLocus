package com.example.classlocus;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;

import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.*;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import android.widget.Toast;

public class MapPane extends Activity {

	static final int REQUEST_CODE_RECOVER_PLAY_SERVICES = 1001;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_activity);
        GoogleMap map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();

        // Some buildings have indoor maps. Center the camera over
        // the building, and a floor picker will automatically appear.
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(
                new LatLng(-33.86997, 151.2089), 18));
    }
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
			case REQUEST_CODE_RECOVER_PLAY_SERVICES:
				if (resultCode == RESULT_CANCELED) {
					Toast.makeText(this, "Google Play Services must be installed.", Toast.LENGTH_SHORT).show();
					finish();
				}
				return;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
		if (checkPlayServices()) {
			// Google Play Services is installed and good to go
		}
	}
	
	private boolean checkPlayServices() {
		int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
		
		if (status != ConnectionResult.SUCCESS) {
			if (GooglePlayServicesUtil.isUserRecoverableError(status)) {
				showErrorDialog(status);
		    } else {
		    	Toast.makeText(this, "This device is not supported.", Toast.LENGTH_LONG).show();
		    	finish();
		    }
		    return false;
		}
		
		return true;
	} 

	void showErrorDialog(int code) {
		GooglePlayServicesUtil.getErrorDialog(code, this, REQUEST_CODE_RECOVER_PLAY_SERVICES).show();
	}
}