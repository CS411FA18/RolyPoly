package com.cs411.RolyPoly;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AccountActivity extends AppCompatActivity {

    private Button editWeeklyGoal;
    private Button editAccount;
    private Button editBike;
    private Button registerNewBike;

    @BindView(R.id.toolbar_account)
    public Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        ButterKnife.bind(this);

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        User user = (User) bundle.getSerializable("user");

        toolbar.setTitle("Profile");
        setSupportActionBar(toolbar);
        DrawerUtil.getDrawer(this, toolbar, user);

        editWeeklyGoal = findViewById(R.id.edit_weekly_goal);
        editAccount = findViewById(R.id.edit_account);
        editBike = findViewById(R.id.edit_bike);
        registerNewBike = findViewById(R.id.register_new_bike);

        editWeeklyGoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(AccountActivity.this, "Edit weekly goal", Toast.LENGTH_LONG).show();
            }
        });

        editAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(AccountActivity.this, "Edit account information", Toast.LENGTH_LONG).show();
            }
        });

        editBike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(AccountActivity.this, "Edit bike information", Toast.LENGTH_LONG).show();
            }
        });

        registerNewBike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(AccountActivity.this, "Register a new bike", Toast.LENGTH_LONG).show();
            }
        });

    }
}
