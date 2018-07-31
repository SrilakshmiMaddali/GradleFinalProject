package com.udacity.gradle.builditbigger.controllers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class ServiceController extends BroadcastReceiver {

    private static ServiceController mInstance;

    public static ServiceController getInstance() {
        if (mInstance == null) mInstance = new ServiceController();
        return mInstance;
    }

    @Override
    public void onReceive(Context context, Intent intent) {

    }
}
