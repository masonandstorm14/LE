package com.example.mason.le;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LogIn extends AppCompatActivity {

    private FirebaseAuth auth;
    private Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        auth = FirebaseAuth.getInstance();

        if(ContextCompat.checkSelfPermission(LogIn.this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            if(auth.getCurrentUser()!= null){
                mover(map.class);
            }
        }else{
            ActivityCompat.requestPermissions(LogIn.this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},1
            );

        }


        final EditText email = (EditText) findViewById(R.id.editText_emailLogIn);
        final EditText password = (EditText) findViewById(R.id.editText_passwordLogIn);


        Button logIn = (Button) findViewById(R.id.btn_logIn);
        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                toast = Toast.makeText(LogIn.this, e.getMessage(), Toast.LENGTH_SHORT);
                                toast.show();
                            }
                        })
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                mover(map.class);
                            }
                        });
            }
        });

        final TextView createAccount = (TextView) findViewById(R.id.textView_makeAccount);
        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mover(createAccount.class);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if(auth.getCurrentUser()!= null){
                        mover(map.class);
                    }

                } else {
                    LogIn.this.finish();
                    System.exit(0);
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
    protected void mover(Class move){
        Intent i = new Intent(this, move);
        startActivity(i);
    }
}
