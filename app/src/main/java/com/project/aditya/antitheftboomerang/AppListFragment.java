package com.project.aditya.antitheftboomerang;

/**
 * Created by agarw_000 on 02-10-2016.
 */

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.project.aditya.antitheftboomerang.ApplicationAdapter;
import com.project.aditya.antitheftboomerang.PermissionFragment;
import com.project.aditya.antitheftboomerang.R;

import java.util.ArrayList;
import java.util.List;

public class AppListFragment extends Fragment implements AdapterView.OnItemClickListener {

    private PackageManager packageManager;
    private List<ApplicationInfo> applist = null;
    private ApplicationAdapter listadaptor = null;
    PackageInfo packageinfo=null;
    ListView lv = null;
    String[] reqpermissions;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInsatnceState){
        View view=inflater.inflate(R.layout.applist_fragment,container,false);
        lv = (ListView)view.findViewById(R.id.applistfragment_listview);
        lv.setOnItemClickListener(this);
        packageManager = getActivity().getPackageManager();
        new LoadApplications(getActivity(),lv).execute();
        SharedPreferences.Editor editor = getActivity().getSharedPreferences("data",0).edit();
        editor.putString("frag","");
        editor.commit();
        return view;
    }
    //@Override
    //  public void onListItemClick(ListView l, View v, int position, long id) {
    //  super.onListItemClick(l, v, position, id);

        /**/
    //  }

    public List<ApplicationInfo> checkForLaunchIntent(List<ApplicationInfo> list) {
        ArrayList<ApplicationInfo> applist = new ArrayList<>();

        for (ApplicationInfo info : list) {
            try {
                if (null != packageManager.getLaunchIntentForPackage(info.packageName)) {
                    applist.add(info);

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return applist;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ApplicationInfo app = applist.get(position);
        try {
            //  Intent intent = packageManager.getLaunchIntentForPackage(app.packageName);
            packageinfo=getActivity().getPackageManager().getPackageInfo(app.packageName,PackageManager.GET_PERMISSIONS);
            reqpermissions=packageinfo.requestedPermissions;
            //int y=reqpermissions.length;
            //reqpermissions[y]=app.packageName;
            if(reqpermissions == null)
            {
                Toast.makeText(getActivity(), "NO PERMISSIONS REQUIRED!", Toast.LENGTH_SHORT).show();
            }
            else {
                PermissionFragment sec = new PermissionFragment();
                Bundle b = new Bundle();
                b.putStringArray("perm", reqpermissions);
                sec.setArguments(b);
                b.putString("pkg",app.packageName);

                FragmentTransaction transaction = getActivity().getFragmentManager().beginTransaction();
                transaction.replace(R.id.appmain_fragment, sec);
               // transaction.addToBackStack(null);
                transaction.commit();
                //getFragmentManager().beginTransaction().add(R.id.appmain_fragment, sec).commit();
                //Toast.makeText(getActivity().getApplicationContext(),reqpermissions[0], Toast.LENGTH_LONG).show();
                //if (null != intent) {startActivity(intent);}
            }
        }
        catch (ActivityNotFoundException e) {
            //Toast.makeText(MainActivity.this, e.getMessage(),Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            //Toast.makeText(MainActivity.this, e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    public class LoadApplications extends AsyncTask<Void,Void,Void> {
        private ProgressDialog progress = null;

        ListView lv1;
        Activity mcontext;
        public  LoadApplications(Activity a, ListView lv)
        {   this.mcontext = a;
            this.lv1=lv;
        }

        @Override
        protected Void doInBackground(Void... params) {
            applist = checkForLaunchIntent(packageManager.getInstalledApplications(PackageManager.GET_META_DATA));
            listadaptor = new ApplicationAdapter(getActivity(), R.layout.snippet_list_row, applist);
            return null;
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

        @Override
        protected void onPostExecute(Void result) {

            progress.dismiss();
            lv1.setAdapter(listadaptor);
            super.onPostExecute(result);
        }

        @Override
        protected void onPreExecute() {
            progress = ProgressDialog.show(getActivity(), null, "Loading application info...");
//            Log.i("Error","hello");
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }
}
