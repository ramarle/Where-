package com.example.where;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class SearchActivity extends AppCompatActivity {

    private Spinner spinnerFood, spinnerPrice, spinnerDistance;

    private Button btnSearchRest;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        getSupportActionBar().hide();
        progressDialog = new ProgressDialog(this);


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
                progressDialog.setTitle(R.string.loading);
                progressDialog.show();

                Intent i = new Intent(SearchActivity.this, RestaurantListActivity.class);
                startActivity(i);

                progressDialog.hide();

            }
        });
    }
}
