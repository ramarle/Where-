package com.example.where;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.google.android.gms.maps.model.LatLng;

public class SearchActivity extends AppCompatActivity {

    private Spinner spinnerFood, spinnerPrice, spinnerDistance;

    private Button btnSearchRest;

    private LatLng userUbication;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        getSupportActionBar().hide();

        userUbication = new LatLng(getIntent().getDoubleExtra("lat",0.0),getIntent().getDoubleExtra("long",0.0));

        spinnerFood = (Spinner) findViewById(R.id.spinner_food);

        ArrayAdapter<CharSequence> adapterFood = ArrayAdapter.createFromResource(this,R.array.food_array, R.layout.custom_spinner_items);
        adapterFood.setDropDownViewResource(R.layout.custom_dropdown_spinner);
        spinnerFood.setAdapter(adapterFood);


        spinnerPrice = (Spinner) findViewById(R.id.spinner_price);
        ArrayAdapter<CharSequence> adapterPrice = ArrayAdapter.createFromResource(this,R.array.price_array, R.layout.custom_spinner_items);
        adapterPrice.setDropDownViewResource(R.layout.custom_dropdown_spinner);
        spinnerPrice.setAdapter(adapterPrice);


        spinnerDistance = (Spinner) findViewById(R.id.spinner_distance);
        ArrayAdapter<CharSequence> adapterDistance = ArrayAdapter.createFromResource(this,R.array.distance_array, R.layout.custom_spinner_items);
        adapterDistance.setDropDownViewResource(R.layout.custom_dropdown_spinner);
        spinnerDistance.setAdapter(adapterDistance);


        btnSearchRest = findViewById(R.id.btnSearchRest);
        btnSearchRest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent i = new Intent(SearchActivity.this, DisplayRestaurantsActivity.class);
                i.putExtra("lat", userUbication.latitude);
                i.putExtra("long", userUbication.longitude);
                startActivity(i);

            }
        });
    }
}
