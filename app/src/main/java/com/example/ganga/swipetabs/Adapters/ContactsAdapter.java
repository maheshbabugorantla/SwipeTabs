package com.example.ganga.swipetabs.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ganga.swipetabs.Extra.ContactItem;
import com.example.ganga.swipetabs.R;

import java.util.ArrayList;

/**
 * Created by Mahesh Babu Gorantla on 1/1/17.
 *
 */

public class ContactsAdapter extends ArrayAdapter<ContactItem> {

    private Context context;
    private ArrayList<ContactItem> contactItems;

    public ContactsAdapter(Context context, int resource, ArrayList<ContactItem> objects) {
        super(context, resource, objects);
        this.contactItems = objects;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getContactView(position, convertView, parent);
    }

    private View getContactView(int position, View convertView, ViewGroup parent) {

        ContactItem contact = contactItems.get(position);

        ViewHolder viewHolder;

        // ViewHolder Pattern
        if(convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.contact_item, parent, false);
            viewHolder.contactPhoto = (ImageView) convertView.findViewById(R.id.contactPhoto);
            viewHolder.contactName = (TextView) convertView.findViewById(R.id.contactName);
            viewHolder.contactNumber = (TextView) convertView.findViewById(R.id.contactPhone);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.contactName.setText(contact.getContactName());
        viewHolder.contactNumber.setText(contact.getContactNumber());
        viewHolder.contactPhoto.setImageURI(contact.getContactImage());

        return convertView;
    }

    private static class ViewHolder {
        ImageView contactPhoto;
        TextView contactName;
        TextView contactNumber;
    }
}