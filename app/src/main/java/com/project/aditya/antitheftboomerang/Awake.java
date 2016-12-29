package com.project.aditya.antitheftboomerang;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class Awake extends Service {
    public Awake() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        return super.onStartCommand(intent, flags, startId);
    }
}
