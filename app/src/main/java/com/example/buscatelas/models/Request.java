package com.example.buscatelas.models;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

public class Request {

    public Client client;
    public ServiceProvider serviceProvider;
    public String description;
    public Instant startTime;
    public Instant endTime;
    public String specialization;
    public double totalPrice;
    public boolean done;
    public String id;

    public Request(){

    }
    public Request(Client client, String description, String specialization){
        this.id = String.valueOf(UUID.randomUUID());
        this.client = client;
        this.description = description;
        this.done = false;
        this.specialization = specialization;
    }

    public ServiceProvider getServiceProvider() {
        return serviceProvider;
    }

    public void setServiceProvider(ServiceProvider serviceProvider) {
        this.serviceProvider = serviceProvider;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public boolean isDone() {
        return done;
    }

    public Client getClient() {
        return client;
    }

    public String getDescription() {
        return description;
    }

    public Instant getStartTime() {
        return startTime;
    }

    public Instant getEndTime() {
        return endTime;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void setTotalPrice() {
        this.totalPrice = calculateTotalPrice();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private double calculateTotalPrice(){
        long hours = ChronoUnit.HOURS.between(startTime,endTime);
        return serviceProvider.getHourlyRate() * hours;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getSpecialization() {
        return specialization;
    }
}
