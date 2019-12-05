package com.lorenzorigato.base.network.component;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.lorenzorigato.base.network.component.interfaces.IReachabilityChecker;

public class ReachabilityChecker implements IReachabilityChecker {

    private Context context;

    public ReachabilityChecker(Context context) {
        this.context = context;
    }

    @Override
    public boolean isInternetAvailable() {
        ConnectivityManager cm = (ConnectivityManager) this.context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }
}
