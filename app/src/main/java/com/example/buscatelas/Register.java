package com.example.buscatelas;
import com.example.buscatelas.Utils.Authentication;
import com.example.buscatelas.Utils.Database;
import com.example.buscatelas.models.Client;
import com.example.buscatelas.models.ServiceProvider;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;



import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.auth.User;

import java.util.ArrayList;

public class Register extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private Database databs;
    private Authentication authentication;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
        databs = new Database();

        ActionBar ab = getSupportActionBar();

        ab.setDisplayHomeAsUpEnabled(true);

        Button registerButton = findViewById(R.id.registerSuccessBtn);
        EditText emailT = findViewById(R.id.registerEmail);
        EditText passwordT = findViewById(R.id.passwordRegister);
        EditText nameT = findViewById(R.id.personName);
        EditText numberT = findViewById(R.id.editTextPhone);
        EditText hourlyRateT = findViewById(R.id.hourlyRate);
        TextView hourlyText = findViewById(R.id.textView7);

        Switch s = findViewById(R.id.workerSwitch);
        s.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    hourlyRateT.setVisibility(View.VISIBLE);
                    hourlyText.setVisibility(View.VISIBLE);
                } else{
                    hourlyRateT.setVisibility(View.INVISIBLE);
                    hourlyText.setVisibility(View.INVISIBLE);
                }
            }
        });
        mDatabase = FirebaseDatabase.getInstance().getReference();

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailT.getText().toString().trim();
                String password = passwordT.getText().toString().trim();
                String name = nameT.getText().toString().trim();
                String number = numberT.getText().toString().trim();
                String hourlyRate = hourlyRateT.getText().toString().trim();
                boolean workerSwitch = ((Switch) findViewById(R.id.workerSwitch)).isChecked();
                registerUser(email, password, name, number, hourlyRate, workerSwitch);
            }
        });

    }

    //Add transições e merdas assim, fazer retrieve do user tbm e adicionar firebase
    private void registerUser(String email, String password, String name, String number, String hourlyRate, boolean workerSwitch){
        if ( isFieldEmpty(email)) {
            Toast.makeText(Register.this, "Empty Email",
                    Toast.LENGTH_SHORT).show();

        } else if(isFieldEmpty(password)){
            Toast.makeText(Register.this, "Empty Password",
                    Toast.LENGTH_SHORT).show();

        }else {
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (isFieldEmpty(email)) {
                                Toast.makeText(Register.this, "Empty Email",
                                        Toast.LENGTH_SHORT).show();

                            } else if (isFieldEmpty(password)) {
                                Toast.makeText(Register.this, "Empty Password",
                                        Toast.LENGTH_SHORT).show();

                            } else if (isFieldEmpty(number)) {
                                Toast.makeText(Register.this, "Empty Phone Number",
                                        Toast.LENGTH_SHORT).show();

                            } else if (isFieldEmpty(name)) {
                                Toast.makeText(Register.this, "Empty Name",
                                        Toast.LENGTH_SHORT).show();

                            //} else if (isFieldEmpty(hourlyRate) && workerSwitch) {
                             //   Toast.makeText(Register.this, "Empty Hourly Rate",
                              //          Toast.LENGTH_SHORT).show();

                            } else if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                FirebaseUser user = mAuth.getCurrentUser();
                                if (workerSwitch) {
                                    mAuth.createUserWithEmailAndPassword(email, password)
                                            .addOnCompleteListener(Register.this, new OnCompleteListener<AuthResult>() {
                                                @Override
                                                public void onComplete(Task<AuthResult> task) {
                                                    if (task.isSuccessful()) {
                                                        ServiceProvider serviceProvider = new ServiceProvider(name, email, number);
                                                        currentUser = mAuth.getCurrentUser();
                                                        String userId = currentUser.getUid();
                                                        databs.pushServiceProvider(serviceProvider, userId);
                                                        Toast.makeText(Register.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                                                    } else {
                                                        // registration failed
                                                        Toast.makeText(Register.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                                                        ServiceProvider serviceProvider = new ServiceProvider(name, email, number);
                                                        currentUser = mAuth.getCurrentUser();
                                                        String userId = currentUser.getUid();
                                                        databs.pushServiceProvider(serviceProvider, userId);
                                                        System.out.println(task.getException());
                                                    }
                                                }
                                            });
                                } else {

                                    mAuth.createUserWithEmailAndPassword(email, password)
                                            .addOnCompleteListener(Register.this, new OnCompleteListener<AuthResult>() {
                                                @Override
                                                public void onComplete(Task<AuthResult> task) {
                                                    if (task.isSuccessful()) {
                                                        // registration successful
                                                        Toast.makeText(Register.this, "Registration Successful_", Toast.LENGTH_SHORT).show();
                                                        currentUser = mAuth.getCurrentUser();
                                                        String userId = currentUser.getUid();
                                                        Client client = new Client(name, email, number);
                                                        databs.pushClient(client, userId);

                                                    } else {
                                                        // registration successful
                                                        Toast.makeText(Register.this, "Registration Successful_", Toast.LENGTH_SHORT).show();
                                                        currentUser = mAuth.getCurrentUser();
                                                        String userId = currentUser.getUid();
                                                        Client client = new Client(name, email, number);
                                                        databs.pushClient(client, userId);
                                                        System.out.println(task.getException());
                                                    }
                                                }
                                            });
                                }

                                startActivity(new Intent(Register.this, Login.class));

                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(Register.this, "User already registered ",
                                        Toast.LENGTH_SHORT).show();
                                System.out.println(task.getException().toString());
                            }
                        }
                    });
        }
    }


    private boolean isFieldEmpty(String text){
        return text.trim().length() == 0;

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(Register.this, Login.class));
        }
        return super.onOptionsItemSelected(item);
    }


}