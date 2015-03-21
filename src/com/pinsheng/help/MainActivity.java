package com.pinsheng.help;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListPopupWindow;
import android.widget.TextView;

import com.pinsheng.app.HELPActivity;
import com.pinsheng.map.SearchFragment;
import com.pinsheng.more.HelpRecordActivity;
import com.pinsheng.more.ShareActivity;
import com.pinsheng.util.Util;
import com.umeng.message.PushAgent;
import com.umeng.update.UmengUpdateAgent;
import com.umeng.update.UpdateConfig;

public class MainActivity extends HELPActivity implements OnClickListener,OnPageChangeListener{
	private ViewPager mViewPager;
	private static Boolean isExit = false; // 双击退出
	private FragmentManager fm;
	private FragmentTransaction trans;
	public static  HELPFragment helpFragment;
	private SearchFragment serachFragment;
	private ImageView more;
	private ImageView tab_help;
	private ImageView tab_around;
	//private ImageView left_menu;
	ListPopupWindow listPopupWindow;
	private Intent intent;
//	private SlidingMenu slidingMenuLeft;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		UmengUpdateAgent.update(this);
		UpdateConfig.setDebug(true);
		PushAgent mPushAgent = PushAgent.getInstance(MainActivity.this);
		mPushAgent.enable();
		com.umeng.socialize.utils.Log.LOG = true;
		initView();
		initFragment();
		initListener();
		
	}



	private void initView() {
		mViewPager =(ViewPager)findViewById(R.id.viewPager);
		more = (ImageView)findViewById(R.id.main_more);
		tab_help = (ImageView)findViewById(R.id.tab_help);
		tab_around = (ImageView)findViewById(R.id.tab_around);
	//	left_menu = (ImageView)findViewById(R.id.main_menu);
		/*slidingMenuLeft = new SlidingMenu(MainActivity.this);
		slidingMenuLeft.setMode(SlidingMenu.LEFT);
		slidingMenuLeft.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		slidingMenuLeft.setShadowWidthRes(R.dimen.shadow_width);
		slidingMenuLeft.setShadowDrawable(R.drawable.shadow);
		slidingMenuLeft.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		slidingMenuLeft
				.setBehindWidth(Util.getScreenWidth(MainActivity.this) * 8/9);
		slidingMenuLeft.setFadeDegree(1f);
		slidingMenuLeft.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
		slidingMenuLeft.setMenu(R.layout.left_drawlayout);*/
	}
	
	private void initListener() {
	//	left_menu.setOnClickListener(this);
		tab_help.setOnClickListener(this);
		tab_around.setOnClickListener(this);
		mViewPager.setAdapter(new VPAdapter(fm));
		mViewPager.setOnPageChangeListener(this);
		more.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				showPopupWindows(MainActivity.this,more,getResources().getStringArray(R.array.spinner_more),new PopupListener() {
					
					@Override
					public void onListenerPop(ListPopupWindow listp) {
						listp.setWidth((int)(Util.getScreenWidth(MainActivity.this) / 2.5));
					}
					
					@Override
					public void onListItemClickBack(ListPopupWindow popwindow, View parent,
							int position) {
						if(popwindow.isShowing()){
							popwindow.dismiss();
						}
						switch(position){
						case 0:
							
							break;
						case 1:
							intent = new Intent(MainActivity.this, ShareActivity.class);
							startActivity(intent);
							break;
						case 2:
							intent = new Intent(MainActivity.this, HelpRecordActivity.class);
							startActivity(intent);
							break;
						}
					}
				});
			}
		});
	}
	private void initFragment() {
		helpFragment = new HELPFragment();
		serachFragment =new SearchFragment();
		fm=getFragmentManager();
		fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
		trans = fm.beginTransaction();
		trans.add(serachFragment, "1");
			trans.add(helpFragment, "0");
		trans.commit();
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		 MenuInflater inflater = getMenuInflater();
		 inflater.inflate(R.menu.main, menu);
		return true;
	}



	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()){
		case R.id.menu_1:
			break;
		case R.id.menu_2:
			intent = new Intent(MainActivity.this, ShareActivity.class);
			startActivity(intent);
			break;
		case R.id.menu_3:
			intent = new Intent(MainActivity.this, HelpRecordActivity.class);
			startActivity(intent);
			break;
		case R.id.menu_4:
			break;
		}
		return true;
	}



	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			exitBy2Click();
			return false;
		}
		/*if (keyCode == KeyEvent.KEYCODE_MENU) {
			if(listPopupWindow!=null&&listPopupWindow.isShowing()){
				return true;
			}
			showPopupWindows(MainActivity.this,more,getResources().getStringArray(R.array.spinner_more),new PopupListener() {
				
				@Override
				public void onListenerPop(ListPopupWindow listp) {
					listp.setWidth((int)(Util.getScreenWidth(MainActivity.this) / 2.5));
				}
				
				@Override
				public void onListItemClickBack(ListPopupWindow popwindow, View parent,
						int position) {
					if(popwindow.isShowing()){
						popwindow.dismiss();
					}
					//Intent intent = new Intent();
					switch(position){
					case 0:
						intent.setClass(MainActivity.this, AboutUsActivity.class);
						intent.putExtra("SET", "true");
						startActivity(intent);
						break;
					case 1:
						intent.setClass(MainActivity.this, ChooseColorActivity.class);
						
						startActivity(intent);
						break;
					}
				}
			});
			return true;
		}*/
		return super.onKeyDown(keyCode, event);
	}

	private void exitBy2Click() {
		Timer tExit = null;
		if (isExit == false) {
			isExit = true; // 准备退出
			Util.Toast(MainActivity.this, "再按一下退出！！");
			tExit = new Timer();
			tExit.schedule(new TimerTask() {
				@Override
				public void run() {
					isExit = false; // 取消退出
				}
			}, 1500);

		} else {
			finish();
			System.exit(0);
		}
	}
	

	@Override
	public void onPageScrollStateChanged(int arg0) {
		
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		
	}

	@Override
	public void onPageSelected(int arg0) {
		switch(arg0){
		case 0:
			tab_help.setImageResource(R.drawable.tab_help_press);
			tab_around.setImageResource(R.drawable.tab_around);
		//	slidingMenuLeft.removeIgnoredView(mViewPager);
			break;
		case 1:
			//slidingMenuLeft.addIgnoredView(mViewPager);
			tab_help.setImageResource(R.drawable.tab_help);
			tab_around.setImageResource(R.drawable.tab_around_press);
			break;
		}
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
	/*	case R.id.main_menu:
			slidingMenuLeft.toggle();
			break;*/
		case R.id.tab_help:
			tab_help.setImageResource(R.drawable.tab_help_press);
			tab_around.setImageResource(R.drawable.tab_around);
			mViewPager.setCurrentItem(0);
			break;
		case R.id.tab_around:
			tab_help.setImageResource(R.drawable.tab_help);
			tab_around.setImageResource(R.drawable.tab_around_press);
			mViewPager.setCurrentItem(1);
			break;
		}
	}
public interface PopupListener{
		
		public void onListenerPop(ListPopupWindow listp);
		
		public void onListItemClickBack(ListPopupWindow popwindow, View parent, int position);
	}
	
	protected void showPopupWindows(Context context,final View parent,final String[] content,final PopupListener popListener){
    	final LayoutInflater _Inflater = LayoutInflater.from(context);
		 listPopupWindow = new ListPopupWindow(context);
		listPopupWindow.setAnchorView(parent);
		listPopupWindow.setModal(true);	
		popListener.onListenerPop(listPopupWindow);
		parent.getViewTreeObserver().addOnGlobalLayoutListener(
				new OnGlobalLayoutListener() {
					@Override
					public void onGlobalLayout() {
						listPopupWindow.setWidth(parent.getWidth());
						listPopupWindow.setHeight(ListPopupWindow.WRAP_CONTENT);
						parent.getViewTreeObserver()
								.removeGlobalOnLayoutListener(this);
					}
				});
		listPopupWindow.setAdapter(new BaseAdapter() {
			
    		@Override
    		public int getCount() {
    			return content.length;
    		}

    		@Override
    		public Object getItem(int position) {
    			return position;
    		}

    		@Override
    		public long getItemId(int position) {
    			return position;
    		}

    		@Override
    		public View getView(int position, View convertView, ViewGroup parent) {
    			View view = _Inflater.inflate(R.layout.popuplist_item, null);
    			TextView tv = (TextView)view.findViewById(R.id.item_tv);
    			tv.setText(content[position]);
    			view.setBackgroundColor(Color.WHITE);
    			return view;
    		}
		});
		
		listPopupWindow.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parents, View view,
					int position, long id) {
				popListener.onListItemClickBack(listPopupWindow,parent,position);
			}
		});
		listPopupWindow.show();
		
	}
}

class VPAdapter extends PagerAdapter{
	private FragmentManager fm;
	/**
	 * @param fm 是android.app.FragmentManager;
	 */
	public VPAdapter(FragmentManager fm){
		this.fm=fm;
	}
	
	@Override
	public int getCount() {
		return 2;
	}

	/**
	 * 判断view与Object之间是否关联。就当是view的标识符。
	 */
	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0==((Fragment)arg1).getView();
	}

	/**
	 * 实例化视图。返回的object就是上面的object，就当是唯一标识符。就按网上的返回view。上面直接==也行
	 */
	@Override
	public Object instantiateItem(ViewGroup container, int position) {	
		Fragment fragment=fm.findFragmentByTag(position+"");
		container.addView(fragment.getView());
		return fragment;
	}

	
	/**
	 * 从视图容器中销毁指定视图。
	 */
	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView(((Fragment)object).getView());
	}
	
	
}