package com.coverevi.coverevi.Misc;

import android.content.Context;
import android.net.ConnectivityManager;
//internet bağlantısı olup olmadığını kontrol ediyor.
public class Connectivity {

    public static boolean isNetworkAvailable(final Context context) {
        final ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }
}
