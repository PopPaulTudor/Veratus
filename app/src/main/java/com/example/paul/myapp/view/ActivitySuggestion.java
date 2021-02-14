package com.example.paul.myapp.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.example.paul.myapp.R;
import com.example.paul.myapp.controller.EventManager;
import com.example.paul.myapp.utils.ApplicationPreferences;
import com.example.paul.myapp.utils.Util;

import java.util.ArrayList;
import java.util.List;


public class ActivitySuggestion extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggestion);

        viewPager = (ViewPager) findViewById(R.id.viewpagerSuggestion);
        tabLayout = (TabLayout) findViewById(R.id.tabsSuggestion);

        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarSuggestion);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle("");


        DrawerLayout drawerMy = (DrawerLayout) findViewById(R.id.drawer_suggestion);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerMy, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerMy.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_suggestion);
        navigationView.setItemIconTintList(null);
        View view= navigationView.getHeaderView(0);
        view.setBackgroundColor(Util.setColor(this));
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int id = item.getItemId();
                if (id == R.id.nav_today) {

                    Intent intent = new Intent(ActivitySuggestion.this, MainActivity.class);
                    startActivity(intent);


                } else if (id == R.id.profile) {
                    Intent intent= new Intent( ActivitySuggestion.this,ActivityPersonEdit.class);
                    startActivity(intent);


                } else if (id == R.id.nav_reward) {
                    Intent intent = new Intent(ActivitySuggestion.this, RewardActivity.class);
                    startActivity(intent);

                }else if(id==R.id.MyCalendar){
                    Intent intent=new Intent(ActivitySuggestion.this,MyCalendarActivity.class);
                    startActivity(intent);


                } else if (id == R.id.nav_about) {
                    Intent intent= new Intent(ActivitySuggestion.this, ActivityAbout.class);
                    startActivity(intent);


                } else if (id == R.id.logout) {


                    ApplicationPreferences apPref = ApplicationPreferences.getInstance(ActivitySuggestion.this);
                    apPref.deleteUsername();
                    EventManager.getInstance(ActivitySuggestion.this).clearLists();
                    Intent intent = new Intent(ActivitySuggestion.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }

                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_suggestion);
                drawer.closeDrawer(GravityCompat.START);

                return false;
            }
        });


    }

    private void setupViewPager(ViewPager viewPager) {

        final ViewPagerAdapterSuggestion adapter = new ViewPagerAdapterSuggestion(getSupportFragmentManager());
        adapter.addFragment(new ScheduleSuggestionFragment(), "Schedule suggestion");
        adapter.addFragment(new ClassSuggestionFragment(), "Class suggestion");
        viewPager.setAdapter(adapter);

    }


    private class ViewPagerAdapterSuggestion extends FragmentPagerAdapter {

        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        ViewPagerAdapterSuggestion(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }
        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }

    }


    @Override
    public void onBackPressed() {

        if(getSupportFragmentManager().getBackStackEntryCount()==1){
            ScheduleSuggestionFragment.frameLayout.setClickable(false);
        }
        super.onBackPressed();
    }
}



