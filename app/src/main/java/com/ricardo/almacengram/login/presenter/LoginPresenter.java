package com.ricardo.almacengram.login.presenter;

import android.app.Activity;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

/***
 * el presenter se comunica con el interector y la vista
 * esta interfaz va a tener los metodos que actuan con los interactores y con las vistas
 *
 *
 */

public interface LoginPresenter {

    void signIn(String username, String password, Activity activity, FirebaseAuth firebaseAuth);//esto actuara con el interactor entonces el intectaror ejecutara este metodo
    void loginSuccess();//una vez esjecutado sigin hay q regresar a la vista y devolver el resultado a loginSuccess
    void loginError(String error);


}
