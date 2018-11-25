package com.example.penapple.projetoac2.activities;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.view.MenuItem;

import com.example.penapple.projetoac2.R;
import com.example.penapple.projetoac2.fragments.FeedFragment;
import com.example.penapple.projetoac2.fragments.MapsFragment;
import com.example.penapple.projetoac2.fragments.ProblemFragment;
import com.example.penapple.projetoac2.fragments.ProfileFragment;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements MapsFragment.SendLocation, ProblemFragment.SendLocationToMark {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        //I added this if statement to keep the selected fragment when rotating the device
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new FeedFragment()).commit();
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    switch (item.getItemId()) {
                        case R.id.nav_feed:
                            selectedFragment = new FeedFragment();
                            break;
                        case R.id.nav_map:
                            selectedFragment = new MapsFragment();
                            break;
                        case R.id.nav_profile:
                            selectedFragment = new ProfileFragment();
                            break;
                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();

                    return true;
                }
            };

    @Override
    public void sendData(Location location, ProblemFragment problemFragment) {

        ProblemFragment pf = problemFragment;
        pf.receiveLocation(getAddresses(location));
    }

    private List<Address> getAddresses(Location location) {
        LatLng myCoordenates = new LatLng(location.getLatitude(), location.getLongitude());
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> addresses = null;

        try{
            addresses = geocoder.getFromLocation(myCoordenates.latitude, myCoordenates.longitude, 1);
        }catch (IOException e){
            e.printStackTrace();
        }

        return addresses;
    }

    @Override
    public void sendLoc(LatLng latLng, MapsFragment mapsFragment) {
        mapsFragment.receiveLocation(latLng);
    }
}