package com.ium.baratheon.progetto_ium;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
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

    List<Integer> getReservation() {
        if(reservation != null) {
            Reservation.removeAllNull();
            Collections.sort(reservation, new Comparator<Integer>() {
                @Override
                public int compare(Integer o1, Integer o2) {
                    try {
                        return Reservation.get(o2).getDay().compareTo(Reservation.get(o1).getDay());
                    }
                    catch(NullPointerException e){
                        if(Reservation.get(o2) == null) return 1;
                        else return -1;
                    }
                }
            });

            return reservation;
        }

        return null;
    }

    public void addReservation(int id) { this.reservation.add(id); }

    void addReservations(List<Reservation> list){
        for(Reservation r: list){
            this.reservation.add(r.getID());
        }

        Collections.sort(this.reservation, new Comparator<Integer>(){
            @Override
            public int compare(Integer s1, Integer s2) {
                try {
                    return Reservation.get(s2).getDay().compareTo(Reservation.get(s1).getDay());
                }
                catch(NullPointerException e){
                    if(Reservation.get(s2) == null) return 1;
                    else return -1;
                }
            }
        });

    }

    void removeReservations(List<Integer> list){
        this.reservation.removeAll(list);
        Reservation.removeAllNull(this.reservation);
    }

    void removeReservation(Reservation reservation){
        for(Iterator<Integer> i = this.reservation.iterator(); i.hasNext();){
            if(i.next().equals(reservation.getID())){
                i.remove();
            }
        }

        Reservation.removeAllNull(this.reservation);
    }

    List<Integer> getFavorites(){
        return this.favorites;
    }

    boolean getFavorite(Court court){
        return this.favorites.contains(court.getID());
    }

    void addFavorite(Integer id){ this.favorites.add(id); }

    void removeFavorite(Integer id){
        if(this.favorites.contains(id)) {
            this.favorites.remove(id);
        }

        for(Iterator<Integer> i = this.favorites.iterator(); i.hasNext();){
            if(i.next().equals(id)){
                i.remove();
            }
        }
    }

    String getPassword() {
        return password;
    }

    void setPassword(String password) {
        this.password = password;
    }

    Bitmap getProfilePic(){
        return this.profilePic;
    }

    static User getUser(String username, String password){
        if(userList != null) {
            for (User u : userList) {
                if (u.getUsername().equals(username) && u.getPassword().equals(password)) {
                    return u;
                }
            }
        }

        return null;
    }

    public String toString(){
        return "Nome: " + this.name + "\nCognome: " + this.surname +
                "\nUsername: " + this.username + "\nPassword: " + this.password +
                "\nEtà: " + this.age + "\nPrenotazioni: " + this.reservation + "\nPreferiti: " + this.favorites;
    }
}
