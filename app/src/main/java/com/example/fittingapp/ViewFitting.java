package com.example.fittingapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ViewFitting extends AppCompatActivity {

    DatabaseHelper completeGolfDB;
    Button btnSave, btnDelete;
    EditText etName, etEmail, etPhone, etFitDate, etFitTime, etFitWith;

    //TODO DONE : Make data fields pick up from clicked on data (via the list view in MainActivity)
    //TODO DONE : Add a delete button with functionality
    //TODO DONE : Have a method of updating the current information
    //TODO DONE : Amend toolbar to have a way back to the homepage
    //TODO DONE : Add a call method to the database that has been created

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_fitting);

        /* Creates the toolbar title and back button */
        getSupportActionBar().setTitle("Existing Customer View");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        /* Setting up the buttons and text views */
        btnSave = (Button) findViewById(R.id.btnSaveAmend);
        btnDelete = (Button) findViewById(R.id.btnDelete);

        etName = (EditText) findViewById(R.id.etEXCustName);
        etEmail = (EditText) findViewById(R.id.etEXCustEmail);
        etPhone = (EditText) findViewById(R.id.etEXCustPhone);
        etFitDate = (EditText) findViewById(R.id.etEXFitDate);
        etFitTime = (EditText) findViewById(R.id.etEXFitTime);
        etFitWith = (EditText) findViewById(R.id.etEXFitWith);

        /* Setting up the database */
        completeGolfDB = new DatabaseHelper(this);

        /* Functions */
        LoadData();

        /* On screen elements */
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* Getting the data passed from the MainActivity */
                Intent receiveIntent = getIntent();

                /* Passing the strings that will contain the updated data */
                String ID = receiveIntent.getStringExtra("selFittingID");
                String nName = etName.getText().toString();
                String nEmail = etEmail.getText().toString();
                String nPhone = etPhone.getText().toString();
                String nFDate = etFitDate.getText().toString();
                String nFTime = etFitTime.getText().toString();
                String nFWith = etFitWith.getText().toString();

                /* Calling the update function from the database */
                boolean update = completeGolfDB.updateFitting(ID, nName, nEmail, nPhone, nFDate, nFTime, nFWith);
                if(update == true){
                    Toast.makeText(ViewFitting.this, "Fitting Updated!", Toast.LENGTH_LONG).show();
                    openFittingList();
                } else {
                    Toast.makeText(ViewFitting.this, "Something went wrong!", Toast.LENGTH_LONG).show();
                }

            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /* Alert dialog box to ensure the user was meant to delete the fitting */
                AlertDialog.Builder builder = new AlertDialog.Builder(ViewFitting.this);

                String cName = etName.getText().toString(); //Retrieving the name of the current customer

                builder.setCancelable(true); //Allowing the user to back out of the decision
                builder.setTitle("Delete Fitting");
                builder.setMessage("Are you sure you want to delete the fitting for " + cName + "?"); //Individual customer delete message

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.setPositiveButton("Delete Fitting", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        /* Run the delete functions */
                        deleteFitting();
                        openFittingList();
                    }
                });

                builder.show(); //Display the dialog box

            }
        });

    }

    /* A function for loading the fitting info */
    public void LoadData(){
        /* Getting the data passed from the MainActivity */
        Intent receiveIntent = getIntent();

        String ID = receiveIntent.getStringExtra("selFittingID");
        /* Retrieving the data from the database based on the ID */
        Cursor data = completeGolfDB.getFitData(ID);

        /* Setting the text views to their corresponding data sets */
        while(data.moveToNext()){
            etName.setText(data.getString(1));
            etEmail.setText(data.getString(2));
            etPhone.setText(data.getString(3));
            etFitDate.setText(data.getString(4));
            etFitTime.setText(data.getString(5));
            etFitWith.setText(data.getString(6));
        }
    }

    /* Function leading back to the home page */
    public void openFittingList(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void deleteFitting(){
        /* Getting the data passed from the MainActivity */
        Intent receiveIntent = getIntent();

        String ID = receiveIntent.getStringExtra("selFittingID");
        completeGolfDB.deleteFitting(ID);
        Toast.makeText(ViewFitting.this, "Fitting Deleted!", Toast.LENGTH_LONG).show();
    }

}
