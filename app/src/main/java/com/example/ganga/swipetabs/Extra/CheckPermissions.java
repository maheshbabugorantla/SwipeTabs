package com.example.ganga.swipetabs.Extra;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

/**
 * Created by Mahesh Babu Gorantla on 1/1/17.
 */

public class CheckPermissions {

    private final Context getApplicationContext;
    private final Activity getActivity;

    // Constants for the Permission Checks
    private static final int MY_REQUEST_CALL_PHONE = 0;
    private static final int MY_REQUEST_READ_CONTACTS = 1;
    private static final int MY_REQUEST_READ_CALL_LOG = 2;

    public CheckPermissions(Context context, Activity activity) {
        this.getApplicationContext = context.getApplicationContext();
        this.getActivity = activity;
    }

    public void checkDialPermission() {

        // Flag to check if the user has permitted the app to allow access to the Dialing Features
        // of the Device
        // If the app has the permission, the method returns 'PackageManager.PERMISSION_GRANTED'
        final int dialPermission = ContextCompat.checkSelfPermission(getApplicationContext, Manifest.permission.CALL_PHONE);

        // Requesting Permission if not granted
        if(dialPermission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity, new String[]{Manifest.permission.CALL_PHONE}, MY_REQUEST_CALL_PHONE);
        }
    }

    public void checkContactsPermission() {

        // Flag to check if the used has permitted the to allow access to the Contacts List
        // on the Device.
        // If the app has the permission, the method returns 'PackageManager.PERMISSION_GRANTED'
        final int readContacts = ContextCompat.checkSelfPermission(getApplicationContext, Manifest.permission.READ_CONTACTS);

        // Requesting Permission if not granted
        if(readContacts != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity, new String[] {Manifest.permission.READ_CONTACTS}, MY_REQUEST_READ_CONTACTS);
        }
    }

    public void checkCallLogsPermission() {

        // Flag to check if the used has permitted the to allow access to the Contacts List
        // on the Device.
        // If the app has the permission, the method returns 'PackageManager.PERMISSION_GRANTED'
        final int readCallLog = ContextCompat.checkSelfPermission(getApplicationContext, Manifest.permission.READ_CALL_LOG);

        // Requesting Permission if not granted
        if(readCallLog != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity, new String[] {Manifest.permission.READ_CALL_LOG}, MY_REQUEST_READ_CALL_LOG);
        }
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {

            // If the request is cancelled the result arrays are empty.
            case MY_REQUEST_CALL_PHONE: {

                if(grantResults.length == 0) {

                }
            }

            case MY_REQUEST_READ_CONTACTS: {
                if(grantResults.length == 0) {

                }
            }

            case MY_REQUEST_READ_CALL_LOG: {
                if(grantResults.length == 0) {

                }
            }
        }
    }
}
