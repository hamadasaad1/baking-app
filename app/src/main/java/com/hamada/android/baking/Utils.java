package com.hamada.android.baking;

import android.annotation.SuppressLint;

public final class Utils {

    private Utils(){

    }
    // preferences
    public static final String PREFERENCES_ID = "ID";
    public static final String PREFERENCES_WIDGET_TITLE = "WIDGET_TITLE";
    public static final String PREFERENCES_WIDGET_CONTENT = "WIDGET_CONTENT";

    @SuppressLint("DefaultLocale")
    public static String fmt(double d) {
        if(d == (long) d)
            return String.format("%d",(long)d);
        else
            return String.format("%s",d);
    }
}
