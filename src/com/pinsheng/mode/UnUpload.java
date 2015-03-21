package com.pinsheng.mode;

import com.google.gson.Gson;

public class UnUpload {
	private String imei;

	public String getImei() {
		return imei;
	}

	public UnUpload(String imei) {
		super();
		this.imei = imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}
	@Override
	public String toString() {
		return new Gson().toJson(this);
	}
}
