package com.example.where;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

public class RestLocationActivity extends FragmentActivity
        implements GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnMyLocationClickListener,
        OnMapReadyCallback {

    private GoogleMap mMap;
    public static final int ACCESS_FINE_LOCATION = 1;
    public final String FAIl = "FAIL";
    private FirebaseAuth mAuth;
    private boolean flag;

    private Bundle paquete;

    double lat;
    double lng;
    Location targetLocation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rest_location);

        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapRest);
        mapFragment.getMapAsync(this);

        mAuth = FirebaseAuth.getInstance();

        paquete = getIntent().getBundleExtra("paquete");

        lat = paquete.getDouble("lat");
        lng = paquete.getDouble("lng");

        targetLocation = new Location("");
        targetLocation.setLatitude(lat);//your coords of course
        targetLocation.setLongitude(lng);


    }

    private void requestLocationPermission() {

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
                    flag = true;

                } else {

                    ActivityCompat.requestPermissions(this,
                            new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                            ACCESS_FINE_LOCATION);
                }
            } else {
                initMap();

            }
        } else {
            initMap();
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
                        initMap();
                    } else {
                        requestLocationPermission();
                        flag = false;
                    }
                    return;
                }

            }
        } else {
            mAuth.signOut();
            Intent i = new Intent(RestLocationActivity.this, MainActivity.class);
            startActivity(i);
            finish();
        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        requestLocationPermission();

    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {
        Toast.makeText(this, "Current location:\n" + location, Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onMyLocationButtonClick() {
        Toast.makeText(this, "MyLocation button clicked", Toast.LENGTH_SHORT).show();
        // Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).
        return false;
    }

    private void  initMap() {

        updateMapLocation(targetLocation);

    }

    private void updateMapLocation(Location location) {

        LatLng restPosition = new LatLng(location.getLatitude(), location.getLongitude());

        mMap.addMarker(new MarkerOptions().position(restPosition));

        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(restPosition, 17);
        mMap.animateCamera(cameraUpdate);
    }
}
