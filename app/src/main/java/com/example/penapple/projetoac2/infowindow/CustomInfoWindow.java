package com.example.penapple.projetoac2.infowindow;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.penapple.projetoac2.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

public class CustomInfoWindow implements GoogleMap.InfoWindowAdapter{

    private final View window;
    private Context context;

    public CustomInfoWindow(Context context) {
        this.context = context;
        window = LayoutInflater.from(context).inflate(R.layout.custom_info_window, null);
    }

    private void renderWindowText(Marker marker, View view){
        String title = marker.getTitle();
        TextView textViewTitle = (TextView) view.findViewById(R.id.title);

        if(!title.equals("")){
            textViewTitle.setText(title);
        }

        String snippet = marker.getSnippet();
        TextView textViewSnippet = (TextView) view.findViewById(R.id.snippet);

        if(!snippet.equals("")){
            textViewSnippet.setText(snippet);
        }

    }

    @Override
    public View getInfoWindow(Marker marker) {
        renderWindowText(marker, window);
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        renderWindowText(marker, window);
        return null;
    }
}
