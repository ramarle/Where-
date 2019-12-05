package com.example.where;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class SearchActivity extends AppCompatActivity {

    private Spinner spinnerFood, spinnerPrice, spinnerDistance;

    private Button btnSearchRest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        getSupportActionBar().hide();

        spinnerFood = (Spinner) findViewById(R.id.spinner_food);

        ArrayAdapter<CharSequence> adapterFood = ArrayAdapter.createFromResource(this,R.array.food_array, R.layout.custom_spinner_items);
        adapterFood.setDropDownViewResource(R.layout.custom_dropdown_spinner);
        spinnerFood.setAdapter(adapterFood);

        spinnerFood.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id){
                //We are here that means an item was selected
                //Now we retrieve the user selection
                //Get the selected item text
                String selectedItemText = parent.getItemAtPosition(pos).toString();
                Toast toastSpinnerSelection = Toast.makeText(getApplicationContext(), selectedItemText, Toast.LENGTH_SHORT);
                //display the toast notification on user interface
                //set the toast display location
                toastSpinnerSelection.setGravity(Gravity.LEFT|Gravity.BOTTOM,20,150);
                toastSpinnerSelection.show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent){
                //Another interface callback
            }

        });

        spinnerPrice = (Spinner) findViewById(R.id.spinner_price);
        ArrayAdapter<CharSequence> adapterPrice = ArrayAdapter.createFromResource(this,R.array.price_array, R.layout.custom_spinner_items);
        adapterPrice.setDropDownViewResource(R.layout.custom_dropdown_spinner);
        spinnerPrice.setAdapter(adapterPrice);

        spinnerPrice.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id){
                //We are here that means an item was selected
                //Now we retrieve the user selection
                //Get the selected item text
                String selectedItemText = parent.getItemAtPosition(pos).toString();
                Toast toastSpinnerSelection = Toast.makeText(getApplicationContext(), selectedItemText, Toast.LENGTH_SHORT);
                //display the toast notification on user interface
                //set the toast display location
                toastSpinnerSelection.setGravity(Gravity.LEFT|Gravity.BOTTOM,20,150);
                toastSpinnerSelection.show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent){
                //Another interface callback
            }

        });

        spinnerDistance = (Spinner) findViewById(R.id.spinner_distance);
        ArrayAdapter<CharSequence> adapterDistance = ArrayAdapter.createFromResource(this,R.array.distance_array, R.layout.custom_spinner_items);
        adapterDistance.setDropDownViewResource(R.layout.custom_dropdown_spinner);
        spinnerDistance.setAdapter(adapterDistance);

        spinnerDistance.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id){
                //We are here that means an item was selected
                //Now we retrieve the user selection
                //Get the selected item text
                String selectedItemText = parent.getItemAtPosition(pos).toString();
                Toast toastSpinnerSelection = Toast.makeText(getApplicationContext(), selectedItemText, Toast.LENGTH_SHORT);
                //display the toast notification on user interface
                //set the toast display location
                toastSpinnerSelection.setGravity(Gravity.LEFT|Gravity.BOTTOM,20,150);
                toastSpinnerSelection.show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent){
                //Another interface callback
            }

        });

        btnSearchRest = findViewById(R.id.btnSearchRest);
        btnSearchRest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
            }
        });
    }
}
