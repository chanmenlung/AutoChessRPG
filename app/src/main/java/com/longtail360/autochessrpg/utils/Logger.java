package com.longtail360.autochessrpg.utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class Logger {
    public static void log(String tag, String str) {
        Log.v(tag, str);
    }
    public static void toast(String str, Context context){
        Toast toast = Toast.makeText(context,
                str,
                Toast.LENGTH_SHORT);
    }
}
