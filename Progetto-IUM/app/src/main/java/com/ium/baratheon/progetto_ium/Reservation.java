package com.ium.baratheon.progetto_ium;

import android.content.Context;
import android.graphics.Canvas;
import android.view.View;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Riccardo Locci on 30/01/2017.
 */

public class Reservation implements Serializable{
    private Calendar day;
    private int begin;
    private int end;
    private int ID;
    private Court court;
    static List<Reservation> reservationList;

    public Reservation(){
        if(reservationList == null){
            reservationList = new ArrayList<Reservation>();
        }
    }

    public Reservation(Calendar day, int begin, int end, Court court){
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
                "\n\n\tData: " + this.day.get(Calendar.DAY_OF_MONTH) + " - " + this.day.get(Calendar.MONTH) + " - " + this.day.get(Calendar.YEAR) +
                "\n\n\tDalle " + this.begin + " alle " + this.end + "\n";
    }

    public Calendar getDay() {
        return day;
    }

    public void setDay(Calendar day) {
        this.day = day;
    }

    public int getBegin() {
        return begin;
    }

    public void setBegin(int begin) {
        this.begin = begin;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public Court getCourt() { return court; }

    public void setCourt(Court court) { this.court = court; }

    public int getID(){
        return this.ID;
    }

    public static Reservation get(int id){
        for(Reservation r: reservationList){
            if(r.ID == id){
                return r;
            }
        }

        return null;
    }
}
