package com.pinsheng.more;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.pinsheng.app.HELPActivity;
import com.pinsheng.help.R;
import com.pinsheng.util.Util;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.SocializeEntity;
import com.umeng.socialize.bean.StatusCode;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners.SnsPostListener;
import com.umeng.socialize.sso.UMSsoHandler;

public class ShareActivity extends HELPActivity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_share);
		findViewById(R.id.record_return).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ShareActivity.this.finish();
			}
		});
	}
	
	public void share(View view){
		SHARE_MEDIA model = null;
		switch (view.getId()) {
		case R.id.share_weixinpyq:
			model = SHARE_MEDIA.WEIXIN_CIRCLE;
			break;
		case R.id.share_weixin:
			model = SHARE_MEDIA.WEIXIN;
			break;
		case R.id.share_qq:
			model = SHARE_MEDIA.QQ;
			break;
		case R.id.share_qqzone:
			model = SHARE_MEDIA.QZONE;
			break;
		case R.id.share_sms:
			model = SHARE_MEDIA.SMS;
			break;
		case R.id.share_sina:
			model = SHARE_MEDIA.SINA;
			break;
		case R.id.share_ewm:
		//	startActivity(new Intent(ShareActivity.this, EwmActivity.class));
			break;
		case R.id.share_fzlj:
			Util.copyToClipboard(ShareActivity.this, "云互救");
			 Util.Toast(ShareActivity.this, "已将分享链接复制到剪贴板");
			break;
		}
		ShareManager.getInstence(this).open(this, model, new SnsPostListener() {
			
			@Override
			public void onStart() {
			}
			
			@Override
			public void onComplete(SHARE_MEDIA arg0, int arg1, SocializeEntity arg2) {
				/*String	showText = "分享成功";
		                if (arg1 == StatusCode.ST_CODE_SUCCESSED) {
		                	 Util.Toast(ShareActivity.this, showText);
		                } */
		                	
				}
		});
	}
	
	final UMSocialService mController = UMServiceFactory.getUMSocialService("com.umeng.share");
	@Override 
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    super.onActivityResult(requestCode, resultCode, data);
	    /**使用SSO授权必须添加如下代码 */
	    UMSsoHandler ssoHandler = mController.getConfig().getSsoHandler(requestCode) ;
	    if(ssoHandler != null){
	       ssoHandler.authorizeCallBack(requestCode, resultCode, data);
	    }
	}
}
