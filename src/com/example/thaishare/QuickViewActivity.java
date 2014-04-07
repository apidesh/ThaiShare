package com.example.thaishare;


import android.support.v7.app.ActionBar.LayoutParams;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;

public class QuickViewActivity extends ActionBarActivity {

	protected SyncHorizontalScrollView headerScrollView;
	protected SyncHorizontalScrollView contentScrollView;
	protected SyncHorizontalScrollView editHeaderScrollView;

	protected static final int STARTING_NUM_ROWS = 4;						// default number of rows
	protected static final int STARTING_NUM_COLUMNS = 3;					// default number of activities
	
	protected TableRow editRow;
	protected TableRow headerRow;

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

		/* Table size variables */
		int fixedColumnWidth = 20;											// percentage of row header width with respect to the screen width
		int scrollableColumnWidth = 20;										// percentage of table row width with respect to the screen width
		int fixedRowHeight = 150;											// height in pixels of table row
		int fixedHeaderHeight = 100;										// height in pixels of header row

		TableRow row = new TableRow(this);
		TableRow editDummyRow = new TableRow(this);

		// Dummy table top corner
		TableLayout dummy = (TableLayout) findViewById(R.id.dummy_table);
		TableLayout editDummy = (TableLayout) findViewById(R.id.edit_dummy_table);

		Button removeBtn = createRemoveButton(true);
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
			removeBtn = createRemoveButton(false);
			editRow.addView(removeBtn);
			headerRow.addView(makeTableRowWithText(getResources().getString( R.string.default_item_placeholder ) + " " + (i+1), 
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
		TableLayout fixedColumn = (TableLayout) findViewById(R.id.fixed_column);

		// Rest of the table (within a scroll view)
		TableLayout scrollablePart = (TableLayout) findViewById(R.id.scrollable_part);
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
				row.addView(makeTableRowWithText("value " + (j+1), scrollableColumnWidth, fixedRowHeight));
			}
			scrollablePart.addView(row);
			((TextView) row.getChildAt(0)).setText("hello!!!");;
		}

		// hide all edit buttons
		editHeaderScrollView.setVisibility(View.INVISIBLE);
		
		//TODO
		//
	}

	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	public Button createRemoveButton(boolean hidden) {
		Button removeBtn = new Button(this);
		if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN) {
			if (!hidden)
				removeBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.remove_btn));
			else 
				removeBtn.setBackgroundColor(Color.TRANSPARENT);
		}
		else {
			if (!hidden)
				removeBtn.setBackground(getResources().getDrawable(R.drawable.remove_btn));
			else 
				removeBtn.setBackgroundColor(Color.TRANSPARENT);
		}
		return removeBtn;
	}

	//	//util method
	//	private Button tableDataBtn;

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public TextView makeTableRowWithText(String text, int widthInPercentOfScreenWidth, int fixedHeightInPixels) {
		int screenWidth = getResources().getDisplayMetrics().widthPixels;
		TextView tableDataView = new TextView(this);
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
