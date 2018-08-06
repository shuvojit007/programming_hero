package com.example.test.programmingmama.Utils;

import java.text.DateFormat;
import java.util.Date;

import static java.text.DateFormat.getDateTimeInstance;

public class TimeUtils {

    public static String getTimeDate(long timeStamp){
        try{
            DateFormat dateFormat = getDateTimeInstance();
            Date netDate = (new Date(timeStamp));
            return dateFormat.format(netDate);
        } catch(Exception e) {
            return "date";
        }
    }
}
