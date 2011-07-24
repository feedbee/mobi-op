package com.feedbee.android.mobiop;

import android.telephony.TelephonyManager;

public class OperatorInfo
{
	protected int code;
	protected String name;
	protected int type;
	protected String countryIso;
	protected int logoResId;
	
	public OperatorInfo(int code, String name, int type, String countryIso,
			int logoResId)
	{
		this.code = code;
		this.name = name;
		this.type = type;
		this.countryIso = countryIso;
		this.logoResId = logoResId;
	}
	
	public String getNetwotkTypeString()
	{
		switch (this.type)
		{
			case TelephonyManager.NETWORK_TYPE_UNKNOWN: return "Unknown";
			case TelephonyManager.NETWORK_TYPE_GPRS: return "GPRS";
			case TelephonyManager.NETWORK_TYPE_EDGE: return "EDGE";
			case TelephonyManager.NETWORK_TYPE_UMTS: return "UMTS";
			case TelephonyManager.NETWORK_TYPE_HSDPA: return "HSDPA";
			case TelephonyManager.NETWORK_TYPE_HSUPA: return "HSUPA";
			case TelephonyManager.NETWORK_TYPE_HSPA: return "HSPA";
			case TelephonyManager.NETWORK_TYPE_CDMA: return "CDMA";
			case TelephonyManager.NETWORK_TYPE_EVDO_0: return "EVDO 0";
			case TelephonyManager.NETWORK_TYPE_EVDO_A: return "EVDO A";
			case TelephonyManager.NETWORK_TYPE_1xRTT: return "1xRTT";
		}
		return "Unknown";
	}
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getCountryIso() {
		return countryIso;
	}
	public void setCountryIso(String countryIso) {
		this.countryIso = countryIso;
	}
	public int getLogoResId() {
		return logoResId;
	}
	public void setLogoResId(int logoResId) {
		this.logoResId = logoResId;
	}
}