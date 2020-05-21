package com.example.fittingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddFitting extends AppCompatActivity {

    DatabaseHelper completeGolfDB; //Gave it a name close to the actual database

    /* Creating variables for the items in the form */
    Button btnSave;
    EditText etName, etEmail, etPhone, etFitDate, etFitTime, etFitWith;

    //TODO DONE : Add database support
    //TODO DONE : Create edit text capture variables

    //TODO DONE : Add a save button method with a way of setting the string variables to the user inputs
    //TODO DONE : Insert the addData function to input the entered data into the database

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_fitting);

        /* Creates the toolbar title and back button */
        getSupportActionBar().setTitle("Add Fitting Form");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        /* Adding an input for the database within the add fitting class */
        completeGolfDB = new DatabaseHelper(this);

        /* Setting the variables for the edit text boxes made within the xml */
        etName = (EditText) findViewById(R.id.etCustName);
        etEmail = (EditText) findViewById(R.id.etCustEmail);
        etPhone = (EditText) findViewById(R.id.etCustPhone);
        etFitDate = (EditText) findViewById(R.id.etFitDate);
        etFitTime = (EditText) findViewById(R.id.etFitTime);
        etFitWith = (EditText) findViewById(R.id.etFitWith);

        btnSave = (Button) findViewById(R.id.btnSave);

        /* Function for save button */
        btnSave.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String name = etName.getText().toString();
                String email = etEmail.getText().toString();
                String phone = etPhone.getText().toString();
                String date = etFitDate.getText().toString();
                String time = etFitTime.getText().toString();
                String fitwith = etFitWith.getText().toString();

                if(name.length() != 0 && email.length() != 0 && phone.length() != 0 && date.length() != 0 && time.length() != 0 && fitwith.length() != 0){

                    /* Other validations in a else if statement */
                    if(!name.matches("^[A-Za-z\\s]+$")) {
                        Toast.makeText(AddFitting.this, "Invalid name! Make sure there's no numbers or special characters!", Toast.LENGTH_LONG).show();
                    } else if(!email.matches("[A-Za-z0-9._-]+@[A-Za-z]+\\.+[a-z]+")) {
                        Toast.makeText(AddFitting.this, "Invalid email address!", Toast.LENGTH_LONG).show();
                    } else if(phone.length() >= 12) {
                        Toast.makeText(AddFitting.this, "Phone number invalid!", Toast.LENGTH_LONG).show();
                    }

                    /* The final else statement that saves the fitting */
                    else {
                        /* Add the data into the database */
                        AddData(name, email, phone, date, time, fitwith);
                        /* Setting the edit texts back to a default */
                        //TODO DONE : Make this lead back to the home page as well
                        etName.setText("");
                        etEmail.setText("");
                        etPhone.setText("");
                        etFitDate.setText("");
                        etFitTime.setText("");
                        etFitWith.setText("");

                        openFittingList();
                    }

                    /* Else statement for when fields have been left empty */
                } else {
                    Toast.makeText(AddFitting.this, "You must fill in all fields!", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    /* A function that adds the data to the database when the user clicks the save button */
    public void AddData(String name, String email, String phone, String date, String time, String fitwith) {
        boolean insertData = completeGolfDB.addData(name, email, phone, date, time, fitwith);

        /* User feedback on if the data has been saved */
        if(insertData == true) {
            Toast.makeText(AddFitting.this, "Fitting has been saved successfully!", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(AddFitting.this, "Unable to save fitting! Try again!", Toast.LENGTH_LONG).show();
        }
    }

    /* A method for opening the new fitting form */
    public void openFittingList(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}
