package com.pinsheng.launch;

import android.content.Intent;
import android.opengl.Visibility;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;

import com.pinsheng.app.HELPActivity;
import com.pinsheng.help.MainActivity;
import com.pinsheng.help.R;


public class WhatsnewActivity extends HELPActivity implements OnClickListener,OnPageChangeListener{
	private ViewPager mViewPager;
	private int[] imgs={R.drawable.help2,R.drawable.help3,R.drawable.help4,R.drawable.help5};
	private ImageView[] imageViews;
	private ImageView[] tips;
	private LinearLayout viewGroup;
	private Button center;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.whatsnew_viewpager);
		mViewPager = (ViewPager) findViewById(R.id.whatsnew_viewpager);
		viewGroup = (LinearLayout)findViewById(R.id.viewGroup);
		center = (Button)findViewById(R.id.center);
		center.setOnClickListener(this );
		imageViews = new ImageView[imgs.length];
		for(int i=0;i<imgs.length;i++){
			ImageView img = new ImageView(this);
			imageViews[i]=img;
			imageViews[i].setImageResource(imgs[i]);
			imageViews[i].setScaleType(ScaleType.FIT_XY);
		}
		tips = new ImageView[imgs.length];
		for(int i=0; i<tips.length; i++){  
            ImageView imageView = new ImageView(this);  
            imageView.setLayoutParams(new LayoutParams(10,10));  
            tips[i] = imageView;  
            if(i == 0){  
                tips[i].setBackgroundResource(R.drawable.page_now);  
            }else{  
                tips[i].setBackgroundResource(R.drawable.page);  
            }  
              
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(new ViewGroup.LayoutParams(LayoutParams.WRAP_CONTENT,    
                    LayoutParams.WRAP_CONTENT));  
            layoutParams.leftMargin = 12;  
            layoutParams.rightMargin = 12;  
            viewGroup.addView(imageView, layoutParams);  
        }
		MyAdapter adapter = new MyAdapter();
		mViewPager.setAdapter(adapter);
		mViewPager.setOnPageChangeListener(this);
	}	
	
	class MyAdapter extends PagerAdapter{

		@Override
		public int getCount() {
			return imgs.length;
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0==arg1;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView(imageViews[position]);
			
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			container.addView(imageViews[position]);
			return imageViews[position];
		}
		
	}

	@Override
	public void onClick(View v) {
		startActivity(new Intent(WhatsnewActivity.this, MainActivity.class));
		finish();
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		
	}

	@Override
	public void onPageSelected(int arg0) {
		setImageBackground(arg0);
		if(arg0==tips.length-1){
			center.setVisibility(View.VISIBLE);
		}else{
			center.setVisibility(View.GONE);
		}
	}
	private void setImageBackground(int selectItems){  
        for(int i=0; i<tips.length; i++){  
            if(i == selectItems){  
                tips[i].setBackgroundResource(R.drawable.page_now);  
            }else{  
                tips[i].setBackgroundResource(R.drawable.page);  
            }  
        }  
    } 
}
