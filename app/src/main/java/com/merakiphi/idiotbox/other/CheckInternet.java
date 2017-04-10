package com.merakiphi.idiotbox.other;

import android.content.Context;
import android.net.ConnectivityManager;

import java.net.InetAddress;

/**
 * Created by anuragmaravi on 10/04/17.
 */

public class CheckInternet {
    private static CheckInternet checkInternet;
    Context context;
    public CheckInternet(Context context){
        this.context = context;
    }

    public static synchronized CheckInternet getInstance(Context context) {
        if (checkInternet == null) {
            checkInternet = new CheckInternet(context);
        }
        return checkInternet;
    }

    public boolean isInternetAvailabe(){
        try {
            InetAddress ipAddr = InetAddress.getByName("google.com");
            return !ipAddr.equals("");

        } catch (Exception e) {
            return false;
        }
    }

    public boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }


}
