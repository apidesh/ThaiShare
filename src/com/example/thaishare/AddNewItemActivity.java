package com.example.thaishare;

import android.support.v7.app.ActionBarActivity;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddNewItemActivity extends Activity {
    Button save;
    EditText name, price;
    DataHandler dataHandler;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_new_item);
		
		save = (Button) findViewById(R.id.save);
		name = (EditText) findViewById(R.id.name);
		price = (EditText) findViewById(R.id.price);
		
		save.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String getName = name.getText().toString();
				String getPrice = price.getText().toString();
				dataHandler = new DataHandler(getBaseContext());
				dataHandler.open();		
				long id = dataHandler.insertData(getName, getPrice);
				Toast.makeText(getBaseContext(), "Data saved", Toast.LENGTH_LONG).show();
				dataHandler.close();
				
				// close this activity
				setResult(RESULT_OK);
				finish();
			}
		});
		
		
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_new_item, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
