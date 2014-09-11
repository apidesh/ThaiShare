package com.example.thaishare;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AddNewItemActivity extends Activity {
    Button save, back, next;
    TextView id;
    EditText name, price;
    DataHandler dataHandler;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();
		setContentView(R.layout.activity_add_new_item);
		id = (TextView) findViewById(R.id.id);
		save = (Button) findViewById(R.id.save);
		back = (Button) findViewById(R.id.back);
		next = (Button) findViewById(R.id.next);
		name = (EditText) findViewById(R.id.name);
		price = (EditText) findViewById(R.id.price);
		
		save.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String getName = name.getText().toString();
				String getPrice = price.getText().toString();
				String updateId = id.getText().toString();
				dataHandler = new DataHandler(getBaseContext());
				dataHandler.open();	
				if(!updateId.equals(""))
				{
					dataHandler.UpdateMenu(Integer.parseInt(updateId), getName, getPrice);
				}
				else
				{
				long insertedId = dataHandler.insertData(getName, getPrice);
				Toast.makeText(getBaseContext(), "Data saved", Toast.LENGTH_LONG).show();
				}
					
				dataHandler.close();
				
				// close this activity
				setResult(RESULT_OK);
				finish();
			}
		});
		
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int getId = -1;
				String getName = "";
				Double getPrice = 0.0;
				
				int menuId = Integer.parseInt(id.getText().toString());
					menuId--;
				dataHandler = new DataHandler(getBaseContext());
				dataHandler.open();	
				Cursor cursor = dataHandler.findById(menuId);
				if(cursor.moveToFirst())
				{
				
					do
					{
						
						getId = cursor.getInt(cursor.getColumnIndex("id"));
						getName = cursor.getString(cursor.getColumnIndex("name"));
						getPrice = cursor.getDouble(cursor.getColumnIndex("price"));
						
					id.setText(Integer.toString(getId));
					name.setText(getName);
					price.setText(getPrice.toString());			
						
					}while(cursor.moveToNext());
					
				}
				
				dataHandler.close();
			}
		});
		
		
        next.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int getId = -1;
				String getName = "";
				Double getPrice = 0.0;
				
				int menuId = Integer.parseInt(id.getText().toString());
					menuId++;
				dataHandler = new DataHandler(getBaseContext());
				dataHandler.open();	
				Cursor cursor = dataHandler.findById(menuId);
				if(cursor.moveToFirst())
				{
				
					do
					{
						getId = cursor.getInt(cursor.getColumnIndex("id"));
						getName = cursor.getString(cursor.getColumnIndex("name"));
						getPrice = cursor.getDouble(cursor.getColumnIndex("price"));
						
					id.setText(Integer.toString(getId));
					name.setText(getName);
					price.setText(getPrice.toString());			
						
					}while(cursor.moveToNext());
					
				}
				
				dataHandler.close();
			}
		});
		
        loadMenuData(intent);
	}

	private void loadMenuData(Intent intent)
	{

		Bundle bundle = intent.getExtras();
		
		MenuItemData menu = (MenuItemData) bundle.getSerializable(QuickViewActivity.MENU_INTENT_DATA);
		
		if(menu != null && menu.getId() > 0)
		{
		
		int getId = -1;
		String getName = "";
		Double getPrice = 0.0;

		dataHandler = new DataHandler(getBaseContext());
		dataHandler.open();	
		Cursor cursor = dataHandler.findById(menu.getId());
		if(cursor.moveToFirst())
		{
		
			do
			{
				MenuItemData item = new MenuItemData();
				getId = cursor.getInt(cursor.getColumnIndex("id"));
				getName = cursor.getString(cursor.getColumnIndex("name"));
				getPrice = cursor.getDouble(cursor.getColumnIndex("price"));
				
			id.setText(Integer.toString(getId));
			name.setText(getName);
			price.setText(getPrice.toString());			
				
			}while(cursor.moveToNext());
			
		}
		
		dataHandler.close();
		}
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
