package com.cs411.RolyPoly;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class YourTimeline extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    LatLng origin = new LatLng(40.1144, -88.2289);
    LatLng dest = new LatLng(40.1054, -88.2215);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_timeline);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng ECEB = new LatLng(40.1, -88.2);
        mMap.addMarker(new MarkerOptions().position(ECEB).title("Marker at ECEB"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(ECEB));
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setZoomGesturesEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);
    }

//    private void drawPolylines() {
//        ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
//        progressDialog.setMessage("Please Wait, Polyline between two locations is building.");
//        progressDialog.setCancelable(false);
//        progressDialog.show();
//
//        // Checks, whether start and end locations are captured
//        // Getting URL to the Google Directions API
//        String url = getDirectionsUrl(origin, dest);
//        Log.d("url", url + "");
//        DownloadTask downloadTask = new DownloadTask();
//        // Start downloading json data from Google Directions API
//        downloadTask.execute(url);
//    }
}
