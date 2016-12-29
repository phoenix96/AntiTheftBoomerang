package com.project.aditya.antitheftboomerang;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

public class LocalisationService extends Service
{
	private LocationManager locationManager;
	private BroadcastReceiver receiver;

	@Override
	public IBinder onBind(Intent intent){return null;}

	@Override
	public void onCreate()
	{
		super.onCreate();
		locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        Log.i("loc","in service");
        if (ContextCompat.checkSelfPermission( this, android.Manifest.permission.ACCESS_COARSE_LOCATION ) == PackageManager.PERMISSION_GRANTED ) {
            // Register the listener with the Location Manager to receive location updates
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,  0, 0, locationListener);
        }


		//If the service receive a specific intent from the SMSReceiver, stop the service
		IntentFilter filter = new IntentFilter();
		filter.addAction("STOP_LOCALISATION_SERVICE");
        Log.i("loc","in service 2");
		receiver = new BroadcastReceiver()
		{
			@Override
			public void onReceive(Context context, Intent intent)
			{
				//Log.e("LocalisationService","OnReceive()");
				stopSelf();
			}
		};
		registerReceiver(receiver, filter);
		Log.e("LocalisationService","register");
	}

	public void sendLocalisation(Location location)
	{
        Log.i("loc","in service 3");
		Log.e("LocalisationService","SMS");
		String body = "Here's the location where your phone is located! \nhttp://maps.google.fr/maps?f=q&source=s_q&hl=fr&geocode=&q="+location.getLatitude()+","+location.getLongitude();
        Toast.makeText(this, body, Toast.LENGTH_SHORT).show();
        SmsManager.getDefault().sendTextMessage("8744985115", null, body, null, null);
		//Log.e("LocalisationService", "SMS envoye, attente de "+SMSReceiver.interval+"ms");
	}
	
	//Define a listener that responds to location updates
	private LocationListener locationListener = new LocationListener()
	{
		//Called when a new location is found by the network location provider
		public void onLocationChanged(Location location)
		{
            Log.i("loc","in service 4");
			//Send the current location by SMS
            SharedPreferences sp = getApplicationContext().getSharedPreferences("data",0);
            String sms_sent = sp.getString("SmsHasToBeSent","");
            if(sms_sent.equals("true"))
            {
                sendLocalisation(location);
				SharedPreferences.Editor editor =getApplicationContext().getSharedPreferences("data",0).edit();
                editor.putString("SmsHasToBeSent", "true");
                editor.commit();
                onDestroy();
            }
		}

		public void onStatusChanged(String provider, int status, Bundle extras)
		{
			Log.e("LocalisationService","onStatusChanged");
		}

		public void onProviderEnabled(String provider)
		{
			Log.e("LocalisationService","onProviderEnabled: "+provider);
		}
		public void onProviderDisabled(String provider)
		{
			Log.e("LocalisationService","onProviderDisabled: "+provider);
		}
	};
	
	@Override
	public void onDestroy()
	{
        Log.i("loc","in service 5");
		unregisterReceiver(receiver);
        if ( ContextCompat.checkSelfPermission( this, android.Manifest.permission.ACCESS_COARSE_LOCATION ) == PackageManager.PERMISSION_GRANTED ) {
            locationManager.removeUpdates(locationListener);
        }
		super.onDestroy();
	}
}
