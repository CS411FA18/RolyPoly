package com.cs411.RolyPoly;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class YourTimeline extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    LatLng origin = new LatLng(40.1144, -88.2289);
    LatLng dest = new LatLng(40.1054, -88.2215);

    Integer onResponse = 0;

    ArrayList<Node> nodeList;
    private RequestQueue requestQueue;
    ArrayList<JSONObject> jsonResults;
    private static final String getAllNodeURL = "https://cs411fa18.web.illinois.edu/phpScripts/getNodeCordinates.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_timeline);

        nodeList = new ArrayList<Node>();
        jsonResults = new ArrayList<JSONObject>();

        getNodes();
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
        // Add a marker at ECEB and move the camera
        LatLng ECEB = new LatLng(40.1144, -88.2289);
        mMap.addMarker(new MarkerOptions().position(ECEB).title("Marker at ECEB"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(ECEB));
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setZoomGesturesEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);
    }

    private void getNodes(){
        requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, getAllNodeURL, (String) null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            System.out.println("Reached onResponse Function");
//                            System.out.println("Printing response");
//                            System.out.println(response);

                            JSONArray jsonArray = response.getJSONArray("results");
//                            System.out.println("Printing jsonArray");
//                            System.out.println(jsonArray);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                jsonResults.add(jsonArray.getJSONObject(i));
                            }
//                            System.out.println("Printing JSON Array from inside getAllBikesFunction: ");
//                            printJsonArray();

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                Gson gson = new Gson();

//                                System.out.println("Printing JSONObject");
//                                System.out.println(jsonObject);

                                Node node = gson.fromJson(jsonObject.toString(), Node.class);
//                                System.out.println(bike.toString());
//                                System.out.println(node);
                                nodeList.add(node);
                            }

                            System.out.println("Printing response");
                            printNodes();
//                            printJsonArray();


                            //Populate the ListView when information comes in
                            if(onResponse == 0){
                                onResponse = 1;
                                //set map coordinates
                            }

                            LatLng tempNode;



//                            if(mMap != null){
//                                mMap.clear();
//                                Node node;
//                                for(int i = 0; i < nodeList.size(); i++){
//                                    node = nodeList.get(i);
//                                    tempNode = new LatLng(node.YCoord, node.XCoord);
//                                    mMap.addMarker(new MarkerOptions().position(tempNode).title(node.Name));
//                                }
//                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley", "Error: " +  error.getMessage());
                    }
                });
        requestQueue.add(jsonObjectRequest);
    }

    private void printNodes(){
        for(int i = 0; i < nodeList.size(); i++){
            System.out.println(nodeList.get(i));
        }
    }

    private void printJsonArray() {

        for (int i = 0; i < jsonResults.size(); i++) {
            JSONObject object = jsonResults.get(i);
            System.out.println(object.toString());
        }
    }

}
