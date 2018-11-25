package com.example.penapple.projetoac2.fragments;

import android.content.Context;
import android.location.Address;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.penapple.projetoac2.R;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;


public class ProblemFragment extends Fragment {
    SendLocationToMark SLM;
    private Address address;

    private EditText nomeRua;
    private EditText bairro;
    private EditText numero;
    private EditText descricao;
    private Button marcar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_problem, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        nomeRua = (EditText) view.findViewById(R.id.nomeRua);
        bairro = (EditText) view.findViewById(R.id.nomeBairro);
        numero = (EditText) view.findViewById(R.id.numero);
        descricao = (EditText) view.findViewById(R.id.descricao);
        marcar = (Button) view.findViewById(R.id.submit);

        nomeRua.setText(address.getThoroughfare());
        bairro.setText(address.getSubLocality());
        numero.setText(address.getSubThoroughfare());

        final LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());

        marcar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fr = getFragmentManager().beginTransaction();
                MapsFragment mapsFragment = new MapsFragment();
                fr.replace(R.id.fragment_container, mapsFragment);
                fr.commit();
                SLM.sendLoc(latLng, descricao, mapsFragment);
            }
        });
    }

    public void receiveLocation(List<Address> location) {
        address = location.get(0);
    }

    public interface SendLocationToMark {
        void sendLoc(LatLng latLng, EditText descricao, MapsFragment mapsFragment);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            SLM = (SendLocationToMark) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException("Error retrieving data");
        }
    }
}
