package com.feedbee.android.mobiop;

import java.util.Collections;
import java.util.Map;
import java.util.HashMap;
import android.content.Context;
import android.telephony.TelephonyManager;
import android.util.Log;

public class OperatorService
{
	protected static final String DEBUG_TAG = "MobiOpDebug";
	
	protected static final int OPERATOR_SIM = 1;
	protected static final int OPERATOR_NETWORK = 2;
	
	// http://en.wikipedia.org/wiki/Mobile_Network_Code
	@SuppressWarnings("serial")
	public final static Map<Integer, Integer> OPERATORS = 
		Collections.unmodifiableMap(new HashMap<Integer, Integer>() {{
			put(25701, R.drawable.logo_velcom);
			put(25702, R.drawable.logo_mts);
			put(25703, R.drawable.logo_diallog);
			put(25704, R.drawable.logo_life_by);

			// RU (part)
			put(25001, R.drawable.logo_mts);
			put(25002, R.drawable.logo_megafon);
			put(25017, R.drawable.logo_usi);
			put(25039, R.drawable.logo_usi);
			put(25020, R.drawable.logo_tele2);
			put(25028, R.drawable.logo_beeline_ru);
			put(25099, R.drawable.logo_beeline_ru);

			// UA (part)
			put(25501, R.drawable.logo_mts);
			put(25502, R.drawable.logo_beeline_ua);
			put(25503, R.drawable.logo_kyivstar);
			put(25504, R.drawable.logo_intertelecom);
			put(25505, R.drawable.logo_golden_telecom_ua);
			put(25506, R.drawable.logo_life_ua);
			put(25507, R.drawable.logo_ukrtelecom);
			put(25521, R.drawable.logo_peoplenet);
			put(25523, R.drawable.logo_cdma_ua);

			// KZ (all)
			put(40101, R.drawable.logo_beeline_ua);
			put(40102, R.drawable.logo_kcell);
			put(40107, R.drawable.logo_dalacom);
			put(40108, R.drawable.logo_kazakhtelecom);
			put(40177, R.drawable.logo_neogsm);

			// UZ (all)
			put(43404, R.drawable.logo_beeline_ua);
			put(43405, R.drawable.logo_ucell);
			put(43406, R.drawable.logo_perfectum_mobile);
			put(43407, R.drawable.logo_mts);

			// LT (all)
			put(24601, R.drawable.logo_omnitel);
			put(24602, R.drawable.logo_bite_lt);
			put(24603, R.drawable.logo_tele2);
			put(24606, R.drawable.logo_mediafon);

			// LV (all)
			put(24701, R.drawable.logo_lmt);
			put(24702, R.drawable.logo_tele2);
			put(24703, R.drawable.logo_triatel);
			put(24705, R.drawable.logo_bite_lv);
			put(24706, R.drawable.logo_mastertelecom);
			put(24707, R.drawable.logo_izzi);
			put(24708, R.drawable.logo_camelmobile);

			// EE (all)
			put(24801, R.drawable.logo_emt);
			put(24802, R.drawable.logo_elisa);
			put(24803, R.drawable.logo_tele2);
			put(24804, R.drawable.logo_top_connect);
			put(24805, R.drawable.logo_bravocom);
			put(24806, R.drawable.logo_progroup_holding);

			// AM (all)
			put(28301, R.drawable.logo_beeline_ua);
			put(28305, R.drawable.logo_vivacell_mts);
			put(28310, R.drawable.logo_orange);

			// KG (all)
			put(43701, R.drawable.logo_beeline_ua);
			put(43703, R.drawable.logo_fonex);
			put(43705, R.drawable.logo_megacom);
			put(43709, R.drawable.logo_o);

			// MD (all, 03 code is shared)
			put(25901, R.drawable.logo_orange);
			put(25902, R.drawable.logo_moldcell);
			put(25903, R.drawable.logo_idc);
			put(25905, R.drawable.logo_unite);
	    }});
	
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
		if (OPERATORS.containsKey(operatorCode))
		{
			return OPERATORS.get(operatorCode);
		}
		else
		{
			return R.drawable.logo_unknown;
		}
		
		// http://en.wikipedia.org/wiki/Mobile_Network_Code
		/*switch (operatorCode)
		{
			// BY (all)
			case 25701: return R.drawable.logo_velcom;
			case 25702: return R.drawable.logo_mts;
			case 25703: return R.drawable.logo_diallog;
			case 25704: return R.drawable.logo_life_by;
			
			// RU (part)
			case 25001: return R.drawable.logo_mts;
			case 25002: return R.drawable.logo_megafon;
			case 25017:
			case 25039: return R.drawable.logo_usi;
			case 25020: return R.drawable.logo_tele2;
			case 25028:
			case 25099: return R.drawable.logo_beeline_ru;
			
			// UA (part)
			case 25501: return R.drawable.logo_mts;
			case 25502: return R.drawable.logo_beeline_ua;
			case 25503: return R.drawable.logo_kyivstar;
			case 25504: return R.drawable.logo_intertelecom;
			case 25505: return R.drawable.logo_golden_telecom_ua;
			case 25506: return R.drawable.logo_life_ua;
			case 25507: return R.drawable.logo_ukrtelecom;
			case 25521: return R.drawable.logo_peoplenet;
			case 25523: return R.drawable.logo_cdma_ua;
			
			// KZ (all)
			case 40101: return R.drawable.logo_beeline_ua;
			case 40102: return R.drawable.logo_kcell;
			case 40107: return R.drawable.logo_dalacom;
			case 40108: return R.drawable.logo_kazakhtelecom;
			case 40177: return R.drawable.logo_neogsm;
			
			// UZ (all)
			case 43404: return R.drawable.logo_beeline_ua;
			case 43405: return R.drawable.logo_ucell;
			case 43406: return R.drawable.logo_perfectum_mobile;
			case 43407: return R.drawable.logo_mts;
			
			// LT (all)
			case 24601: return R.drawable.logo_omnitel;
			case 24602: return R.drawable.logo_bite_lt;
			case 24603: return R.drawable.logo_tele2;
			case 24606: return R.drawable.logo_mediafon;
			
			// LV (all)
			case 24701: return R.drawable.logo_lmt;
			case 24702: return R.drawable.logo_tele2;
			case 24703: return R.drawable.logo_triatel;
			case 24705: return R.drawable.logo_bite_lv;
			case 24706: return R.drawable.logo_mastertelecom;
			case 24707: return R.drawable.logo_izzi;
			case 24708: return R.drawable.logo_camelmobile;
			
			// EE (all)
			case 24801: return R.drawable.logo_emt;
			case 24802: return R.drawable.logo_elisa;
			case 24803: return R.drawable.logo_tele2;
			case 24804: return R.drawable.logo_top_connect;
			case 24805: return R.drawable.logo_bravocom;
			case 24806: return R.drawable.logo_progroup_holding;
			
			// AM (all)
			case 28301: return R.drawable.logo_beeline_ua;
			case 28305: return R.drawable.logo_vivacell_mts;
			case 28310: return R.drawable.logo_orange;
			
			// KG (all)
			case 43701: return R.drawable.logo_beeline_ua;
			case 43703: return R.drawable.logo_fonex;
			case 43705: return R.drawable.logo_megacom;
			case 43709: return R.drawable.logo_o;
			
			// MD (all, 03 code is shared)
			case 25901: return R.drawable.logo_orange;
			case 25902: return R.drawable.logo_moldcell;
			case 25903: return R.drawable.logo_idc;
			case 25905: return R.drawable.logo_unite;
		}
		
		return R.drawable.logo_unknown;*/
	}
}
