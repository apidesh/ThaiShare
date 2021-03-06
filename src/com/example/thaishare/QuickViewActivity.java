package com.example.thaishare;


import java.util.ArrayList;

import android.annotation.TargetApi;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.LayoutParams;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TabHost;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class QuickViewActivity extends ActionBarActivity {

	protected SyncHorizontalScrollView headerScrollView;
	protected SyncHorizontalScrollView contentScrollView;
	protected SyncHorizontalScrollView editHeaderScrollView;

	protected static final int STARTING_NUM_ROWS = 1;						// default number of rows
	protected static final int STARTING_NUM_COLUMNS = 1;					// default number of activities

	protected int fixedColumnWidth = 20;										// percentage of row header width with respect to the screen width
	protected int scrollableColumnWidth = 20;									// percentage of table row width with respect to the screen width
	protected int fixedRowHeight = 150;											// height in pixels of table row
	protected int fixedHeaderHeight = 100;										// height in pixels of header row
	protected TableRow editRow;
	protected TableRow headerRow;
	protected TableLayout fixedColumn;
	protected TableLayout scrollablePart;
    protected ArrayList<MenuItemData> menuArrayList = null;
    protected ArrayList<MemberItemData> memberArrayList = null;
	protected static final int ADD_COLUMN_BTN_TAG = 5000;
	
	protected final static String MENU_INTENT_DATA = "MENU_ID";
	protected final static String MEMBER_INTENT_DATA = "MEMBER_ID";
	
	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_quick_view);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
			.add(R.id.container, new PlaceholderFragment()).commit();
		}

		ActionBar bar = getSupportActionBar();
		bar.setDisplayHomeAsUpEnabled(false);								// hide back button
		//		bar.setDisplayShowHomeEnabled(false);		

		TableRow.LayoutParams wrapWrapTableRowParams = new TableRow.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		TableRow row = new TableRow(this);
		TableRow editDummyRow = new TableRow(this);

		// Dummy table top corner
		TableLayout dummy = (TableLayout) findViewById(R.id.dummy_table);
		TableLayout editDummy = (TableLayout) findViewById(R.id.edit_dummy_table);

		Button removeBtn = createRemoveButton(true, -1);
		row.addView(makeTableRowWithText(getResources().getString( R.string.dummy_header_name ), fixedColumnWidth, fixedHeaderHeight));
		row.setBackgroundColor(getResources().getColor( R.color.table_item_header ));
		editDummyRow.addView(removeBtn);
		editDummy.addView(editDummyRow);
		dummy.addView(row);


		// Header (fixed vertically but scrollable horizontally)
		// Also add hidden overlay edit buttons
		TableLayout header = (TableLayout) findViewById(R.id.table_header);
		TableLayout edit_header = (TableLayout) findViewById(R.id.edit_table_header);
		headerRow = new TableRow(this);
		editRow = new TableRow(this);
		headerRow.setLayoutParams(wrapWrapTableRowParams);
		headerRow.setGravity(Gravity.CENTER);
		headerRow.setBackgroundColor(getResources().getColor( R.color.table_item_header ));
		for (int i = 0; i < STARTING_NUM_COLUMNS; i++) {
			removeBtn = createRemoveButton(false, i);
			editRow.addView(removeBtn);
			headerRow.addView(makeTableRowHeaderWithText(getResources().getString( R.string.default_item_placeholder ) + " " + (i+1), 
					fixedColumnWidth, fixedHeaderHeight));
		}
		edit_header.addView(editRow);
		header.addView(headerRow);

		// synchronize scrollviews
		headerScrollView = (SyncHorizontalScrollView) findViewById(R.id.header_scrollview);
		contentScrollView = (SyncHorizontalScrollView) findViewById(R.id.content_scrollview);
		editHeaderScrollView = (SyncHorizontalScrollView) findViewById(R.id.edit_header_scrollview);
		headerScrollView.setSyncView(contentScrollView, editHeaderScrollView);
		contentScrollView.setSyncView(headerScrollView, editHeaderScrollView);
		editHeaderScrollView.setSyncView(headerScrollView, contentScrollView);

		// Column (fixed when scrolled horizontally)
		fixedColumn = (TableLayout) findViewById(R.id.fixed_column);

		// Rest of the table (within a scroll view)
		scrollablePart = (TableLayout) findViewById(R.id.scrollable_part);
		for(int i = 0; i < STARTING_NUM_ROWS; i++) {
			TextView fixedView = makeTableRowWithText(getResources().getString( R.string.default_group_placeholder ) + " " + (i+1), 
					scrollableColumnWidth, fixedRowHeight);
			fixedView.setBackgroundColor(getResources().getColor( R.color.table_group_column ));
			fixedColumn.addView(fixedView);
			row = new TableRow(this);
			row.setLayoutParams(wrapWrapTableRowParams);
			row.setGravity(Gravity.CENTER);
			//			row.setBackgroundColor(Color.WHITE);
			for (int j = 0; j < STARTING_NUM_COLUMNS; j++) {
				row.addView(makeTableRowCheckBox(scrollableColumnWidth, fixedRowHeight));
			}
			scrollablePart.addView(row);
		}

		editRow.addView(createAddButton());

		// hide all edit buttons
		editHeaderScrollView.setVisibility(View.INVISIBLE);


	}

	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	private Button createAddButton() {
		Button addBtn = new Button(this);
		addBtn.setTag(ADD_COLUMN_BTN_TAG);
		if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN) {
			addBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.add_btn));
		}
		else {
			addBtn.setBackground(getResources().getDrawable(R.drawable.add_btn));
		}

		addBtn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Toast.makeText(QuickViewActivity.this, "Added 1 column", Toast.LENGTH_SHORT).show();
				addColumn();
			}
		});
		return addBtn;
	}

	public void deleteColumn(View v) {
		int column = (Integer) v.getTag();

		// remove current header
		headerRow.removeViewAt(column);

		// subtract all the tag to the right of this column by 1
		for (int i = column+1, numColumns = editRow.getChildCount(); i < numColumns; i++) {
			Button button = (Button) editRow.getChildAt(i);
			int tag = (Integer) button.getTag();
			if (tag != ADD_COLUMN_BTN_TAG) {
				button.setTag(tag - 1);;
			}
		}

		// remove current remove button
		editRow.removeViewAt(column);

		// remove all table cell in this column
		for (int i = 0, numRows = scrollablePart.getChildCount(); i < numRows; i++) {
			TableRow row = (TableRow) scrollablePart.getChildAt(i);
			row.removeViewAt(column);
		}

	}

	/**
	 * Method event called when user tabs the add column button
	 */
	public void addColumn() {
		/* add item header row */
		headerRow.addView(makeTableRowHeaderWithText(getResources().getString( R.string.default_item_placeholder ) 
				, fixedColumnWidth, fixedHeaderHeight)); 

		/* add remove button for row */
		int numColumn = headerRow.getChildCount();
		Button removeBtn = createRemoveButton(false, numColumn-1);
		editRow.addView(removeBtn, editRow.getChildCount() - 1);

		/* add table content row */
		for(int i = 0, numRows = scrollablePart.getChildCount(); i < numRows; i++) {
			TableRow row = (TableRow) scrollablePart.getChildAt(i);
			row.addView(makeTableRowCheckBox(scrollableColumnWidth, fixedRowHeight));
		}
	}
	
	/**
	 * Method event called when user tabs the add row button
	 */
	public void addRow() {
		/* add item header row */
		TextView fixedView = makeTableRowWithText(getResources().getString( R.string.default_group_placeholder ) 
				, scrollableColumnWidth, fixedRowHeight);
		fixedView.setBackgroundColor(getResources().getColor( R.color.table_group_column ));
		fixedColumn.addView(fixedView); 
		

//		/* add remove button for row */
//		int numRow = fixedColumn.getChildCount();
//		Button removeBtn = createRemoveButton(false, numRow-1);
//		editColumn.addView(removeBtn, editColumn.getChildCount() - 1);

		/* add table content row */
		for(int i = 0, numCol = headerRow.getChildCount(); i < numCol; i++) {
			TableRow row = new TableRow(this);
			TableRow.LayoutParams wrapWrapTableRowParams = 
					new TableRow.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			row.setLayoutParams(wrapWrapTableRowParams);
			row.setGravity(Gravity.CENTER);
			//			row.setBackgroundColor(Color.WHITE);
			
			row.addView(makeTableRowCheckBox(scrollableColumnWidth, fixedRowHeight));
			scrollablePart.addView(row);
		}
	}

	@Override 
	protected void onActivityResult(int requestCode, int resultCode, 
			Intent data) { 
		// TODO Auto-generated method stub 
		//Toast.makeText(QuickViewActivity.this, "SAVED FROM ADD_ITEM REQUEst code " + requestCode, Toast.LENGTH_SHORT).show();
		super.onActivityResult(requestCode, resultCode, data); 
		if(requestCode == 30)
		{
			
			menuArrayList = new ArrayList<MenuItemData>();
	           
			Integer getId = -1;
			String getName = "";
			Double getPrice = 0.0;
			if(resultCode == RESULT_OK) { 
				DataHandler dataHandler = new DataHandler(getBaseContext());
				dataHandler.open();		
				Cursor cursor = dataHandler.returnMenusData();
				if(cursor.moveToFirst())
				{
				
					do
					{
						MenuItemData item = new MenuItemData();
						getId = cursor.getInt(cursor.getColumnIndex("id"));
						getName = cursor.getString(cursor.getColumnIndex("name"));
						getPrice = cursor.getDouble(cursor.getColumnIndex("price"));
						
						item.setId(getId);
						item.setName(getName);
						item.setPrice(getPrice);
						
						menuArrayList.add(item);
						
						
					}while(cursor.moveToNext());
					
				}
				
				dataHandler.close();
			} 
			while (headerRow.getChildCount() <  menuArrayList.size()) {
				addColumn();
			}
			// set display
			for(int i=0; i< menuArrayList.size(); i++)
			{
				
				TextView textView = (TextView) headerRow.getChildAt(i);
				MenuItemData menu = (MenuItemData)menuArrayList.get(i);
				menu.setColumnsIndex(i);
				textView.setText(menu.getName());
				textView.setId(menu.getId());
			}
		}
		else
		{
			memberArrayList = new ArrayList<MemberItemData>();
	           
			Integer getId = -1;
			String getFirstName = "";
			String getLastName = "";
			String getPhoneNumber = "";
			
			if(resultCode == RESULT_OK) { 
				DataHandler dataHandler = new DataHandler(getBaseContext());
				dataHandler.open();		
				Cursor cursor = dataHandler.returnMembersData();
				if(cursor.moveToFirst())
				{
				
					do
					{
						MemberItemData item = new MemberItemData();
						getId = cursor.getInt(cursor.getColumnIndex(TableMembers.Field_Id));
						getFirstName = cursor.getString(cursor.getColumnIndex(TableMembers.Field_FirstName));
						getLastName = cursor.getString(cursor.getColumnIndex(TableMembers.Field_LastName));
						getPhoneNumber = cursor.getString(cursor.getColumnIndex(TableMembers.Field_PhoneNumber));
						
						item.setId(getId);
						item.setFirstName(getFirstName);
						item.setLastName(getLastName);
						item.setPhoneNumber(getPhoneNumber);
						
						memberArrayList.add(item);
						
						
					}while(cursor.moveToNext());
					
				}
				
				dataHandler.close();
			} 
			while (fixedColumn.getChildCount() <  memberArrayList.size()) {
				addRow();
			}
			// set display
			for(int i=0; i< memberArrayList.size(); i++)
			{
				
				TextView textView = (TextView) fixedColumn.getChildAt(i);
				MemberItemData menu = (MemberItemData)memberArrayList.get(i);
				menu.setRowsIndex(i);
				textView.setText(menu.getFirstName());
				textView.setId(menu.getId());
			}
			
		}
		
		
	} 


	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	public Button createRemoveButton(boolean hidden, int columnToRemove) {
		Button removeBtn = new Button(this);
		removeBtn.setTag(columnToRemove);
		if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN) {
			if (!hidden)
				removeBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.remove_btn));
			else {
				removeBtn.setVisibility(View.INVISIBLE);
			}

		}
		else {
			if (!hidden)
				removeBtn.setBackground(getResources().getDrawable(R.drawable.remove_btn));
			else 
				removeBtn.setVisibility(View.INVISIBLE);
		}

		removeBtn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				deleteColumn(v);
			}
		});
		return removeBtn;
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public TextView makeTableRowHeaderWithText(String text, int widthInPercentOfScreenWidth, int fixedHeightInPixels) {
		int screenWidth = getResources().getDisplayMetrics().widthPixels;
		final TextView tableDataView = new TextView(this);
		tableDataView.setText(text);
		tableDataView.setTextColor(Color.BLACK);
		tableDataView.setTextSize(20);
		tableDataView.setWidth(widthInPercentOfScreenWidth * screenWidth / 100);
		tableDataView.setHeight(fixedHeightInPixels);

		// long touch


		// set ontouch event
		tableDataView.setOnTouchListener(new View.OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case android.view.MotionEvent.ACTION_DOWN :
					//					changeTextViewColor(v, Color.YELLOW);
					
					Intent intent = new Intent(getBaseContext(), AddMenus.class);
					intent.putExtra(MENU_INTENT_DATA, findMenuItemData(tableDataView.getId()));
					navToAddItemViewMode(intent);
					break;
				case android.view.MotionEvent.ACTION_UP :
					//					changeTextViewColor(v, prevColor);
					break;
				}

				return true;
			}
		});

		return tableDataView;
	}
	
	private MenuItemData findMenuItemData(int id) {
		if (menuArrayList != null) {
			for (MenuItemData item : menuArrayList) {
				if (id == item.getId())
					return item;
			}
		}
		return new MenuItemData();
	}

	
	private MemberItemData findMemberItemData(int id) {
		if (memberArrayList != null) {
			for (MemberItemData item : memberArrayList) {
				if (id == item.getId())
					return item;
			}
		}
		return new MemberItemData();
	}
	
	public void navToAddItemViewMode(Intent intent) {
		startActivityForResult(intent, 30);
		//	    	overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
	}
	
	public void navToAddMemberItemViewMode(Intent intent) {
		startActivityForResult(intent, 40);
		//	    	overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
	}

	//	//util method
	//	private Button tableDataBtn;
	
	/**
	 * Create checkbox
	 * @param text
	 * @param widthInPercentOfScreenWidth
	 * @param fixedHeightInPixels
	 * @return
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public TextView makeTableRowCheckBox(int widthInPercentOfScreenWidth, int fixedHeightInPixels) {
		int screenWidth = getResources().getDisplayMetrics().widthPixels;
		final CheckBox checkBox = new CheckBox(this);
		checkBox.setWidth(widthInPercentOfScreenWidth * screenWidth / 100);
		checkBox.setHeight(fixedHeightInPixels);

		checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
		    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
		    {
		        if ( isChecked )
		        {
		        	//TODO
		            Toast.makeText(QuickViewActivity.this,"CheckBox is Checked ..",3000).show();
		        }
		        else{
		            Toast.makeText(QuickViewActivity.this,"CheckBox is Unchecked ..",3000).show();
		        }

		    }
		});

		return checkBox;
	}

	/**
	 * Create column member
	 * @param text
	 * @param widthInPercentOfScreenWidth
	 * @param fixedHeightInPixels
	 * @return
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public TextView makeTableRowWithText(String text, int widthInPercentOfScreenWidth, int fixedHeightInPixels) {
		int screenWidth = getResources().getDisplayMetrics().widthPixels;
		final TextView tableDataView = new TextView(this);
		tableDataView.setText(text);
		tableDataView.setTextColor(Color.BLACK);
		tableDataView.setTextSize(20);
		tableDataView.setWidth(widthInPercentOfScreenWidth * screenWidth / 100);
		tableDataView.setHeight(fixedHeightInPixels);


		// long touch


		// set ontouch event
		tableDataView.setOnTouchListener(new View.OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case android.view.MotionEvent.ACTION_DOWN :
					//					changeTextViewColor(v, Color.YELLOW);
					Intent intent = new Intent(getBaseContext(), AddMembers.class);
					intent.putExtra(MEMBER_INTENT_DATA, findMemberItemData(tableDataView.getId()));
					System.out.println("Column id = " + tableDataView.getId());
					navToAddMemberItemViewMode(intent);
					
					break;
				case android.view.MotionEvent.ACTION_UP :
					//					changeTextViewColor(v, prevColor);
					break;
				}

				return true;
			}
		});

		return tableDataView;
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public int getBackgroundColor(View v) {
		Drawable background = v.getBackground();
		int color = -1;
		if (background instanceof ColorDrawable) {
			if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
				color = Color.GRAY;
			}
			else {
				color = ((ColorDrawable) background).getColor();
			}
		}
		return color;
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
	}

	/**
	 * On tap event handler
	 * @param v
	 * @param color
	 */
	public void changeTextViewColor(View v, int color) {
		v.setBackgroundColor(color);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.quick_edit_actions, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		switch (item.getItemId()) {
		case R.id.action_settings:
			showSettings();
			return true;
		case R.id.action_edit_table:
			showTableEditMode();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public void showSettings() {
		Toast.makeText(QuickViewActivity.this, "Settings", Toast.LENGTH_SHORT).show();
	}

	public void showTableEditMode() {
		int state = editHeaderScrollView.getVisibility();
		if (state == View.VISIBLE) {
			Toast.makeText(QuickViewActivity.this, "Done editing", Toast.LENGTH_SHORT).show();
			editHeaderScrollView.setVisibility(View.INVISIBLE);
		}
		else {
			Toast.makeText(QuickViewActivity.this, "Edit mode. Tab red button to delete row/column, and green button"
					+ " to add one.", Toast.LENGTH_SHORT).show();
			editHeaderScrollView.setVisibility(View.VISIBLE);
		}
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_quick_view,
					container, false);
			return rootView;
		}
	}

}
