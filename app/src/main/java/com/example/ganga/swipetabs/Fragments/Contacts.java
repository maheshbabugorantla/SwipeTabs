package com.example.ganga.swipetabs.Fragments;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.ganga.swipetabs.Adapters.ContactsAdapter;
import com.example.ganga.swipetabs.Extra.CheckPermissions;
import com.example.ganga.swipetabs.Extra.ContactItem;
import com.example.ganga.swipetabs.R;

import java.util.ArrayList;

/**
 * Created by Mahesh Babu Gorantla on 1/1/17.
 *
 */

public class Contacts extends Fragment {

    ArrayList<ContactItem> contacts;
    Context mContext;

    ContactsAdapter contactsAdapter;
    ListView contactsList;

    ContentResolver contentResolver;
    ContentObserver contentObserver;

    boolean registeredFlag = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        checkPermissions();
        contacts = readContacts();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_contacts_screen, container, false);

        contactsList = (ListView) rootView.findViewById(R.id.contactsList);
        contactsAdapter = new ContactsAdapter(getContext(), R.layout.contact_item, contacts);
        contactsList.setAdapter(contactsAdapter);

        contentResolver = mContext.getContentResolver();
        contentObserver = new ContactsObserver(new Handler());
        contentResolver.registerContentObserver(ContactsContract.Contacts.CONTENT_URI, true, contentObserver);

        registeredFlag = true;
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        if(!registeredFlag) {
            contentResolver.registerContentObserver(ContactsContract.Contacts.CONTENT_URI, true, contentObserver);
            registeredFlag = false;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if(registeredFlag) {
            contentResolver.unregisterContentObserver(contentObserver);
            registeredFlag =  false;
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    public ArrayList<ContactItem> readContacts() {

        ArrayList<ContactItem> contactItems = new ArrayList<>();

        try {

            FragmentActivity fragmentActivity = getActivity();

            if(fragmentActivity != null) {
                ContentResolver cr = getActivity().getApplication().getContentResolver();
                Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                        null, null, null, ContactsContract.Contacts.DISPLAY_NAME + " ASC");

                if (cur.getCount() > 0) {
                    while (cur.moveToNext()) {

                        ContactItem contactItem;

                        String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
                        Uri photoUri = getPhotoUri(Long.parseLong(id));
                        String name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                        if (Integer.parseInt(cur.getString(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                            //System.out.println("name : " + name + ", ID : " + id);

                            // get the phone number
                            Cursor pCur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                                    new String[]{id}, null);

                            while (pCur.moveToNext()) {
                                String phone = pCur.getString(
                                        pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                                //System.out.println("phone" + phone);
                                //System.out.println("Photo Uri: " + photoUri);

                                contactItem = new ContactItem(photoUri, name, phone);
                                contactItems.add(contactItem);
                            }
                            pCur.close();


                            // get email and type
                  /*  Cursor emailCur = cr.query(
                            ContactsContract.CommonDataKinds.Email.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?",
                            new String[]{id}, null);
                    while (emailCur.moveToNext()) {
                        // This would allow you get several email addresses
                        // if the email addresses were stored in an array
                        String email = emailCur.getString(
                                emailCur.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
                        String emailType = emailCur.getString(
                                emailCur.getColumnIndex(ContactsContract.CommonDataKinds.Email.TYPE));

                        System.out.println("Email " + email + " Email Type : " + emailType);
                    }
                    emailCur.close();

                    // Get note.......
                    String noteWhere = ContactsContract.Data.CONTACT_ID + " = ? AND " + ContactsContract.Data.MIMETYPE + " = ?";
                    String[] noteWhereParams = new String[]{id,
                            ContactsContract.CommonDataKinds.Note.CONTENT_ITEM_TYPE};
                    Cursor noteCur = cr.query(ContactsContract.Data.CONTENT_URI, null, noteWhere, noteWhereParams, null);
                    if (noteCur.moveToFirst()) {
                        String note = noteCur.getString(noteCur.getColumnIndex(ContactsContract.CommonDataKinds.Note.NOTE));
                        System.out.println("Note " + note);
                    }
                    noteCur.close(); */

                            //Get Postal Address....
                /*  String addrWhere = ContactsContract.Data.CONTACT_ID + " = ? AND " + ContactsContract.Data.MIMETYPE + " = ?";
                    String[] addrWhereParams = new String[]{id,
                            ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_ITEM_TYPE};
                    Cursor addrCur = cr.query(ContactsContract.Data.CONTENT_URI,
                            null, null, null, null);
                    while(addrCur.moveToNext()) {
                        String poBox = addrCur.getString(
                                addrCur.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.POBOX));
                        String street = addrCur.getString(
                                addrCur.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.STREET));
                        String city = addrCur.getString(
                                addrCur.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.CITY));
                        String state = addrCur.getString(
                                addrCur.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.REGION));
                        String postalCode = addrCur.getString(
                                addrCur.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.POSTCODE));
                        String country = addrCur.getString(
                                addrCur.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.COUNTRY));
                        String type = addrCur.getString(
                                addrCur.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.TYPE));

                        // Do something with these....

                    }
                    addrCur.close();
               */

                            // Get Instant Messenger.........
                /*    String imWhere = ContactsContract.Data.CONTACT_ID + " = ? AND " + ContactsContract.Data.MIMETYPE + " = ?";
                    String[] imWhereParams = new String[]{id,
                            ContactsContract.CommonDataKinds.Im.CONTENT_ITEM_TYPE};
                    Cursor imCur = cr.query(ContactsContract.Data.CONTENT_URI,
                            null, imWhere, imWhereParams, null);
                    if (imCur.moveToFirst()) {
                        String imName = imCur.getString(
                                imCur.getColumnIndex(ContactsContract.CommonDataKinds.Im.DATA));
                        String imType;
                        imType = imCur.getString(
                                imCur.getColumnIndex(ContactsContract.CommonDataKinds.Im.TYPE));
                    }
                    imCur.close();
                */

                            // Get Organizations.........
                /*  String orgWhere = ContactsContract.Data.CONTACT_ID + " = ? AND " + ContactsContract.Data.MIMETYPE + " = ?";
                    String[] orgWhereParams = new String[]{id,
                            ContactsContract.CommonDataKinds.Organization.CONTENT_ITEM_TYPE};
                    Cursor orgCur = cr.query(ContactsContract.Data.CONTENT_URI,
                            null, orgWhere, orgWhereParams, null);
                    if (orgCur.moveToFirst()) {
                        String orgName = orgCur.getString(orgCur.getColumnIndex(ContactsContract.CommonDataKinds.Organization.DATA));
                        String title = orgCur.getString(orgCur.getColumnIndex(ContactsContract.CommonDataKinds.Organization.TITLE));
                    }
                    orgCur.close();
                */
                        }
                    }
                    cur.close();
                }
            }
        }

        catch (NullPointerException e) {
            e.printStackTrace();
        }

        return contactItems;
    }

    public Uri getPhotoUri(long contactId) {

        Uri contactUri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, contactId);
        return Uri.withAppendedPath(contactUri, ContactsContract.Contacts.Photo.CONTENT_DIRECTORY);
    }

    private void checkPermissions() {

        CheckPermissions checkPermissions = new CheckPermissions(getActivity().getApplicationContext(), getActivity());

        // Checks if the user has permitted the app to make phone calls
        checkPermissions.checkContactsPermission();
    }

    public class ContactsObserver extends ContentObserver {

        /**
         * Creates a content observer.
         *
         * @param handler The handler to run {@link #onChange} on, or null if none.
         */


        public ContactsObserver(Handler handler) {
            super(handler);
        }

        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);

            System.out.println("Inside OnChange");

            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    contacts.clear();
                    contacts.addAll(readContacts());
                    contactsAdapter.notifyDataSetChanged();
                }
            });
        }

        @Override
        public boolean deliverSelfNotifications() {
            return true;
        }
    }
}