package com.project.aditya.antitheftboomerang;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Rishabh Jain on 17-09-2016.
 */
public class app_perm extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.layout_app_perm, container, false);
        AppListFragment  appListFragment_object =new AppListFragment();
        FragmentTransaction transaction = app_perm.this.getFragmentManager().beginTransaction();
        transaction.replace(R.id.appmain_fragment, appListFragment_object);
        //transaction.addToBackStack(null);
        transaction.commit();
        SharedPreferences.Editor editor = getActivity().getSharedPreferences("data",0).edit();
        editor.putString("frag","");
        editor.commit();
        return layout;
    }
}
