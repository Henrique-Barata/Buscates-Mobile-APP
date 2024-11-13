package com.example.buscatelas.Utils;

import android.util.Log;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.Map;

public class Firestore {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    public void writeData(String collection, Map<String, Object> data) {
        db.collection(collection).add(data)
                .addOnSuccessListener(documentReference -> Log.d("Firestore", "DocumentSnapshot added with ID: " + documentReference.getId()))
                .addOnFailureListener(e -> Log.w("Firestore", "Error adding document", e));
    }

    public void writeData(String collection, String document, Map<String, Object> data) {
        db.collection(collection).document(document).set(data)
                .addOnSuccessListener(aVoid -> Log.d("Firestore", "DocumentSnapshot successfully written!"))
                .addOnFailureListener(e -> Log.w("Firestore", "Error writing document", e));
    }

    public void readData(String collection, String document, OnSuccessListener<DocumentSnapshot> listener) {
        db.collection(collection).document(document).get()
                .addOnSuccessListener(listener)
                .addOnFailureListener(e -> Log.w("Firestore", "Error getting document", e));
    }
}