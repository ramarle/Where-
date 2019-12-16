package com.example.where;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private ProgressDialog progressDialog;

    private EditText txtEmail, txtPass;

    private TextView txtForgotPass, txtCreateNewUser;

    private Button btnAccess;

    //Tags
    final String SUCCESS = "success", FAIL = "fail";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        progressDialog = new ProgressDialog(this);

        mAuth = FirebaseAuth.getInstance();

        txtEmail = findViewById(R.id.txtEmail);
        txtPass = findViewById(R.id.txtPass);

        txtForgotPass = findViewById(R.id.txtForgotPass);

        txtForgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forgotPass();
            }
        });

        txtCreateNewUser = findViewById(R.id.txtCreateNewUser);

        txtCreateNewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNewUser();
            }
        });

        btnAccess = findViewById(R.id.btnAccess);

        btnAccess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                singInUser();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            Intent i = new Intent(this, UbicacionUserActivity.class);
            startActivity(i);
        }
    }

    private void forgotPass(){
        Intent i = new Intent(this, ForgotPassActivity.class);
        startActivity(i);
    }

    private void createNewUser(){
        Intent i = new Intent(this, CreateUserActivity.class);
        startActivity(i);

    }

    private void singInUser(){

        progressDialog.setTitle(R.string.loading);
        progressDialog.show();

        mAuth.signInWithEmailAndPassword(txtEmail.getText().toString(), txtPass.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(SUCCESS, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();

                            navigate();

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(FAIL, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, R.string.auth_fail,
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        progressDialog.hide();

    }

    private void navigate(){
        Intent i = new Intent(this, UbicacionUserActivity.class);

        startActivity(i);
    }
}
