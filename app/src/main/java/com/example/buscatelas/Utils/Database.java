package com.example.buscatelas.Utils;

import com.example.buscatelas.models.Client;
import com.example.buscatelas.models.Request;
import com.example.buscatelas.models.ServiceProvider;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Database {

    private DatabaseReference databaseReference;

    public Database() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    public void pushClient(Client client, String id) {
        databaseReference.child("clients").child(id).push().setValue(client);
        client.setId(id);
    }

    public void updateClient(Client client, String id) {
        databaseReference.child("clients").child(id).setValue(client);
    }

    public void pushServiceProvider(ServiceProvider serviceProvider, String id) {
        databaseReference.child("providers").child(id).push().setValue(serviceProvider);
        serviceProvider.setId(id);
    }

    public void updateServiceProvider(ServiceProvider serviceProvider, String id) {
        databaseReference.child("providers").child(id).setValue(serviceProvider);
    }

    public void pushRequest(Request request, String id) {
        databaseReference.child("requests").child(id).push().setValue(request);
        request.setId(id);
    }

    public void updateRequest(Request request, String id) {
        databaseReference.child("requests").child(id).setValue(request);
    }

    public boolean isProvider(String id, final OnGetDataListener listener){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("providers");
        final boolean[] isProvider = null;

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(id)) {
                    System.out.println("----------------------------------------1: ");

                    isProvider[0] = true;

                } else {
                    System.out.println("----------------------------------------2: ");
                    isProvider[0] = false;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("Error checking for ID: " + databaseError.getMessage());
            }
        });

        System.out.println("----------------------------------------ola: " +isProvider[0]);
        return isProvider[0];
    }

    public void mReadDataOnce(String id, final OnGetDataListener listener) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("/providers");
        final boolean[] isProvider = null;
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listener.onSuccess(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("Error checking for ID: " + databaseError.getMessage());
                listener.onFailed(databaseError);
            }
        });
    }

    public Client getClientById(String id){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("/providers");
        final Client[] client = {null};

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                client[0] = dataSnapshot.getValue(Client.class);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("Error retrieving object: " + databaseError.getMessage());
            }
        });
        return client[0];

    }

    public ServiceProvider getServiceProviderById(String id){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("providers").child(id);
        final ServiceProvider[] serviceProvider = {null};
        System.out.println("actual id inside" + id + ref);

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                serviceProvider[0] = dataSnapshot.getValue(ServiceProvider.class);
                System.out.println("return inside: " + serviceProvider[0]);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("return: " + serviceProvider[0]);
                System.out.println("Error retrieving object: " + databaseError.getMessage());
            }
        });
        System.out.println("aaa" + serviceProvider[0]);
        return serviceProvider [0];
    }

    public ArrayList<Request> getRequests(String skill) {
        // Get a reference to the Firebase Realtime Database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("requests");

        // Build the query to find requests where serviceProvider field is null
        // and the skill field is equal to the specified skill
        Query query = ref.orderByChild("serviceProvider").equalTo(null)
                .orderByChild("specialization").equalTo(skill).orderByChild("done").equalTo(false);

        // Get the requests from the query
        ArrayList<Request> requests = new ArrayList<>();
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Request request = ds.getValue(Request.class);
                    requests.add(request);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle the error
            }
        });

        return requests;
    }

    public ArrayList<ServiceProvider> findProvidersWithSkill(String skill) {
        ArrayList<ServiceProvider> providers = new ArrayList<>();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot providerSnapshot : dataSnapshot.getChildren()) {
                    ServiceProvider provider = providerSnapshot.getValue(ServiceProvider.class);
                    System.out.println("provider provider" + provider.getSkills());
                    providers.add(provider);
                    //if (provider.getSkills().contains(skill)) {

                    //}
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle error
            }
        });
        return providers;
    }





}
