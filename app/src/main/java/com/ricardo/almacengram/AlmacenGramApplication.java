package com.ricardo.almacengram;
import android.app.Application;
import android.support.annotation.NonNull;
import android.util.Log;

import com.crashlytics.android.Crashlytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;



public class AlmacenGramApplication extends Application {
/**
 * en este metodo @onCreate colocaremos todos lo relacionados con Firebase
 *
 * **/

    private FirebaseStorage firebaseStorage;
    private String TAG="AlmacenGramAplication";

    @Override
    public void onCreate() {
        super.onCreate();
        //FacebookSdk.sdkInitialize(getApplicationContext());
        // TODO: Move this to where you establish a user session

//       Crashlytics.log("Inicializando variables en la app");//recomendacion es poner este mensaje en cada metodo oncreate
        firebaseStorage=FirebaseStorage.getInstance();
        FirebaseAuth.AuthStateListener authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                if (firebaseUser != null) {
                    Crashlytics.log(Log.WARN, TAG, "Usuario logeado" + firebaseUser.getEmail());
                } else {
                    Crashlytics.log(Log.WARN, TAG, "Usuario no logueado");
                }
            }
        };

    }


   // FacebookSdk.sdkInitialize(getApplicationContext());//se puede omitir la linea

    public StorageReference  getStorageReference (){
        return firebaseStorage.getReference();
    }

}

