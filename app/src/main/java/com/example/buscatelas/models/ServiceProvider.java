package com.example.buscatelas.models;

import android.app.Service;
import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class ServiceProvider {

    private String name;
    private String email;
    private String phoneNumber;
    private double hourlyRate;
    private double balance;
    private ArrayList<String> skills;
    private boolean isOccupied;
    private float rating;
    private String id;
    private ArrayList<Request> pastRequests;
    private String token;
    private LatLng location;



    public ServiceProvider(){

    }
    public ServiceProvider(String name, String email, String phoneNumber){
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.balance = 0;
        this.rating = 0;
        this.isOccupied = false;
        skills = new ArrayList<>();
        pastRequests = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public double getHourlyRate() {
        return hourlyRate;
    }

    public void setHourlyRate(double hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public ArrayList<String> getSkills() {
        return skills;
    }

    public void setSkills(ArrayList<String> skills) {
        this.skills = skills;
    }

    public boolean isOccupied() {
        return isOccupied;
    }

    public void setOccupied(boolean occupied) {
        isOccupied = occupied;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void addRequest(Request r){
        pastRequests.add(r);
    }

    public ArrayList<Request> getPastRequests() {
        return pastRequests;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LatLng getLocation() {
        return location;
    }

    public void setLocation(LatLng location) {
        location = location;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }
}
