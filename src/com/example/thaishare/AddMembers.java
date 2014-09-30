package com.example.thaishare;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AddMembers extends Activity {

    Button save, back, next;
    TextView id;
    EditText firstName, lastName, phoneNumber;
    DataHandler dataHandler;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();
		setContentView(R.layout.activity_add_members);
		id = (TextView) findViewById(R.id.id);
		save = (Button) findViewById(R.id.save);
		back = (Button) findViewById(R.id.back);
		next = (Button) findViewById(R.id.next);
		firstName = (EditText) findViewById(R.id.firstName);
		lastName = (EditText) findViewById(R.id.lastName);
		phoneNumber = (EditText) findViewById(R.id.phoneNumber);
		save.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String getFirstName = firstName.getText().toString();
				String getLastName = lastName.getText().toString();
				String getPhoneNumber = phoneNumber.getText().toString();
				String updateId = id.getText().toString();
				dataHandler = new DataHandler(getBaseContext());
				dataHandler.open();	
				if(!updateId.equals(""))
				{
				//	dataHandler.UpdateMenusData(Integer.parseInt(updateId), getName, getPrice);
				}
				else
				{
			    dataHandler.insertMemberssData(getFirstName, getLastName, getPhoneNumber);
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
				String getFirstName = "";
				String getLastName = "";
				String getPhoneNumber = "";
				
				String updateId = id.getText().toString();
				if(!updateId.equals(""))
				{
					int menuId = Integer.parseInt(updateId);
						menuId--;
					dataHandler = new DataHandler(getBaseContext());
					dataHandler.open();	
					Cursor cursor = dataHandler.findMenusById(menuId);
					if(cursor.moveToFirst())
					{
					
						do
						{
							
							getId = cursor.getInt(cursor.getColumnIndex(TableMembers.Field_Id));
							getFirstName = cursor.getString(cursor.getColumnIndex(TableMembers.Field_FirstName));
							getLastName = cursor.getString(cursor.getColumnIndex(TableMembers.Field_LastName));
							getPhoneNumber = cursor.getString(cursor.getColumnIndex(TableMembers.Field_PhoneNumber));
						id.setText(Integer.toString(getId));
						firstName.setText(getFirstName);
						lastName.setText(getLastName);			
						phoneNumber.setText(getPhoneNumber);	
						}while(cursor.moveToNext());
						
						back.setEnabled(true);
						next.setEnabled(true);
					}
					else
					{
						back.setEnabled(false);
					}
						
					
					dataHandler.close();
				}
				else
				{
					back.setEnabled(false);
				}
					
			}
		});
		
	 next.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int getId = -1;
				String getFirstName = "";
				String getLastName = "";
				String getPhoneNumber = "";
				String updateId = id.getText().toString();
				if(!updateId.equals(""))
				{
						int menuId = Integer.parseInt(updateId);
							menuId++;
						dataHandler = new DataHandler(getBaseContext());
						dataHandler.open();	
						Cursor cursor = dataHandler.findMenusById(menuId);
							if(cursor.moveToFirst())
							{
								do
								{
									getId = cursor.getInt(cursor.getColumnIndex(TableMembers.Field_Id));
									getFirstName = cursor.getString(cursor.getColumnIndex(TableMembers.Field_FirstName));
									getLastName = cursor.getString(cursor.getColumnIndex(TableMembers.Field_LastName));
									getPhoneNumber = cursor.getString(cursor.getColumnIndex(TableMembers.Field_PhoneNumber));
									
								id.setText(Integer.toString(getId));
								firstName.setText(getFirstName);
								lastName.setText(getLastName);			
								phoneNumber.setText(getPhoneNumber);			
									
								}while(cursor.moveToNext());
								
								back.setEnabled(true);
								next.setEnabled(true);
							}
							else
							{
								id.setText("");
								firstName.setText("");
								lastName.setText("");			
								phoneNumber.setText("");	
								next.setEnabled(false);
							}
						
						
						dataHandler.close();
				}
				else
				{
					id.setText("");
					firstName.setText("");
					lastName.setText("");			
					phoneNumber.setText("");	
					next.setEnabled(false);
				}
			}
		});
		
     loadMembersData(intent);
		
	}
	private void loadMembersData(Intent intent)
	{

		Bundle bundle = intent.getExtras();
		
		MemberItemData member = (MemberItemData) bundle.getSerializable(QuickViewActivity.MEMBER_INTENT_DATA);
		
		if(member != null && member.getId() > 0)
		{
		
		int getId = -1;
		String getFirstName = "";
		String getLastName = "";
		String getPhoneNumber = "";
		dataHandler = new DataHandler(getBaseContext());
		dataHandler.open();	
		Cursor cursor = dataHandler.findMembersById(member.getId());
		if(cursor.moveToFirst())
		{
		
			do
			{
				getId = cursor.getInt(cursor.getColumnIndex(TableMembers.Field_Id));
				getFirstName = cursor.getString(cursor.getColumnIndex(TableMembers.Field_FirstName));
				getLastName = cursor.getString(cursor.getColumnIndex(TableMembers.Field_LastName));
				getPhoneNumber = cursor.getString(cursor.getColumnIndex(TableMembers.Field_PhoneNumber));
				
				
			id.setText(Integer.toString(getId));
			firstName.setText(getFirstName);
			lastName.setText(getLastName);			
			phoneNumber.setText(getPhoneNumber);			
						
				
			}while(cursor.moveToNext());
			
		}
		
		dataHandler.close();
		}
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_members, menu);
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
	
	
	private void getContacts()
	{
		/*
		 String phoneNumber = null;
		 String firstName = null;
		 
		 Uri CONTENT_URI = ContactsContract.Contacts.CONTENT_URI;
		 String _ID = ContactsContract.Contacts._ID;
		 String DISPLAY_NAME = ContactsContract.Contacts.DISPLAY_NAME;
		 String HAS_PHONE_NUMBER = ContactsContract.Contacts.HAS_PHONE_NUMBER;
		 Uri PhoneCONTENT_URI = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
		 String Phone_CONTACT_ID = ContactsContract.CommonDataKinds.Phone.CONTACT_ID;
		 String NUMBER = ContactsContract.CommonDataKinds.Phone.NUMBER;
		 Uri EmailCONTENT_URI =  ContactsContract.CommonDataKinds.Email.CONTENT_URI;
		 String EmailCONTACT_ID = ContactsContract.CommonDataKinds.Email.CONTACT_ID;
		 String DATA = ContactsContract.CommonDataKinds.Email.DATA;
		 StringBuffer output = new StringBuffer();
		 ContentResolver contentResolver = getContentResolver();

		 Cursor cursor = contentResolver.query(CONTENT_URI, null,null, null, null); 
		 if (cursor.getCount() > 0) {

			 

	            while (cursor.moveToNext()) {

	 

	                String contact_id = cursor.getString(cursor.getColumnIndex( _ID ));

	                String name = cursor.getString(cursor.getColumnIndex( DISPLAY_NAME ));

	 

	                int hasPhoneNumber = Integer.parseInt(cursor.getString(cursor.getColumnIndex( HAS_PHONE_NUMBER )));

	 

	                if (hasPhoneNumber > 0) {

	 

	                    output.append("\n First Name:" + name);

	 

	                    // Query and loop for every phone number of the contact

	                    Cursor phoneCursor = contentResolver.query(PhoneCONTENT_URI, null, Phone_CONTACT_ID + " = ?", new String[] { contact_id }, null);

	 

	                    while (phoneCursor.moveToNext()) {

	                        phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(NUMBER));

	                        output.append("\n Phone number:" + phoneNumber);

	 

	                    }

	 

	                    phoneCursor.close();

	 

	                    // Query and loop for every email of the contact

	                    Cursor emailCursor = contentResolver.query(EmailCONTENT_URI,    null, EmailCONTACT_ID+ " = ?", new String[] { contact_id }, null);

	 

	                    while (emailCursor.moveToNext()) {

	 

	                        String email = emailCursor.getString(emailCursor.getColumnIndex(DATA));

	 

	                        output.append("\nEmail:" + email);

	 

	                    }

	 

	                    emailCursor.close();

	                }

	 

	                output.append("\n");

	            }

	 

	            outputText.setText(output);

	        }
*/

	}
}
