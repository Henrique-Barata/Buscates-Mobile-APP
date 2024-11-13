package com.example.buscatelas;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationRequest;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.buscatelas.Utils.Authentication;
import com.example.buscatelas.Utils.Database;
import com.example.buscatelas.databinding.ActivityClientBinding;
import com.example.buscatelas.databinding.ActivityProviderBinding;
import com.example.buscatelas.models.Client;
import com.example.buscatelas.models.Request;
import com.example.buscatelas.models.ServiceProvider;
import com.example.buscatelas.ui.home.HomeFragmentProvider;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProviderActivity extends AppCompatActivity {

    private ActivityProviderBinding binding;
    private Database database;
    private FirebaseAuth auth;
    private MyApplication app;
    private ServiceProvider currentProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE) ;
        this.getWindow().setFlags(WindowManager. LayoutParams. FLAG_FULLSCREEN, WindowManager.LayoutParams. FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        binding = ActivityProviderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        app = (MyApplication) getApplication();
        auth = app.getAuth();
        database = app.getDatabase();

        //System.out.println(auth.getCurrentUser());
        //System.out.println(auth.getCurrentUser().getUid());

        //ServiceProvider provider = getServiceProviderById(auth.getCurrentUser().getUid());
        //System.out.println(provider);


        System.out.println("ooooooooooooooooooooooooooooooooooooooooooooooooooooooo" );
        if (getIntent().getExtras() != null) {
            Bundle bundle = getIntent().getExtras();
            System.out.println("ooooooooooooooooooooooooooooooooooooooooooooooooooooooo" + bundle);
            String fragment = bundle.getString("fragment");
            if (fragment.equals("specific_fragment")) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.nav_host_fragment_activity_provider, new HomeFragmentProvider())
                        .commit();
            }
        }


        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.homeFragmentProvider, R.id.dashboardFragmentProvider, R.id.navigation_settings)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_provider);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

    }

    public LatLng getUserLocation() {
        FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        Task<Location> task = null;
        final LatLng[] loca = {null};
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            //if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {

                fusedLocationClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {

                    public void onComplete(@NonNull Task<Location> task) {
                        Location location = task.getResult();
                        if (location == null) {
                            System.out.println("entrei aqui pixa");
                        } else {
                            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                            loca[0] = latLng;
                        }
                    }
            });
            //}
        }
        
        return loca[0];

    }

    public ServiceProvider getServiceProviderById(String id){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference(id);
        System.out.println("A ref existe?:" + ref.getKey());
        final ServiceProvider[] serviceProvider = {null};

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                currentProvider = (dataSnapshot.getValue(ServiceProvider.class));
                //currentProvider.setLocation(getUserLocation());
                database.pushServiceProvider(currentProvider, auth.getCurrentUser().getUid());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("return: " + serviceProvider[0]);
                System.out.println("Error retrieving object: " + databaseError.getMessage());
            }
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return currentProvider;
    }


}

