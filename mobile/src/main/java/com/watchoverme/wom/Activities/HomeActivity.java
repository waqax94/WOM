package com.watchoverme.wom.Activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.watchoverme.wom.Adapters.ViewPagerAdapter;
import com.watchoverme.wom.Fragments.ContactFragment;
import com.watchoverme.wom.Fragments.HomeFragment;
import com.watchoverme.wom.Fragments.NotificationFragment;
import com.watchoverme.wom.Fragments.SettingsFragment;
import com.watchoverme.wom.R;

public class HomeActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPager;
    TextView serviceId,wearerName;
    ViewPagerAdapter viewPagerAdapter;
    HomeFragment homeFragment;
    NotificationFragment notificationFragment;
    ContactFragment contactFragment;
    SettingsFragment settingsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        SharedPreferences loginData = getSharedPreferences("wearerInfo", Context.MODE_PRIVATE);
        String sId = loginData.getString("service_id","");
        String wName = loginData.getString("name","");

        serviceId = (TextView) findViewById(R.id.service_id);
        tabLayout = (TabLayout) findViewById(R.id.menu_tabs);
        viewPager = (ViewPager) findViewById(R.id.menu_pages);
        wearerName = (TextView) findViewById(R.id.wearer_name);

        serviceId.setText("SVC-" + sId);
        wearerName.setText(wName);
        homeFragment = new HomeFragment();
        notificationFragment = new NotificationFragment();
        contactFragment = new ContactFragment();
        settingsFragment = new SettingsFragment();

        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        viewPagerAdapter.addFragments(notificationFragment,"Notifications");
        viewPagerAdapter.addFragments(homeFragment,"Home");
        viewPagerAdapter.addFragments(contactFragment,"Watchers");
        viewPagerAdapter.addFragments(settingsFragment,"Settings");


        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        TabLayout.Tab tab = tabLayout.getTabAt(1);
        tab.select();
    }
}
