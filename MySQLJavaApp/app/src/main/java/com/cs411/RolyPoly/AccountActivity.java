package com.cs411.RolyPoly;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AccountActivity extends AppCompatActivity {
    @BindView(R.id.toolbar_account_setting)
    public Toolbar toolbar;

    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        Toolbar toolbar = findViewById(R.id.toolbar);
        TextView accountTV = findViewById(R.id.account_tv);
        Button editProfileButton = findViewById(R.id.edit_account);

        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        ButterKnife.bind(this);

//        toolbar.setTitle(getResources().getString(R.string.settings));
        setSupportActionBar(toolbar);

        final Bundle bundle = this.getIntent().getExtras();
        user = (User) bundle.getSerializable("user");

        DrawerUtil.getDrawer(this, toolbar, user);

        accountTV.setText(user.toString());

        editProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), EditAccount.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
}
