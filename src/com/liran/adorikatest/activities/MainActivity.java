package com.liran.adorikatest.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.adorikatest.R;
import com.liran.adorikatest.SlidingTab.SlidingTabLayout;
import com.liran.adorikatest.fragments.MessagesFragment;
import com.liran.adorikatest.others.Constants;

public class MainActivity extends    ActionBarActivity {

	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a {@link FragmentPagerAdapter}
	 * derivative, which will keep every loaded fragment in memory. If this
	 * becomes too memory intensive, it may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	SectionsPagerAdapter mSectionsPagerAdapter;

	private int[] imageResId = {
	        R.drawable.ic_chat,
	        R.drawable.ic_contacts,
	        R.drawable.ic_tag
	};
	 
 
    /**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;
	
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// Set up toolbar
		Toolbar actionBar = (Toolbar) findViewById(R.id.qqq);
		setSupportActionBar(actionBar);
		actionBar.setTitle(null);
		getSupportActionBar().setTitle(null);

		getSupportActionBar().setDisplayShowTitleEnabled(false);
 
		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(
				new SectionsPagerAdapter(
						getSupportFragmentManager(), this));
		
		//  Set up SlidingTabLayout for custom tabs
		SlidingTabLayout mSlidingTabLayout = (SlidingTabLayout) findViewById(R.id.sliding_tabs);
		mSlidingTabLayout.setCustomTabView(R.layout.pager_item, 0);
		mSlidingTabLayout.setDistributeEvenly(true);

		mSlidingTabLayout.setViewPager(mViewPager);
         
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		return super.onOptionsItemSelected(item);
	}
	  
	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {
		
		Context mContext;
		
		public SectionsPagerAdapter(FragmentManager fragmentManager, Context context) {
			super(fragmentManager);
			mContext = context;
			
		}
  
		@Override
		public Fragment getItem(int position) {
			// getItem is called to instantiate the fragment for the given page.
			// Return a PlaceholderFragment (defined as a static inner class
			// below).
			
		   switch (position) {
			
			case 0:
				return MessagesFragment.newInstance(position + 1, false);
			default:
				return MessagesFragment.newInstance(position + 1, true);
			}
		}

		@Override
		public int getCount() {
			// Show 3 total pages.
			return 3;
		}
		 
		@Override
		public CharSequence getPageTitle(int position) {
			  
			  // Set icon instead of text title
			  Drawable mDrawableChat = getResources().getDrawable( imageResId[position]);
			  ImageSpan span = null;
			  SpannableStringBuilder sbChat = new SpannableStringBuilder(" "); // space added before text for convenience
			  
			  mDrawableChat.setBounds(0, 0, mDrawableChat.getIntrinsicWidth(), mDrawableChat.getIntrinsicHeight()); 
			    span = new ImageSpan(mDrawableChat, ImageSpan.ALIGN_BASELINE); 
			    sbChat.setSpan(span, 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); 
			    return sbChat;
			  
		}
	} 
	
	public void alertDialog( View v ) {
		String alert = null;
		
		switch (v.getId()) {
	    case (R.id.btn_menu):
	    	alert = Constants.MENU;
	    break;
	    case (R.id.btn_new_chat):
	    	alert = Constants.NEW_CHAT;
	    break;
	    case (R.id.btn_add_contact):
	    	alert = Constants.ADD_CONTACT;
	    break;
	    case (R.id.btn_search):
	    	alert = Constants.SEARCH;
	    break;
	    case (R.id.btn_componse):
	    	alert = Constants.COMPOSE;
	    break;
	    }
		
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				this);
 
			// set title
			alertDialogBuilder.setTitle(alert);
			alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		                //do nothing
		           }
		       });
			
			// create alert dialog
			AlertDialog alertDialog = alertDialogBuilder.create();

			// show it
			alertDialog.show();
 
	}
  
}
