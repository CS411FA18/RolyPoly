package com.cs411.RolyPoly;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class BikeList extends AppCompatActivity {

    ArrayList<Bike> bikeList;

    private Button returnMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bike_list);

        bikeList = new ArrayList<Bike>();

        returnMain = findViewById(R.id.return_Main);

        Intent intent = getIntent();

        bikeList = intent.getParcelableArrayListExtra("BikeList");

        ArrayAdapter<Bike> arrayAdapter = new ArrayAdapter<Bike>(this, R.layout.activity_bike_list_element, R.id.bikeText, bikeList);

        ListView listView = (ListView) findViewById(R.id.Bike_List);

        listView.setAdapter(arrayAdapter);

        System.out.println("Printing BikeList");
        printBikeList();
        System.out.println("After Printing BikeList");

        returnMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }


    private void printBikeList(){
        for (int i = 0; i < bikeList.size(); i++){
            System.out.println("Bike Serial Number: " + bikeList.get(i).serialNumber);
        }
    }
}
