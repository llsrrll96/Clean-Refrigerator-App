package com.example.emptytherefrigerator.login;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.Nullable;

import java.util.Map;
import java.util.Set;

public class UserInfo           //자동 로그인 밑 앱 내에서 사용자의 정보를 저장하기 위해 sharedpreference 활용
{
    public static final String PREFERENCES_NAME = "come.example.emptytherefrigerator.userInfo";

    private static final String DEFAULT_VALUE_STRING = "";
    public static final String ID_KEY = "USER_ID";
    public static final String PW_KEY = "USER_PW";

    private static SharedPreferences getPreferences(Context context)
    {
        return context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    public static void setString(Context context, String key, String value)
    {
        SharedPreferences prefs = getPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.apply();
    }
    public static String getString(Context context, String key)
    {
        SharedPreferences prefs = getPreferences(context);
        String value = prefs.getString(key,DEFAULT_VALUE_STRING);
        return value;
    }
    public static void removeKey(Context context, String key)
    {
        SharedPreferences prefs = getPreferences(context);
        SharedPreferences.Editor edit = prefs.edit();
        edit.remove(key);
        edit.apply();
    }
    public static void clear(Context context)
    {
        SharedPreferences prefs = getPreferences(context);
        SharedPreferences.Editor edit = prefs.edit();
        edit.clear();
        edit.apply();
    }
}
