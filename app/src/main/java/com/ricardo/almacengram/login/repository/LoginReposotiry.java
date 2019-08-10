package com.ricardo.almacengram.login.repository;

import android.app.Activity;

import com.google.firebase.auth.FirebaseAuth;

public interface LoginReposotiry {

    void signIn(String username, String password, Activity activity, FirebaseAuth firebaseAuth);


}
