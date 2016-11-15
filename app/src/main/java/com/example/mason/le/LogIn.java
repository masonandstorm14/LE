package com.example.mason.le;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

        final EditText email = (EditText) findViewById(R.id.editText_emailLogIn);
        final EditText password = (EditText) findViewById(R.id.editText_passwordLogIn);

        if(auth.getCurrentUser()!= null){
            mover(map.class);
        }

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

    protected void mover(Class move){
        Intent i = new Intent(this, move);
        startActivity(i);
    }
}
