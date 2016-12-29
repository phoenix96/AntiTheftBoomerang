package com.project.aditya.antitheftboomerang;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.BatteryManager;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Rishabh Jain on 30-11-2016.
 */
public class BatteryBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        SharedPreferences sp2 = context.getSharedPreferences("data",0);
        String run = sp2.getString("sig_flare", "false");
        //Toast.makeText(context,"test",Toast.LENGTH_SHORT).show();

        if(run.equals("false"))
            return;

        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = context.registerReceiver(null, ifilter);
        final int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);

        //Log.i("check","Battery Changed to "+level);
        Toast.makeText(context, "Broadcasted Battery: "+level, Toast.LENGTH_SHORT).show();

        SharedPreferences sp = context.getSharedPreferences("data",0);
        String battery = sp.getString("battery_stat", "");

        if(Integer.parseInt(battery) >= level)
        {   //Toast.makeText(context,"test2",Toast.LENGTH_SHORT).show();
            String temp = sp.getString("bat_sms","false");
            if(temp.equals("false")) {
                //Toast.makeText(context,"test3",Toast.LENGTH_SHORT).show();
                SharedPreferences.Editor editor = context.getSharedPreferences("data", 0).edit();
                editor.putString("bat_sms", "true");
                editor.commit();
            }
            Toast.makeText(context,".",Toast.LENGTH_SHORT).show();
            Intent localisationIntent = new Intent(context,LocalisationService.class);
            context.startService(localisationIntent);
        }
        else
        {
            SharedPreferences.Editor editor = context.getSharedPreferences("data", 0).edit();
            editor.putString("bat_sms", "false");
            editor.commit();
        }
    }
}