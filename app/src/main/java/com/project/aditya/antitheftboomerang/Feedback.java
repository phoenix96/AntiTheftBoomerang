package com.project.aditya.antitheftboomerang;

import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by Rishabh Jain on 17-09-2016.
 */

public class Feedback extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.layout_feedback, container, false);
        Button b = (Button) layout.findViewById(R.id.feedback_button);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/html");
                intent.putExtra(Intent.EXTRA_EMAIL, new String[] { "boomerang.database@gmail.com" });
                intent.putExtra(Intent.EXTRA_SUBJECT, "BUG_REPORT/FEATURE REQUEST FOR BOOMERANG");
                intent.putExtra(Intent.EXTRA_TEXT, "Hey,\nMy device model is: \nRunning android version: \n");
                startActivity(Intent.createChooser(intent, "Send Email"));
            }
        });
        SharedPreferences.Editor editor = getActivity().getSharedPreferences("data",0).edit();
        editor.putString("frag","");
        editor.commit();
        return layout;
    }
}
