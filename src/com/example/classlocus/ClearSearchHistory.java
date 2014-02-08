package com.example.classlocus;

import android.content.Context;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.provider.SearchRecentSuggestions;
import android.app.DialogFragment;

public class ClearSearchHistory extends DialogFragment implements DialogInterface.OnClickListener {
	
	SearchRecentSuggestions suggestions;
	
	public ClearSearchHistory() {
		// default constructor
	}
	
	public boolean clearSearchHistory(Context context) {
		
		suggestions = new SearchRecentSuggestions(context, SearchSuggestionProvider.AUTHORITY, SearchSuggestionProvider.MODE);
		
		AlertDialog.Builder alert = new AlertDialog.Builder(context);
		alert.setTitle("Clear Search History");
		alert.setMessage("Clear search history?");
		alert.setCancelable(false);
		alert.setPositiveButton("Ok", this);
		alert.setPositiveButton("Confirm", this);
		alert.setNeutralButton("Cancel", this);
		
		// create and display alert dialog
		AlertDialog alertDialog = alert.create();
		alertDialog.show();
	
		return true;
	}
	
	public void onClick(DialogInterface dialog, int which) {
		
		switch (which)
		{
			case DialogInterface.BUTTON_POSITIVE:
				suggestions.clearHistory();
				dialog.cancel();
				return;
			case DialogInterface.BUTTON_NEGATIVE:
				dialog.cancel();
				return;
		}
	}

}
