package com.project.aditya.antitheftboomerang;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.os.Vibrator;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Rishabh Jain on 17-09-2016.
 */

public class SmsBroadcastListener extends BroadcastReceiver {

    private Intent localisationIntent;
    public static final void vibratePhone(Context context, int vibrateMilliSeconds) {
        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(vibrateMilliSeconds);
    }
    public void gen_alarm(Context context)
    {
        MediaPlayer mp = null;
        boolean start = false;
        final AudioManager audio = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        int max = audio.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        final int setVolFlags = AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE | AudioManager.FLAG_VIBRATE;
        audio.setStreamVolume(AudioManager.STREAM_MUSIC, max, setVolFlags);
        mp = MediaPlayer.create(context, R.raw.siren);
        vibratePhone(context,1000);
        mp.start();
        mp.setLooping(true);

    }

    public void send_location(Context context)
    {
        Log.i("loc","function_called");
        localisationIntent = new Intent(context,LocalisationService.class);
        context.startService(localisationIntent);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        //Log.i("broad","start");
        Bundle extras = intent.getExtras();
        String strMsgBody = "";
        SharedPreferences sp = context.getSharedPreferences("data",0);
        String password = sp.getString("sms_password","");
        String isRingerChecked = sp.getString("ringer","");
        String isAlarmChecked = sp.getString("alarm","");
        String isLocationChecked = sp.getString("location","");

        //Log.i("broad",password);
        if(password.equals(""))
            return;

        if (extras != null) {
            Object[] sms_extras = (Object[]) extras.get("pdus");

            for (int i = 0; i < sms_extras.length; i++) {
                SmsMessage sms_msg = SmsMessage.createFromPdu((byte[]) sms_extras[i]);
                strMsgBody = sms_msg.getMessageBody().toString();
            }

            if(isRingerChecked.equals("true")) {
                String code = "RINGER";
                for (int i = 0; i < strMsgBody.length() - code.length() - password.length(); i++) {
                    if (strMsgBody.substring(i, i + code.length() + password.length() + 1).equals(password + " " + code)) {

                        AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
                        audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                        int max_volume = audioManager.getStreamMaxVolume(AudioManager.STREAM_RING);
                        audioManager.setStreamVolume(AudioManager.STREAM_RING, max_volume, 0);

                        break;
                    }
                }
            }

            if(isAlarmChecked.equals("true")) {
                String code = "ALARM";
                for (int i = 0; i < strMsgBody.length() - code.length() - password.length(); i++) {
                    if (strMsgBody.substring(i, i + code.length() + password.length() + 1).equals(password + " " + code)) {
                        gen_alarm(context);
                        break;
                    }
                }
            }
            if(isLocationChecked.equals("true")) {
                String code = "LOCATION";
                for (int i = 0; i < strMsgBody.length() - code.length() - password.length(); i++) {
                    if (strMsgBody.substring(i, i + code.length() + password.length() + 1).equals(password + " " + code)) {
                        Log.i("loc","msg received!");

                        SharedPreferences.Editor editor =context.getSharedPreferences("data",0).edit();
                        editor.putString("SmsHasToBeSent", "true");
                        editor.commit();

                        send_location(context);
                        break;
                    }
                }
            }

        }
    }
}

class KMP_String_Matching
{
    void KMPSearch(String pat, String txt)
    {
        int M = pat.length();
        int N = txt.length();
        int lps[] = new int[M];
        int j = 0;  // index for pat[]

        // Preprocess the pattern (calculate lps[]
        // array)
        computeLPSArray(pat,M,lps);

        int i = 0;  // index for txt[]
        while (i < N)
        {
            if (pat.charAt(j) == txt.charAt(i))
            {
                j++;
                i++;
            }
            if (j == M)
            {
                System.out.println("Found pattern "+ "at index " + (i-j));
                j = lps[j-1];
            }

            // mismatch after j matches
            else if (i < N && pat.charAt(j) != txt.charAt(i))
            {
                // Do not match lps[0..lps[j-1]] characters,
                // they will match anyway
                if (j != 0)
                    j = lps[j-1];
                else
                    i = i+1;
            }
        }
    }

    void computeLPSArray(String pat, int M, int lps[])
    {
        // length of the previous longest prefix suffix
        int len = 0;
        int i = 1;
        lps[0] = 0;  // lps[0] is always 0

        // the loop calculates lps[i] for i = 1 to M-1
        while (i < M)
        {
            if (pat.charAt(i) == pat.charAt(len))
            {
                len++;
                lps[i] = len;
                i++;
            }
            else  // (pat[i] != pat[len])
            {
                // This is tricky. Consider the example.
                // AAACAAAA and i = 7. The idea is similar
                // to search step.
                if (len != 0)
                {
                    len = lps[len-1];

                    // Also, note that we do not increment
                    // i here
                }
                else  // if (len == 0)
                {
                    lps[i] = len;
                    i++;
                }
            }
        }
    }
}