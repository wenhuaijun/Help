package com.pinsheng.more;

import java.util.List;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.pinsheng.app.HELPActivity;
import com.pinsheng.app.HELPApllication;
import com.pinsheng.help.R;
import com.pinsheng.mode.SendMsgHistory;
import com.pinsheng.util.Util;

public class HelpRecordActivity extends HELPActivity{
	private ListView listView;
	private List<SendMsgHistory> records;
	private MyAdapter adapter;
	private ImageView return_img;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_help_record);
		listView = (ListView)findViewById(R.id.record_listView);
		return_img =(ImageView)findViewById(R.id.record_return);
		records = ((HELPApllication)getApplication()).getSendHistory();
		if(records.size()==0){
			TextView tv = new TextView(HelpRecordActivity.this);
			tv.setGravity(Gravity.CENTER);
			tv.setBackgroundColor(Color.parseColor("#ffffff"));
			tv.setText("还未发送任何求救短信");
			tv.setLayoutParams(new AbsListView.LayoutParams(LayoutParams.MATCH_PARENT,Util.dip2px(this, 48)));
			listView.addHeaderView(tv);
		}
		adapter = new MyAdapter();
		listView.setAdapter(adapter);
		return_img.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}
	class MyAdapter extends BaseAdapter{
		private LayoutInflater inflater =getLayoutInflater();
		@Override
		public int getCount() {
			return records.size() ;
		}

		@Override
		public Object getItem(int position) {
			return records.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view =inflater.inflate(R.layout.help_record_listview_item, null);
			((TextView)view.findViewById(R.id.name)).setText(records.get(position).getName());
			((TextView)view.findViewById(R.id.content)).setText(records.get(position).getContent());
			((TextView)view.findViewById(R.id.data)).setText(records.get(position).getData());
			return view;
		}
		
	}
}
