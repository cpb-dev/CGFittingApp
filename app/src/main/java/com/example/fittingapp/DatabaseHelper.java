package com.example.fittingapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;


public class DatabaseHelper extends SQLiteOpenHelper {

    //TODO DONE : Create variables | DB, Table, Columns

    /* Creating the Database */
    public static final String DATABASE_NAME = "completeGolf.db";
    public static final String TABLE_NAME = "fittings";

    /* Creating the data within database */
    public static final String COL1 = "ID";
    public static final String COL2 = "Name";
    public static final String COL3 = "Email";
    public static final String COL4 = "Phone";
    public static final String COL5 = "Date";
    public static final String COL6 = "Time";
    public static final String COL7 = "FitWith"; //Who the fitting is with


    public DatabaseHelper(@Nullable Context context) {
        /* Declaring the database and the version that will be worked on */
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO DONE : Create table and columns ready for data
        /* SQL code making the table for the fittings */
        String createTable = "CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                " Name TEXT, Email TEXT, Phone VARCHAR(15), Date DATE, Time TIMESTAMP, FitWith TEXT)";
        db.execSQL(createTable); //Running the SQL code made
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        /* This is declaring how the database will update its information */
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    //TODO DONE : Create a boolean that adds data to the table (this is a method called in the rest of the app)
    //TODO DONE : Add a confirmation method for when data has been successfully inputted
    public boolean addData(String name, String email, String phone, String date, String time, String fitwith) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues(); //Setting up the input of the tables values
        contentValues.put(COL2, name);
        contentValues.put(COL3, email);
        contentValues.put(COL4, phone);
        contentValues.put(COL5, date);
        contentValues.put(COL6, time);
        contentValues.put(COL7, fitwith);

        /* Check if the data has been inserted correctly */
        long result = db.insert(TABLE_NAME, null, contentValues);

        if(result == -1) { //This value is provided with the insert statement above
            return false;
        } else{
            return true;
        }
    }

    //TODO DONE : Add a way to extract the data for the list view
    public Cursor getDbData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return data;
    }

    //TODO DONE: Add functions for editing and deleting data based on either the id or all the data inputted
    public Cursor getFitData(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE ID = " + id, null);
        return data;
    }

    /* UPDATE query based on the given fitting ID */
    public boolean updateFitting(String id, String CustName, String CustEmail, String CustPhone, String FitDate, String FitTime, String FitWith){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL1, id);
        contentValues.put(COL2, CustName);
        contentValues.put(COL3, CustEmail);
        contentValues.put(COL4, CustPhone);
        contentValues.put(COL5, FitDate);
        contentValues.put(COL6, FitTime);
        contentValues.put(COL7, FitWith);
        db.update(TABLE_NAME, contentValues, "ID = ?", new String[] {id});
        return true;
    }

    /* Delete fitting based on fitting ID */
    public void deleteFitting(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_NAME + " WHERE ID = " + id;
        db.execSQL(query);
    }

}
