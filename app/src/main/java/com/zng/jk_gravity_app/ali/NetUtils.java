package com.zng.jk_gravity_app.ali;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetUtils {
    public static int NETWORK_WIFI = 0;
    public static int NETWORK_MOBILE = 1;
    public static int NETWORK_NONE = -1;

    public NetUtils() {
    }

    public static int getNetWorkState(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService("connectivity");
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
            if (activeNetworkInfo.getType() == 1) {
                return NETWORK_WIFI;
            } else {
                return activeNetworkInfo.getType() == 0 ? NETWORK_MOBILE : NETWORK_NONE;
            }
        } else {
            return NETWORK_NONE;
        }
    }

}
