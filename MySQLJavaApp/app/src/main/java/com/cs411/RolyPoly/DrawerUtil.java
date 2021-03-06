package com.cs411.RolyPoly;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.View;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

public class DrawerUtil {
    public static void getDrawer(final Activity activity, android.support.v7.widget.Toolbar toolbar, final User user) {
        //if you want to update the items at a later time it is recommended to keep it in a variable
        PrimaryDrawerItem drawerEmptyItem = new PrimaryDrawerItem().withIdentifier(0).withName("");
        drawerEmptyItem.withEnabled(false);

        PrimaryDrawerItem drawerItemDashboard = new PrimaryDrawerItem()
                .withIdentifier(1).withName(R.string.dashboard).withIcon(R.drawable.ic_dashboard_black_24dp);
        PrimaryDrawerItem drawerItemLeaderboard = new PrimaryDrawerItem()
                .withIdentifier(2).withName(R.string.leaderboard).withIcon(R.drawable.ic_person_black_24dp);
        PrimaryDrawerItem drawerItemTimeline = new PrimaryDrawerItem()
                .withIdentifier(4).withName(R.string.node_map).withIcon(R.drawable.ic_map_black_24dp);

        SecondaryDrawerItem drawerItemAccount = new SecondaryDrawerItem()
                .withIdentifier(3).withName(R.string.account).withIcon(R.drawable.ic_directions_bike_black_24dp);
        SecondaryDrawerItem drawerItemSettings = new SecondaryDrawerItem()
                .withIdentifier(5).withName(R.string.settings).withIcon(R.drawable.ic_settings_black_24dp);
        SecondaryDrawerItem drawerItemAbout = new SecondaryDrawerItem()
                .withIdentifier(6).withName(R.string.about).withIcon(R.drawable.ic_info_black_24dp);
        /* Found this icon on the material design icon page https://materialdesignicons.com */
        SecondaryDrawerItem drawerItemSignOut = new SecondaryDrawerItem()
                .withIdentifier(7).withName(R.string.signOut).withIcon(R.drawable.ic_logout);


        //create the drawer and remember the `Drawer` result object
        Drawer result = new DrawerBuilder()
                .withActivity(activity)
                .withToolbar(toolbar)
                .withActionBarDrawerToggle(true)
                .withActionBarDrawerToggleAnimated(true)
                .withCloseOnClick(true)
                .withSelectedItem(-1)
                .withDrawerWidthDp(250)
                .withHeaderPadding(false)
                .withHeader(R.layout.navigation_header)
                .addDrawerItems(
                        drawerItemDashboard,
                        drawerItemLeaderboard,
                        drawerItemTimeline,
                        new DividerDrawerItem(),
                        drawerItemAccount,
                        drawerItemSettings,
                        drawerItemAbout,
                        drawerItemSignOut
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(final View view, int position, IDrawerItem drawerItem) {
                        if (drawerItem.getIdentifier() == 1) {
                            // load Dashboard Screen
                            Intent intent = new Intent(activity, TabbedDashboardActivity.class);
                            intent.putExtra("user", user);
                            view.getContext().startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(activity).toBundle());
                        }
                        if (drawerItem.getIdentifier() == 2) {
                            // load Leaderboard screen
                            Intent intent = new Intent(activity, TabbedLeaderboardActivity.class);
                            intent.putExtra("user", user);
                            view.getContext().startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(activity).toBundle());
                        }
                        if (drawerItem.getIdentifier() == 3) {
                            // load BikeList screen
                            Intent intent = new Intent(activity, BikeList.class);
                            intent.putExtra("user", user);
                            view.getContext().startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(activity).toBundle());
                        }
                        if (drawerItem.getIdentifier() == 4) {
                            // load NodeMap screen
                            Intent intent = new Intent(activity, YourTimeline.class);
                            intent.putExtra("user", user);
                            view.getContext().startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(activity).toBundle());
                        }
                        if (drawerItem.getIdentifier() == 5) {
                            // load Account Settings screen
                            Intent intent = new Intent(activity, AccountActivity.class);
                            intent.putExtra("user", user);
                            view.getContext().startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(activity).toBundle());
                        }
                        if (drawerItem.getIdentifier() == 6) {
                            // load About screen
                            Intent intent = new Intent(activity, AboutActivity.class);
                            intent.putExtra("user", user);
                            view.getContext().startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(activity).toBundle());
                        }
                        if (drawerItem.getIdentifier() == 7) {
                            // load tournament screen
                            AuthUI.getInstance()
                                    .signOut(activity)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            activity.finish();
                                            Intent intent = new Intent(activity, MainActivity.class);
                                            view.getContext().startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(activity).toBundle());
                                        }
                                    });

                        }
                        return true;
                    }
                })
                .build();
    }
}