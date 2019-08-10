package com.ricardo.almacengram.post.view;

import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.transition.Fade;
import android.widget.ImageView;
import android.widget.Toast;


import com.crashlytics.android.Crashlytics;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.StorageReference;
import com.ricardo.almacengram.AlmacenGramApplication;
import com.ricardo.almacengram.R;
import com.squareup.picasso.Picasso;

public class PictureDetailActivity extends AppCompatActivity {

    private static final String TAG ="PictureDetailActivity " ;
    private ImageView imageHeader;
    private AlmacenGramApplication app;
    StorageReference storageReference;
    private String PHOTO_NAME="JPEG20190526_03-25-04_715321601.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Crashlytics.log("Inicializando "+TAG);
        //FirebaseCrash.log("Inicializando "+TAG);

        setContentView(R.layout.activity_picture_detail);

        app=(AlmacenGramApplication)getApplicationContext();
        storageReference=app.getStorageReference();

        imageHeader=findViewById(R.id.imageHeader);

        showToolbar("",true);
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
            getWindow().setStatusBarColor(Color.TRANSPARENT);
            getWindow().setEnterTransition(new Fade());

        }
        showData();


    }

    private void showData() {

        storageReference.child("postImages/"+PHOTO_NAME).getDownloadUrl()
                .addOnSuccessListener(new OnSuccessListener<Uri>(){
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.get().load(uri.toString()).into(imageHeader);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(PictureDetailActivity.this, "Ocurrio un error al descargar la foto", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
                Crashlytics.logException(e);
                //FirebaseCrash.report(e);


            }
        });

    }

    public void showToolbar(String tittle, boolean upButton) {
        Toolbar toolbar =  findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(tittle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);
       // CollapsingToolbarLayout collapsingToolbarLayout=findViewById(R.id.collapsToolbar);


    }

}
