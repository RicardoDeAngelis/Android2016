package com.ricardo.almacengram.login.view;

public interface LoginView {


    void enableInputs();
    void disableInputs();

    void showProgressBar();
    void hideProgressBar();

    void loginError(String error);



    void goCreateAccount () ;

    void startCreateContainer ();

    void goToHomePage() ;
}
