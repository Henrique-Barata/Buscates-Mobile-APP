package com.example.buscatelas;


import android.app.AlertDialog;
import android.app.usage.NetworkStats;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.buscatelas.Utils.Authentication;
import com.example.buscatelas.Utils.Database;
import com.example.buscatelas.models.Client;
import com.example.buscatelas.models.Request;
import com.example.buscatelas.models.ServiceProvider;
import com.example.buscatelas.ui.maps.MapsFragment;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.Executor;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link maps_provider_locations#newInstance} factory method to
 * create an instance of this fragment.
 */
public class maps_provider_locations extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Button closeButton;
    private AlertDialog.Builder builder;
    private Request request;
    private String espc;
    private Database databs;
    private Client currentClient;
    private Authentication mauth;


    public maps_provider_locations() {
        // Required empty public constructor
    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment maps_provider_locations.
     */
    // TODO: Rename and change types and number of parameters
    public static maps_provider_locations newInstance(String param1, String param2) {
        maps_provider_locations fragment = new maps_provider_locations();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public maps_provider_locations(String i) {
        this.espc = i;
    }

    public maps_provider_locations(String i, Client cli) {
        this.espc = i;
        this.currentClient = cli;
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
        View view = inflater.inflate(R.layout.fragment_maps_provider_locations, container, false);

        if (container != null) {
            container.removeAllViews();
        }

        databs = new Database();



        MapsFragment maps = new MapsFragment();

        FragmentContainerView mapsContainer = view.findViewById(R.id.mapsContainer);

        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.mapsContainer, maps);
        //maps.onMapReady();
        //maps.allProviders();

        for (ServiceProvider sp : databs.findProvidersWithSkill(espc)) {
            maps.addUserMarker(sp.getId(), sp.getLocation());
        }

        closeButton = view.findViewById(R.id.buttonpopup);
        closeButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                builder = new AlertDialog.Builder(getContext());
                final EditText edittext = new EditText(getContext());
                builder.setView(edittext);

                builder.setMessage("Descriçao do pedido")
                        .setCancelable(false)
                        .setPositiveButton("Search", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                String descript = edittext.getText().toString();
                                //Authentication firebaseAuth = new Authentication(getActivity());
                                //FirebaseUser client = firebaseAuth.getCurrentUser();
                                //Client cli = getClientById(client.getUid());
                                request = new Request(currentClient, descript, espc);
                                //databs.pushRequest(request, currentClient.getId());

                                FragmentTransaction fragmentTransaction = getActivity()
                                        .getSupportFragmentManager().beginTransaction();
                                fragmentTransaction.replace(R.id.nav_host_fragment_activity_client, new Waiting_for_response());
                                fragmentTransaction.commit();

                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();

            }
        });

        return view;
    }

    public LatLng getUserLocation() {
        FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(getContext());
        Task<Location> task = null;
        if (ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            task = fusedLocationClient.getLastLocation();
        }

        final LatLng[] loca = {null};
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                    loca[0] = latLng;
                }
            }
        });
        return loca[0];

    }

    public Client getClientById(String id) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference(id);
        final Client[] client = {null};

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                client[0] = dataSnapshot.getValue(Client.class);
                client[0].setLocation(getUserLocation());
                currentClient = client[0];
                databs.pushClient(client[0], mauth.getCurrentUser().getUid());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("Error retrieving object: " + databaseError.getMessage());
            }
        });
        return client[0];

    }

    public void openMap() {
        FusedLocationProviderClient fusedLocationClient;
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());
        if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener((Executor) this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            Uri gmmIntentUri = Uri.parse("geo:" + location.getLatitude() + "," + location.getLongitude());
                            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                            mapIntent.setPackage("com.google.android.apps.maps");
                            if (mapIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                                startActivity(mapIntent);
                            }
                        }
                    }
                });
    }
}