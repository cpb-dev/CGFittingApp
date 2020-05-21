package com.example.fittingapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //TODO DONE : Add Backgrounds to the pages
    //TODO : Add a logo to the toolbar and any necessary navigation
    //TODO DONE : Amend toolbar to include a link to add_fitting - INSTEAD a floating button was added

    //TODO DONE : Add a call method to the database that has been created

    //TODO DONE : Create a list adapter view that will describe how the data is shown
    //TODO DONE : Create an Array with ArrayAdapter for fitting list

    //TODO DONE : Add a method of accessing the fitting forms page

    DatabaseHelper completeGolfDB;

    /* Creating an array list to hold the display data for the fittings (customer name, phone, email and the fitting date/time) */
    /* This also calls to the get methods made in the fittings class */
    ArrayList<Fittings> fittingsList;
    ListView listView;
    Fittings fittings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* Database setup */
        completeGolfDB = new DatabaseHelper(this);

        /* Functions */
        generateList();

        /* On screen elements */
        FloatingActionButton addFit = findViewById(R.id.fabAddFit);
        addFit.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                openAddFitting();
            }
        });
    }

    /* A method for loading the list view */
    public void generateList() {

        /* Here is retrieving the data for the list view */
        fittingsList = new ArrayList<>();
        final Cursor data = completeGolfDB.getDbData();
        final int listLength = data.getCount(); //How many rows there are for fittings in the DB

        if(listLength == 0){ //Error Message
            /* As this is to be expected the first time you boot up the application, the add fitting page will be opened */
            Toast.makeText(MainActivity.this, "There are no fittings booked yet", Toast.LENGTH_LONG).show();
            openAddFitting();
        } else {
            while(data.moveToNext()) {
                /* Calling the fittings class and inserting the data from the DB */
                fittings = new Fittings(data.getString(0), data.getString(1),
                        data.getString(2), data.getString(3),
                        data.getString(4));
                fittingsList.add(fittings); //Adding the data to be displayed
            }

            /* Setting up the list view adapter using the list fittings class */
            ListFittings adapter = new ListFittings(this, R.layout.fitting_view,fittingsList);
            listView = findViewById(R.id.listView); //This finds the list view box created in the activity_main.xml
            listView.setAdapter(adapter);
        }

        // TODO DONE : Add an on click listener that allows the user to view the fitting info based on the clicked customer info
        // TODO DONE : Add a way in which the user can delete the selected fitting from the database

        /* OnItemClickListener for the list view of fittings */
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                /* Finding the fitting ID used in the database based on data pulled from the invisible ID text view */
                String ID = ((TextView) view.findViewById(R.id.textFitID)).getText().toString();

                /* IF statement to ensure fitting ID is collected properly */
                if(ID != null){
                    /* Creating an intent to move on to the fitting view based on the selected fitting ID */
                    Intent viewFittingIntent = new Intent(MainActivity.this, ViewFitting.class);
                    viewFittingIntent.putExtra("selFittingID", ID);
                    startActivity(viewFittingIntent);

                } else {
                    Toast.makeText(MainActivity.this, "Something went wrong, try again!", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    /* A method for opening the new fitting form */
    public void openAddFitting(){
        Intent intent = new Intent(this, AddFitting.class);
        startActivity(intent);
    }

}
