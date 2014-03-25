package com.soubo.app;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

public class SearchApp extends Activity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//ШЅБъЬт
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_search_shop);
	}
	

}
