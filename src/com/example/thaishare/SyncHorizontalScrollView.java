package com.example.thaishare;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.HorizontalScrollView;

/**
 *  Horizontal scroll view custom class to sync scrolling
 * @author Jitrapon
 */
public class SyncHorizontalScrollView extends HorizontalScrollView {

	SyncHorizontalScrollView other;
	SyncHorizontalScrollView other2;

	public SyncHorizontalScrollView(Context context, AttributeSet attr) {
		super(context, attr);
	}

	/**
	 * Set the other sync horizontal view to synchronize with this view
	 * @param v The other view
	 */
	public void setSyncView(SyncHorizontalScrollView v, SyncHorizontalScrollView v2) {
		other = v;
		other2 = v2;
	}

	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		other.scrollTo(l, 0);
		other2.scrollTo(l, 0);
	}

}
