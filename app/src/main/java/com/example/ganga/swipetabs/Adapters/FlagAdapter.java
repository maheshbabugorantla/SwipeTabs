package com.example.ganga.swipetabs.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ganga.swipetabs.Extra.FlagItem;
import com.example.ganga.swipetabs.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Mahesh Babu Gorantla on 1/1/17.
 */

public class FlagAdapter extends ArrayAdapter<FlagItem> {

    LayoutInflater inflater;
    ViewHolder holder = null;
    ArrayList<FlagItem> flagItems;

    public FlagAdapter(Context context, int resource, ArrayList<FlagItem> flagItems) {
        super(context, resource, flagItems);
        inflater = ((Activity) context).getLayoutInflater();
        this.flagItems = flagItems;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getFlagView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    public View getFlagView(int position, View convertView, ViewGroup parent) {

        FlagItem flagItem = flagItems.get(position);
        View row = convertView;

        if(row == null) {
            holder = new ViewHolder();
            row = inflater.inflate(R.layout.flag_view, parent, false);
            holder.flag = (ImageView) row.findViewById(R.id.flag_dropped);
            row.setTag(holder);
        }
        else {
            holder = (ViewHolder) row.getTag();
        }

        Picasso.with(getContext()).load(flagItem.getFlag()).resize(95,65).onlyScaleDown().into(holder.flag);

        return row;
    }

    public View getCustomView(int position, View convertView, ViewGroup parent) {

        FlagItem flagItem = flagItems.get(position);
        View row = convertView;

        if(row == null) {
            holder = new ViewHolder();
            row = inflater.inflate(R.layout.drop_down_item, parent, false);
            holder.countryName = (TextView) row.findViewById(R.id.countryName);
            holder.flag = (ImageView) row.findViewById(R.id.flag_down);
            row.setTag(holder);
        }

        else {
            holder = (ViewHolder) row.getTag();
        }

        holder.countryName.setText(flagItem.getCountryName());
        Picasso.with(getContext()).load(flagItem.getFlag()).resize(50,25).onlyScaleDown().into(holder.flag);

        return row;
    }

    private static class ViewHolder {
        TextView countryName;
        ImageView flag;
    }
}
