package com.ricardo.almacengram.login.repository;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.util.Log;

import com.crashlytics.android.Crashlytics;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.ricardo.almacengram.login.presenter.LoginPresenter;

public class LoginRepositoryImpl implements  LoginReposotiry {

    /**
     * loginRepository es llamdo por el interactor
    esta clase tendra toda la carga de trabajo y hara la conexion con firebase
    **/


    private   LoginPresenter loginPresenter;
    private String TAG="LoginRepositoryImpl";


    public LoginRepositoryImpl(LoginPresenter loginPresenter) {
        this.loginPresenter = loginPresenter;
    }

    /**
     * este metodo se comunicara con firebase para hacer sus validaciones y nos devolvera un resultado
     * si el sigIn  es exitoso debemos comnunciarle a la vista en caso contrario tambien
     * Camino de ida el usuario da click en login el presenter se instancia,el presenter llamaa al interactor,
     * el interactor llama a una fuente de datos, en este caso repository este ejecuta el login en caso de exito o no,
     * devuelve una repuesta
     * Camino de vuelta
     *
     * **/

     @Override
    public void signIn( String username, String password, final Activity activity, FirebaseAuth firebaseAuth) {
         //firebase con in if

         firebaseAuth.signInWithEmailAndPassword(username,password).addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
             @Override
             public void onComplete(@NonNull Task<AuthResult> task) {

                 if (task.isSuccessful()){
                     FirebaseUser user=task.getResult().getUser();
                     SharedPreferences preferences=
                             activity.getSharedPreferences("USER",Context.MODE_PRIVATE);
                     SharedPreferences.Editor editor=preferences.edit();
                     editor.putString("email",user.getEmail());
                     editor.apply();
                     loginPresenter.loginSuccess();
                     Crashlytics.log(Log.WARN,TAG,"Usuario Logeado"+user.getEmail());

                 }else{

                     loginPresenter.loginError("Se ha producido un error");
                     Crashlytics.log(Log.WARN,TAG,"Se ha producido un error");

                 }

             }
         });




    }
}
