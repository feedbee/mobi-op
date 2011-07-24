package com.feedbee.android.mobiop;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;
import android.util.Log;


public class MobiOpWidget extends AppWidgetProvider
{
	protected MobiOpPhoneStateListener stateListener;
	
	public void onReceive(Context context, Intent intent)
	{
		Log.d("MobiOpDebug", "onReceive");
		super.onReceive(context, intent);
	}
	
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds)
	{
		Log.d("MobiOpDebug", "onUpdate");
		this.updateState(context, appWidgetManager, appWidgetIds);
	}
	
	public void onEnabled(Context context)
	{
		Log.d("MobiOpDebug", "onEnabled");
		this.registerListener(context);
	}
	
	public void onDisabled(Context context)
	{
		Log.d("MobiOpDebug", "onDisabled");
		this.unregisterListener();
	}
	
	public void updateWidgets(Context context, String text)
	{
		Log.d("MobiOpDebug", "updateWidgets");
		ComponentName cn = new ComponentName(context, MobiOpWidget.class);
		this.updateState(context, AppWidgetManager.getInstance(context),
			AppWidgetManager.getInstance(context).getAppWidgetIds(cn));
	}
	
	protected void setClickHandler(Context context, RemoteViews views)
	{
		Log.d("MobiOpDebug", "initialize");
		Intent intent = new Intent("android.intent.action.CALL",
				Uri.parse("tel:*100" + Uri.encode("#")));
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 1, intent, 1);
        
        //views.setOnClickPendingIntent(R.id.text, pendingIntent);
        views.setOnClickPendingIntent(R.id.opLogo, pendingIntent);
	}
	
	protected void updateState(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds)
	{
		Log.d("MobiOpDebug", "updateState");
		
		OperatorInfo opInfo = OperatorService.getNetworkOperatorInfo(context);
		
		final int N = appWidgetIds.length;
		for (int i=0; i<N; i++)
		{
			int appWidgetId = appWidgetIds[i];
			
			RemoteViews views = new RemoteViews("com.feedbee.android.mobiop", R.layout.mobiopw);
			
			views.setTextViewText(R.id.text, opInfo.getName());
			views.setImageViewResource(R.id.opLogo, opInfo.getLogoResId());
			this.setClickHandler(context, views);
			
			appWidgetManager.updateAppWidget(appWidgetId, views);
		}
	}
	
	protected void registerListener(Context context)
	{
		Log.d("MobiOpDebug", "registerListener");
		if (this.stateListener == null)
		{
			this.stateListener = new MobiOpPhoneStateListener(this, context);
		}
	}
	protected void unregisterListener()
	{
		Log.d("MobiOpDebug", "unregisterListener");
		if (this.stateListener != null)
		{
			this.stateListener.unregister();
			this.stateListener = null;
		}
	}
}
