package com.cs411.RolyPoly;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BikeAdapter extends ArrayAdapter<Bike> {
    private static final String deleteBikeURL = "https://cs411fa18.web.illinois.edu/phpScripts/Delete_Bike.php";

    private RequestQueue requestQueue;
    User user;
    Bundle bundle;

    public BikeAdapter(Context context, ArrayList<Bike> bikes) {
        super(context, 0, bikes);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        bundle = ((Activity)getContext()).getIntent().getExtras();
        final Bike bike = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_bike_list_element, parent, false);
        }

        // TODO: display tagID?
        // TODO: display values for non mandatory fields - check profile view also

        TextView nicknameTv = convertView.findViewById(R.id.nickname_tv);
        TextView serialNumberTv = convertView.findViewById(R.id.serial_number_tv);
        TextView makeTv = convertView.findViewById(R.id.make_tv);
        TextView modelTv = convertView.findViewById(R.id.model_tv);
        TextView colorTv = convertView.findViewById(R.id.color_tv);
        TextView descriptionTv = convertView.findViewById(R.id.description_tv);
        TextView uniqueCharsTv = convertView.findViewById(R.id.unique_chars_tv);
        Button deleteBike = convertView.findViewById(R.id.delete_bike_button);

        nicknameTv.setText(bike.nickname);
        serialNumberTv.setText(bike.serialNumber);
        makeTv.setText(bike.bikeMake);
        modelTv.setText(bike.model);
        colorTv.setText(bike.bikeColor);
        descriptionTv.setText(bike.bikeDescription);
        uniqueCharsTv.setText(bike.uniqueCharacteristics);
        deleteBike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteBike(bike);
                ((Activity)getContext()).finish();
                Intent intent = new Intent(getContext(), BikeList.class);
                intent.putExtras(bundle);
                getContext().startActivity(intent);
            }
        });

        return convertView;
    }

    public void deleteBike(final Bike bike) {
        requestQueue = Volley.newRequestQueue(getContext());

        StringRequest stringRequest = new StringRequest(Request.Method.POST, deleteBikeURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject jsonObj;
                        try {
                            jsonObj = new JSONObject(response);

                            if(jsonObj.get("success").equals("0")){
                                Toast.makeText(getContext(), "Invalid TagID. Try Again!", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getContext(), "Bike Deleted Successfully!", Toast.LENGTH_SHORT).show();
                            }
                        }  catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley", error.toString());
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("TagID", bike.tagID.toString());
                params.put("UIN", bike.UIN.toString());
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }
}
