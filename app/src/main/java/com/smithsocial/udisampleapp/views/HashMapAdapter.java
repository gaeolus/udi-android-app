package com.smithsocial.udisampleapp.views;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

class HashMapAdapter extends ArrayAdapter {

    private static class ViewHolder {
        TextView deviceId;
        TextView brandName;
    }

    HashMapAdapter(Context context, int resource, int textViewResourceId, List<Map.Entry<String, String>> objects) {
        super(context, resource, textViewResourceId, objects);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_list_item_2, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.brandName = (TextView) convertView.findViewById(android.R.id.text1);
            viewHolder.deviceId = (TextView) convertView.findViewById(android.R.id.text2);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Map.Entry<String, String> entry = (Map.Entry<String, String>) this.getItem(position);

        viewHolder.deviceId.setText(entry.getKey());
        viewHolder.brandName.setText(entry.getValue());
        return convertView;
    }

}
