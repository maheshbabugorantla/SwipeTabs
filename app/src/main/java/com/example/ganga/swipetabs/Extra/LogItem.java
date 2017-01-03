package com.example.ganga.swipetabs.Extra;

import android.net.Uri;

/**
 * Created by Ganga on 1/2/17.
 */

public class LogItem {

    private Uri imageUri;
    private String contactName;
    private String contactPhone;
    private int callType;

    public LogItem(Uri imageUri, String contactName, String contactPhone, int callType) {

        this.imageUri = imageUri;
        this.contactName = contactName;
        this.contactPhone = contactPhone;
        this.callType = callType;
    }

    public String getContactName() {
        return contactName;
    }

    public int getCallType() {
        return callType;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public Uri getImageUri() {
        return imageUri;
    }
}
