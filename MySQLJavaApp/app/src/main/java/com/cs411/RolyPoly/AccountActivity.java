package com.cs411.RolyPoly;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AccountActivity extends AppCompatActivity {

    @BindView(R.id.toolbar_account_setting)
    public Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        ButterKnife.bind(this);

        toolbar.setTitle(getResources().getString(R.string.settings));
        setSupportActionBar(toolbar);

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        User user = (User)bundle.getSerializable("user");

        DrawerUtil.getDrawer(this, toolbar, user);
    }
}
