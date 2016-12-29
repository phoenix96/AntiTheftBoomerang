package com.project.aditya.antitheftboomerang;

import android.app.Fragment;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
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

import com.project.aditya.antitheftboomerang.R;

/**
 * Created by Rishabh Jain on 17-09-2016.
 */

public class pat_cam extends Fragment {
    CheckBox cb;
    SeekBar sb;
    int battery_set;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.layout_pat_cam, container, false);

        sb =(SeekBar)layout.findViewById(R.id.pat_cam_input);
        sb.setVisibility(View.INVISIBLE);

        cb = (CheckBox) layout.findViewById(R.id.pat_cam_checkbox);
        final TextView tv = (TextView)layout.findViewById(R.id.pat_cam_textbox);
        tv.setVisibility(View.INVISIBLE);
        SharedPreferences sp2 = getActivity().getSharedPreferences("data",0);
        String checked = sp2.getString("pat_cam","");

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
                    bat.putString("pat_cam","true");
                    bat.commit();
                    sb.setVisibility(View.VISIBLE);
                    tv.setVisibility(View.VISIBLE);
                }
                else
                {
                    sb.setVisibility(View.INVISIBLE);
                    tv.setVisibility(View.INVISIBLE);

                    SharedPreferences.Editor bat = getActivity().getSharedPreferences("data",0).edit();
                    bat.putString("pat_cam","false");
                    bat.commit();
                }
            }
        });


        SharedPreferences sp = getActivity().getSharedPreferences("data",0);
        String attempts = sp.getString("pat_attempts","");

        if(!attempts.equals(""))
        {
            battery_set = Integer.parseInt(attempts);
            sb.setProgress(Integer.parseInt(attempts));
        }

        tv.setText("Number of wrong attempts are: " + battery_set);

        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                battery_set = progress+1;
                tv.setText("Number of wrong attempts are: " + battery_set);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

                //Saving Battery Level entred
                SharedPreferences.Editor bat = getActivity().getSharedPreferences("data",0).edit();
                bat.putString("pat_attempts",""+battery_set);
                bat.commit();
            }
        });


        SharedPreferences.Editor editor = getActivity().getSharedPreferences("data",0).edit();
        editor.putString("frag","");
        editor.commit();

        Log.i("pat","here");
        //Toast.makeText(getActivity(), "here", Toast.LENGTH_SHORT).show();
        ComponentName cn=new ComponentName(getActivity(), AdminReceiver.class);
        DevicePolicyManager mgr=
                (DevicePolicyManager)getActivity().getSystemService(getActivity().DEVICE_POLICY_SERVICE);

        if (mgr.isAdminActive(cn)) {
            int msgId;
            Log.i("pat","here 2");
            //Toast.makeText(getActivity(), "here 2", Toast.LENGTH_SHORT).show();

            if (mgr.isActivePasswordSufficient()) {
                msgId=R.string.compliant;
            }
            else {
                msgId=R.string.not_compliant;
            }

            Toast.makeText(getActivity(), msgId, Toast.LENGTH_LONG).show();
        }
        else {
            Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
            intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, cn);
            intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, getString(R.string.device_admin_explanation));
            startActivity(intent);
        }
        return layout;
    }
}
