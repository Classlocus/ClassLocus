package com.example.classlocus.search;

import android.content.SearchRecentSuggestionsProvider;

public class SearchSuggestionProvider extends SearchRecentSuggestionsProvider {
	public final static String AUTHORITY = "com.slouchstd.SearchSuggestionProvider";
    public final static int MODE = DATABASE_MODE_QUERIES;
	
	public SearchSuggestionProvider() {
		setupSuggestions(AUTHORITY, MODE);
	}

}