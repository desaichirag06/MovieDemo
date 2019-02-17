package com.chirag.valarassignment.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.chirag.valarassignment.R;

public class SessionManager {

    public static final String IS_LOGIN = "is_login";
    private static final String KEY_LOGIN_DATA = "login_data";
    private static final String KEY_PREF = "KEY_PREF";
    private SecurePreferences pref;

    public SessionManager(Context context) {
        String PREF_NAME = context.getResources().getString(R.string.app_name);
        pref = SecurePreferences.getInstance(context, PREF_NAME);
    }

    // region Helper Methods
    private static SharedPreferences.Editor getEditor(Context context) {
        SharedPreferences preferences = getSharedPreferences(context);
        return preferences.edit();
    }

    private static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(KEY_PREF, Context.MODE_PRIVATE);
    }

    public String getDataByKey(String Key) {
        return getDataByKey(Key, "");
    }

    public String getDataByKey(String Key, String DefaultValue) {
        String returnValue;
        if (pref.containsKey(Key)) {
            returnValue = pref.getString(Key, DefaultValue);
        } else {
            returnValue = DefaultValue;
        }
        return returnValue;
    }

    public Boolean getDataByKey(String Key, boolean DefaultValue) {
        if (pref.containsKey(Key)) {
            return pref.getBoolean(Key, DefaultValue);
        } else {
            return DefaultValue;
        }
    }

    public int getDataByKey(String Key, int DefaultValue) {
        if (pref.containsKey(Key)) {
            return pref.getInt(Key, DefaultValue);
        } else {
            return DefaultValue;
        }
    }

    public void storeDataByKey(String key, int Value) {
        pref.putInt(key, Value);
        pref.commit();
    }

    public void storeDataByKey(String key, String Value) {
        pref.putString(key, Value);
        pref.commit();
    }

    public void storeDataByKey(String key, boolean Value) {
        pref.putBoolean(key, Value);
        pref.commit();
    }

    public void clearDataByKey(String key) {
        pref.removeValue(key);
    }

    // Reset Challenge
    public void logoutUser(Context context) {
        storeDataByKey(SessionManager.IS_LOGIN, false);
//        storeDataByKey(SessionManager.KEY_USER_MODEL, null);
        SharedPreferences.Editor editor = getEditor(context);
        editor.remove(KEY_LOGIN_DATA)
                .apply();
        pref.clear();
    }
}
