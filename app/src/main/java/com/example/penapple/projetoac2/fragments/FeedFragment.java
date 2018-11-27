package com.example.penapple.projetoac2.fragments;

import android.app.ListActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.penapple.projetoac2.R;
import com.example.penapple.projetoac2.bean.Problem;

import java.util.ArrayList;

public class FeedFragment extends Fragment{

    private ArrayList<Problem> problems = new ArrayList<>();

    private ArrayAdapter<Problem> adapter;

    private int counter = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.feed_fragment, container, false);

        ListView listView = (ListView)getActivity().findViewById(R.id.feed_list);


        return view;
    }

    public void receiveProblem(Problem problem) {
        problems.add(problem);
    }
}