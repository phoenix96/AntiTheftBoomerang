package com.project.aditya.antitheftboomerang;

import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener  {
    ImageView iv;
    TextView name,sub;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_home);
        /*SharedPreferences sp = getApplicationContext().getSharedPreferences("data",0);
        String prof = sp.getString("profile_activity_filled","false");
*/
        /*if(prof.equals(false))
        {
            Intent i = new Intent(getApplicationContext(), ProfileActivity.class);
            startActivity(i);
            finish();
        }*/

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        iv =    (ImageView)findViewById(R.id.app_icon_nav);
        name =  (TextView) findViewById(R.id.boomerang_text_nav);
        sub =   (TextView) findViewById(R.id.boomerang_sub_text_nav);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        HomeFragment hf = new HomeFragment();
        FragmentTransaction transactionhome = HomeActivity.this.getFragmentManager().beginTransaction();
        transactionhome.replace(R.id.content_frame,hf);
        transactionhome.commit();
        //startService(new Intent(this, Awake.class));
        //runtime_perms();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
            return;
        }
        SharedPreferences sp = getSharedPreferences("data",0);
        String  curr_frag= sp.getString("frag","");
        if(curr_frag.equals("SMS2"))
        {
            Sms newFragmentSms = new Sms();
            FragmentTransaction transactionSms = HomeActivity.this.getFragmentManager().beginTransaction();
            transactionSms.replace(R.id.content_frame, newFragmentSms,"SMS");
            transactionSms.commit();
            return;
        }
        else if(curr_frag.equals("PERM2"))
        {
            app_perm newFragmentapp = new app_perm();
            FragmentTransaction transactionapp = HomeActivity.this.getFragmentManager().beginTransaction();
            transactionapp.replace(R.id.content_frame, newFragmentapp,"APP");
            transactionapp.commit();
            return;
        }
        new AlertDialog.Builder(this).setTitle("Exit?").setMessage("Are you sure you want to exit?")
        .setNegativeButton("No",null)
        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                HomeActivity.super.onBackPressed();
            }
        }).create().show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id==R.id.deleteacc)
        {
            new AlertDialog.Builder(this).setTitle("Delete Account?").setMessage("Are you sure you want to delete your account?")
                    .setNegativeButton("No",null)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
                            user.delete()
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                Toast.makeText(HomeActivity.this, "Account Deleted", Toast.LENGTH_SHORT).show();
                                                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                                                firebaseAuth.signOut();
                                                startActivity(new Intent(HomeActivity.this,MainActivity.class));
                                                finish();
                                            }
                                        }
                                    });
                        }
                    }).create().show();

        }
        if (id == R.id.logout) {
            FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(this,Login.class));
            return true;
        }
        else if(id == R.id.help_menu)
        {
            Intent i = new Intent(getApplicationContext(),help.class);
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        switch(id){
            case R.id.home_nav_drawer:
                HomeFragment hf = new HomeFragment();
                FragmentTransaction transactionhome = HomeActivity.this.getFragmentManager().beginTransaction();
                transactionhome.replace(R.id.content_frame,hf);
                transactionhome.commit();
                break;
            case R.id.sms_nav_drawer:
                Sms newFragmentSms = new Sms();
                FragmentTransaction transactionSms = HomeActivity.this.getFragmentManager().beginTransaction();
                transactionSms.replace(R.id.content_frame, newFragmentSms);
                transactionSms.commit();
                break;
            case R.id.signal_flare_nav_drawer:
                sig_flare newFragmentSf = new sig_flare();
                FragmentTransaction transactionSf = HomeActivity.this.getFragmentManager().beginTransaction();
                transactionSf.replace(R.id.content_frame, newFragmentSf);
                transactionSf.commit();
                break;
            case R.id.app_nav_drawer:
                app_perm newFragmentapp = new app_perm();
                FragmentTransaction transactionapp = HomeActivity.this.getFragmentManager().beginTransaction();
                transactionapp.replace(R.id.content_frame, newFragmentapp);
                transactionapp.commit();
                break;
            case R.id.pat_nav_drawer:
                pat_cam newFragmentpc = new pat_cam();
                FragmentTransaction transactionpc = HomeActivity.this.getFragmentManager().beginTransaction();
                transactionpc.replace(R.id.content_frame, newFragmentpc);
                transactionpc.commit();
                break;
            case R.id.about_nav_drawer:
                about newFragmenta = new about();
                FragmentTransaction transactiona = HomeActivity.this.getFragmentManager().beginTransaction();
                transactiona.replace(R.id.content_frame, newFragmenta);
                //transaction.addToBackStack(null);
                transactiona.commit();
                break;
            case R.id.feedback_nav_drawer:
                Feedback newFragmentf = new Feedback();
                FragmentTransaction transactionf = HomeActivity.this.getFragmentManager().beginTransaction();
                transactionf.replace(R.id.content_frame, newFragmentf);
                //transaction.addToBackStack(null);
                transactionf.commit();
                break;


        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void runtime_perms()
    {
        //Marshmallow Check, requesting for runtim permissions!
       /* int permission_check = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_SMS);
        if(permission_check!= PackageManager.PERMISSION_GRANTED)
        {
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    Manifest.permission.READ_CONTACTS)) {

            }

            else {
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.READ_CONTACTS},
                        MY_PERMISSIONS_REQUEST_READ_CONTACTS);
            }
        }*/
    }
}
