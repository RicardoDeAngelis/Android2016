package com.ricardo.almacengram.login.presenter;

import android.app.Activity;

import com.google.firebase.auth.FirebaseAuth;
import com.ricardo.almacengram.login.interactor.LoginInteractor;
import com.ricardo.almacengram.login.interactor.LoginInteractorImpl;
import com.ricardo.almacengram.login.view.LoginView;

public class LoginPresenterImpl implements  LoginPresenter{
    @Override
    public void signIn(String username, String password, Activity activity, FirebaseAuth firebaseAuth) {
        /**
         * El presenter se llama desde la vista por eso se hace la inyeccion de la vista mediante el constructor
         * Para q el presenter pueda hacer su trabajo necesitamos 2 entidades aqui la entidad de la  vista y del interator
        **/


        loginView.disableInputs();
        loginView.showProgressBar();

        loginInteractor.signIn(username, password, activity,firebaseAuth);


    }

         //definicion de los objetos para la vista
    private LoginView loginView;
    private LoginInteractor loginInteractor;

    public LoginPresenterImpl(LoginView loginView) {
        this.loginView = loginView;//inyeccion o colocacion de login view unicamente
        loginInteractor=new LoginInteractorImpl(this);//aca pasamos la referencia para instanciar
    }

    /**
     * para que el presenter pueda funcionar necesitamos que en su metodo constructor
     * se este inyectando la vista
     *
     * **/




    @Override
    public void loginSuccess() {
        loginView.startCreateContainer();

        loginView.hideProgressBar();


    }

    @Override
    public void loginError( String error) {

        loginView.enableInputs();
        loginView.hideProgressBar();
        loginView.loginError(error);

    }
}
