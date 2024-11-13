package com.example.buscatelas.models;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class Client {

    private String name;
    private String email;
    private String phoneNumber;
    private boolean occupied;
    private String id;
    private String token;
    private ArrayList<Request> pastRequests;
    private LatLng location;

    public Client(){

    }
    public Client(String name, String email, String phoneNumber){
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.occupied = false;
        pastRequests = new ArrayList<>();
        this.token = token;
    }

    public void setOccupied(){
        this.occupied = true;
    }

    public void setUnoccupied(){
        this.occupied  = false;
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

    public boolean isOccupied() {
        return occupied;
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
        this.location = location;
    }
}
