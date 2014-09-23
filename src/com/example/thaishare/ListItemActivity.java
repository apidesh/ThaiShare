package com.example.thaishare;

//import android.support.v7.app.ActionBarActivity;
import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class ListItemActivity extends Activity {

	DataHandler dataHandler;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_item);
		
		dataHandler = new DataHandler(getBaseContext());
		dataHandler.open();		
		//Integer getId = -1;
		String getName = "", getPrice = "";
		Cursor cursor = dataHandler.returnMenusData();
		if(cursor.moveToFirst())
		{
		
			do
			{
				//getId = cursor.getInt(0);
				getName = cursor.getString(1);
				getPrice = cursor.getString(2);
				
			}while(cursor.moveToNext());
			
		}
		
		dataHandler.close();	
		Toast.makeText(getBaseContext(), "Name : "+getName+ " Price : "+getPrice, Toast.LENGTH_LONG).show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.list_item, menu);
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
