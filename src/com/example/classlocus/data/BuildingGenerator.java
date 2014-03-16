package com.example.classlocus.data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.R;
import android.content.Context;
import android.util.Log;

public class BuildingGenerator {

	public static void initialDbState(Context c){
		BuildingsRepository database = new BuildingsRepository(c);
		
		InputStream  in = c.getResources().openRawResource(
			c.getResources().getIdentifier("raw/gps_coords",
				"raw", c.getPackageName()));
		if (in != null){
			Log.e("classLocus", "Loaded file");
		} else{
			Log.e("classLocus", "Could not open file");
		}
		BufferedReader input = new BufferedReader(new InputStreamReader(in), 1024 * 8);
		String line;
		try{
			while ((line = input.readLine()) != null){
				Building a = new Building();
				
				String name = "";
				boolean nameDone = false;
				String abbr = "";
				boolean abbrDone = false;
				String lat = "";
				boolean latDone = false;
				String lng = "";
				boolean lngDone = false;
				String acc = "";
				for (int i = 0; i < line.length(); i++){
					if (!nameDone){
						if (line.charAt(i) == '|'){
							nameDone = true;
							continue;
						}
						name = name.concat(String.valueOf(line.charAt(i)));
					}
					else if (!abbrDone){
						if (line.charAt(i) == '|'){
							abbrDone = true;
							continue;
						}
						abbr = abbr.concat(String.valueOf(line.charAt(i)));
					}
					else if (!latDone){
						if (line.charAt(i) == '|'){
							latDone = true;
							continue;
						}
						lat = lat.concat(String.valueOf(line.charAt(i)));
					}
					else if (!lngDone){
						if (line.charAt(i) == '|'){
							lngDone = true;
							continue;
						}
						lng = lng.concat(String.valueOf(line.charAt(i)));
					}
					else{
						acc = acc.concat(String.valueOf(line.charAt(i)));
					}
				}
				
				Log.d("classLocus", "Loading...");
				Log.d("classLocus", "Name: " + name + "(" + name.length() + ")");
				Log.d("classLocus", "Abbreviation: " + abbr + "(" + abbr.length() + ")");
				Log.d("classLocus", "Lat: " + lat + "(" + lat.length() + ")");
				Log.d("classLocus", "Long: " + lng + "(" + lng.length() + ")");
				Log.d("classLocus", "Accessibility: " + acc + "(" + acc.length() + ")");
				Log.d("classLocus", "\n");
				a.setName(name);
				a.setAbbreviation(abbr);
				a.setLatLng(Double.valueOf(lat), Double.valueOf(lng));
				a.setParentId(10);
				if (acc.compareToIgnoreCase("y") == 0){
					a.setAccessible(true);
				}
				else {
					a.setAccessible(false);
				}
			
				database.saveBuilding(a);
				Log.d("classLocus", "Loaded Building!");
				
			}
		}
		catch (IOException err){
			Log.e("classLocus", "Error reading gps coords file.");
		}
	}
}
