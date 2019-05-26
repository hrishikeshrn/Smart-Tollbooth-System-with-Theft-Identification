package com.tollbooth.CommonClasses;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class DateFormation {


    public static String ValueDate(String date)
    {
        Date d = Calendar.getInstance().getTime(); // Current time
        if(!date.equals(""))
        {
            d= java.sql.Date.valueOf(date);
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // Set your date format
        String currentDate = sdf.format(d); // Get Date String according to date format
        return  currentDate;
    }

    public static String DisplayDate(String date)
    {
        Date d = Calendar.getInstance().getTime(); // Current time
        if(!date.equals(""))
        {
            d= java.sql.Date.valueOf(date);
        }
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy"); // Set your date format
        String currentDate = sdf.format(d); // Get Date String according to date format
        return currentDate;
    }

    public static String StoreDate(String date)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Date testDate = null;
        try {
            testDate = sdf.parse(date);
        }catch(Exception ex){
            ex.printStackTrace();
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); // Set your date format
        String currentDate = dateFormat.format(testDate); // Get Date String according to date format
        return  currentDate;
    }

    public static String DateDialog(String date)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy"); // Set your date format
        String currentDate = sdf.format(date); // Get Date String according to date format
        return currentDate;
    }

    public static String ValueTime()
    {
        Date d = Calendar.getInstance().getTime(); // Current time
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss"); // Set your date format
        String currentDate = sdf.format(d); // Get Date String according to date format
        return  currentDate;
    }
}
