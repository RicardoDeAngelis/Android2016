package com.ricardo.almacengram.login.view;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.crashlytics.android.Crashlytics;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.ricardo.almacengram.R;

public class CreateAccountActivity extends AppCompatActivity {

    private  String TAG ="CreateAccountActivity";
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private EditText email;
    private EditText password_createaccount;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        showToolbar(getResources().getString(R.string.toolbar_tittle_createaccount),true);

        Button joinUs = findViewById(R.id.joinUs);
        email=findViewById(R.id.email);
        password_createaccount=findViewById(R.id.password_createaccount);

       firebaseAuth= FirebaseAuth.getInstance();
       authStateListener= new FirebaseAuth.AuthStateListener() {
           @Override
           public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
               FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();
               if (firebaseUser!=null){
                   Log.w(TAG,"Usuario Logeado"+firebaseUser.getEmail());
                   Crashlytics.log(Log.WARN,TAG,"Usuario Logeado"+firebaseUser.getEmail());


               }else {

                   Log.w(TAG,"Usuario no Logeado");
                   //FirebaseCrash.logcat(Log.WARN,TAG,"Usuario no Logeado");
                    Crashlytics.log(Log.WARN,TAG,"Usuario no Logeado");
               }
           }
       };

       joinUs.setOnClickListener(new View.OnClickListener() {


           @Override
           public void onClick(View v) {
               createAccount();



           }
       });



    }

    private void createAccount() {

        String Email=email.getText().toString();
        String password=password_createaccount.getText().toString();
        firebaseAuth.createUserWithEmailAndPassword(Email,password).
                addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete( Task task) {
                        if (task.isSuccessful()){

                            Toast.makeText(CreateAccountActivity.this, "Cuenta creada exitosamente", Toast.LENGTH_SHORT).show();

                        }else {

                            Toast.makeText(CreateAccountActivity.this, "Ocurrio un error en la creacion de cuenta", Toast.LENGTH_SHORT).show();

                        }

                    }
                });
    }


    public void showToolbar(String tittle, boolean upButton){
        Toolbar toolbar= findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(tittle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);
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
}
