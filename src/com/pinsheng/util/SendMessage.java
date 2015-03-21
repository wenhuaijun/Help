package com.pinsheng.util;

import java.util.List;

import com.google.gson.Gson;
import com.pinsheng.app.HELPApllication;
import com.pinsheng.mode.Person;
import com.pinsheng.mode.SendMsgHistory;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;
  
/** 
 * Created by dyb on 13-10-25. 
 */  
public class SendMessage {  
    private String SENT_SMS_ACTION = "SENT_SMS_ACTION";  
    private Context context;  
    private Intent sentIntent = new Intent(SENT_SMS_ACTION);  
    private boolean toastSendSuccess=true;
    private PendingIntent sentPI;  
    private String DELIVERED_SMS_ACTION = "DELIVERED_SMS_ACTION";  
    private Intent deliverIntent = new Intent(DELIVERED_SMS_ACTION);  
    private PendingIntent deliverPI;  
    private List<SendMsgHistory> sendHistorys;
    private SendMsgHistory sendHistory;
    private HELPApllication application;
    /** 
     * 构造函数 
     * @param c 
     */  
    public SendMessage(Context c,HELPApllication application){  
        this.context = c;  
        this.application =application;
        this.sendHistorys =application.getSendHistory();
        sentPI = PendingIntent.getBroadcast(context, 0, sentIntent, 0);  
  
  
        //短信发送状态监控  
        context.registerReceiver(new BroadcastReceiver(){  
            @Override  
            public void onReceive(Context context, Intent intent) {  
                switch(getResultCode()){  
                    case Activity.RESULT_OK:  
                        Log.i("response","信息已发出");  
                        updateStatus();  
                        break;  
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:  
                        Toast.makeText(context, "未指定失败 \n 信息未发出，请重试", Toast.LENGTH_LONG).show();  
                        break;  
                    case SmsManager.RESULT_ERROR_RADIO_OFF:  
                        Toast.makeText(context, "无线连接关闭 \n 信息未发出，请重试", Toast.LENGTH_LONG).show();  
                        break;  
                    case SmsManager.RESULT_ERROR_NULL_PDU:  
                        Toast.makeText(context, "PDU失败 \n 信息未发出，请重试", Toast.LENGTH_LONG).show();  
                        break;  
                }  
  
            }  
        }, new IntentFilter(SENT_SMS_ACTION));  
  
        //短信是否被接收状态监控  
        deliverPI = PendingIntent.getBroadcast(context, 0, deliverIntent, 0);  
        context.registerReceiver(new BroadcastReceiver(){  
  
            @Override  
            public void onReceive(Context context, Intent intent) {  
                // TODO Auto-generated method stub  
                //myDialog.setMessage("已送达服务终端");  
            	Log.i("response", "已送达服务终端");
                //checkService();  
            }  
        }, new IntentFilter(DELIVERED_SMS_ACTION));  
  
  
    }  
  
    /** 
     * 发送短信，这里是我需要的几个参数，你可以根据你的具体情况来使用不同的参数 
     * @param qid 
     * @param mobile 要发送的目标手机号，这个必须要有 
     * @param code 
     * @param msg 发送的短信内容 
     */  
    public void send(Context ctx, List<Person> persons, String location,String imei){  
    	for (Person person : persons) {
			if (person.isMessage() && !person.getMessage_content().isEmpty()) {
				
				SmsManager.getDefault().sendTextMessage(person.getTel(), null,
						person.getMessage_content() +"\n"+"(" +"当前位置: "+ location +"."+"IMEI码: "+imei+"下载云互救app可通过IMEI码查看我求救后的运动轨迹)",sentPI,deliverPI);
				addMsgHistory(location, imei, person);
			}
		}
    }

	private void addMsgHistory(String location, String imei, Person person) {
		sendHistory = new SendMsgHistory();
		sendHistory.setName(person.getName());
		sendHistory.setContent(person.getMessage_content() +"\n"+"(" +"当前位置: "+ location +"."+"IMEI码: "+imei+"下载云互救app可通过IMEI码查看我求救后的运动轨迹)");
		sendHistory.setData(Util.getCurrentTime());
		sendHistorys.add(sendHistory);
		application.saveSendHistory();
		Log.i("response", new Gson().toJson(application.getSendHistory()));
		
		
	}  
  
    private void updateStatus() {  
        //短信发送成功后做什么事情，就自己定吧  
    	if(toastSendSuccess){
    		Util.Toast(context, "求救短信发送成功！");
    		toastSendSuccess=false;
    	}
    	
    }  
  
  
}  