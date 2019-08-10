package com.ricardo.almacengram.view.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.crash.FirebaseCrash;
import com.ricardo.almacengram.R;
import com.ricardo.almacengram.adapter.PictureAdapterRecyclerView;
import com.ricardo.almacengram.model.Picture;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {


    private String TAG="SearchFragment";

    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        FirebaseCrash.log("Inicializando "+TAG);
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        RecyclerView picturesRecycler = view.findViewById(R.id.search_recyclerview_photos);
        GridLayoutManager linearLayoutManager = new GridLayoutManager(getContext(), 2);
        picturesRecycler.setHasFixedSize(true);
        picturesRecycler.setLayoutManager(linearLayoutManager);

        PictureAdapterRecyclerView pictureAdapterRecyclerView = new PictureAdapterRecyclerView(buildPictures(), R.layout.cardview_picture, getActivity());
        picturesRecycler.setAdapter(pictureAdapterRecyclerView);
        return view;
    }

    public ArrayList<Picture> buildPictures() {
        ArrayList<Picture> pictures = new ArrayList<>();
        pictures.add(new Picture("http://lorempixel.com/400/200/nature", "Uriel Ramírez", "4 días", "3 Me Gusta"));
        pictures.add(new Picture("http://lorempixel.com/400/200/nature/1", "Juan Pablo", "3 días", "10 Me Gusta"));
        pictures.add(new Picture("http://lorempixel.com/400/200/nature/2", "Anahi Salgado", "2 días", "9 Me Gusta"));
        pictures.add(new Picture("http://lorempixel.com/400/200/nature/3", "Oswaldo Rodriguez", "5 días", "4 Me Gusta"));
        pictures.add(new Picture("http://lorempixel.com/400/200/nature/4", "Anahi Salgado", "2 días", "9 Me Gusta"));
        pictures.add(new Picture("http://lorempixel.com/400/200/nature/5", "Anahi Salgado", "2 días", "9 Me Gusta"));
        pictures.add(new Picture("http://lorempixel.com/400/200/nature/6", "Anahi Salgado", "2 días", "9 Me Gusta"));
        pictures.add(new Picture("http://lorempixel.com/400/200/nature/7", "Anahi Salgado", "2 días", "9 Me Gusta"));
        pictures.add(new Picture("http://lorempixel.com/400/200/nature/8", "Anahi Salgado", "2 días", "9 Me Gusta"));
        pictures.add(new Picture("http://lorempixel.com/400/200/nature/9", "Anahi Salgado", "2 días", "9 Me Gusta"));
        return pictures;
    }

}