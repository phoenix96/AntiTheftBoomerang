package com.project.aditya.antitheftboomerang;

/**
 * Created by agarw_000 on 18-11-2016.
 */
import android.app.admin.DeviceAdminReceiver;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Camera;
import android.util.Log;
import android.view.SurfaceView;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.IOException;

public class AdminReceiver extends DeviceAdminReceiver {
    static int i=0;
    public void takePictureNoPreview(Context context) {
        // open back facing camera by default
        Log.i("temp","here");
        Camera myCamera = Camera.open();
        if(myCamera == null){
            Log.i("temp","null");
        }
        if (myCamera != null) {
            try {
                //set camera parameters if you want to
                //...

                // here, the unused surface view and holder
                Log.i("temp","here");
                SurfaceView dummy = new SurfaceView(context);
                myCamera.setPreviewDisplay(dummy.getHolder());
                myCamera.startPreview();
                Log.i("temp","here 2");
                myCamera.takePicture(null, null, getJpegCallback());

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (myCamera != null) {
                    myCamera.stopPreview();
                    myCamera.release();
                    myCamera = null;
                }
            }

        }
    }


    private Camera.PictureCallback getJpegCallback() {
        Camera.PictureCallback jpeg = new Camera.PictureCallback() {

            @Override
            public void onPictureTaken(byte[] data, Camera camera) {
                FileOutputStream fos;
                try {
                    Log.i("temp","here 3");
                    fos = new FileOutputStream("test.jpeg");
                    fos.write(data);
                    fos.close();
                    Log.i("temp","here 4");
                } catch (IOException e) {
                    //do something about it
                }
            }
        };
        return jpeg;
    }

    @Override
    public void onEnabled(Context ctxt, Intent intent) {
        ComponentName cn=new ComponentName(ctxt, AdminReceiver.class);
        DevicePolicyManager mgr=
                (DevicePolicyManager)ctxt.getSystemService(Context.DEVICE_POLICY_SERVICE);

        mgr.setPasswordQuality(cn,
                DevicePolicyManager.PASSWORD_QUALITY_ALPHANUMERIC);

        onPasswordChanged(ctxt, intent);
    }

    @Override
    public void onPasswordChanged(Context ctxt, Intent intent) {
        DevicePolicyManager mgr=
                (DevicePolicyManager)ctxt.getSystemService(Context.DEVICE_POLICY_SERVICE);
        int msgId;

        if (mgr.isActivePasswordSufficient()) {
            msgId=R.string.compliant;
        }
        else {
            msgId=R.string.not_compliant;
        }

        Toast.makeText(ctxt, msgId, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onPasswordFailed(Context ctxt, Intent intent) {
        //Toast.makeText(ctxt, R.string.password_failed, Toast.LENGTH_LONG).show();
        i=i+1;
        String d=Integer.toString(i);
        String re="ads";
        Log.i("temp", d);
        SharedPreferences sp2 = ctxt.getSharedPreferences("data",0);
        String checked = sp2.getString("pat_cam","");
        Integer attempts = Integer.parseInt(sp2.getString("pat_attempts","0"));

        if(!checked.equals("true"))
            return;

        if(attempts <= i) {
            Intent i1 = new Intent(ctxt, AndroidCameraApi.class);
            i1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            ctxt.startActivity(i1);
        }




    }

    @Override
    public void onPasswordSucceeded(Context ctxt, Intent intent) {
        //Toast.makeText(ctxt, R.string.password_success, Toast.LENGTH_LONG).show();
        i=0;
    }
}
