package com.feedbee.android.mobiop;

import android.content.Context;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

public class MobiOpPhoneStateListener extends PhoneStateListener
{
	protected static final String DEBUG_TAG = "MobiOpDebug";
	
	Context context;
	MobiOpWidget wd;
	
	MobiOpPhoneStateListener(MobiOpWidget wd, Context context)
	{
		super();
		this.wd = wd;
		this.context = context;
		
		this.register();
	}
	
	public void register()
	{
		Log.d(DEBUG_TAG, "MobiOpPhoneStateListener::register");
		TelephonyManager tm =
			(TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE); 
		tm.listen(this, PhoneStateListener.LISTEN_SERVICE_STATE);
	}
	
	public void unregister()
	{
		Log.d(DEBUG_TAG, "MobiOpPhoneStateListener::unregister");
		TelephonyManager tm =
			(TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE); 
		tm.listen(this, PhoneStateListener.LISTEN_NONE);
	}
	
	public void onServiceStateChanged(android.telephony.ServiceState serviceState)
	{
		/*
		 * STATE_EMERGENCY_ONLY
		 * STATE_IN_SERVICE
		 * STATE_OUT_OF_SERVICE
		 * STATE_POWER_OFF
		 */
		this.wd.updateWidgets(this.context, null); //serviceState.toString()
	}
}
