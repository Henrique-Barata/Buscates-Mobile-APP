package com.example.buscatelas.Utils;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.widget.Toast;

import com.example.buscatelas.Login;
import com.example.buscatelas.ProviderActivity;
import com.example.buscatelas.models.Client;
import com.example.buscatelas.models.ServiceProvider;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.onesignal.OneSignal;

public class Authentication {

    private FirebaseAuth firebaseAuth;
    private Activity activity;
    private Database db;
    private FirebaseUser currentUser;


    public Authentication(Activity activity) {
        firebaseAuth = FirebaseAuth.getInstance();
        this.activity = activity;
        this.db = new Database();
    }

    public void registerClientWithEmailAndPassword(String email, String password, String name, String phoneNumber) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // registration successful
                            Toast.makeText(activity, "Registration Successful", Toast.LENGTH_SHORT).show();
                            currentUser = firebaseAuth.getCurrentUser();
                            String userId = currentUser.getUid();
                            Client client = new Client(name, email, phoneNumber);
                            db.pushClient(client, userId);

                        } else {
                            // registration failed
                            Toast.makeText(activity, "Registration Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void loginWithEmailAndPassword(String email, String password) {
        System.out.println("entrou aqui?");
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            System.out.println("get current user: " + firebaseAuth.getCurrentUser().getUid());
                            currentUser = firebaseAuth.getCurrentUser();
                            Toast.makeText(activity, "Login Successful", Toast.LENGTH_SHORT).show();
                        } else {
                            // login failed
                            Toast.makeText(activity, "Login Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void registerProviderWithEmailAndPassword(String email, String password, String name, String phoneNumber) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            ServiceProvider serviceProvider = new ServiceProvider(name, email, phoneNumber);
                            currentUser = firebaseAuth.getCurrentUser();
                            String userId = currentUser.getUid();
                            db.pushServiceProvider(serviceProvider, userId);
                            Toast.makeText(activity, "Registration Successful", Toast.LENGTH_SHORT).show();
                        } else {
                            // registration failed
                            Toast.makeText(activity, "Registration Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public FirebaseUser getCurrentUser(){
        return this.currentUser;
    }

    public boolean isLoggedIn(){
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if (currentUser != null) {
            return true;
        } else {
            return false;
        }
    }

    public void signOut(){
        this.firebaseAuth.signOut();
    }

    public FirebaseAuth getFirebaseAuth(){
        return firebaseAuth;
    }




}
