package com.example.thaishare;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class AddNewMenuTabActivity extends Activity {
	    Button save;
	    TextView id;
	    EditText name, price;
	    DataHandler dataHandler;
	    Activity parent;
	    
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);	
		Intent intent = getIntent();
		setContentView(R.layout.addnewmenuitems);
		 id = (TextView) findViewById(R.id.id);
		 save = (Button) findViewById(R.id.save);
		 name = (EditText) findViewById(R.id.name);
		 price = (EditText) findViewById(R.id.price);
		 
		 parent = this.getParent();
		 
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
						dataHandler.UpdateMenusData(Integer.parseInt(updateId), getName, getPrice);
					}
					else
					{
					dataHandler.insertMenusData(getName, getPrice);
					//Toast.makeText(getBaseContext(), "Data saved", Toast.LENGTH_LONG).show();
					}
						
					dataHandler.close();
					parent.setResult(RESULT_OK);
					// close this activity
					setResult(RESULT_OK);
					
					finish();
				}
			});
		 
		 
	}

}
