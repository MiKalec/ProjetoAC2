package com.example.penapple.projetoac2.fragments;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.penapple.projetoac2.LocationData;
import com.example.penapple.projetoac2.R;
import com.example.penapple.projetoac2.infowindow.CustomInfoWindow;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class MapsFragment extends Fragment implements OnMapReadyCallback, LocationListener  {

    SupportMapFragment mapFragment;

    SendLocation SL;


    /*
    private static final String TAG = "MapActivity";

    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private static final float DEFAULT_ZOOM = 15f;

    //vars
    private Boolean mLocationPermissionsGranted = false;
    */

    private GoogleMap mGoogleMap;

    private Marker currentLocationMaker;
    private static List<MarkerOptions> markers =  new ArrayList<>();
    private LatLng currentLocationLatLong;

    public MapsFragment(){

    }

    @Override
    public void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View v = inflater.inflate(R.layout.maps_fragment, container, false);
        Button problemButton = (Button)v.findViewById(R.id.problemButton);

        problemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fr = getFragmentManager().beginTransaction();
                //add a marker
                LocationManager lm = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
                Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                ProblemFragment problemFragment = new ProblemFragment();
                fr.replace(R.id.fragment_container, problemFragment);
                SL.sendData(location, problemFragment);
                fr.commit();
            }
        });

        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment == null){
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            mapFragment = SupportMapFragment.newInstance();
            ft.replace(R.id.map, mapFragment).commit();
        }
        mapFragment.getMapAsync(this);
        return v;
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

        MapsInitializer.initialize(getContext());

        LocationManager lm = (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);
        assert lm != null;
        Location lastKnownLocation = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 100, this);
        lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 100, this);

        mGoogleMap = googleMap;

        for(MarkerOptions m : markers){
            mGoogleMap.addMarker(m);
        }

        mGoogleMap.setInfoWindowAdapter(new CustomInfoWindow(getActivity()));

        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

    }

    @Override
    public void onLocationChanged(Location location) {

        System.out.println("Atualizei");
        if (currentLocationMaker != null) {
            currentLocationMaker.remove();
        }
        //Add marker
        currentLocationLatLong = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(currentLocationLatLong);
        markerOptions.title("Localização atual");
        markerOptions.snippet("");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
        currentLocationMaker = mGoogleMap.addMarker(markerOptions);

        //Move to new location
        CameraPosition cameraPosition = new CameraPosition.Builder().zoom(15).target(currentLocationLatLong).build();
        mGoogleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        LocationData locationData = new LocationData(location.getLatitude(), location.getLongitude());

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    public void receiveLocation(LatLng latLng, String desc, String title) {
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title(title);
        markerOptions.snippet(desc);
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
        markers.add(markerOptions);
    }

    public interface SendLocation {
        void sendData(Location location, ProblemFragment problemFragment);
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);

        try{
            SL = (SendLocation) getActivity();
        }catch (ClassCastException e){
            throw new ClassCastException( "Error retrieving data");
        }
    }
}


