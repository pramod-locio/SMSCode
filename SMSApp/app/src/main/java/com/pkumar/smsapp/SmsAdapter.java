package com.pkumar.smsapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by P KUMAR on 31-05-2016.
 */
public class SmsAdapter extends ArrayAdapter<SmsInfo> {
    public SmsAdapter(Context context, ArrayList<SmsInfo> smsInfos) {
        super(context, 0, smsInfos);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Get the data item for this position
        SmsInfo sms = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.sms, parent, false);
        }
        // Lookup view for data population
        ImageView senderImage = (ImageView) convertView.findViewById(R.id.senderImage);
        TextView senderAddress = (TextView) convertView.findViewById(R.id.senderAddress);
        TextView date = (TextView) convertView.findViewById(R.id.date);
        TextView body = (TextView) convertView.findViewById(R.id.body);

        // Populate the data into the template view using the data object
        senderImage.setImageResource(sms.senderImage);
        senderAddress.setText(sms.senderAddress);
        body.setText(sms.body);
        date.setText(sms.date);

        // Return the completed view to render on screen
        return convertView;
    }
}
