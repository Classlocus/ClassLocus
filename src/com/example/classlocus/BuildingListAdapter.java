package com.example.classlocus;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class BuildingListAdapter extends BaseAdapter {

	private Context mContext;
	private ArrayList<Building> mBuildings;
	private LayoutInflater inflater;
	
	public BuildingListAdapter(Context c, ArrayList<Building> buildings) {
		inflater = LayoutInflater.from(c);
		mContext = c;
		mBuildings = buildings;
	}
	
	@Override
	public int getCount() {
		return mBuildings.size();
	}
	
	@Override
	public Object getItem(int position) {
		return mBuildings.get(position);
	}
	
	@Override
	public long getItemId(int position) {
		return 0;
	}
	
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
	/*
		
		View v = convertView;
		TextView text;
		
		if (v == null) {
			v = inflater.inflate(R.layout.building_list_item, parent, false);
			v.setTag(R.id.building_list_item_textView, v.findViewById(R.id.building_list_item_textView));
		}
		
		text = (TextView)v.getTag(R.id.building_list_item_textView);
		// get the current item and set the text
		Building item = (Building)getBuilding(position);
		text.setText(item.getText());
	*/
		return convertView;
	}

}
