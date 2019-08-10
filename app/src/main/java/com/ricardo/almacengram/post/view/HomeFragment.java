package com.ricardo.almacengram.post.view;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.crashlytics.android.Crashlytics;
import com.google.firebase.crash.FirebaseCrash;
import com.ricardo.almacengram.R;
import com.ricardo.almacengram.adapter.PictureAdapterRecyclerView;
import com.ricardo.almacengram.model.Picture;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private static final int REQUEST_CAMERA = 1;
    private String photoPathTemp="";


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

      //FirebaseCrash.report(new Exception("Reporte Home Fragment"));
      Crashlytics.log("Reporte Home Fragment");


        View view = inflater.inflate(R.layout.fragment_home, container, false);
       showToolbar(getResources().getString(R.string.tab_home), false, view);
        RecyclerView picturesRecycler = view.findViewById(R.id.pictureRecycler);
        FloatingActionButton fabCamera = view.findViewById(R.id.fabCamera);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        picturesRecycler.setLayoutManager(linearLayoutManager);

        PictureAdapterRecyclerView pictureAdapterRecyclerView =
                new PictureAdapterRecyclerView(buildPictures(), R.layout.cardview_picture, getActivity());
        picturesRecycler.setAdapter(pictureAdapterRecyclerView);

        fabCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                takePicture();

            }
        });
        return view;
    }

    private void takePicture() {

        Intent intentTakePicture=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intentTakePicture.resolveActivity(getActivity().getPackageManager())!=null){

            File photoFile=null;
            try {
                photoFile=createImageFile();

            }catch (Exception e){
                e.printStackTrace();

                //FirebaseCrash.report(e);
                Crashlytics.logException(e);

            }

            if (photoFile!=null) {
                Uri photoUri= FileProvider.getUriForFile(getActivity(),"com.ricardo.almacengram",photoFile);
                intentTakePicture.putExtra(MediaStore.EXTRA_OUTPUT,photoUri);
                startActivityForResult(intentTakePicture, REQUEST_CAMERA);//este metodo abre la camara
            }

        }
    }

    private File createImageFile() throws IOException {

        String timeStamp=new SimpleDateFormat("yyyyMMdd_HH-mm-ss").format(new Date());
        String imageFileName="JPEG"+timeStamp+"_";
        File storageDir =getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        File photo=File.createTempFile(imageFileName,".jpg",storageDir);

        photoPathTemp="file:"+photo.getAbsolutePath();

        return photo;

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode==REQUEST_CAMERA&&resultCode==getActivity().RESULT_OK){
            Log.d("HomeFragment","CAMERA OK!! :)");
            Intent i= new Intent(getActivity(),NewPostActivity.class);
            i.putExtra("PHOTO_PATH_TEMP",photoPathTemp);
            startActivity(i);

        }
    }

    public ArrayList<Picture> buildPictures() {

        ArrayList<Picture> pictures=new ArrayList<>();
        pictures.add(new Picture("https://mobimg.b-cdn.net/pic/v2/gallery/preview/more-pejzazh-priroda-46891.jpg","Almacen","4 dias","10 Me gusta"));
        pictures.add(new Picture("https://mobimg.b-cdn.net/pic/v2/gallery/preview/brendy-fon-logotipy-ndroid_android-22331.jpg","Ropa","5 dias","11 Me gusta"));
        pictures.add(new Picture("https://mobimg.b-cdn.net/pic/v2/gallery/preview/igry-lyudi-muzhchiny-44311.jpg","Pana","6 dias","12 Me gusta"));


        return pictures;

    }

    public void showToolbar(String tittle, boolean upButton, View view) {
        Toolbar toolbar =  view.findViewById(R.id.toolbar);

        try {

            ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(tittle);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);
        }catch (Exception e)
        {

        }



    }

}