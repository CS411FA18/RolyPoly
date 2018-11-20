package com.cs411.RolyPoly;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.shinelw.library.ColorArcProgressBar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.toolbar_main)
    public Toolbar toolbar;

    //ProgressBar imported from open source library
    public ColorArcProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        setContentView(R.layout.activity_main);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        toolbar = findViewById(R.id.toolbar_main);

        ButterKnife.bind(this);

        toolbar.setTitle(getResources().getString(R.string.app_name));
        setSupportActionBar(toolbar);

        DrawerUtil.getDrawer(this, toolbar);

        //find the Progress Bar and set the Value to 50
        // SET THIS TO THE USERS CURRENT NUMBER OF PINGS
        // SET THE MAX VALUE TO USERS WEEKLY GOAL
        progressBar = (ColorArcProgressBar) findViewById(R.id.arcProgressbar1);
        progressBar.setCurrentValues(50);

    }
}
