package com.example.buscatelas.ui.settings;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.buscatelas.Login;
import com.example.buscatelas.R;
import com.example.buscatelas.Register;
import com.example.buscatelas.databinding.FragmentSettingsBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.auth.User;

public class SettingsFragment extends Fragment {

    private SettingsViewModel settingsViewModel;
    private FragmentSettingsBinding binding;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    private User currentUser;
    private FirebaseUser currentFBUser;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        settingsViewModel =
                new ViewModelProvider(this).get(SettingsViewModel.class);

        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        currentFBUser = mAuth.getCurrentUser();

        getUser();

        if(container!= null){
            container.removeAllViews();
        }
        ConstraintLayout layout;

        Button changeEmailBtn = root.findViewById(R.id.changeEmailBtn);
        Button changePasswordBtn = root.findViewById(R.id.changePassBtn);
        Button changePhoneBtn = root.findViewById(R.id.changePhoneBtn);

        Button logout = root.findViewById(R.id.buttonl1);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Login.class));
            }
        });

        Switch darkswitch = root.findViewById(R.id.switch1);
        View seting = this.getView();
        darkswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    binding.getRoot().setBackgroundResource(R.color.dark);
                    // darkswitch.setBackgroundResource(R.color.dark);
                } else{
                    binding.getRoot().setBackgroundResource(R.color.white);
                }
            }
        });

        changeEmailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new ChangeEmailFragment());

            }
        });

        changePasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new ChangePasswordFragment());

            }
        });


        changePhoneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new ChangePhoneFragment());

            }
        });

        return root;
    }

    private void getUser(){
        DatabaseReference uidRef = mDatabase.child("users").child(currentFBUser.getUid());
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                currentUser = dataSnapshot.getValue(User.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        uidRef.addListenerForSingleValueEvent(valueEventListener);
    }


    public void replaceFragment(Fragment fragment){
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_fragment_activity_client, fragment);
        transaction.addToBackStack(null);
        transaction.commit();

    }




}