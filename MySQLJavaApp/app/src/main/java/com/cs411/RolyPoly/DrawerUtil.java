package com.cs411.RolyPoly;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;


public class DrawerUtil {
    public static void getDrawer(final Activity activity, android.support.v7.widget.Toolbar toolbar) {
        //if you want to update the items at a later time it is recommended to keep it in a variable
        PrimaryDrawerItem drawerEmptyItem= new PrimaryDrawerItem().withIdentifier(0).withName("");
        drawerEmptyItem.withEnabled(false);

        PrimaryDrawerItem drawerItemLeaderboard = new PrimaryDrawerItem().withIdentifier(1)
                .withName(R.string.leaderboard).withIcon(R.drawable.ic_person_black_24dp);
        PrimaryDrawerItem drawerItemAccount = new PrimaryDrawerItem()
                .withIdentifier(2).withName(R.string.account).withIcon(R.drawable.ic_directions_bike_black_24dp);
        PrimaryDrawerItem drawerItemTimeline = new PrimaryDrawerItem()
                .withIdentifier(3).withName(R.string.node_map).withIcon(R.drawable.ic_map_black_24dp);


        SecondaryDrawerItem drawerItemSettings = new SecondaryDrawerItem().withIdentifier(4)
                .withName(R.string.settings).withIcon(R.drawable.ic_settings_black_24dp);
        SecondaryDrawerItem drawerItemAbout = new SecondaryDrawerItem().withIdentifier(5)
                .withName(R.string.about).withIcon(R.drawable.ic_info_black_24dp);
//        SecondaryDrawerItem drawerItemHelp = new SecondaryDrawerItem().withIdentifier(5)
//                .withName(R.string.help).withIcon(R.drawable.ic_help_black_24px);
//        SecondaryDrawerItem drawerItemDonate = new SecondaryDrawerItem().withIdentifier(6)
//                .withName(R.string.donate).withIcon(R.drawable.ic_payment_black_24px);





        //create the drawer and remember the `Drawer` result object
        Drawer result = new DrawerBuilder()
                .withActivity(activity)
                .withToolbar(toolbar)
                .withActionBarDrawerToggle(true)
                .withActionBarDrawerToggleAnimated(true)
                .withCloseOnClick(true)
                .withSelectedItem(-1)
                .withDrawerWidthDp(250)
                .withHeaderPadding(true)
                .withHeader(R.layout.navigation_header)
                .addDrawerItems(
//                        drawerEmptyItem,drawerEmptyItem,drawerEmptyItem,
                        drawerItemLeaderboard,
                        drawerItemAccount,
                        drawerItemTimeline,
                        new DividerDrawerItem(),
                        drawerItemSettings,
                        drawerItemAbout

//                        drawerItemHelp,
//                        drawerItemDonate
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        if (drawerItem.getIdentifier() == 1 ) {
//                            && !(activity instanceof MainActivity) add this for cannot switch to page that is already active
                            // load tournament screen
                            Intent intent = new Intent(activity, MainActivity.class);
                            view.getContext().startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(activity).toBundle());
                        }
                        if (drawerItem.getIdentifier() == 2 ) {
//                            && !(activity instanceof BikeManage) add this for cannot switch to page that is already active
                            // load tournament screen
                            Intent intent = new Intent(activity, BikeManage.class);
                            view.getContext().startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(activity).toBundle());
                        }
                        if (drawerItem.getIdentifier() == 3 ) {
//                            && !(activity instanceof BikeManage) add this for cannot switch to page that is already active
                            // load tournament screen
                            Intent intent = new Intent(activity, YourTimeline.class);
                            view.getContext().startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(activity).toBundle());
                        }
                        if (drawerItem.getIdentifier() == 4) {
                            // load tournament screen
                            Toast.makeText(activity, R.string.settings, Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(activity, AccountActivity.class);
                            view.getContext().startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(activity).toBundle());
                        }
                        if (drawerItem.getIdentifier() == 5) {
                            // load tournament screen
                            Toast.makeText(activity, R.string.about, Toast.LENGTH_LONG).show();
                        }
                        return true;
                    }
                })
                .build();
    }
}