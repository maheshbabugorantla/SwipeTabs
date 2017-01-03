package com.example.ganga.swipetabs.Extra;

import android.net.Uri;

/**
 * Created by Mahesh Babu Gorantla on 1/1/17.
 *
 * This Class is used to populate the Contacts List
 */

public class ContactItem {

    private Uri contactImage;
    private String contactName;
    private String contactNumber;

    public ContactItem(Uri contactImage, String contactName, String contactNumber) {
        this.contactImage = contactImage;
        this.contactName = contactName;
        this.contactNumber = contactNumber;
    }

    public Uri getContactImage() {
        return contactImage;
    }

    public String getContactName() {
        return contactName;
    }

    public String getContactNumber() {
        return contactNumber;
    }
}
