package com.project.aditya.antitheftboomerang;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Rishabh Jain on 17-09-2016.
 */

public class Sms extends Fragment {
    Button settings;
    TextView help;
    CheckBox ringer,alarm,location;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Inflating Layout
        final View layout = inflater.inflate(R.layout.layout_sms, container, false);

        //Back Jaane k liy hai! DONT CHANGE THIS!
        final SharedPreferences.Editor editor = getActivity().getSharedPreferences("data",0).edit();
        editor.putString("frag","");
        editor.commit();

        //Mapping checkboxes to variables
        ringer = (CheckBox)layout.findViewById(R.id.CheckBoxRingerMode);
        settings = (Button)layout.findViewById(R.id.sms_settings_button);
        location = (CheckBox)layout.findViewById(R.id.CheckBoxGetLocation);
        alarm = (CheckBox)layout.findViewById(R.id.CheckBoxAlarmMode);
        /*help = (TextView)layout.findViewById(R.id.sms_textbox);
        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(),help.class);
                startActivity(i);
            }
        });*/



        //Checking previous checked boxes
        SharedPreferences sp = getActivity().getSharedPreferences("data",0);
        String wasRingerChecked = sp.getString("ringer","");
        String wasAlarmChecked = sp.getString("alarm","");
        String wasLocationChecked = sp.getString("location","");

        if(wasRingerChecked.equals("true")){
            ringer.setChecked(true);
        }
        if(wasAlarmChecked.equals("true")){
            alarm.setChecked(true);
        }
        if(wasLocationChecked.equals("true")){
            location.setChecked(true);
        }

        //Activate Broadcast Listener
        PackageManager pm = getActivity().getPackageManager();
        ComponentName componentName = new ComponentName(getActivity(), SmsBroadcastListener.class);
        pm.setComponentEnabledSetting(componentName, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);


         /*
         //Deactivate Broadcast Listener
         PackageManager pm = getActivity().getPackageManager();
         ComponentName componentName = new ComponentName(getActivity(), SmsBroadcastListener.class);
         pm.setComponentEnabledSetting(componentName, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
         //Toast.makeText(getActivity(), "RINGER MODE DEACTIVATED", Toast.LENGTH_SHORT).show();
         */


        //Settings button link to next fragment
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Sms_more_settings newFragment = new Sms_more_settings();
                FragmentTransaction transaction = Sms.this.getFragmentManager().beginTransaction();
                transaction.replace(R.id.content_frame, newFragment);
                transaction.commit();
            }
        });

        //Ringer Functionality

        ringer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(ringer.isChecked()) {

                    //To remember that the ringer button is checked! Will have to add email to maintain consistency.
                    //Like, email+ringer, true
                    int res = check_for_password();
                    if(res == 1)
                        return;
                    SharedPreferences.Editor editor = getActivity().getSharedPreferences("data",0).edit();
                    editor.putString("ringer","true");
                    editor.commit();
                    Toast.makeText(getActivity(), "Ringer Mode Activated!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    //To remember that the ringer button is unchecked! Will have to add email to maintain consistency.
                    //Like, email+ringer, false
                    SharedPreferences.Editor editor = getActivity().getSharedPreferences("data",0).edit();
                    editor.putString("ringer","false");
                    editor.commit();
                }
            }
        });


        //Location Checking!
       location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (location.isChecked())
                {
                    int res = check_for_password();
                    if(res == 1)
                        return;
                    SharedPreferences.Editor editor = getActivity().getSharedPreferences("data",0).edit();
                    editor.putString("location","true");
                    editor.commit();
                    Toast.makeText(getActivity(), "LOCATION ENABLED!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    SharedPreferences.Editor editor = getActivity().getSharedPreferences("data",0).edit();
                    editor.putString("location","false");
                    editor.commit();

                }
            }
        });



       alarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (alarm.isChecked())
                {
                    int res = check_for_password();
                    if(res == 1)
                        return;
                    SharedPreferences.Editor editor = getActivity().getSharedPreferences("data",0).edit();
                    editor.putString("alarm","true");
                    editor.commit();
                    Toast.makeText(getActivity(), "ALARM", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    SharedPreferences.Editor editor = getActivity().getSharedPreferences("data",0).edit();
                    editor.putString("alarm","false");
                    editor.commit();
                }
            }
        });
        return layout;
    }


    public int check_for_password()
    {
        //First Time, Fill in the password details!
        SharedPreferences sp = getActivity().getSharedPreferences("data",0);
        String pass = sp.getString("sms_password","");
        if(pass.equals(""))
        {
            Toast.makeText(getActivity(), "Please fill in your password first!", Toast.LENGTH_SHORT).show();
            //Go to password page
            Sms_more_settings newFragment = new Sms_more_settings();
            FragmentTransaction transaction = Sms.this.getFragmentManager().beginTransaction();
            transaction.replace(R.id.content_frame, newFragment);
            transaction.commit();
            return 1;
        }

        return 0;
    }
}
