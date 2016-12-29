package com.project.aditya.antitheftboomerang;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class Splash extends Activity {
    //private static boolean splashLoaded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash2);
        //fade();
            int secondsDelayed = 1;
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    startActivity(new Intent(Splash.this, MainActivity.class));
                    finish();
                }
            }, secondsDelayed * 800);
  //          splashLoaded = true;
        }
    /*public void fade(){
        ImageView image = (ImageView)findViewById(R.id.imageView2);
        Animation animation1 =
                AnimationUtils.loadAnimation(getApplicationContext(),
                        R.anim.fade);
        image.startAnimation(animation1);
    }*/
    }
