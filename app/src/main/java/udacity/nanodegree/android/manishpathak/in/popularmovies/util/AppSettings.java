package udacity.nanodegree.android.manishpathak.in.popularmovies.util;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Map;

public class AppSettings {

    public static final String PREF_NAME_POPULAR_MOVIES = "popularmovies";
    private static final String PREF_NAME = "prefName";

    // private static AppSettings mSelf;

    // public static Object getPrefernce(Context context,String tag){
    // return getObject(context,tag);
    // }

    public static Object getPrefernce(Context context, String prefName, String tag, Object defaultValue) {
        return getObject(context, prefName, tag, defaultValue);
    }

    // don't use it
    // public static Integer getPrefernceInt(Context context, String prefName, String tag){
    // return (Integer)getObject(context, prefName , tag);
    // }
    public static String getPrefernceString(Context context, String prefName, String tag) {
        return (String) getObject(context, prefName, tag);
    }

    public static void setPreference(Context context, String prefName, String tag, Object value) {
        putString(context, prefName, tag, value);
    }

    public static void setPreference(Context context, String prefName, String tag, Integer value) {
        putString(context, prefName, tag, String.valueOf(value));
    }

    private static Object getObject(Context context, String prefName, String tag) {
        SharedPreferences pref;
        if (StringUtils.notEmpty(prefName) && prefName.equalsIgnoreCase(PREF_NAME_POPULAR_MOVIES)) {
            pref = context.getSharedPreferences(PREF_NAME_POPULAR_MOVIES, 0);
        } else {
            pref = context.getSharedPreferences(PREF_NAME, 0);
        }
        Map<String, ?> map = pref.getAll();
        Object value = map.get(tag);
        if (value instanceof Boolean)
            return pref.getBoolean(tag, false);
        else if (value instanceof Integer)
            return pref.getInt(tag, 0);
        else if (value instanceof Float)
            return pref.getFloat(tag, 0);
        else if (value instanceof Long)
            return pref.getLong(tag, 0);
        else
            return pref.getString(tag, "");
    }

    private static Object getObject(Context context, String prefName, String tag, Object defaultValue) {
        SharedPreferences pref;
        if (StringUtils.notEmpty(prefName) && prefName.equalsIgnoreCase(PREF_NAME_POPULAR_MOVIES)) {
            pref = context.getSharedPreferences(PREF_NAME_POPULAR_MOVIES, 0);
        } else {
            pref = context.getSharedPreferences(PREF_NAME, 0);
        }
        Map<String, ?> map = pref.getAll();
        Object value = map.get(tag);
        if (value instanceof Boolean)
            return pref.getBoolean(tag, ((Boolean) defaultValue).booleanValue());
        else if (value instanceof Integer)
            return pref.getInt(tag, ((Integer) defaultValue).intValue());
        else if (value instanceof Float)
            return pref.getFloat(tag, ((Float) defaultValue).floatValue());
        else if (value instanceof Long)
            return pref.getLong(tag, ((Long) defaultValue).longValue());
        else
            return pref.getString(tag, (String) defaultValue);
    }

    public static void putString(Context context, String prefName, String tag, Object value) {
        SharedPreferences pref;
        if (StringUtils.notEmpty(prefName) && prefName.equalsIgnoreCase(PREF_NAME_POPULAR_MOVIES)) {
            pref = context.getSharedPreferences(PREF_NAME_POPULAR_MOVIES, 0);
        } else {
            pref = context.getSharedPreferences(PREF_NAME, 0);
        }
        SharedPreferences.Editor editor = pref.edit();
        if (value instanceof Boolean)
            editor.putBoolean(tag, (Boolean) value);
        else if (value instanceof Integer)
            editor.putInt(tag, (Integer) value);
        else if (value instanceof Float)
            editor.putFloat(tag, (Float) value);
        else if (value instanceof Long)
            editor.putLong(tag, (Long) value);
        else
            editor.putString(tag, String.valueOf(value));
        editor.commit();
    }
}
