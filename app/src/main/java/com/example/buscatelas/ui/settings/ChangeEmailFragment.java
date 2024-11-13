package com.example.buscatelas.ui.settings;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.buscatelas.ClientActivity;
import com.example.buscatelas.Login;
import com.example.buscatelas.ProviderActivity;
import com.example.buscatelas.R;
import com.example.buscatelas.databinding.FragmentChangeEmailBinding;
import com.example.buscatelas.models.Client;
import com.example.buscatelas.ui.Client.Lista_Completos_Servicos_client;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.auth.User;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChangeEmailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChangeEmailFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    private FragmentChangeEmailBinding binding;

    private Client currentUser;
    private FirebaseUser currentFBUser;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ChangeEmailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChangeEmailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChangeEmailFragment newInstance(String param1, String param2) {
        ChangeEmailFragment fragment = new ChangeEmailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentChangeEmailBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        //currentFBUser = mAuth.getCurrentUser();

        //getUser();

        Button changeEmailBtn = root.findViewById(R.id.emailChangeBtn);

        changeEmailBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                EditText oldEmailEdit = root.findViewById(R.id.newEmailEdit);
                EditText passwordT = root.findViewById(R.id.editTextTextPassword);

                ImageButton show = root.findViewById(R.id.imageButton5);
                show.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(passwordT.getTransformationMethod() == null) {
                            passwordT.setTransformationMethod(new PasswordTransformationMethod());
                        } else
                            passwordT.setTransformationMethod(null);
                    }
                });
                String oldEmail = oldEmailEdit.getText().toString().trim();
                String pass = passwordT.getText().toString().trim();

                //changeEmail(oldEmail, pass);

                if (isFieldEmpty(oldEmail) || isFieldEmpty(pass)) {
                    Toast.makeText(getContext(), "Fields are empty",
                            Toast.LENGTH_SHORT).show();
                }else{
                    mAuth.signInWithEmailAndPassword(oldEmail, pass)
                            .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    if (task.isSuccessful()) {
                                        Toast.makeText(getContext(), "Success!",
                                                Toast.LENGTH_SHORT).show();
                                        FragmentTransaction fragmentTransaction = getActivity()
                                                .getSupportFragmentManager().beginTransaction();
                                        fragmentTransaction.replace(R.id.nav_host_fragment_activity_client, new SettingsFragment());
                                        fragmentTransaction.commit();
                                    }else {
                                        Toast.makeText(getContext(), "Fail!",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
            });

        return root;

    }

    private boolean isFieldEmpty(String text){
        return text.length() == 0;

    }

    private void getUser(){
        DatabaseReference uidRef = mDatabase.child("users").child(currentFBUser.getUid());
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                currentUser = dataSnapshot.getValue(Client.class);
                System.out.println("Current user:" + currentUser);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        uidRef.addListenerForSingleValueEvent(valueEventListener);
    }

    private void changeEmail(String oldEmail, String newEmail){
        if(currentUser == null){
            Toast.makeText(getActivity(), "User is null",
                    Toast.LENGTH_SHORT).show();
        }
        if(!oldEmail.equals(currentUser.getEmail())){
            Toast.makeText(getActivity(), "Old email is not correct",
                    Toast.LENGTH_SHORT).show();
        }
        else {
            currentUser.setEmail(newEmail);
            currentFBUser.updateEmail(newEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(getActivity(), "Email adresss updated with success",
                                Toast.LENGTH_SHORT).show();

                        mDatabase.child("users").child(currentFBUser.getUid()).setValue(currentUser);
                        getParentFragmentManager().popBackStack();
                    }

                }
            });




        }
    }



}