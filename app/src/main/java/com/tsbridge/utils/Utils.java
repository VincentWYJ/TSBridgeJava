package com.tsbridge.utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class Utils {
    public final static String LOG_TAG = "TSBridge";

    public final static String BULLETIN_IMAGE_KEY = "bulletinImage";
    public final static String BULLETIN_NAME_KEY = "bulletinName";
    public final static String BULLETIN_TIME_KEY = "bulletinTime";
    public final static String BULLETIN_CONTENT_KEY = "bulletinContent";

    public static void ShowToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void ShowLog(String message) {
        Log.i(LOG_TAG, String.valueOf(message));
    }
}
