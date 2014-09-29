package com.example.thaishare;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class HomeActivity extends Activity {
	
	private Button quickModeBtn, addItemBtn, listItemBtn, addMembersBtn;


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
        
        // set up button listener
        addMembersBtn = (Button) findViewById(R.id.addMembersBtn);
        addMembersBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	navToAddMemberItemViewMode();
            }
        });
        
        addItemBtn = (Button) findViewById(R.id.addItemBtn);
        addItemBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	navToAddItemViewMode();
            }
        });
        
        listItemBtn =(Button) findViewById(R.id.listItemBtn);
        listItemBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				navToListItemViewMode();
			}
		});
        
    }
    
    /**
     * Go to quick edit mode
     */
    public void navToQuickViewMode() {
    	Intent intent = new Intent(this, QuickViewActivity.class);
    	startActivity(intent);
    	overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    public void navToAddItemViewMode() {
    	Intent intent = new Intent(this, AddMenus.class);
    	startActivity(intent);
    	overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
    
    public void navToAddMemberItemViewMode() {
    	Intent intent = new Intent(this, AddMembers.class);
    	startActivity(intent);
    	overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
    
    
    public void navToListItemViewMode() {
    	Intent intent = new Intent(this, ListItemActivity.class);
    	startActivity(intent);
    	overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }
    
}
