package com.feedbee.android.mobiop;

import java.util.regex.Pattern;

import android.app.Activity;
import android.os.Bundle;
import android.text.util.Linkify;
import android.widget.TextView;

public class MobiOp extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        TextView appFooter = (TextView)this.findViewById(R.id.app_footer);
        Pattern pattern = Pattern.compile("http://valera.ws/mobiop/");
        Linkify.addLinks(appFooter, pattern, "http://");
        pattern = Pattern.compile("feedbee@gmail.com");
        Linkify.addLinks(appFooter, pattern, "mailto:");
    }
}