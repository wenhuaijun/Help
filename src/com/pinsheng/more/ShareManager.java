package com.pinsheng.more;

import java.io.File;

import android.app.Activity;
import android.content.Context;

import com.pinsheng.help.R;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners.SnsPostListener;
import com.umeng.socialize.media.BaseShareContent;
import com.umeng.socialize.media.QQShareContent;
import com.umeng.socialize.media.QZoneShareContent;
import com.umeng.socialize.media.SinaShareContent;
import com.umeng.socialize.media.SmsShareContent;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.sso.QZoneSsoHandler;
import com.umeng.socialize.sso.SinaSsoHandler;
import com.umeng.socialize.sso.SmsHandler;
import com.umeng.socialize.sso.UMQQSsoHandler;
import com.umeng.socialize.weixin.controller.UMWXHandler;
import com.umeng.socialize.weixin.media.CircleShareContent;
import com.umeng.socialize.weixin.media.WeiXinShareContent;

public class ShareManager {
	private static final UMSocialService mController = UMServiceFactory.getUMSocialService("com.umeng.share");
	private static ShareManager Instence = null;

	public static final String title = "云互救";
	public static final String content = "求救软件";
	public static final String url = "http://www.baidu.com";

	public static final ShareManager getInstence(Activity ctx){
		if(Instence == null){
			Instence = new ShareManager();
			Instence.configPlatforms(ctx); 
			Instence.initShareContent(ctx);
		}
		return Instence;
	}

	public void open(Activity ctx ,SHARE_MEDIA model,SnsPostListener listener){
		mController.postShare(ctx,model, listener);
	}

	/**
	 * 配置分享平台参数</br>
	 */
	private void configPlatforms(Activity ctx) {
		// 添加新浪SSO授权
		mController.getConfig().setSsoHandler(new SinaSsoHandler());
		// 添加人人网SSO授权
		mController.getConfig().closeToast();
		// 添加QQ、QZone平台
		addQQQZonePlatform(ctx);
		// 添加微信、微信朋友圈平台
		addWXPlatform(ctx);
		addSMS(ctx);
	}


	/**
	 * @功能描述 : 添加QQ平台支持 QQ分享的内容， 包含四种类型， 即单纯的文字、图片、音乐、视频. 参数说明 : title, summary,
	 *       image url中必须至少设置一个, targetUrl必须设置,网页地址必须以"http://"开头 . title :
	 *       要分享标题 summary : 要分享的文字概述 image url : 图片地址 [以上三个参数至少填写一个] targetUrl
	 *       : 用户点击该分享时跳转到的目标地址 [必填] ( 若不填写则默认设置为友盟主页 )
	 * @return
	 */
	private void addQQQZonePlatform(Activity ctx) {
		String appId = "100424468";
		String appKey = "c7394704798a158208a74ab60104f0ba";
		// 添加QQ支持, 并且设置QQ分享内容的target url
		UMQQSsoHandler qqSsoHandler = new UMQQSsoHandler(ctx,
				appId, appKey);
		//qqSsoHandler.setTargetUrl("http://www.umeng.com/social");
		qqSsoHandler.addToSocialSDK();

		// 添加QZone平台
		QZoneSsoHandler qZoneSsoHandler = new QZoneSsoHandler(ctx, appId, appKey);
		qZoneSsoHandler.addToSocialSDK();
	}


	/**
	 * @功能描述 : 添加微信平台分享
	 * @return
	 */
	private void addWXPlatform(Activity ctx) {
		// 注意：在微信授权的时候，必须传递appSecret
		// wx967daebe835fbeac是你在微信开发平台注册应用的AppID, 这里需要替换成你注册的AppID
		String appId2 = "wx7d204fe8509fd561";
		String appSecret2 = "7ea288826a2b6609659cb1b730c1b243";
		// 添加微信平台
		UMWXHandler wxHandler = new UMWXHandler(ctx, appId2, appSecret2);
		wxHandler.addToSocialSDK();
		// 支持微信朋友圈
		UMWXHandler wxCircleHandler = new UMWXHandler(ctx, appId2, appSecret2);
		wxCircleHandler.setToCircle(true);
		wxCircleHandler.addToSocialSDK();
	}

	/**
	 * 添加短信平台</br>
	 */
	private void addSMS(Activity ctx) {
		// 添加短信
		SmsHandler smsHandler = new SmsHandler();
		smsHandler.addToSocialSDK();
	}


	private void initShareContent(Activity ctx){
		setContent(ctx, new CircleShareContent());
		setContent(ctx, new WeiXinShareContent());
		
		setContent(ctx, new QQShareContent());
		setContent(ctx, new QZoneShareContent());
		setContent(ctx, new SinaShareContent());
		WeiXinShareContent wx =new WeiXinShareContent();
		wx.setShareContent(content);
		wx.setTitle(title);
		wx.setShareImage(new UMImage(ctx, R.drawable.ic_launcher));
		wx.setTargetUrl("http://www.baidu.com");
		mController.setShareMedia(wx);
		CircleShareContent pyq =new CircleShareContent();
		pyq.setShareContent(content);
		pyq.setTitle("赚钱应用—酷赚(邀请码: ");
		pyq.setShareImage(new UMImage(ctx, R.drawable.ic_launcher));
		pyq.setTargetUrl("http://www.baidu.com");
		mController.setShareMedia(pyq);
		// 设置短信分享内容
		SmsShareContent sms = new SmsShareContent();
		sms.setShareContent(content);
		sms.setShareImage(new UMImage(ctx, R.drawable.ic_launcher));
		mController.setShareMedia(sms);

	}

	private void setContent(Context ctx,BaseShareContent sharecontent){
		sharecontent.setShareContent(content);
		sharecontent.setTitle(title);
		sharecontent.setShareImage(new UMImage(ctx, R.drawable.ic_launcher));
		sharecontent.setTargetUrl(url);
		mController.setShareMedia(sharecontent);
	}
}
