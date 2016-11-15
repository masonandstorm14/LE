package com.example.mason.le;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class createAccount extends AppCompatActivity {

    private  Toast toast;
    private  FirebaseAuth Auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        //firebase
        Auth = FirebaseAuth.getInstance();

        final EditText email = (EditText) findViewById(R.id.editText_createEmail);
        final EditText password = (EditText) findViewById(R.id.editText_createPassword);
        final EditText passwordConfirm = (EditText) findViewById(R.id.editText_confirmPassword);

        final Button createAccount = (Button) findViewById(R.id.btn_createAccount);
        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(password.getText().toString().equals(passwordConfirm.getText().toString())){
                    Auth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                            .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {
                                    if(Auth.getCurrentUser()!= null){
                                        mover(map.class);
                                    }else{
                                        mover(LogIn.class);
                                    }
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    toast = Toast.makeText(createAccount.this, e.getMessage(), Toast.LENGTH_SHORT);
                                    toast.show();
                                }
                            });
                }else{
                    toast = Toast.makeText(createAccount.this, "Passwords do not match", Toast.LENGTH_SHORT);
                    toast.show();
                }

            }
        });
    }

    protected void mover(Class move) {
        Intent i = new Intent(this, move);
        startActivity(i);
    }
}
