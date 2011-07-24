package com.feedbee.android.mobiop;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.util.Log;

public class OperatorService
{
	protected static final String DEBUG_TAG = "MobiOpDebug";
	
	protected static final int OPERATOR_SIM = 1;
	protected static final int OPERATOR_NETWORK = 2;
	
	private OperatorService() throws Exception
	{
		throw new Exception("Method is not implemented");
	}
	
	public static OperatorInfo getNetworkOperatorInfo(Context context)
	{
		return getOperatorInfo(OPERATOR_NETWORK, context);
	}
	
	public static OperatorInfo getSimOperatorInfo(Context context)
	{
		return getOperatorInfo(OPERATOR_SIM, context);
	}
	
	public static OperatorInfo getOperatorInfo(int source, Context context)
	{
		TelephonyManager tm = 
			(TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
		
		// Name
		String opName = (source == OPERATOR_SIM ? tm.getSimOperatorName() : tm.getNetworkOperatorName());
		if (opName == null || opName.equals(""))
		{
			opName = "Operator unknown";
		}
		Log.d(DEBUG_TAG, "opName = [" + opName + "]");
		
		// Code
		String opCode = (source == OPERATOR_SIM ? tm.getSimOperator() : tm.getNetworkOperator());
		int iOpCode = 0;
		try
		{
			iOpCode = Integer.parseInt(opCode);
		}
		catch (NumberFormatException e)
		{}
		
		// Logo
		int opLogo = getLogoByCode(iOpCode);
		
		// Country ISO
		String opCountry = (source == OPERATOR_SIM ? tm.getSimCountryIso() : tm.getNetworkCountryIso());
		
		// Network type
		int opType = 0;
		try
		{
			opType = tm.getNetworkType();
		}
		catch (Exception e)
		{}
		
		return new OperatorInfo(iOpCode, opName, opType, opCountry, opLogo);
	}
	
	protected static int getLogoByCode(int operatorCode)
	{
		// http://en.wikipedia.org/wiki/Mobile_Network_Code
		switch (operatorCode)
		{
			// BY (all)
			case 25701: return R.drawable.logo_velcom;
			case 25702: return R.drawable.logo_mts;
			case 25703: return R.drawable.logo_dialog;
			case 25704: return R.drawable.logo_life;
			// RU (part)
			case 25001: return R.drawable.logo_mts;
			case 25002: return R.drawable.logo_megafon;
			case 25017:
			case 25039: return R.drawable.logo_usi;
			case 25020: return R.drawable.logo_tele2_rus;
			case 25028:
			case 25099: return R.drawable.logo_beeline;
			// UA (part)
			case 25501: return R.drawable.logo_mts;
			case 25502: return R.drawable.logo_beeline;
			case 25503: return R.drawable.logo_kyivstar;
			case 25504: return R.drawable.logo_intertelecom;
			case 25505: return R.drawable.logo_golden_telecom_ua;
			case 25506: return R.drawable.logo_life;
			case 25507: return R.drawable.logo_ukrtelecom;
			case 25521: return R.drawable.logo_peoplenet;
			case 25523: return R.drawable.logo_cdma_ua;
			// KZ (all)
			case 40101: return R.drawable.logo_beeline;
			case 40102: return R.drawable.logo_kcell;
			case 40107: return R.drawable.logo_dalacom;
			case 40108: return R.drawable.logo_kazakhtelecom;
			case 40177: return R.drawable.logo_neogsm;
			// UZ (all)
			case 43404: return R.drawable.logo_beeline;
			case 43405: return R.drawable.logo_ucell;
			case 43406: return R.drawable.logo_perfectum_mobile;
			case 43407: return R.drawable.logo_mts;
		}
		
		return R.drawable.logo_unknown;
	}
}