package com.pinsheng.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class StartServiceReceiver extends BroadcastReceiver{

	@Override
	public void onReceive(Context context, Intent intent) {
		Intent serviceIntent = new Intent(context,HelpService.class);
		serviceIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startService(serviceIntent);
	}

}
