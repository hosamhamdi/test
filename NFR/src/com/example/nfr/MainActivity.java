package com.example.nfr;

import java.util.Locale;

import tabsswipe.adapter.TabsPagerAdapter;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;



@SuppressLint("NewApi")
public class MainActivity extends FragmentActivity implements
		ActionBar.TabListener {

	
	private String[] tabs = new String[4];
	
	
	
	
	private ViewPager viewPager;
	private TabsPagerAdapter mAdapter;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
		Resources res = getResources();
//
		Locale current = getResources().getConfiguration().locale;
		Log.d("current", current.toString());
		
		
		
		tabs[0]=String.format(res.getString(R.string.tab0));
		tabs[1]=String.format(res.getString(R.string.tab1));
		tabs[2]=String.format(res.getString(R.string.tab2));
		tabs[3]=String.format(res.getString(R.string.tab3));
		
		
		
		
		final ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		viewPager = (ViewPager) findViewById(R.id.pager);
		mAdapter = new TabsPagerAdapter(getSupportFragmentManager());
		viewPager.setAdapter(mAdapter);

		ActionBar.TabListener tabListener = new ActionBar.TabListener() {
			public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
				// show the given tab
				viewPager.setCurrentItem(tab.getPosition());
			}

			public void onTabUnselected(ActionBar.Tab tab,
					FragmentTransaction ft) {
				// hide the given tab

			}

			@Override
			public void onTabReselected(Tab tab, FragmentTransaction ft) {
				// TODO Auto-generated method stub

			}

		};

		// Add 3 tabs, specifying the tab's text and TabListener
		for (int i = 0; i < 4; i++) {
			actionBar.addTab(actionBar.newTab().setText(tabs[i])
					.setTabListener(tabListener));
		}

		viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				// on changing the page
				// make respected tab selected
				actionBar.setSelectedNavigationItem(position);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});

	}

	@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.

		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);

		return true;
	}

	@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.menu_facebook:
			
			
				
				startActivity(getOpenFacebookIntent(this));				
			return true;
			
		case R.id.menu_tweeter:
			
			
			
			startActivity(getOpenTwitterIntent(this));				
		return true;
		
		case R.id.action_share:

			Intent sendIntent = new Intent();
			sendIntent.setAction(Intent.ACTION_SEND);
			sendIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
					 "تطبيق الفتح");
			sendIntent.putExtra(android.content.Intent.EXTRA_TEXT,
					 "قم بتحميل تطبيق الفتح على الأندرويد https://play.google.com/store/apps/details?id=com.innoflame.alfateh_club");
			sendIntent.setType("text/plain");
			startActivity(sendIntent);

			// Toast.makeText(this, "share", Toast.LENGTH_LONG).show();

			return true;

		case R.id.menu_English:
			
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}

	}

	

	@Override
	public void onTabReselected(Tab arg0, FragmentTransaction arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTabSelected(Tab arg0, FragmentTransaction arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTabUnselected(Tab arg0, FragmentTransaction arg1) {
		// TODO Auto-generated method stub
		
	}
	
	public static Intent getOpenFacebookIntent(Context context) {



		    return new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com"));

		}
	
	public static Intent getOpenTwitterIntent(Context context) {



	    return new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com"));

	}


	
}
