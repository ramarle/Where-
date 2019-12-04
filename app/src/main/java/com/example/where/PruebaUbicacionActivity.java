package com.example.where;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.where.data.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PruebaUbicacionActivity extends AppCompatActivity {

    public static final int ACCESS_FINE_LOCATION = 1;
    public final String FAIl = "FAIL";

    private TextView txtPrueba;

    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private FirebaseUser FUser;

    private FirebaseAuth mAuth;

    private User user;

    private boolean flag;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prueba_ubicacion);

        getSupportActionBar().hide();

        txtPrueba = findViewById(R.id.txtPrueba);

        mAuth = FirebaseAuth.getInstance();

        FUser = mAuth.getCurrentUser();

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("users");

        flag = true;

        addDatabaseListener();

        requestContactPermission();
    }

    private void addDatabaseListener(){

        ValueEventListener userListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                user = dataSnapshot.getValue(User.class);
                String name = user.name;
                String email = user.email;
                txtPrueba.setText(name + " " + email);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(FAIl, "loadPost:onCancelled", databaseError.toException());

            }
        };

        myRef.child(FUser.getUid()).addListenerForSingleValueEvent(userListener);

    }

    private void requestContactPermission() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                        android.Manifest.permission.ACCESS_FINE_LOCATION)) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle(R.string.get_location);
                    builder.setPositiveButton(android.R.string.ok, null);
                    builder.setMessage(R.string.get_location_explanation);
                    builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @TargetApi(Build.VERSION_CODES.M)
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            requestPermissions(
                                    new String[]
                                            {android.Manifest.permission.ACCESS_FINE_LOCATION}
                                    , ACCESS_FINE_LOCATION);
                        }
                    });
                    builder.show();

                } else {

                    ActivityCompat.requestPermissions(this,
                            new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                            ACCESS_FINE_LOCATION);
                }
            } else {

            }
        } else {

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        if (flag){
            switch (requestCode) {
                case ACCESS_FINE_LOCATION: {
                    // If request is cancelled, the result arrays are empty.
                    if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        // permission was granted, yay! Do the
                        // contacts-related task you need to do.
                    } else {
                        requestContactPermission();
                        flag = false;
                    }
                    return;
                }

            }
        } else {
            mAuth.signOut();
            Intent i = new Intent(PruebaUbicacionActivity.this, MainActivity.class);
            startActivity(i);
            finish();
        }

    }
}
