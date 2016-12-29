package com.project.aditya.antitheftboomerang;

import android.app.Fragment;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.BatteryManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Rishabh Jain on 17-09-2016.
 */
public class sig_flare extends Fragment {
    CheckBox cb;
    SeekBar sb;
    int battery_set;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.layout_sig_flare, container, false);

        sb = (SeekBar)layout.findViewById(R.id.sig_flare_battery_input);
        sb.setVisibility(View.INVISIBLE);

        cb = (CheckBox) layout.findViewById(R.id.sig_flare_checkbox);
        final TextView tv = (TextView)layout.findViewById(R.id.sig_flare_textbox);
        tv.setVisibility(View.INVISIBLE);

        SharedPreferences sp2 = getActivity().getSharedPreferences("data",0);
        String checked = sp2.getString("sig_flare","");
        if(checked.equals("true"))
        {
            cb.setChecked(true);
            sb.setVisibility(View.VISIBLE);
            tv.setVisibility(View.VISIBLE);

        }
        cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cb.isChecked())
                {
                    SharedPreferences.Editor bat = getActivity().getSharedPreferences("data",0).edit();
                    bat.putString("sig_flare","true");
                    bat.commit();

                    sb.setVisibility(View.VISIBLE);
                    tv.setVisibility(View.VISIBLE);

                }
                else
                {
                    sb.setVisibility(View.INVISIBLE);
                    tv.setVisibility(View.INVISIBLE);
                    SharedPreferences.Editor bat = getActivity().getSharedPreferences("data",0).edit();
                    bat.putString("sig_flare","false");
                    bat.commit();
                }
            }
        });


        SharedPreferences sp = getActivity().getSharedPreferences("data",0);
        String battery = sp.getString("battery_stat","");
        if(!battery.equals(""))
        {
            battery_set = Integer.parseInt(battery);
            sb.setProgress(Integer.parseInt(battery));
        }

        tv.setText("Signal Flare at: "+battery_set);

        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                battery_set = progress+1;
                tv.setText("Signal Flare at: "+battery_set);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

                Toast.makeText(getActivity(), ""+battery_set, Toast.LENGTH_SHORT).show();
                SharedPreferences sp = getActivity().getSharedPreferences("data",0);
                String battery = sp.getString("bat_sms", "false");

                //Saving Battery Level entred
                SharedPreferences.Editor bat = getActivity().getSharedPreferences("data",0).edit();
                bat.putString("battery_stat",""+battery_set);
                bat.commit();
            }
        });

        //To go back properly
        SharedPreferences.Editor editor = getActivity().getSharedPreferences("data",0).edit();
        editor.putString("frag","");
        editor.commit();
        return layout;
    }
}
