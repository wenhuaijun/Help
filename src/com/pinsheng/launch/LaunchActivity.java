package com.pinsheng.launch;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.pinsheng.app.HELPActivity;
import com.pinsheng.help.MainActivity;
import com.pinsheng.util.Constant;
import com.pinsheng.util.Util;

public class LaunchActivity extends HELPActivity {

	private Intent intent;
	private SharedPreferences sp;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initData();
		
		new Handler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
				startActivity(intent);
				finish();
			}
		}, 1500);
	}
	
	private void initData() {
		sp=Util.getPreference(LaunchActivity.this);
		String str=sp.getString(Constant.LOCAL_FIRST_LOGIN, "");
		
		intent = new Intent();
		if(str==""){
			intent.setClass(this, WhatsnewActivity.class);
			sp.edit().putString(Constant.LOCAL_FIRST_LOGIN, "false").commit();
		}else{
			intent.setClass(this, MainActivity.class);
		}
		
	}
}
