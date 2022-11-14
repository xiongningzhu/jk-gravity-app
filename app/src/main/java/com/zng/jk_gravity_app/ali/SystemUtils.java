package com.zng.jk_gravity_app.ali;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import java.util.Locale;

public class SystemUtils {
    public SystemUtils() {
    }

    public static String getSystemLanguage() {
        return Locale.getDefault().getLanguage();
    }

    public static Locale[] getSystemLanguageList() {
        return Locale.getAvailableLocales();
    }

    public static String getSystemVersion() {
        return Build.VERSION.RELEASE;
    }

    public static String getSystemModel() {
        return Build.MODEL;
    }

    public static String getDeviceBrand() {
        return Build.BRAND;
    }

    @SuppressLint({"MissingPermission"})
    public static String getIMEI(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager)context.getSystemService("phone");
        String deviceId = null;

        try {
            deviceId = telephonyManager.getDeviceId();
        } catch (Exception var4) {
            var4.printStackTrace();
        }

        if (TextUtils.isEmpty(deviceId)) {
            deviceId = Settings.System.getString(context.getContentResolver(), "android_id");
        }

        return deviceId;
    }

}
