package com.example.fittingapp;

public class Fittings {

    /* This is a class  that retrieves the fitting info */
    /* This will be used to display the customer info based on the database in the home screen list */

    private String fitID;
    private String custName;
    private String custEmail;
    private String custPhone;
    private String fitDate;

    public Fittings(String ID, String cName, String Email, String cPhone, String fDate) {
        /* Making variables to be called within the main activity */
        fitID = ID;
        custName = cName;
        custEmail = Email;
        custPhone = cPhone;
        fitDate = fDate;
    }

    public String getFitID(){
        return fitID;
    }
    public String getCustName(){
        return custName;
    }
    public String getCustEmail(){
        return custEmail;
    }
    public String getCustPhone(){
        return custPhone;
    }
    public String getFitDate(){
        return fitDate;
    }
}
