package com.example.ganga.swipetabs.Fragments;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.ganga.swipetabs.Adapters.CallLogAdapter;
import com.example.ganga.swipetabs.Extra.CheckPermissions;
import com.example.ganga.swipetabs.Extra.LogItem;
import com.example.ganga.swipetabs.R;

import java.util.ArrayList;

/**
 * Created by Mahesh Babu Gorantla on 1/1/17.
 *
 * This class is used to deal with CallLogs Fragment.
 */

public class CallLogs extends Fragment {

    Context mContext;

    ListView listView;
    ArrayList<LogItem> callLogs = new ArrayList<>();

    ContentResolver contentResolver;
    ContentObserver callLogsObserver;
    CallLogAdapter callLogAdapter;

    boolean [] contentObservers = {false, false};


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        checkPermissions();
        callLogs = getCallLog();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        System.out.println("Inside onCreateView");

        View rootView = inflater.inflate(R.layout.fragment_call_logs, container, false);

        listView = (ListView) rootView.findViewById(R.id.callsLog);
        callLogAdapter = new CallLogAdapter(getContext(), R.layout.call_log_item, callLogs);
        listView.setAdapter(callLogAdapter);

        contentResolver = mContext.getContentResolver();
        callLogsObserver = new CallLogsObserver(new Handler()); //, callLogs, callLogAdapter);
        contentResolver.registerContentObserver(CallLog.Calls.CONTENT_URI, true, callLogsObserver);
        contentObservers[0] = true;
        contentObservers[1] = false;

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        /*for(LogItem logItem: callLogs) {
            System.out.println(logItem.toString());
        }*/
        if(!contentObservers[0]) {
            System.out.println("Inside onResume");
            contentResolver.registerContentObserver(CallLog.Calls.CONTENT_URI, true, callLogsObserver);
            contentObservers[0] = true;
            contentObservers[1] = false;
        }
    }

    @Override
    public void onDestroy() {

        super.onDestroy();

        System.out.println("Inside onDestroy");

        if (!contentObservers[1]) {
            contentResolver.unregisterContentObserver(callLogsObserver);
            contentObservers[1] = true;
            contentObservers[0] = false;
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    public ArrayList<LogItem> getCallLog() {

        ArrayList<LogItem> callLogs = new ArrayList<>();

        try {
            Cursor cursor = getActivity().getContentResolver().query(CallLog.Calls.CONTENT_URI, null, null, null, CallLog.Calls.DATE + " DESC");

            int number = cursor.getColumnIndex(CallLog.Calls.NUMBER);
            int type = cursor.getColumnIndex(CallLog.Calls.TYPE);
            int name = cursor.getColumnIndex(CallLog.Calls.CACHED_NAME);

            while(cursor.moveToNext()) {

                String PhoneNumber = cursor.getString(number);
                String callType = cursor.getString(type);
                String contactName = cursor.getString(name);
                String contactId = getIDfromNumber(PhoneNumber);

                int directionCode = Integer.parseInt(callType);

                Uri photoUri = null;

                if(contactId != null) {
                    photoUri = getPhotoUri(Long.parseLong(contactId));
                }

                LogItem callLog = new LogItem(photoUri, contactName, PhoneNumber, directionCode);
                callLogs.add(callLog);
            }

            cursor.close();
        }
        catch (NullPointerException e) {
            e.printStackTrace();
        }

        return callLogs;
    }

    private void checkPermissions() {

        CheckPermissions checkPermissions = new CheckPermissions(getActivity().getApplicationContext(), getActivity());

        // Checks if the user has permitted the app to read phone calls log
        checkPermissions.checkCallLogsPermission();
    }

    public Uri getPhotoUri(long contactId) {

        Uri contactUri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, contactId);
        return Uri.withAppendedPath(contactUri, ContactsContract.Contacts.Photo.CONTENT_DIRECTORY);
    }

    private String getIDfromNumber(String number) {

        try {
            String[] projection = new String[]{ContactsContract.CommonDataKinds.Phone.CONTACT_ID};

            Uri contactUri = Uri.withAppendedPath(ContactsContract.CommonDataKinds.Phone.CONTENT_FILTER_URI, Uri.encode(number));

            Cursor cursor = getContext().getContentResolver().query(contactUri, projection, null, null, null);

            if (cursor.moveToFirst()) {
                return cursor.getString(cursor.getColumnIndex(projection[0]));
            }

            cursor.close();
        }
        catch (NullPointerException e) {
            e.printStackTrace();
        }
        return null;
    }

    public class CallLogsObserver extends ContentObserver {

        /**
         * Creates a content observer.
         *
         * @param handler The handler to run {@link #onChange} on, or null if none.
         */

        public CallLogsObserver(Handler handler) { //, ArrayList<LogItem> contactItems, CallLogAdapter callLogAdapter) {
            super(handler);
            //this.callLogItems = contactItems;
            //this.callLogsAdapter = callLogAdapter;
        }

        @Override
        public void onChange(boolean selfChange, Uri uri) {
            super.onChange(selfChange);

            System.out.println("Changed Content: " + uri);

            System.out.println("Inside onChange");

            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    updateCallLog();
                }
            });
        }

        private void updateCallLog() {

            callLogs.clear();
            callLogs.addAll(getCallLog());
            callLogAdapter.notifyDataSetChanged();
        }

        @Override
        public boolean deliverSelfNotifications() {
            return true;
        }
    }
}
