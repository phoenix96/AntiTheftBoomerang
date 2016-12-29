package com.project.aditya.antitheftboomerang;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Rishabh Jain on 29-09-2016.
 */


public class Sms_more_settings extends Fragment {
    EditText op,np,npc;Button sub;
    //Old password is correct -> opic
    int opic;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.layout_sms_more_settings,container,false);

        //Mapping layout to variables
        op = (EditText)layout.findViewById(R.id.old_password);
        np = (EditText)layout.findViewById(R.id.new_password_sms);
        npc = (EditText)layout.findViewById(R.id.new_password_sms_confirm);
        sub = (Button)layout.findViewById(R.id.confirm_pass_sms);
        opic = 0;

        //Back Functionality Shared Preference
        SharedPreferences.Editor editor = getActivity().getSharedPreferences("data",0).edit();
        editor.putString("frag","SMS2");
        editor.commit();

        //Fetch password from Shared Preference
        SharedPreferences sp = getActivity().getSharedPreferences("data",0);
        final String old_pass = sp.getString("sms_password","");
        if(old_pass.isEmpty())
        {
            op.setVisibility(View.INVISIBLE);
            opic=1;
        }
        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (opic != 1 && old_pass.equals(op.getText().toString())) {
                    opic = 1;
                }
                if (opic != 1) {
                    op.setError("The entered password is incorrect. Please try again!");
                    return;
                }

                //Now we know that old password is either correct, or we are here for first time
                if (!np.getText().toString().equals(npc.getText().toString())) {

                    npc.setError("The entered passwords does not match!");
                    return;
                }
                if (np.getText().toString().equals(""))
                {
                    np.setError("Please enter a valid password! The password field cannot be blank!");
                    return;
                }
                if (npc.getText().toString().equals(""))
                {
                    npc.setError("Please enter a valid password! The password field cannot be blank!");
                    return;
                }
                //Updating Password!
                SharedPreferences.Editor editor = getActivity().getSharedPreferences("data",0).edit();
                editor.putString("sms_password", np.getText().toString());
                editor.commit();

                Toast.makeText(getActivity(), "Your Password for SMS Queries has been changed!", Toast.LENGTH_SHORT).show();

                //After completion of password change, go to main SMS page
                Sms newFragment = new Sms();
                FragmentTransaction transaction = Sms_more_settings.this.getFragmentManager().beginTransaction();
                transaction.replace(R.id.content_frame, newFragment);
                transaction.commit();
            }
        });
        return layout;
    }
}
