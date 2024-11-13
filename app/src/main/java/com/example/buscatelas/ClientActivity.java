package com.example.buscatelas;

import static android.provider.MediaStore.MediaColumns.TITLE;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;


import com.example.buscatelas.Utils.Authentication;
import com.example.buscatelas.Utils.Database;
import com.example.buscatelas.models.Client;
import com.example.buscatelas.ui.worker.lista_servicos_worker;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.buscatelas.databinding.ActivityClientBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ClientActivity extends AppCompatActivity {

    private Database database;
    private FirebaseAuth auth;
    private ActivityClientBinding binding;
    private Client currentClient;
    private MyApplication app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);


        requestWindowFeature(Window.FEATURE_NO_TITLE) ;
        this.getWindow().setFlags(WindowManager. LayoutParams. FLAG_FULLSCREEN, WindowManager.LayoutParams. FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        app = (MyApplication) getApplication();
        auth = app.getAuth();
        database = app.getDatabase();
        
        binding = ActivityClientBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destination
        Switch switchP = findViewById(R.id.switch2);
        //bol = database.isProvider(user.getUid());
        if(((Switch) findViewById(R.id.switch2)).isChecked()){
            AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                    R.id.navigation_home, R.id.dashboardFragment2, R.id.navigation_settings)
                    .build();
            NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_client);
            NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
            NavigationUI.setupWithNavController(binding.navView, navController);
        }else{
            AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                    R.id.navigation_home, R.id.dashboardFragmentProvider, R.id.navigation_settings)
                    .build();
            NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_provider);
            NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
            NavigationUI.setupWithNavController(binding.navView, navController);
        }



        System.out.println("ooooooooooooooooooooooooooooooooooooooooooooooooooooooo" );
        if (getIntent().getExtras() != null) {
            Bundle bundle = getIntent().getExtras();
            System.out.println("ooooooooooooooooooooooooooooooooooooooooooooooooooooooo" + bundle);
            String fragment = bundle.getString("fragment");
            if (fragment.equals("specific_fragment")) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.nav_host_fragment_activity_client, new maps_percurso_client())
                        .commit();
            }
        }

    }

    public LatLng getUserLocation() {
        FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        Task<Location> task = null;
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

        } else
        {
            System.out.println("tem permissao ne");
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

    public Client getClientById(String id){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference(id);
        final Client[] client = {null};

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                client[0] = dataSnapshot.getValue(Client.class);
                client[0].setLocation(getUserLocation());
                currentClient = client[0];
                app.setCurrentClient(currentClient);
                database.pushClient(client[0], auth.getCurrentUser().getUid());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("Error retrieving object: " + databaseError.getMessage());
            }
        });
        return client[0];

    }

    public Client getCurrentClient() {
        return currentClient;
    }
}