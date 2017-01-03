package com.example.ganga.swipetabs.Fragments;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.ganga.swipetabs.Extra.CheckPermissions;
import com.example.ganga.swipetabs.Adapters.FlagAdapter;
import com.example.ganga.swipetabs.Extra.FlagItem;
import com.example.ganga.swipetabs.R;

import java.util.ArrayList;

/**
 * Created by Mahesh Babu Gorantla on 1/1/17.
 */

public class DialScreen extends Fragment implements Spinner.OnItemSelectedListener {

    int[] flags = {R.drawable.australia, R.drawable.brazil, R.drawable.china, R.drawable.india,
            R.drawable.italy, R.drawable.new_zealand, R.drawable.russia, R.drawable.uk, R.drawable.usa};
    String[] countries = {"Australia", "Brazil", "China", "India", "Italy", "New Zealand",
            "Russia", "UK", "USA"};
    String[] internationalCodes = {"+61", "+55", "+86", "+91", "+39", "+64", "+7", "+44", "+1"};

    int selectedPosition = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        checkPermissions();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_dial_screen, container, false);

        final Button dialButton = (Button) rootView.findViewById(R.id.Dial);
        dialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialPhone(rootView);
            }
        });

        Spinner flagsList = (Spinner) rootView.findViewById(R.id.flags);

        // Adapter for the Spinner
        flagsList.setAdapter(new FlagAdapter(getActivity(), R.layout.drop_down_item, getFlagItems()));
        flagsList.setOnItemSelectedListener(this);

        return rootView;
    }

    public void dialPhone(View view) {

        EditText phoneNo = (EditText) view.findViewById(R.id.phoneNumber);
        String phoneNumber = internationalCodes[selectedPosition] + phoneNo.getText().toString();

        try {
            startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNumber)));
        }
        catch (SecurityException e) {
            Log.e("Android Permission", e.toString());
        }
    }

    public ArrayList<FlagItem> getFlagItems() { // Associating Flag Icon with corresponding countries.

        ArrayList<FlagItem> flagItems = new ArrayList<>();

        for(int index = 0; index < countries.length; index++) {
            FlagItem flagItem = new FlagItem(countries[index], flags[index]);
            flagItems.add(flagItem);
        }

        return flagItems;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        selectedPosition = position;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    // This method is used to check the permissions granted for the App by the user.
    private void checkPermissions() {

        CheckPermissions checkPermissions = new CheckPermissions(getActivity().getApplicationContext(), getActivity());

        // Checks if the user has permitted the app to make phone calls
        checkPermissions.checkDialPermission();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        CheckPermissions checkPermissions = new CheckPermissions(getActivity().getApplicationContext(), getActivity());

        checkPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
