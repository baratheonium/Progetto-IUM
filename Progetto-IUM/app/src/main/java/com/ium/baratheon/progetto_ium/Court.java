package com.ium.baratheon.progetto_ium;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by Riccardo Locci on 30/01/2017.
 */

class Court implements Serializable{
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

    Court(){
        if(courtList == null){
            courtList = new ArrayList<Court>();
        }
    }

    Court(String name, String mail, String phoneNumber, int begin, int end, int fieldNumber, int price){
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

    Court(String name, String mail, int ID, String phoneNumber,
                 int begin, int end, int fieldNumber, int price){
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
        this.courtID = ID;
        this.courtPic = courtPic;
        courtList.add(this);
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

    Integer getBegin() {
        return begin;
    }

    void setBegin(int begin) {
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

    String getMail() {
        return mail;
    }

    void setMail(String mail) {
        this.mail = mail;
    }

    Integer getPrice() {
        return price;
    }

    void setPrice(int price) {
        this.price = price;
    }

    String getPhoneNumber() {
        return phoneNumber;
    }

    void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    int getID() {
        return courtID;
    }

    void setID(int ID) { this.courtID = ID; }

    Integer getFieldNumber() {
        return fieldNumber;
    }

    void setFieldNumber(Integer fieldNumber) {
        this.fieldNumber = fieldNumber;
    }

    static Court getCourt(int ID){
        for(Court c: courtList){
            if(c.courtID==ID){
                return c;
            }
        }
        return null;
    }
}
