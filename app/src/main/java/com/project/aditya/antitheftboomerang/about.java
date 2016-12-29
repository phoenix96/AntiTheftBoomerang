package com.project.aditya.antitheftboomerang;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * Created by Rishabh Jain on 17-09-2016.
 */
public class about extends Fragment {
    ImageView rishabh, tarun, aditya;
    TextView r,t,a;
    View layout;
    private Drawable resize(Drawable image) {
        Bitmap bitmap = ((BitmapDrawable) image).getBitmap();
        Bitmap bitmapResized = Bitmap.createScaledBitmap(bitmap,
                (int) (bitmap.getWidth() * 0.1), (int) (bitmap.getHeight() * 0.1), false);
        return new BitmapDrawable(getResources(), bitmapResized);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final LayoutInflater in = inflater;
        final ViewGroup con = container;
        layout = in.inflate(R.layout.layout_about, con, false);
        rishabh = (ImageView) layout.findViewById(R.id.rj);
        aditya = (ImageView) layout.findViewById(R.id.baks);
        tarun = (ImageView) layout.findViewById(R.id.tj);

        r = (TextView) layout.findViewById(R.id.rishabh);
        a = (TextView) layout.findViewById(R.id.aditya);
        t = (TextView) layout.findViewById(R.id.tarun);
        Thread th = new Thread(new Runnable() {
            @Override
            public void run() {
                ImageView imageView = (ImageView) layout.findViewById(R.id.rj);
                imageView.setImageResource(0);
                Drawable draw = getResources().getDrawable(R.mipmap.rishabh);
                draw = resize(draw);
                imageView.setImageDrawable(draw);

                ImageView imageView2 = (ImageView) layout.findViewById(R.id.tj);
                imageView2.setImageResource(0);
                Drawable draw2 = getResources().getDrawable(R.mipmap.tarun);
                draw2 = resize(draw2);
                imageView2.setImageDrawable(draw2);

                ImageView imageView3 = (ImageView) layout.findViewById(R.id.baks);
                imageView3.setImageResource(0);
                Drawable draw3 = getResources().getDrawable(R.mipmap.aditya);
                draw3 = resize(draw3);
                imageView3.setImageDrawable(draw3);
            }
        });
        th.run();

        return layout;
    }
}
