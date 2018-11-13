package com.cs411.RolyPoly;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.android.volley.RequestQueue;

import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BikeManage extends AppCompatActivity {

    private Button getAll;
    private Button addBike;
    private Button deleteButton;
    private Button updateBikeButton;

    @BindView(R.id.toolbar_bike_manage)
    public android.support.v7.widget.Toolbar toolbar;

    private static final String getAllBikeURL = "https://cs411fa18.web.illinois.edu/phpScripts/ReadAll_Bike.php";

    private Button searchButton;

    private RequestQueue requestQueue;

    ArrayList<Bike> bikeList;

    ArrayList<JSONObject> jsonResults;

    private int count = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_bike_manage);
//        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bike_manage);


        ButterKnife.bind(this);

        toolbar.setTitle(getResources().getString(R.string.app_name));
        setSupportActionBar(toolbar);

        DrawerUtil.getDrawer(this, toolbar);

        getAll = findViewById(R.id.getAll);

        updateBikeButton = findViewById(R.id.updateBikeButton);
        addBike = findViewById(R.id.addBike);
        deleteButton = findViewById(R.id.deleteButton);
        searchButton = findViewById(R.id.searchButtonMain);

        jsonResults = new ArrayList<JSONObject>();

        bikeList = new ArrayList<Bike>();

        updateBikeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), UpdateBikeActivity.class);
                startActivity(intent);
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SearchForBikeActivity.class);
                startActivity(intent);
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RemoveBikeActivity.class);
                startActivity(intent);
            }
        });

        addBike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), addBikeActivity.class);
                startActivity(intent);
            }
        });

        getAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                printJsonArray();
//                printBikeList();

                Intent intent = new Intent(getApplicationContext(), BikeList.class);
//                    intent.putParcelableArrayListExtra("BikeList", bikeList);
                startActivity(intent);
            }
        });
    }


    private void printJsonArray() {
//        int spacesToIndentEachLevel = 2;

        for (int i = 0; i < jsonResults.size(); i++) {
            JSONObject object = jsonResults.get(i);
            System.out.println(object.toString());
        }
    }

    private void printBikeList() {
        for (int i = 0; i < bikeList.size(); i++) {
            System.out.println("Bike Serial Number: " + bikeList.get(i).serialNumber);
        }
    }

}

