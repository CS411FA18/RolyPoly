package com.cs411.RolyPoly;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TabLeaderboardPageAdapater extends FragmentPagerAdapter {

    int tabCount;

    public TabLeaderboardPageAdapater(FragmentManager fm, int numberOfTabs) {
        super(fm);
        this.tabCount = numberOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                Leaderboard1Fragment tab1 = new Leaderboard1Fragment();
                return tab1;
            case 1:
                Leaderboard2Fragment tab2 = new Leaderboard2Fragment();
                return tab2;
            case 2:
                Leaderboard3Fragment tab3 = new Leaderboard3Fragment();
                return tab3;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
