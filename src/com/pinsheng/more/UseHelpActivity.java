package com.pinsheng.more;

import com.pinsheng.help.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class UseHelpActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.use_help_activity);
		findViewById(R.id.record_return).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				UseHelpActivity.this.finish();
			}
		});
	}
	
}
