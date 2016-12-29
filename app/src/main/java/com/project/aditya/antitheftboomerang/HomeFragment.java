package com.project.aditya.antitheftboomerang;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by Rishabh Jain on 04-10-2016.
 */
public class HomeFragment extends Fragment {
    private TextView welcome_name;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.layout_home_fragment,container,false);
        welcome_name=(TextView)layout.findViewById(R.id.welcome_name);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user!=null)
        {

            //Toast.makeText(HomeActivity.this, user.getDisplayName(), Toast.LENGTH_SHORT).show();
            //Toast.makeText(HomeActivity.this, user.getEmail(), Toast.LENGTH_SHORT).show();
            //Toast.makeText(HomeActivity.this, user.getProviderId(), Toast.LENGTH_SHORT).show();
            welcome_name.setText(user.getDisplayName());
        }
        SharedPreferences.Editor editor = getActivity().getSharedPreferences("data",0).edit();
        editor.putString("frag","");
        editor.commit();
        return layout;
    }
}
