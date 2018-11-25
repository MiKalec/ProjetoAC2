package com.example.penapple.projetoac2.fragments;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.penapple.projetoac2.R;

import java.util.List;
import java.util.Locale;


public class ProblemFragment extends Fragment {
    private Geocoder geocoder;

    public void setGeocoder(Geocoder geocoder) {
        this.geocoder = geocoder;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_problem, container, false);


        return view;
    }

}
