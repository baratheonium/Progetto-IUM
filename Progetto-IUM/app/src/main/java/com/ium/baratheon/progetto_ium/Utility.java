package com.ium.baratheon.progetto_ium;

import java.util.Calendar;

/**
 *
 * Created by Riccardo Locci on 14/04/2017.
 **/

class Utility {

    static String toDoubleDigit(Integer n){
        return n < 10 ? 0 + "" + n : n.toString();}

    static boolean isPastHour(String hour){
        Calendar c = Calendar.getInstance();
        int h = Integer.parseInt(hour.substring(0,2)),
                m = Integer.parseInt(hour.substring(3,5));

        return (Integer.valueOf(c.get(Calendar.HOUR_OF_DAY)).equals(h) && c.get(Calendar.MINUTE) > m) || c.get(Calendar.HOUR_OF_DAY) > h;
    }

    static boolean isToday(String date){
        Calendar c = Calendar.getInstance();

        int day = Integer.parseInt(date.substring(0,2)),
                month = Integer.parseInt(date.substring(3,5)),
                year = Integer.parseInt(date.substring(6,10));

        return Integer.valueOf(c.get(Calendar.DAY_OF_MONTH)).equals(day) &&
                Integer.valueOf(c.get(Calendar.MONTH)+1).equals(month) &&
                Integer.valueOf(c.get(Calendar.YEAR)).equals(year);
    }

    static boolean isEndBeforeStart(String start, String end){
        Integer startHour = Integer.parseInt(start.substring(0,2)),
                startMinute = Integer.parseInt(start.substring(3,5)),
                endHour = Integer.parseInt(end.substring(0,2)),
                endMinute = Integer.parseInt(end.substring(3,5));

        return (startHour.equals(endHour) && startMinute > endMinute)  || startHour > endHour;
    }

    public static void setUp(){
        DBHandler.getInstance().setAllCourts();
        DBHandler.getInstance().setAllReservations();
    }
}
