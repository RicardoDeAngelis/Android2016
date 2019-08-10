package com.ricardo.almacengram.login.interactor;

import android.app.Activity;

import com.google.firebase.auth.FirebaseAuth;
import com.ricardo.almacengram.login.presenter.LoginPresenter;
import com.ricardo.almacengram.login.repository.LoginRepositoryImpl;
import com.ricardo.almacengram.login.repository.LoginReposotiry;

public class LoginInteractorImpl implements  LoginInteractor {

    private LoginReposotiry loginReposotiry;

    public LoginInteractorImpl(LoginPresenter loginPresenter) {
        /**
         * este interctor se comunica con la fuente de datos especifica por ejemplo aca lo
         * haremos el login con firebase
         aca agregaremos una capa extra para repositorio
         **/
       LoginPresenter loginPresenter1= loginPresenter;
        loginReposotiry=new LoginRepositoryImpl(loginPresenter);
    }

    @Override
    public void signIn(String username, String password, Activity activity, FirebaseAuth firebaseAuth) {
        loginReposotiry.signIn(username, password, activity,firebaseAuth);





    }
}
