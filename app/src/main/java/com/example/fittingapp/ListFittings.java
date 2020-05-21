package com.example.fittingapp;

/* This class is used to set up a list adapter for the list view in main activity */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ListFittings extends ArrayAdapter<Fittings> {

    private LayoutInflater mInflater;
    private ArrayList<Fittings> fittingsList;
    private int mViewResourceId;

    public ListFittings(Context context, int textViewResourceId, ArrayList<Fittings> fittingsList) {
        super(context, textViewResourceId, fittingsList);
        this.fittingsList = fittingsList;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mViewResourceId = textViewResourceId;
    }

    public View getView(int position, View convertView, ViewGroup parents) {

        convertView = mInflater.inflate(mViewResourceId,null);
        Fittings fittings = fittingsList.get(position);

        if(fittings != null) {
            /* These are the values of the columns made in fitting_view.xml */
            TextView fitID = (TextView) convertView.findViewById(R.id.textFitID);
            TextView custName = (TextView) convertView.findViewById(R.id.textListName);
            TextView custEmail = (TextView) convertView.findViewById(R.id.textListEmail);
            TextView custPhone = (TextView) convertView.findViewById(R.id.textListPhone);
            TextView fitDate = (TextView) convertView.findViewById(R.id.textListDate);

            /* If statements to fill in the list view (only if data is in the DB) */
            /* Data is retrieved through the get methods made in the fittings class */
            if (fitID != null){
                fitID.setText((fittings.getFitID()));
            }
            if (custName != null){
                custName.setText((fittings.getCustName()));
            }
            if (custEmail != null){
                custEmail.setText((fittings.getCustEmail()));
            }
            if (custPhone != null){
                custPhone.setText((fittings.getCustPhone()));
            }
            if (fitDate != null){
                fitDate.setText((fittings.getFitDate()));
            }
        }

        return convertView;
    }

}
