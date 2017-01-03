package com.example.ganga.swipetabs.Adapters;

import android.content.Context;
import android.provider.CallLog;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ganga.swipetabs.Extra.LogItem;
import com.example.ganga.swipetabs.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Mahesh Babu Gorantla on 1/2/17.
 */

public class CallLogAdapter extends ArrayAdapter<LogItem> {

    private Context context;
    private ArrayList<LogItem> callLogs;

    public CallLogAdapter(Context context, int resource, ArrayList<LogItem> objects) {
        super(context, resource, objects);
        this.context = context;
        this.callLogs = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getLogItem(position, convertView, parent);
    }

    public View getLogItem(int position, View convertView, ViewGroup parent) {

        LogItem logItem = callLogs.get(position);
        ViewHolder viewHolder = new ViewHolder();

        if(convertView == null) {

            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.call_log_item, parent, false);

            viewHolder.contactImage = (ImageView) convertView.findViewById(R.id.contactImage);
            viewHolder.contactName = (TextView) convertView.findViewById(R.id.conName);
            viewHolder.contactNumber = (TextView) convertView.findViewById(R.id.conNumber);
            viewHolder.callType = (ImageView) convertView.findViewById(R.id.callType);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // Setting the Call Type Icon
        switch (logItem.getCallType()){

            case CallLog.Calls.INCOMING_TYPE:
                Picasso.with(getContext()).load(R.drawable.incoming_call).resize(35,35).onlyScaleDown().into(viewHolder.callType);
                break;

            case CallLog.Calls.OUTGOING_TYPE:
                Picasso.with(getContext()).load(R.drawable.outgoing_call).resize(35,35).onlyScaleDown().into(viewHolder.callType);
                break;

            case CallLog.Calls.MISSED_TYPE:
                Picasso.with(getContext()).load(R.drawable.missed_call).resize(35,35).onlyScaleDown().into(viewHolder.callType);
                break;
        }

        if(logItem.getImageUri() == null) {
            Picasso.with(getContext()).load(R.drawable.india).resize(60, 60).onlyScaleDown().into(viewHolder.contactImage);
        }
        else {
            Picasso.with(getContext()).load(logItem.getImageUri()).resize(60, 60).onlyScaleDown().into(viewHolder.contactImage);
            //viewHolder.contactImage.setImageURI(logItem.getImageUri());
        }

        viewHolder.contactName.setText(logItem.getContactName());
        viewHolder.contactNumber.setText(logItem.getContactPhone());

        return convertView;
    }

    private static class ViewHolder {
        ImageView contactImage;
        TextView contactName;
        TextView contactNumber;
        ImageView callType;
    }
}
