package com.example.bookinglessons.Adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Preferences {
    public static void saveCookie(Context context, String cookie) {
        if (cookie == null) {
            return;
        }

        // Save in the preferences
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        if (null == sharedPreferences) {
            return;
        }
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("cookie", cookie);
        editor.commit();
    }

    public static String getCookie(Context context)
    {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        String cookie = sharedPreferences.getString("cookie", "");
        if (cookie.contains("expires")) {
            return "";
        }
        return cookie;
    }
}
