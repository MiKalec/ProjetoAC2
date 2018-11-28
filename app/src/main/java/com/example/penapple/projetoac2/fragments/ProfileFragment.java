package com.example.penapple.projetoac2.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.provider.MediaStore.Images.Media;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.example.penapple.projetoac2.R;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class ProfileFragment extends Fragment {

    public static final String TAG = "Test";

    private ScrollView mScrollView;
    private LinearLayout mFormView;
    private Button save;

    private static int RESULT_LOAD_IMAGE = 1;
    Uri myPicture = null;
    Button buttonLoadImage;


    private static int sId = 0;

    private static int id() {
        return sId++;
    }

    private EditText nome;
    private EditText email;
    private EditText CPF;
    private EditText cidade;
    private EditText telefone;

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        nome = (EditText) view.findViewById(R.id.Nome);
        email = (EditText) view.findViewById(R.id.Email);
        CPF = (EditText) view.findViewById(R.id.CPF);
        cidade = (EditText) view.findViewById(R.id.Cidade);
        telefone = (EditText) view.findViewById(R.id.Telefone);
        save = (Button) view.findViewById(R.id.save);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fr = getFragmentManager().beginTransaction();
                ProfileFragment profileFragment = new ProfileFragment();
                fr.replace(R.id.fragment_container, profileFragment);
                fr.commit();
                }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView(): container = " + container
                + "savedInstanceState = " + savedInstanceState);

        if (mScrollView == null) {
            // normally inflate the view hierarchy
            mScrollView = (ScrollView) inflater.inflate(R.layout.profile_fragment,container, false);
            mFormView = (LinearLayout) mScrollView.findViewById(R.id.form);

            buttonLoadImage = (Button) mScrollView.findViewById(R.id.select);

            buttonLoadImage.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {

                    Intent i = new Intent(
                            Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                    startActivityForResult(i, RESULT_LOAD_IMAGE);
                }
            });


        } else {

            ViewGroup parent = (ViewGroup) mScrollView.getParent();
            parent.removeView(mScrollView);
        }
        return mScrollView;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE  && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getActivity().getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            ImageView imageView = (ImageView) mFormView.findViewById(R.id.imageView);
            imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));

        }


    }



    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "onActivityCreated(): savedInstanceState = "
                + savedInstanceState);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "onDestroyView()");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy()");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "onDetach()");
    }



    public void onLoadFinished(Loader<Void> id, Void result) {
        Log.d(TAG, "onLoadFinished(): id=" + id);
    }

    public void onLoaderReset(Loader<Void> loader) {
        Log.d(TAG, "onLoaderReset(): id=" + loader.getId());
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.d(TAG, "onAttach(): activity = " + activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(): savedInstanceState = " + savedInstanceState);
        setRetainInstance(true);
    }

    public void receiveProfile(String nomeRecebido, String emailRecebido, String CPFRecebido,String cidadeRecebido, String telefoneRecebido, ProfileFragment profileFragment) {
        ProfileFragment teste = new ProfileFragment();
        teste = profileFragment;
        teste = profileFragment;
        teste.nome.setText(nomeRecebido);
        teste.email.setText(emailRecebido);
        teste.CPF.setText(CPFRecebido);
        teste.cidade.setText(cidadeRecebido);
        teste.telefone.setText(telefoneRecebido);
        //FragmentTransaction fr = getFragmentManager().beginTransaction();
        //fr.commit();

    }

    public interface profile {
        void sendProfile(EditText nome, EditText email, EditText CPF,EditText cidade, EditText telefone, ProfileFragment profileFragment);
    }


}

