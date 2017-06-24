package com.ium.baratheon.progetto_ium;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
    private List<Integer> reservation; //private List<Integer> reservation;
    private List<Integer> favorites; //private List<Boolean> favorites;
    static List<User> userList;

    User(){
        this.reservation = new ArrayList<>();
        this.favorites = new ArrayList<>();
        if(userList==null){
            userList = new ArrayList<>();
        }
    }

    User(String username, String password, String name, String surname, String email, int age, Bitmap profilePic){
        this.username = username;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.age = age;
        this.profilePic = profilePic;
        this.reservation = new ArrayList<>();
        this.favorites = new ArrayList<>();
        if(userList==null){
            userList = new ArrayList<>();
        }
        userList.add(this);
    }

    User(String username, String password, String name, String surname, String email, int age,
                Bitmap profilePic, List<Integer> reservation, List<Integer> favorites){
        this.username = username;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.age = age;
        this.profilePic = profilePic;
        this.reservation = reservation;
        this.favorites = favorites;
        if(userList==null){
            userList = new ArrayList<>();
        }
        userList.add(this);
    }

    String getUsername() {
        return username;
    }

    void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    String getSurname() {
        return surname;
    }

    void setSurname(String surname) {
        this.surname = surname;
    }

    String getEmail() {
        return email;
    }

    void setEmail(String email) {
        this.email = email;
    }

    int getAge() {
        return age;
    }

    void setAge(int age) {
        this.age = age;
    }

    List<Integer> getReservation() { return reservation; }

    public void addReservation(int id) { this.reservation.add(id); }

    boolean getFavorite(Court court){
        return this.favorites.contains(court.getID());
    }

    void addFavorite(Integer id){ this.favorites.add(id); }

    void removeFavorite(Integer id){
        if(this.favorites.contains(id)) {
            this.favorites.remove(id);
        }
    }

    String getPassword() {
        return password;
    }

    void setPassword(String password) {
        this.password = password;
    }
}
