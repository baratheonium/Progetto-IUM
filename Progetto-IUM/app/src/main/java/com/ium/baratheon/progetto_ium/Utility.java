package com.ium.baratheon.progetto_ium;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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

    static List<Reservation> populateSearchResult(Court c, String day, String start, String end){
        List<Reservation> list = new ArrayList<>();
        if(c!=null) {
            int begins = c.getBegin(), ends = c.getEnd();

            int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
            if (Calendar.getInstance().get(Calendar.MINUTE) > 0) hour++;

            Calendar cal = Calendar.getInstance();
            if (day != null && !day.isEmpty()) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.ITALIAN);

                try {
                    cal.setTime(sdf.parse(day));
                } catch (ParseException e) {
                    cal.setTime(new Date());
                    if (begins < hour) begins = hour;
                }
            } else if (begins < hour) begins = hour;

            if (start != null && !start.isEmpty()) {
                hour = Integer.parseInt(start.substring(0, start.indexOf(':')));
                if (begins < hour) {
                    begins = hour;
                    if (Integer.parseInt(start.substring(start.indexOf(':') + 1)) > 0)
                        begins++;
                }
            }
            if (end != null && !end.isEmpty()) {
                hour = Integer.parseInt(end.substring(0, end.indexOf(':')));
                if (ends > hour) ends = hour;
            }

            for (int i = begins; i < ends; i++) {
                Reservation r = new Reservation();
                r.setBegin(i);
                r.setEnd(i + 1);
                r.setCourt(c);
                r.setDay(cal);
                if (!DBHandler.getInstance().lookForReservation(r)) list.add(r);
            }
        }

        return list;
    }
}
