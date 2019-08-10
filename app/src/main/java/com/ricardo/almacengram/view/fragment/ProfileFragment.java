package com.ricardo.almacengram.view.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.crashlytics.android.Crashlytics;
import com.google.firebase.crash.FirebaseCrash;
import com.ricardo.almacengram.R;
import com.ricardo.almacengram.adapter.PictureAdapterRecyclerView;
import com.ricardo.almacengram.model.Picture;

import java.util.ArrayList;

/**
 * A simple Fragment subclass.
 */
public class ProfileFragment extends Fragment {


    private String TAG ="ProfileFragment" ;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //FirebaseCrash.log("Inicializando "+TAG);
        Crashlytics.log("Inicializando "+TAG);
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        RecyclerView picturesRecycler = view.findViewById(R.id.pictureprofileRecycler);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        picturesRecycler.setLayoutManager(linearLayoutManager);

        PictureAdapterRecyclerView pictureAdapterRecyclerView =
                new PictureAdapterRecyclerView(buildPictures(), R.layout.cardview_picture, getActivity());
        picturesRecycler.setAdapter(pictureAdapterRecyclerView);

        return view;
    }

/**
    public void showToolbar(String tittle, boolean upButton, View view) {
        Toolbar toolbar = view.findViewById(R.id.toolbar);

        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(tittle);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);


    }
**/
    public ArrayList<Picture> buildPictures() {

        ArrayList<Picture> pictures = new ArrayList<>();
        pictures.add(new Picture("https://mobimg.b-cdn.net/pic/v2/gallery/preview/more-pejzazh-priroda-46891.jpg", "Almacen", "4 dias", "10 Me gusta"));
        pictures.add(new Picture("https://mobimg.b-cdn.net/pic/v2/gallery/preview/brendy-fon-logotipy-ndroid_android-22331.jpg", "Ropa", "5 dias", "11 Me gusta"));
        pictures.add(new Picture("https://mobimg.b-cdn.net/pic/v2/gallery/preview/igry-lyudi-muzhchiny-44311.jpg", "Pana", "6 dias", "12 Me gusta"));


        return pictures;

    }
}