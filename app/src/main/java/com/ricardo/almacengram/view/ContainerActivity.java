package com.ricardo.almacengram.view;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.ricardo.almacengram.R;
import com.ricardo.almacengram.login.view.LoginActivity;
import com.ricardo.almacengram.post.view.HomeFragment;
import com.ricardo.almacengram.view.fragment.ProfileFragment;
import com.ricardo.almacengram.view.fragment.SearchFragment;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

public class ContainerActivity extends AppCompatActivity {

    private  String TAG="ContainerActivity";
   /**
    HomeFragment homeFragment;
    ProfileFragment profileFragment;
    SearchFragment searchFragment;
    **/
    private FirebaseAuth firebaseAuth;
      FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

      //  Crashlytics.log("Inicializando "+TAG);//recomendacion es poner este mensaje en cada metodo oncreate

        setContentView(R.layout.activity_container);

        firebaseInitialize();

        BottomBar bottomBar=findViewById(R.id.bottombar);
        bottomBar.setDefaultTab(R.id.home);

        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(int tabId) {
                switch (tabId){
                    case R.id.home:
                        HomeFragment homeFragment=new HomeFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,homeFragment)
                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).addToBackStack(null).commit();
                        break;
                    case R.id.profile:
                        ProfileFragment profileFragment=new ProfileFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,profileFragment)
                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).addToBackStack(null).commit();
                        break;
                    case R.id.search:
                        SearchFragment searchFragment=new SearchFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,searchFragment)
                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).addToBackStack(null).commit();
                        break;

                }
            }
        });


    }

    private void firebaseInitialize(){

        firebaseAuth=FirebaseAuth.getInstance();
        authStateListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                if (firebaseUser != null) {
                    Log.w(TAG, "Usuario Logeado" + firebaseUser.getEmail());

                } else {

                    Log.w(TAG, "Usuario no Logeado");
                }


            }};




        }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       getMenuInflater().inflate(R.menu.menu_opciones,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case (R.id.mSingOut):
                firebaseAuth.signOut();
                if (LoginManager.getInstance()!=null){
                    LoginManager.getInstance().logOut();

                }
                Toast.makeText(this, "Se cerro la sesion con exito", Toast.LENGTH_SHORT).show();

                Intent i= new Intent (ContainerActivity.this, LoginActivity.class);
                startActivity(i);

                break;
            case (R.id.mAbout):
                Toast.makeText(this, "AlmacenGram by Ricardo", Toast.LENGTH_SHORT).show();


        }

        return super.onOptionsItemSelected(item);
    }
}
