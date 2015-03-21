package com.pinsheng.mode;

import android.util.Log;

import com.google.gson.Gson;

public class Params {
	private String imei;

	public Params() {
		
	}
	
	public Params(String imei) {
		this.imei = imei;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	@Override
	public String toString() {
		Log.i("cao", "params" + new Gson().toJson(this));
		return new Gson().toJson(this);
	}
}
