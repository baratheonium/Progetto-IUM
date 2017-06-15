package com.ium.baratheon.progetto_ium;

import android.graphics.Bitmap;
import android.media.Image;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Riccardo Locci on 30/01/2017.
 */

public class Court implements Serializable{
    private String name;
    private String mail;
    private String phoneNumber;
    private int courtID;
    private Integer begin;
    private Integer end;
    private Integer fieldNumber;
    private Integer price;
    private Bitmap courtPic;  //Cambiato il tipo di courtpic da Image a Bitmap
    static List<Court> courtList;

    public Court(){
        if(courtList == null){
            courtList = new ArrayList<Court>();
        }
    }

    public Court(String name, String mail, String phoneNumber, int begin, int end, int fieldNumber, int price){
        this.name = name;
        this.mail = mail;
        this.phoneNumber = phoneNumber;
        this.begin = (begin >= 0 && begin <= 24) ? begin : 15;
        this.end = (end >= 0 && end <= 24 && end > begin) ? end : 16;
        this.fieldNumber = fieldNumber;
        this.price = price;
        if(courtList == null){
            courtList = new ArrayList<Court>();
        }
        courtList.add(this);
        this.courtID = courtList.size();
    }

    public static void setDefaultCourtList(){
        if(courtList == null) {
            new Court("Pincopallino", "pincopallino@yahoo.it", "0123456789", 16, 23, 6, 35);
            new Court("Sempronio", "sempronio@yahoo.it", "0123456789", 15, 22, 2, 40);
            new Court("Caio", "caio@yahoo.it", "0123456789", 16, 24, 4, 25);
            new Court("Gnegnegnegne", "gnegnegnegne@yahoo.it", "0123456789", 17, 24, 3, 30);
            new Court("Lalalalala", "lalalalala@yahoo.it", "0123456789", 16, 23, 2, 30);
        }
    }

    public String toString(){
        return "\n\tNome: \t\t" + this.name +
                "\n\tAperto:\t\t" + this.begin + " - " + this.end + "\n";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getBegin() {
        return begin;
    }

    public void setBegin(int begin) {
        this.begin = begin;
    }

    public Integer getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public Integer getNumber() {
        return getFieldNumber();
    }

    public void setNumber(int number) {
        this.setFieldNumber(number);
    }

    public Bitmap getCourt() {
        return courtPic;
    }

    public void setCourt(Bitmap courtPic) {
        this.courtPic = courtPic;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getID() {
        return courtID;
    }

    public void setID(int ID) { this.courtID = ID; }

    public Integer getFieldNumber() {
        return fieldNumber;
    }

    public void setFieldNumber(Integer fieldNumber) {
        this.fieldNumber = fieldNumber;
    }
}
