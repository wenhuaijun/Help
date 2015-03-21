package com.pinsheng.app;


import java.lang.reflect.Field;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.ViewConfiguration;

import com.pinsheng.help.R;
import com.umeng.analytics.MobclickAgent;
import com.umeng.message.PushAgent;

public class HELPActivity extends Activity {
	
	
	public HELPApllication getWXApplication() {
		return (HELPApllication) getApplication();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		getWXApplication();
		PushAgent.getInstance(this).onAppStart();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
	}

	@Override
	protected void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}

	public void setReturnAble(boolean returnable){
		if(returnable){
	    	getActionBar().setIcon(R.drawable.ic_return);
	    	getActionBar().setHomeButtonEnabled(true);
	    	getActionBar().setDisplayShowHomeEnabled(true); 
		}else{
			getActionBar().setIcon(R.drawable.ic_launcher);
	    	getActionBar().setHomeButtonEnabled(false);
	    	getActionBar().setDisplayShowHomeEnabled(false); 
		}
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			this.finish();
		}
		return super.onOptionsItemSelected(item);
	}

	private void getOverflowMenu() {
		try {
			ViewConfiguration config = ViewConfiguration.get(this);
			Field menuKeyField = ViewConfiguration.class
					.getDeclaredField("sHasPermanentMenuKey");
			if (menuKeyField != null) {
				menuKeyField.setAccessible(true);
				menuKeyField.setBoolean(config, false);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}