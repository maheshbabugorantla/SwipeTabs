package com.example.ganga.swipetabs;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.ganga.swipetabs.Fragments.CallLogs;
import com.example.ganga.swipetabs.Fragments.Contacts;
import com.example.ganga.swipetabs.Fragments.DialScreen;

/**
 * Created by Mahesh Babu Gorantla on 1/1/17.
 */

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager viewPager = (ViewPager) findViewById(R.id.swipeTabs);
        FragmentPagerAdapter fragmentPagerAdapter = new myPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(fragmentPagerAdapter);
    }

    public static class myPagerAdapter extends FragmentPagerAdapter {

        private static int NUM_PAGES = 3;

        public myPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public Fragment getItem(int position) {

            switch(position) {

                case 0:
                    return new DialScreen();

                case 1:
                    return new CallLogs();

                case 2:
                    return new Contacts();

                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }

        @Override
        public CharSequence getPageTitle(int position) {

            switch (position) {

                case 0:
                    return "DIAL";

                case 1:
                    return "LOGS";

                case 2:
                    return "CONTACTS";

                default:
                    return "TAB";
            }
        }
    }
}