package com.cs411.RolyPoly;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LeaderboardActivity extends AppCompatActivity {

    @BindView(R.id.toolbar_leaderboard)
    public Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        ButterKnife.bind(this);

        toolbar.setTitle(getResources().getString(R.string.leaderboard));
        setSupportActionBar(toolbar);
        DrawerUtil.getDrawer(this, toolbar);

    }

}
