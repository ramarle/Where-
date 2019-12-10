package com.example.where;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassActivity extends AppCompatActivity {

    private final String SEND = "SEND";
    private ProgressDialog progressDialog;

    private FirebaseAuth auth;

    private EditText txtForgotEmail;
    private Button btnForgotPass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pass);

        getSupportActionBar().hide();

        auth = FirebaseAuth.getInstance();

        txtForgotEmail = findViewById(R.id.txtForgotEmail);
        btnForgotPass = findViewById(R.id.btnForgotPass);

        btnForgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (txtForgotEmail.getText().toString().trim().length() > 0){
                    sendEmail(txtForgotEmail.getText().toString());
                } else {
                    Toast.makeText(ForgotPassActivity.this, R.string.toast_forgot_pass, Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    private void sendEmail(String emailAddress){

        progressDialog.setTitle(R.string.sending_email);
        progressDialog.show();

        auth.useAppLanguage();

        auth.sendPasswordResetEmail(emailAddress)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {

                            AlertDialog.Builder builder = new AlertDialog.Builder(ForgotPassActivity.this);

                            builder.setTitle(R.string.title_forgot_pass_dialog);
                            builder.setMessage(R.string.text_forgot_pass_dialog);

                            builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    Intent i = new Intent(ForgotPassActivity.this, MainActivity.class);
                                    startActivity(i);
                                }
                            });

                            AlertDialog dialog = builder.create();

                            dialog.show();

                            Log.d(SEND, "Email sent.");
                        } else {
                            Toast.makeText(ForgotPassActivity.this, R.string.fail_forgot_pass, Toast.LENGTH_LONG);
                        }
                    }
                });

        progressDialog.hide();
    }
}
