package com.mobi.samsung.manausmobiadmin.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.mobi.samsung.manausmobiadmin.MainActivity;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class StartOnBootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent pushIntent = new Intent(context, MainActivity.class);
        pushIntent.addFlags(FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(pushIntent);
    }
}
