package com.ricardo.almacengram.login.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.crashlytics.android.Crashlytics;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;


import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.ricardo.almacengram.R;
import com.ricardo.almacengram.login.presenter.LoginPresenter;
import com.ricardo.almacengram.login.presenter.LoginPresenterImpl;
import com.ricardo.almacengram.view.ContainerActivity;


import java.util.Arrays;

import io.fabric.sdk.android.Fabric;


public class LoginActivity extends AppCompatActivity implements LoginView{
/**
* aca declaramos los atributos o campos que habilitaremos en la vista
* que son numbre de usuario ,contraseña y el boton de login
* */
    private TextInputEditText username,password;
    private Button login;
    private ProgressBar progressBarLogin;
    private LoginPresenter loginPresenter;//el view llama al presenter

    private String TAG ="LoginRepositoryImpl";
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private CallbackManager callbackManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       Fabric.with(this,new Crashlytics());
        setContentView(R.layout.activity_main);

      // FacebookSdk.sdkInitialize(getApplicationContext());//se puede omitir la linea

        callbackManager=CallbackManager.Factory.create();


        firebaseAuth= FirebaseAuth.getInstance();




        authStateListener= new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();
                if (firebaseUser!=null){
                    Log.w(TAG,"Usuario Logeado"+firebaseUser.getEmail());
                    startCreateContainer();

                }else {

                    Log.w(TAG,"Usuario no Logeado");
                }
            }
        };


        /**
        * aca le damos vida a los objetos buscandolos por id
        * */
        username=findViewById(R.id.username);
        password=findViewById(R.id.password);
        login=findViewById(R.id.login);
        LoginButton loginButtonFacebok = findViewById(R.id.login_facebookbutton);
        progressBarLogin=findViewById(R.id.progressbarLogin);
        hideProgressBar();

        loginPresenter=new LoginPresenterImpl(this);

/**
 * este metodo toma lo que hay en los edittext de login y pass al hacer click en el boton login
 * **/
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //if (username.equals(""))
                signIn( username.getText().toString(),password.getText().toString());

            }
        });

       loginButtonFacebok.setReadPermissions(Arrays.asList("email"));

        loginButtonFacebok.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                Log.w(TAG,"Facebook Login Success Token "+loginResult.getAccessToken().getApplicationId());
                signInFacebookFireBase(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {

                Log.w(TAG,"Facebook Login Success Canceled ");


            }

            @Override
            public void onError(FacebookException error) {

                Log.w(TAG,"Facebook Login Success Error "+error.toString());

                error.printStackTrace();
               Crashlytics.logException(error);

            }
        });
    }

    private void signInFacebookFireBase(AccessToken accessToken) {//crea una credencial para
                                                                  // validar el login con firebase
        AuthCredential authCredential= FacebookAuthProvider.getCredential(accessToken.getToken());
        firebaseAuth.signInWithCredential(authCredential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {



                if (task.isSuccessful()){
                    FirebaseUser user=task.getResult().getUser();
                    SharedPreferences preferences=
                            getSharedPreferences("USER", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor=preferences.edit();
                    editor.putString("email",user.getEmail());
                    editor.apply();
                    startCreateContainer();
                    Crashlytics.log(Log.WARN,TAG,"Login Face Exitoso");

                    Toast.makeText(LoginActivity.this, "Login Face Exitoso", Toast.LENGTH_SHORT).show();

                }else{

                    Crashlytics.log(Log.ERROR,TAG,"Login a Face sin Exito");

                    Toast.makeText(LoginActivity.this, "Login Face sin Exito", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    private void signIn(String username, String password) {

        loginPresenter.signIn(username,password,this,firebaseAuth);
    }


    public void goCreateAccount(View view){

        goCreateAccount();
    }

    @Override
    public void enableInputs() {
    /**
     * aca pondremos todos los elementos en habilitados
     * */
        username.setEnabled(true);
        password.setEnabled(true);
        login.setEnabled(true);
        progressBarLogin.setEnabled(true);
    }
    @Override
    public void disableInputs() {
        /**
         * aca pondremos todos los elementos en deshabilitados
         * */
        username.setEnabled(false);
        password.setEnabled(false);
        login.setEnabled(false);
        progressBarLogin.setEnabled(false);
    }

    /**
     * aca hacemos visible la barra de progreso con visible
     * **/
    @Override
    public void showProgressBar() {

        progressBarLogin.setVisibility(View.VISIBLE);

    }

    /**
     * aca oculatamos la barra de progreso con gome
     * **/

    @Override
    public void hideProgressBar() {

        progressBarLogin.setVisibility(View.GONE);

    }

    @Override
    public void loginError(String error) {

        Toast.makeText(this, getString(R.string.login_error)+error, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void goCreateAccount() {

        Intent intent = new Intent(this, CreateAccountActivity.class);
        startActivity(intent);
    }


    @Override
    public void startCreateContainer() {

        Intent intent = new Intent(this, ContainerActivity.class);
        startActivity(intent);

    }

    @Override
    public void goToHomePage() {

        Intent intent= new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com.ar"));
        startActivity(intent);

    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        firebaseAuth.removeAuthStateListener(authStateListener);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode,resultCode,data);
    }


    public void startCreateContainer(View view) {

        Intent intent = new Intent(this, ContainerActivity.class);
        startActivity(intent);

    }
}




