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
import android.widget.Toast;

import com.example.where.data.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateUserActivity extends AppCompatActivity {

    //Firabase objects
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    //Tags
    final String SUCCESS = "success", FAIL = "fail";

    //Layout views
    private EditText txtNameAdd, txtSurnameAdd, txtEmailAdd, txtConfirmEmailAdd, txtPassAdd, txtConfirmPassAdd;
    private Button btnCreateAdd;

    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);

        getSupportActionBar().hide();

        txtNameAdd = findViewById(R.id.txtNameAdd);
        txtSurnameAdd = findViewById(R.id.txtSurnameAdd);
        txtEmailAdd = findViewById(R.id.txtEmailAdd);
        txtConfirmEmailAdd = findViewById(R.id.txtConfirmEmailAdd);
        txtPassAdd = findViewById(R.id.txtPassAdd);
        txtConfirmPassAdd = findViewById(R.id.txtConfirmPassAdd);

        btnCreateAdd = findViewById(R.id.btnCreateAdd);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        mAuth = FirebaseAuth.getInstance();

        btnCreateAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createUser();
            }
        });
    }

    private void createUser(){

        if (checkEmail() && checkPass() && (txtNameAdd.getText().toString().trim().length() > 0) && (txtSurnameAdd.getText().toString().trim().length() > 0)){

            progressDialog.setTitle(R.string.creating);
            progressDialog.show();

            mAuth.createUserWithEmailAndPassword(txtEmailAdd.getText().toString(), txtPassAdd.getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(SUCCESS, "createUserWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();

                                writeNewUser(user.getUid(),txtNameAdd.getText().toString(),txtSurnameAdd.getText().toString() ,txtEmailAdd.getText().toString());

                                //AQUÍ HAY QUE NAVEGAR A LA VENTANA DE LA UBICACIÓN

                                Intent i = new Intent(CreateUserActivity.this, UbicacionUserActivity.class);
                                startActivity(i);

                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(FAIL, "createUserWithEmail:failure", task.getException());
                                Toast.makeText(CreateUserActivity.this, R.string.create_user_fail,
                                        Toast.LENGTH_SHORT).show();
                            }

                        }
                    });

            progressDialog.hide();

        } else {

            Toast.makeText(CreateUserActivity.this, R.string.missing_fields, Toast.LENGTH_LONG).show();

        }

    }


    private void writeNewUser(String userId, String name, String surname, String email) {
        User user = new User(name, surname, email);

        mDatabase.child("users").child(userId).setValue(user);
    }

    private boolean checkEmail(){
        return txtEmailAdd.getText().toString().equals(txtConfirmEmailAdd.getText().toString());
    }

    private boolean checkPass(){
        return txtPassAdd.getText().toString().equals(txtConfirmPassAdd.getText().toString());
    }
}
