package com.example.thaishare;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class MenuListActivity extends Activity {
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		TextView textView = new TextView(this);
		textView.setText("MenuList");
		setContentView(textView);
	}

}
