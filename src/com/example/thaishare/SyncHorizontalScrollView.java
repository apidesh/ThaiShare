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

	public SyncHorizontalScrollView(Context context, AttributeSet attr) {
		super(context, attr);
	}

	/**
	 * Set the other sync horizontal view to synchronize with this view
	 * @param v The other view
	 */
	public void setSyncView(SyncHorizontalScrollView v) {
		other = v;
	}

	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		other.scrollTo(l, 0);
	}

}
