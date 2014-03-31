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
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_quick_view);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
			.add(R.id.container, new PlaceholderFragment()).commit();
		}

		TableRow.LayoutParams wrapWrapTableRowParams = new TableRow.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		int[] fixedColumnWidths = new int[]{20, 20, 20, 20, 20};
		int[] scrollableColumnWidths = new int[]{20, 20, 20, 20, 20};
		int fixedRowHeight = 150;
		int fixedHeaderHeight = 100;
		int numRows = 10;

		TableRow row = new TableRow(this);

		// Dummy table top corner
		TableLayout dummy = (TableLayout) findViewById(R.id.dummy_table);
		row.addView(makeTableRowWithText("Group Name", fixedColumnWidths[0], fixedHeaderHeight));
		dummy.addView(row);

		// Header (fixed vertically but scrollable horizontally)
		TableLayout header = (TableLayout) findViewById(R.id.table_header);
		row = new TableRow(this);
		row.setLayoutParams(wrapWrapTableRowParams);
		row.setGravity(Gravity.CENTER);
		row.setBackgroundColor(Color.RED);
		row.addView(makeTableRowWithText("Activity 1", fixedColumnWidths[0], fixedHeaderHeight));
		row.addView(makeTableRowWithText("Activity 2", fixedColumnWidths[1], fixedHeaderHeight));
		row.addView(makeTableRowWithText("Activity 3", fixedColumnWidths[2], fixedHeaderHeight));
		row.addView(makeTableRowWithText("Activity 4", fixedColumnWidths[3], fixedHeaderHeight));
		row.addView(makeTableRowWithText("Activity 5", fixedColumnWidths[4], fixedHeaderHeight));
		row.addView(makeTableRowWithText("Activity 6", fixedColumnWidths[4], fixedHeaderHeight));
		row.addView(makeTableRowWithText("Activity 7", fixedColumnWidths[4], fixedHeaderHeight));
		header.addView(row);

		// synchronize scrollviews
		// TODO
		headerScrollView = (SyncHorizontalScrollView) findViewById(R.id.header_scrollview);
		contentScrollView = (SyncHorizontalScrollView) findViewById(R.id.content_scrollview);
		headerScrollView.setSyncView(contentScrollView);
		contentScrollView.setSyncView(headerScrollView);

		// Column (fixed when scrolled horizontally)
		TableLayout fixedColumn = (TableLayout) findViewById(R.id.fixed_column);

		// Rest of the table (within a scroll view)
		TableLayout scrollablePart = (TableLayout) findViewById(R.id.scrollable_part);
		for(int i = 0; i < numRows; i++) {
			TextView fixedView = makeTableRowWithText("row number " + (i+1), scrollableColumnWidths[0], fixedRowHeight);
			fixedView.setBackgroundColor(Color.BLUE);
			fixedColumn.addView(fixedView);
			row = new TableRow(this);
			row.setLayoutParams(wrapWrapTableRowParams);
			row.setGravity(Gravity.CENTER);
			row.setBackgroundColor(Color.WHITE);
			row.addView(makeTableRowWithText("value 1", scrollableColumnWidths[1], fixedRowHeight));
			row.addView(makeTableRowWithText("value 2", scrollableColumnWidths[2], fixedRowHeight));
			row.addView(makeTableRowWithText("value 3", scrollableColumnWidths[3], fixedRowHeight));
			row.addView(makeTableRowWithText("value 4", scrollableColumnWidths[4], fixedRowHeight));
			row.addView(makeTableRowWithText("value 5", scrollableColumnWidths[4], fixedRowHeight));
			row.addView(makeTableRowWithText("value 6", scrollableColumnWidths[4], fixedRowHeight));
			row.addView(makeTableRowWithText("value 7", scrollableColumnWidths[4], fixedRowHeight));
			scrollablePart.addView(row);
			((TextView) row.getChildAt(0)).setText("hello!!!");;
		}
		
		
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
//					Toast.makeText(QuickViewActivity.this, "Pressed", Toast.LENGTH_SHORT).show();
					prevColor = getBackgroundColor(v);
					changeTextViewColor(v, Color.YELLOW);
					break;
				case android.view.MotionEvent.ACTION_UP :
//					Toast.makeText(QuickViewActivity.this, "Released", Toast.LENGTH_SHORT).show();
					changeTextViewColor(v, prevColor);
					break;
				}
				
				return true;
			}
        });
		
		return tableDataView;
	}
	
	private int prevColor;
	
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
		getMenuInflater().inflate(R.menu.quick_view, menu);
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
