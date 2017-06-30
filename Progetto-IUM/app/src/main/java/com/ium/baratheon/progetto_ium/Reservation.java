package com.ium.baratheon.progetto_ium;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

/**
 *
 * Created by Riccardo Locci on 30/01/2017.
 */

class Reservation implements Serializable{
    private Calendar day;
    private int begin;
    private int end;
    private int ID;
    private Court court;
    static List<Reservation> reservationList;

    Reservation(){
        if(reservationList == null){
            reservationList = new ArrayList<>();
        }
    }

    Reservation(Calendar day, int begin, int end, Court court){
        this.day = day;
        this.begin = begin;
        this.end = end;
        this.court = court;
        if(reservationList==null){
            reservationList = new ArrayList<>();
        }
        this.ID = UUID.randomUUID().hashCode();
        reservationList.add(this);
    }

    Reservation(int ID, Calendar day, int begin, int end, Court court, Boolean addToList){
        this.ID = ID;
        this.day = day;
        this.begin = begin;
        this.end = end;
        this.court = court;
        if(addToList) {
            if (reservationList == null) {
                reservationList = new ArrayList<>();
            }
            reservationList.add(this);
        }
    }

    public String toString(){
        return "\n\tCampo " + this.court.getName() +
                "\n\n\tData: " + new SimpleDateFormat("dd-MM-yyyy", Locale.ITALIAN).format(day.getTime()) +
                "\n\n\tDalle " + this.begin + " alle " + this.end + "\n";
    }

    Calendar getDay() {
        return day;
    }

    public void setDay(Calendar day) {
        this.day = day;
    }

    public int getBegin() {
        return begin;
    }

    void setBegin(int begin) {
        this.begin = begin;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    Court getCourt() { return court; }

    void setCourt(Court court) { this.court = court; }

    int getID(){
        return this.ID;
    }

    static Reservation get(int id){
        System.out.println(reservationList);
        for(Reservation r: reservationList){
            if(r.ID == id){
                return r;
            }
        }
        return null;
    }

    static void remove(int id){
        for(Iterator<Reservation> i = reservationList.iterator();i.hasNext();){
            if(i.next().getID() == id){
                i.remove();
            }
        }
    }

    static void remove(List<Integer> list){
        for(Integer i: list){
            remove(i);
        }
    }

    static void removeAllNull(){
        reservationList.removeAll(Collections.singleton(null));
    }
}
