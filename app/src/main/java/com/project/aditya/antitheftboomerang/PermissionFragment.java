package com.project.aditya.antitheftboomerang;

import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.project.aditya.antitheftboomerang.R;

public class PermissionFragment extends Fragment implements View.OnClickListener{
    String[] reqperm;
    String pkgname;
    private PackageManager packageManager;
    private static final String SCHEME = "package";
    private static final String APP_PKG_NAME_21 = "com.android.settings.ApplicationPkgName";
    private static final String APP_PKG_NAME_22 = "pkg";
    private static final String APP_DETAILS_PACKAGE_NAME = "com.android.settings";
    private static final String APP_DETAILS_CLASS_NAME = "com.android.settings.InstalledAppDetails";

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInsatnceState) {
        View view1 = inflater.inflate(R.layout.permission_fragment, container, false);
        reqperm = getArguments().getStringArray("perm");
        pkgname=getArguments().getString("pkg");
        SharedPreferences.Editor editor = getActivity().getSharedPreferences("data",0).edit();
        editor.putString("frag","PERM2");
        editor.commit();

        try
        {
            Drawable d = getActivity().getPackageManager().getApplicationIcon(pkgname);
            ImageView iconview = (ImageView) view1.findViewById(R.id.apps_icon);
            iconview.setImageDrawable(d);
        }
        catch (PackageManager.NameNotFoundException e)
        {
            return null;
        }
        PackageManager packageManager = getActivity().getPackageManager();
        ApplicationInfo applicationInfo = null;
        try {
            applicationInfo = packageManager.getApplicationInfo(pkgname, 0);
        } catch (final PackageManager.NameNotFoundException e) {}
        final String title = (String)((applicationInfo != null) ? packageManager.getApplicationLabel(applicationInfo) : "???");
        Toast.makeText(getActivity(),title, Toast.LENGTH_SHORT).show();
        TextView appName = (TextView) view1.findViewById(R.id.apps_name);
            appName.setText(title);
        TextView appPackageName = (TextView) view1.findViewById(R.id.apps_paackage);
        appPackageName.setText(pkgname);
        for(int i=0;i<reqperm.length;i++)
        {
            int pos=0;
            for(int j=0;j<reqperm[i].length();j++)
            {
                if(reqperm[i].charAt(j) == '.')
                    pos = j;
            }
            reqperm[i] = reqperm[i].substring(pos+1);
        }

        ArrayAdapter<String> list1=new ArrayAdapter<String>(getActivity(),android.R.layout.test_list_item,reqperm);
        ListView permissions=(ListView)view1.findViewById(R.id.permfragment_listview);
        permissions.setAdapter(list1);
        Button bttn;
        bttn=(Button)view1.findViewById(R.id.uninstall);
        bttn.setOnClickListener(this);
       //Toast.makeText(getActivity(),pkgname, Toast.LENGTH_SHORT).show();
        return view1;
    }

    @Override
    public void onClick(View v) {


            Intent intent = new Intent();
            final int apiLevel = Build.VERSION.SDK_INT;
            if (apiLevel >= 9) { // above 2.3
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts(SCHEME, pkgname, null);
                intent.setData(uri);
            } else { // below 2.3
                final String appPkgName = (apiLevel == 8 ? APP_PKG_NAME_22
                        : APP_PKG_NAME_21);
                intent.setAction(Intent.ACTION_VIEW);
                intent.setClassName(APP_DETAILS_PACKAGE_NAME,
                        APP_DETAILS_CLASS_NAME);
                intent.putExtra(appPkgName, pkgname);
            }
            getActivity().startActivity(intent);

        //transaction.addToBackStack(null);

    }
}


