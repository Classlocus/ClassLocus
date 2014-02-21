package com.example.classlocus.data;

import com.example.classlocus.R;

import android.widget.BaseAdapter;
//import android.widget.ImageView;
import android.widget.TextView;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import java.util.List;
import java.util.Collections;

public class BuildingAdapter extends BaseAdapter {

	private List<Building> buildings = Collections.emptyList();
	private final Context context;
	
	public BuildingAdapter(Context context) {
		this.context = context;
	}
	
	public void updateBuildings(List<Building> buildings) {
		ThreadPreconditions.checkOnMainThread();
		this.buildings = buildings;
		notifyDataSetChanged();
	}
	
	@Override
	public int getCount() {
		return buildings.size();
	}
	
	// Java return type covariance allows the standard result from getItem(int) in Adapter, which is 
	// to return Object, to be overridden to return Building instead.
	@Override
	public Building getItem(int position) {
		return buildings.get(position);
	}
	
	@Override
	public long getItemId(int position) {
		return position;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.activity_building_found, parent, false);
		}
		
		//ImageView buildingImageView = (ImageView) convertView.findViewById(R.id.building);
		TextView buildingTextView = (TextView) convertView.findViewById(R.id.buildings_found);
		
		Building building = getItem(position);
		buildingTextView.setText(building.getName());
		//buildingImageView.setImageResource(building.getImage());
		
		return convertView;
	}
}
