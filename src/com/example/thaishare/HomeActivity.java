package com.example.thaishare;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class HomeActivity extends Activity {
	
	private Button quickModeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        
        Toast.makeText(HomeActivity.this,"Welcome to ThaiShare!", Toast.LENGTH_SHORT).show();
        
        
        // set up button listener
        quickModeBtn = (Button) findViewById(R.id.quickModeBtn);
        quickModeBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	navToQuickViewMode();
            }
        });
    }
    
    public void navToQuickViewMode() {
    	Intent intent = new Intent(this, QuickViewActivity.class);
    	startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }
    
}
