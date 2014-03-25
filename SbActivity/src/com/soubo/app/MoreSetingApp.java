package com.soubo.app;

import android.app.Activity;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.Window;

public class MoreSetingApp extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//ШЅБъЬт
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_more_setting);
	}
}
