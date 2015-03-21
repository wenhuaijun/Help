package com.pinsheng.mode;

import com.google.gson.Gson;

public class Position {
	private double jingdu;
	private double weidu;
	private String imei;
	private String address;
	
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Position(double jingdu, double weidu, String imei,String address) {
		super();
		this.jingdu = jingdu;
		this.weidu = weidu;
		this.imei = imei;
		this.address=address;
	}

	public double getJingdu() {
		return jingdu;
	}

	public void setJingdu(double jingdu) {
		this.jingdu = jingdu;
	}

	public double getWeidu() {
		return weidu;
	}

	public void setWeidu(double weidu) {
		this.weidu = weidu;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	@Override
	public String toString() {
		return new Gson().toJson(this);
	}
}
