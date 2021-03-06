package com.brownstone.agelessgrace;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.Set;

public class SharedPref {
    private static SharedPreferences mSharedPref;

    // initialize

    private SharedPref() {

    }

    public static void init(Context context) {
        if(mSharedPref == null)
            mSharedPref = context.getSharedPreferences(context.getPackageName(), Activity.MODE_PRIVATE);
    }

    // check for key

    public static Boolean keyExists(String key) {
        return mSharedPref.contains(key);
    }

    // general read/wite

    // read/write/remove string

    public static String read(String key, String defValue) {
        return mSharedPref.getString(key, defValue);
    }

    public static void write(String key, String value) {
        SharedPreferences.Editor prefsEditor = mSharedPref.edit();
        prefsEditor.putString(key, value).apply();
    }

    public static void remove(String key) {
        SharedPreferences.Editor prefsEditor = mSharedPref.edit();
        prefsEditor.remove(key).apply();
    }

    // read/write boolean

    public static boolean read(String key, boolean defValue) {
        return mSharedPref.getBoolean(key, defValue);
    }

    public static void write(String key, boolean value) {
        SharedPreferences.Editor prefsEditor = mSharedPref.edit();
        prefsEditor.putBoolean(key, value).apply();
    }

    // read/write String set

    public static Set<String> readSet(String key, Set<String> defValue) {
        return mSharedPref.getStringSet(key,defValue);
    }

    public static void writeSet(String key, Set<String> set) {
        SharedPreferences.Editor prefsEditor = mSharedPref.edit();
        prefsEditor.putStringSet(key, set);
        prefsEditor.commit();
    }
    // read/write long

    public static void write(String key, long value) {
        SharedPreferences.Editor prefsEditor = mSharedPref.edit();
        prefsEditor.putLong(key, value).apply();
        prefsEditor.commit();
    }

    public static long read(String key, long defValue) {
        return mSharedPref.getLong(key,defValue);
    }

    // read/write int

    public static Integer read(String key, int defValue) {
        return mSharedPref.getInt(key, defValue);
    }

    public static void write(String key, Integer value) {
        SharedPreferences.Editor prefsEditor = mSharedPref.edit();
        prefsEditor.putInt(key, value).apply();
        prefsEditor.commit();
    }

    // Cpmpleted tool sets and ids

    public static void saveLastCompletedToolIds(ArrayList<Integer> toolIds) {
        SharedPreferences.Editor prefsEditor = mSharedPref.edit();
        StringBuilder sb = new StringBuilder();
        String delim = "";

        for (int i = 0; i < 3; i++) {
            String s = String.valueOf(toolIds.get(i));
            sb.append(delim);
            sb.append(s);
            delim = ",";
        }
        prefsEditor.putString(Constants.LAST_COMPLETED_TOOL_IDS, sb.toString()).apply();
        prefsEditor.commit();
    }

    public static void addToCompletedToolSets(String set) {
        write(Constants.LAST_COMPLETED_TOOL_SET, set);
        String sets = read(Constants.COMPLETED_TOOL_SETS,"");
        if (sets.contains(",")) {
            sets += " " + set;
        } else {
            sets += set;
        }
        write(Constants.COMPLETED_TOOL_SETS, sets);
    }

    public static void getLastCompletedToolSet() {
        ArrayList<Integer> lastSetIds = getLastCompletedToolIds();
    }


    public static ArrayList<Integer> getLastCompletedToolIds() {
        String ids = read(Constants.LAST_COMPLETED_TOOL_IDS,"");
        if (ids.equals("")) {
            return null;
        }
        String[] idsStr = ids.split(",");
        ArrayList<Integer> result = new ArrayList<>();
        for(int i=0; i<3; i++) {
            result.add(i,Integer.parseInt(idsStr[i]));
        }
        return result;
    }


    public static ArrayList<Integer> getCompletedToolIds() {
        String ids = read(Constants.COMPLETED_TOOL_IDS,"");
        if (ids.equals("")) {
            return null;
        }
        String[] idsStr = ids.split(",");
        ArrayList<Integer> arr = new ArrayList<>();
        for(int i=0; i<idsStr.length; i++) {
            int thisId = Integer.parseInt(idsStr[i]);
            if (!arr.contains(thisId)) {
                arr.add(i, thisId);
            }
        }
        return arr;
    }

    public static void saveToCompletedToolIds(String key, ArrayList<Integer> value) {
        SharedPreferences.Editor prefsEditor = mSharedPref.edit();
        ArrayList<Integer> currentValue = getCompletedToolIds();
        if (currentValue == null) {
            currentValue = new ArrayList<>();
        }
        currentValue.addAll(value);

        StringBuilder sb = new StringBuilder();
        String delim = "";

        for (int i = 0; i < currentValue.size(); i++) {
            String s = String.valueOf(currentValue.get(i));
//            if ((sb.length() == 0) || (sb.indexOf(s) == -1))   {
            if (sb.length() > 0) {
                sb.append(delim);
            }
            sb.append(s);
            delim = ",";
        }
        prefsEditor.putString(Constants.COMPLETED_TOOL_IDS, sb.toString()).apply();
        prefsEditor.commit();
    }

    // read/write selected tool sets

    public static void addSelectedToolSets(String set) {
        write(Constants.SELECTED_TOOL_SETS, set);
    }

    public static ArrayList<String> getAllSelectedToolSets() {
        String set = read(Constants.SELECTED_TOOL_SETS,"");
        String[] setStr = set.split(" ");
        ArrayList<String> result = new ArrayList<>();
        for(int i=0; i<setStr.length; i++) {
            result.add(i,setStr[i]);
        }
        return result;
    }

    public static ArrayList<String> getSelectedToolIds() {
        String ids = read(Constants.SELECTED_TOOL_IDS,"");
        if (ids.equals("")) {
            return new ArrayList<>();
        }
        String[] idsStr = ids.split(",");
        ArrayList<String> arr = new ArrayList<>();
        for(int i=0; i<idsStr.length; i++) {
            arr.add(i,idsStr[i]);
        }
        return arr;
    }

    public static void saveToSelectedToolIds(String key, ArrayList<String> value) {
        SharedPreferences.Editor prefsEditor = mSharedPref.edit();
        ArrayList<String> currentValue;

        if (value.size() == 21) {
            currentValue = value;
        } else {
            currentValue = getSelectedToolIds();
            if (currentValue.size() < 21) {
                if (currentValue == null) {
                    currentValue = new ArrayList<>();
                }
                currentValue.addAll(value);
            } else {
                return;
            }
        }
        StringBuilder sb = new StringBuilder();
        String delim = "";
        for (int i = 0; i < currentValue.size(); i++) {
            String s = currentValue.get(i);
            sb.append(delim);
            sb.append(s);
            delim = ",";
        }
        prefsEditor.putString(Constants.SELECTED_TOOL_IDS, sb.toString()).apply();
        prefsEditor.commit();
    }

    public static ArrayList<String> getAllSelectedSets() {
        // this gets  string of sets each separated by a space
        // example "1,2,3 0,4,5 6,7,9 8,10,11"
        // and creates an array of Strings ["1,2,3", "0,4,5", "6,7,9", "8,10,11"]
        ArrayList<String> arr = new ArrayList<>();

        if (keyExists(Constants.SELECTED_TOOL_SETS)) {
            String ids = read(Constants.SELECTED_TOOL_SETS,"");
//            if (ids.length() < 21) {
                String[] idsStr = ids.split(" ");
                for (String s : idsStr) {
                    arr.add(s);
                }
//            }
        }
        return arr;
    }

    public static void addASelectedSet(String set) {
        String currentSet = read(Constants.SELECTED_TOOL_SETS,"");
        currentSet += " " + set;
        write(Constants.SELECTED_TOOL_SETS,currentSet);
    }

    // clear all the preferences

    public static void clearAllPreferences() {
        remove(Constants.SELECTED_TOOL_IDS);
        remove(Constants.SELECTED_TOOL_SETS);
        remove(Constants.COMPLETED_TOOL_IDS);
        remove(Constants.COMPLETED_TOOL_SETS);
        remove(Constants.LAST_COMPLETED_TOOL_IDS);
        remove(Constants.LAST_COMPLETED_TOOL_SET);
        remove(Constants.LAST_EXERCISE_DATE);
    }

}