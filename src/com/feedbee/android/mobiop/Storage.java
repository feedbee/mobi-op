package com.feedbee.android.mobiop;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import android.content.Context;
import android.content.SharedPreferences;

public class Storage
{
	public static final String PREFS_NAME
			= "com.feedbee.android.mobiop.widget";
	public static final int FORMAT_VERSION
			= 1;
	
	private Storage(){}
	
	// Write the config to the SharedPreferences object for this widget
	public static void saveWidgetConfig(Context context, int appWidgetId, WidgetConfig widgetConfig)
	{
		// Serialize
		String config;
		try
		{
			config = toString(widgetConfig);
		} catch (IOException e)
		{
			return;
		}
		
		SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_NAME, 0).edit();
		prefs.putInt("MobiOp_Widget_Version_" + appWidgetId, FORMAT_VERSION);
		prefs.putString("MobiOp_Widget_Config_" + appWidgetId, config);
		prefs.commit();
	}
	
	// Read the config from the SharedPreferences object for this widget.
	// If there is no preference saved, return null
	public static WidgetConfig loadWidgetConfig(Context context, int appWidgetId)
	{
		SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
		int version = prefs.getInt("MobiOp_Widget_Version_" + appWidgetId, 0);
		if (version != FORMAT_VERSION)
		{
			return null;
		}
		
		String data = prefs.getString("MobiOp_Widget_Config_" + appWidgetId, null);
		if (data == null || data == "")
		{
			return null;
		}
		
		// Deserialize
		WidgetConfig wc = null;
		try
		{
			wc = (WidgetConfig)fromString(data);
		}
		catch (Exception e)
		{
			return null;
		}
		
		return wc;
	}
	
	// http://stackoverflow.com/questions/134492/how-to-serialize-an-object-into-a-string
	
    /** Read the object from Base64 string. */
	private static Object fromString(String s)
		throws IOException,	ClassNotFoundException
	{
		byte [] data = Base64Coder.decode(s);
		ObjectInputStream ois = new ObjectInputStream( 
				new ByteArrayInputStream(data));
		Object o  = ois.readObject();
		ois.close();
		
		return o;
	}
	/** Write the object to a Base64 string. */
	private static String toString(Serializable o) throws IOException
	{
	    ByteArrayOutputStream baos = new ByteArrayOutputStream();
	    ObjectOutputStream oos = new ObjectOutputStream(baos);
	    oos.writeObject(o);
	    oos.close();
	    
	    return new String(Base64Coder.encode(baos.toByteArray()));
	}
}
