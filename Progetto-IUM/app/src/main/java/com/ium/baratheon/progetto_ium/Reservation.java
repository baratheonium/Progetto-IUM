package com.ium.baratheon.progetto_ium;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
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

    Reservation(int ID, Calendar day, int begin, int end, Court court){
        this.ID = ID;
        this.day = day;
        this.begin = begin;
        this.end = end;
        this.court = court;
        if(reservationList==null){
            reservationList = new ArrayList<>();
        }
        reservationList.add(this);
    }

    public String toString(){
        return "\n\tCampo " + this.court.getName() +
                "\n\n\tData: " + this.day.get(Calendar.DAY_OF_MONTH) + " - " +
                this.day.get(Calendar.MONTH) + " - " + this.day.get(Calendar.YEAR) +
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
        for(Reservation r: reservationList){
            if(r.ID == id){
                return r;
            }
        }

        return null;
    }
}
