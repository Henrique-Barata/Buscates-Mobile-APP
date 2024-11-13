package com.example.buscatelas.ui.maps;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.buscatelas.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MapsFragment extends Fragment implements OnMapReadyCallback, LocationListener {

    private GoogleMap mMap;
    private Map<String, Marker> userMarkers = new HashMap<>();
    private Map<String, Boolean> userTracking = new HashMap<>();
    private LocationManager locationManager;
    private String locationProvider;
    private String userId;
    private boolean isTracking;
    private  FusedLocationProviderClient fusedLocationClient;
    private GoogleMap googleMap;
    private LatLng deviceLocation;

    @SuppressLint("MissingPermission")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_maps, container, false);

        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        locationProvider = locationManager.getBestProvider(criteria, false);

        SupportMapFragment mapFragment = (SupportMapFragment) getFragmentManager().findFragmentById(R.id.mapView);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }


        fusedLocationClient = LocationServices.getFusedLocationProviderClient(getContext());
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {

                        if (location != null) {
                            SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
                            mapFragment.getMapAsync(new OnMapReadyCallback() {
                                public void onMapReady(GoogleMap googleMap) {
                                    deviceLocation = new LatLng(location.getLatitude(), location.getLongitude());
                                    googleMap.addMarker(new MarkerOptions().position(deviceLocation).title("Device Location").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                                    googleMap.moveCamera(CameraUpdateFactory.newLatLng(deviceLocation));
                                    List<LatLng> locations = new ArrayList<>();
                                    locations.add(new LatLng(39.006613, -9.352222));

                                    for (int i = 0; i < locations.size(); i++) {
                                        googleMap.addMarker(new MarkerOptions().position(locations.get(i)).title("Provider"));
                                    }

                                  //  public static CameraUpdate newLatLngZoom (location.getLatitude(),location.getLongitude(), float zoom)

                                    googleMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
                                        @Override
                                        public void onCameraIdle() {
                                            // Logic to handle camera idle event
                                        }
                                    });
                                }
                            });

                        }
                    }
                });
        return rootView;
    }


    public void allProviders() {

        List<LatLng> locations = new ArrayList<>();
        locations.add(new LatLng(39.390610, -9.0766638));
        locations.add(new LatLng(40.730610, -73.935242));
        locations.add(new LatLng(39.006613, -9.352222));

        for (int i = 0; i < locations.size(); i++) {
           // googleMap.addMarker(new MarkerOptions().position(locations.get(i)).title("Provider"));
        }

    }
    public void oneProv() {

        List<LatLng> locations = new ArrayList<>();
        locations.add(new LatLng(39.006613, -9.352222));

        for (int i = 0; i < locations.size(); i++) {
            //googleMap.addMarker(new MarkerOptions().position(locations.get(i)).title("Provider"));
        }


    }
    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                mMap.addMarker((new MarkerOptions().position(latLng)));
            }
        });

    }

    public void addUserMarker(String userId, LatLng location) {
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);

        this.userId = userId;
        MarkerOptions markerOptions = new MarkerOptions()
                .position(location)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
        Marker marker = mMap.addMarker(markerOptions);
        userMarkers.put(userId, marker);
    }

    public void removeUserMarker(String userId) {
        Marker marker = userMarkers.get(userId);
        if (marker != null) {
            marker.remove();
            userMarkers.remove(userId);
        }
    }

    public void startTrackingUser(String userId) {
        isTracking = true;
        userTracking.put(userId, true);
        Marker marker = userMarkers.get(userId);
        if (marker != null) {
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(marker.getPosition(), 16));
        }
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationManager.requestLocationUpdates(locationProvider, 1000, 0, (android.location.LocationListener) this);
    }

    public void stopTrackingUser(String userId) {
        isTracking = false;
        userTracking.put(userId, false);
        locationManager.removeUpdates((android.location.LocationListener) this);
    }

    @Override
    public void onLocationChanged(Location location) {
        if (isTracking) {
            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
            Marker marker = userMarkers.get(userId);
            if (marker != null) {
                marker.setPosition(latLng);
            } else {
                addUserMarker(userId, latLng);
            }
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16));
        }
    }
}