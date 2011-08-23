package com.feedbee.android.mobiop;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.RadioGroup;
import android.widget.TextView;

public class MobiOpWidgetConfigure extends Activity
	implements OnClickListener
{
	public static final String DEBUG_TAG = "MobiOpWidgetConfigure";
	
	public static final int WIDGET_INFO_TYPE_SIM = 0;
	public static final int WIDGET_INFO_TYPE_NETWORK = 1;
	
	int appWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
	
	public MobiOpWidgetConfigure()
	{
		super();
		Log.d(DEBUG_TAG, "Conf: constructor");
	}
	
	@Override
	public void onCreate(Bundle icicle)
	{
		Log.d(DEBUG_TAG, "Conf: onCreate");
		super.onCreate(icicle);
		
		// Set the result to CANCELED.  This will cause the widget host to cancel
		// out of the widget placement if they press the back button.
		this.setResult(RESULT_CANCELED);
		
		// Set the view layout resource to use.
		this.setContentView(R.layout.mobiopwconf);
		
		// Bind the action for the save button.
		this.findViewById(R.id.cfgBtnApply).setOnClickListener(this);
		this.findViewById(R.id.cfgBtnQuit).setOnClickListener(this);
		//((RadioGroup)this.findViewById(R.id.wc_phone_list)).setOnCheckedChangeListener(this);
		
		// Find the widget id from the intent. 
		Intent intent = getIntent();
		Bundle extras = intent.getExtras();
		if (extras != null)
		{
			this.appWidgetId = extras.getInt(
					AppWidgetManager.EXTRA_APPWIDGET_ID,
					AppWidgetManager.INVALID_APPWIDGET_ID);
		}
		
		// If they gave us an intent without the widget id, just bail.
		if (this.appWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID)
		{
			finish();
		}
		
		// Initialization
		/*WidgetConfig wc = Storage.loadWidgetConfig(WidgetConfigure.this, this.appWidgetId);
		if (wc != null)
		{
			this.loadContact(wc.getContactUri());
		}*/
	}
	
	public void onClick(View v)
	{
		switch (v.getId())
		{
			case R.id.cfgBtnApply:
				this.onApplyClick();
				return;
			case R.id.cfgBtnQuit:
				this.finish();
				return;
		}
	}
	
	/*public void onCheckedChanged(RadioGroup group, int checkedId)
	{
		Log.d(AppActivity.DEBUG_TAG, "Conf:onCheckedChanged");
		if (checkedId != View.NO_ID)
		{
			this.findViewById(R.id.wc_btn_apply).setEnabled(true);
		}
		else
		{
			this.findViewById(R.id.wc_btn_apply).setEnabled(false);
		}
	}*/
	
	protected void onApplyClick()
	{
		Log.d(DEBUG_TAG, "Conf: onApplyClick");
		// When the button is clicked, save the string in our prefs and return that they
		// clicked OK.
		
		// Save
		final Context context = MobiOpWidgetConfigure.this;
		int id = ((RadioGroup)this.findViewById(R.id.cfgOperatorType))
				.getCheckedRadioButtonId();
		WidgetConfig.OPERATOR operator = id == R.id.cfgOperatorTypeNw
			? WidgetConfig.OPERATOR.NETWORK
			: WidgetConfig.OPERATOR.SIM;
		boolean showNwTitle = ((CheckBox)this.findViewById(R.id.cfgShowTitle)).isChecked();
		String customTitle = ((TextView)this.findViewById(R.id.cfgNwTitle)).getText().toString();
		String nwCustomCode = ((TextView)this.findViewById(R.id.cfgNwCode)).getText().toString();
		int nwCodeInt = 0;
		if (!nwCustomCode.equals(""))
		{
			try {
				nwCodeInt = Integer.parseInt(nwCustomCode);
			}
			catch (NumberFormatException e){}
			if (!OperatorService.OPERATORS.containsKey(nwCodeInt))
			{
				nwCodeInt = 0;
			}
		}
		
		Storage.saveWidgetConfig(context, this.appWidgetId,
				new WidgetConfig(showNwTitle, customTitle.equals("") ? null : customTitle,
						nwCodeInt == 0 ? null : nwCodeInt, null, operator));
		
		// Push widget update
		AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
		MobiOpWidget.updateWidget(context, appWidgetManager, this.appWidgetId);
		
		// Exit: Make sure we pass back the original appWidgetId
		Intent resultValue = new Intent();
		resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
		this.setResult(RESULT_OK, resultValue);
		this.finish();
	}
}
