package com.ium.baratheon.progetto_ium;

import android.graphics.Bitmap;
import android.media.Image;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

/*
 * Created by Riccardo Locci on 30/01/2017.
 */

class User implements Serializable{
    private String username;
    private String password;
    private String name;
    private String surname;
    private String email;
    private int age;
    private Bitmap profilePic;
    private List<Reservation> reservation;
    private List<Boolean> favorites;
    static List<User> userList;

    public User(){
        this.reservation = new ArrayList<>();
        this.favorites = new ArrayList<>();
        for(Court c: Court.courtList){
            favorites.add(false);
        }
        if(userList==null){
            userList = new ArrayList<>();
        }
    }

    public User(String username, String password, String name, String surname, String email, int age, Bitmap profilePic){
        this.username = username;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.age = age;
        this.profilePic = profilePic;
        this.reservation = new ArrayList<>();
        this.favorites = new ArrayList<>();
        for(Court c: Court.courtList){
            favorites.add(false);
        }
        if(userList==null){
            userList = new ArrayList<>();
        }
        userList.add(this);
    }

    public static User getDefaultUser(){
        User u = new User("admin", "admin", "Admin", "Admin", "admin@admin.it", 22, null);
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR + 2016, Calendar.MONTH, Calendar.DAY_OF_MONTH);

        for(Court c: Court.courtList) {
            u.reservation.add(new Reservation(cal, 19, 20, c));
        }

        return u;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<Reservation> getReservation() {
        return reservation;
    }

    public void setReservation(List<Reservation> reservation) {
        this.reservation = reservation;
    }

    public List<Boolean> getFavorites() {
        return favorites;
    }

    public void setFavorites(List<Boolean> favorites) {
        this.favorites = favorites;
    }

    public Bitmap getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(Bitmap profilePic) {
        this.profilePic = profilePic;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
