package com.ricardo.almacengram.post.view;

import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.crashlytics.android.Crashlytics;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.ricardo.almacengram.AlmacenGramApplication;
import com.ricardo.almacengram.R;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;

public class NewPostActivity extends AppCompatActivity {

    private static final Object TAG = "NewPostActivity";
    private ImageView imgPhoto;
    private  String photoPath;
    private StorageReference storageReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);
        AlmacenGramApplication app = (AlmacenGramApplication) getApplicationContext();

        storageReference= app.getStorageReference();


        imgPhoto=findViewById(R.id.imgPhoto);
        Button btnCreatePost = findViewById(R.id.btnCreatePost);

    if (getIntent().getExtras()!=null){
            photoPath=getIntent().getExtras().getString("PHOTO_PATH_TEMP");
            showPhoto();
        }

        btnCreatePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                uploadPhoto();

            }
        });

    }

    private void uploadPhoto() {

        imgPhoto.setDrawingCacheEnabled(true);
        imgPhoto.buildDrawingCache();
        Bitmap bitmap= imgPhoto.getDrawingCache();
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byte[] photoByte=byteArrayOutputStream.toByteArray();

        String photoName=photoPath.substring(photoPath.lastIndexOf("/")+1);

        StorageReference photoReference=storageReference.child("postImages/"+photoName);
        UploadTask uploadTask=photoReference.putBytes(photoByte);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w((String) TAG,"Error al subir la foto > "+e.toString());
                e.printStackTrace();

                //FirebaseCrash.report(e);
                Crashlytics.logException(e);



            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                Uri uriPhoto=taskSnapshot.getUploadSessionUri();
                String photoURL=uriPhoto.toString();
                Log.w((String) TAG,"URL Photo > "+photoURL);
                finish();

            }
        });

    }

    private  void  showPhoto(){
        Picasso.get().load(photoPath).into(imgPhoto);

    }
}
