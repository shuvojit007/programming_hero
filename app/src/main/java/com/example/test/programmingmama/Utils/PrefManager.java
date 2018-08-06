package com.example.test.programmingmama.Utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.firebase.database.FirebaseDatabase;

public class PrefManager {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;
    private static PrefManager prefManager;

    // shared pref mode
    int PRIVATE_MODE = 0;

    // Shared preferences file name
    private static final String PREF_NAME = "hero";

    private static final String FIRST_TIME_LAUNCH = "FirstTimeLaunch";
    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";
    private static final String SOUNDONOFF = "SOUNDONOFF";
    private static final String HOMEMODULEPOSITION = "HOMEMODULEPOSITION";

    private static final String ONESIGNALTOKEN = "ONESIGNALTOKEN";
    private static final String ONESIGNALAPPID = "ONESIGNALAPPID";


    private static final String GOBACK = "GOBACK";
    private static final String FIRST = "FIRST";
    private static final String SECEND = "SECEND";


    private static final String PROMOSTATUS = "PROMOSTATUS";

    private static final String CurrentModule = "CurrentModule";

    public PrefManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    //todo intro screen
    public void setFirstTimeLaunch(boolean isFirstTime) {
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        editor.commit();
    }

    public boolean isFirstTimeLaunch() {
        return pref.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }

    //todo intro screen


    //todo Audio on/off
    public void SoundOnOFF(boolean on) {
        editor.putBoolean(SOUNDONOFF, on);
        editor.commit();
    }
    public boolean SoundStatus() {
        return pref.getBoolean(SOUNDONOFF, true);
    }
    //todo Audio on/off


    public void SetHomePosition(int i) {
        editor.putInt(HOMEMODULEPOSITION, i);
        editor.commit();
    }
    public int GetHomePosition() {
        return pref.getInt(HOMEMODULEPOSITION, 0);
    }
    public void setTime(String time) {
        editor.putString(FIRST_TIME_LAUNCH, time);
        editor.commit();
    }

    public String getTime() {
        return pref.getString(FIRST_TIME_LAUNCH, null);
    }


    //todo OneSignalUtils appID && token
    public void SetToken(String token) {
        editor.putString(ONESIGNALTOKEN, token);
        editor.commit();
    }
    public void SetAppID(String appid) {
        editor.putString(ONESIGNALAPPID, appid);
        editor.commit();
    }
    public String getToken() {
        return pref.getString(ONESIGNALTOKEN, "null");
    }
    public String geAppID() {
        return pref.getString(ONESIGNALAPPID, "null");
    }
    //todo OneSignalUtils appID && token

    //todo Goback FirsTimes and secondTimes
    public void SetGoBack(int i) {
        editor.putInt(GOBACK, i);
        editor.commit();
    }
    public int GetGoBack() {
        return pref.getInt(GOBACK, 0);
    }

    public void SetFIRST(int i) {
        editor.putInt(FIRST, i);
        editor.commit();
    }
    public int GetFIRST() {
        return pref.getInt(FIRST, 0);
    }

    public void SetSECEND(int i) {
        editor.putInt(SECEND, i);

        editor.commit();
    }
    public int GetSECEND() {
        return pref.getInt(SECEND, 0);
    }
    //todo Goback FirsTimes and secondTimes


    public void SetCurrentModule(int i) {
        editor.putInt(CurrentModule, i);
        editor.commit();
    }

    public int GetCurrentModule() {
        return pref.getInt(CurrentModule, 0);
    }



    public void SetPROMOSTATUS(boolean bb) {
        editor.putBoolean(PROMOSTATUS, bb);
        editor.commit();
    }

    public boolean GetPROMOSTATUS() {
        return pref.getBoolean(PROMOSTATUS, false);
    }


    public static PrefManager GetPrefManager(Context cn){
        if (prefManager ==null){
            prefManager = new PrefManager(cn);
            return prefManager;
        }
        return prefManager;
    }

}
