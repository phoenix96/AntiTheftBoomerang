package com.project.aditya.antitheftboomerang;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Rishabh Jain on 01-12-2016.
 */
public class help extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_help);

        TextView msg = (TextView) findViewById(R.id.msg_help_text);
        TextView perm = (TextView) findViewById(R.id.perm_help_text);
        TextView sig = (TextView)findViewById(R.id.textView9);
        TextView pc = (TextView)findViewById(R.id.textView7);

        String text = "By turning on this feature, you can get some basic control over your phone. There are in total three" +
                " options: Ringer, Alarm and Location. First of all you need to set a password for message control. This" +
                " is used whenever you wish to do something related to messaging. Using \n" +
                "Password + RINGER: Your Phone will be put to ringer mode, if its on silent mode.\n" +
                "Password + ALARM: An alarm will be raised on your phone.\n" +
                "Password + Location: You can get back the location of your device";

        String text2 = "By turning on this feature, you can get list of permissions that an application is accessing. If any undesired " +
                "permission is found, you may uninstall it!";

        String text3 = "For this feature, you must enable a password in your phone. After cheecking the password security, you enter a value " +
                "which is the number of wrong attempts. After the specified wrong attempts, the phone clicks the photo of the user and mails it to the registered id.";

        String text4 = "Signal Flare helps you to get the last locaton of your device by sending the location of the device when the battery is less than a certain percentage.";

        msg.setText(text);
        perm.setText(text2);
        pc.setText(text3);
        sig.setText(text4);
    }
}
