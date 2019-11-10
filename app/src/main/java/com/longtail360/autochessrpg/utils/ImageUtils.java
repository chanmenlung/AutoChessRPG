package com.longtail360.autochessrpg.utils;

import android.content.Context;

public class ImageUtils {

    static public int convertImageStringToInt(Context context, String s){
        int headResourceId = context.getResources().getIdentifier(s, "drawable",
                context.getPackageName());
        return headResourceId;
    }
}
