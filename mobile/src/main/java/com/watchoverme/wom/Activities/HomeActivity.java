package com.watchoverme.wom.Activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.watchoverme.wom.Adapters.ViewPagerAdapter;
import com.watchoverme.wom.Fragments.ContactFragment;
import com.watchoverme.wom.Fragments.HomeFragment;
import com.watchoverme.wom.Fragments.NotificationFragment;
import com.watchoverme.wom.R;

public class HomeActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPager;
    ViewPagerAdapter viewPagerAdapter;
    HomeFragment homeFragment;
    NotificationFragment notificationFragment;
    ContactFragment contactFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        tabLayout = (TabLayout) findViewById(R.id.menu_tabs);
        viewPager = (ViewPager) findViewById(R.id.menu_pages);

        homeFragment = new HomeFragment();
        notificationFragment = new NotificationFragment();
        contactFragment = new ContactFragment();

        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        viewPagerAdapter.addFragments(notificationFragment,"Notifications");
        viewPagerAdapter.addFragments(homeFragment,"Home");
        viewPagerAdapter.addFragments(contactFragment,"Contact");


        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

    }
}
