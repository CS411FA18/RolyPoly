package com.cs411.RolyPoly;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AccountActivity extends AppCompatActivity {
    @BindView(R.id.toolbar_account)
    public Toolbar toolbar;

    Button editAccount;
    TextView weeklyGoalTv;
    TextView firstNameTv;
    TextView lastNameTv;
    TextView uinTv;
    TextView userTypeTv;
    TextView deptTv;
    TextView addressTv;
    TextView emailTv;
    TextView phoneNumberTv;

    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        ButterKnife.bind(this);

        final Bundle bundle = this.getIntent().getExtras();
        User user = (User) bundle.getSerializable("user");

        toolbar.setTitle("Profile");
        setSupportActionBar(toolbar);
        DrawerUtil.getDrawer(this, toolbar, user);

        weeklyGoalTv = findViewById(R.id.weekly_goal_tv);
        firstNameTv = findViewById(R.id.first_name_tv);
        lastNameTv = findViewById(R.id.last_name_tv);
        uinTv = findViewById(R.id.uin_tv);
        userTypeTv = findViewById(R.id.user_type_tv);
        deptTv = findViewById(R.id.dept_name_tv);
        addressTv = findViewById(R.id.address_tv);
        emailTv = findViewById(R.id.email_tv);
        phoneNumberTv = findViewById(R.id.phone_number_tv);

//        weeklyGoalTv.setText(String.valueOf(user.weeklyGoal));
        weeklyGoalTv.setText(user.weeklyGoal.toString());
        firstNameTv.setText(user.firstName);
        lastNameTv.setText(user.lastName);
        uinTv.setText(user.UIN.toString());
        userTypeTv.setText(user.userType);
        deptTv.setText(user.deptName);
        addressTv.setText(user.address);
        emailTv.setText(user.email);
        phoneNumberTv.setText(user.phoneNumber);

        editAccount = findViewById(R.id.edit_account);
        editAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), EditAccount.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

    }
}
