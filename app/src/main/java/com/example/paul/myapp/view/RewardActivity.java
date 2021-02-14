package com.example.paul.myapp.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.example.paul.myapp.R;
import com.example.paul.myapp.adaptor.FilterAdaptor;
import com.example.paul.myapp.controller.EventManager;
import com.example.paul.myapp.database.DbHelper;
import com.example.paul.myapp.database.UserDAO;
import com.example.paul.myapp.model.Reward;
import com.example.paul.myapp.model.User;
import com.example.paul.myapp.utils.ApplicationPreferences;
import com.example.paul.myapp.utils.Util;

import java.util.ArrayList;


public class RewardActivity  extends AppCompatActivity{


    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reward_activty);


        ArrayList<Reward> rewardArrayList= new ArrayList<>();
        Reward reward= new Reward(R.drawable.ic_bronze_1,"Bronze Title",100);
        Reward reward1= new Reward(R.drawable.ic_silver,"Silver Title",500);
        Reward reward2= new Reward(R.drawable.ic_gold,"Gold Title",1000);
        Reward reward3= new Reward(R.drawable.ic_veteran,"Veteran Title",3000);
        rewardArrayList.add(reward);
        rewardArrayList.add(reward1);
        rewardArrayList.add(reward2);
        rewardArrayList.add(reward3);


        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerReward);
        recyclerView.setHasFixedSize(true);

        ApplicationPreferences applicationPreferences= ApplicationPreferences.getInstance(this);
        DbHelper dbHelper= DbHelper.getInstance(this);
        UserDAO userDAO= new UserDAO(dbHelper);
        User user= userDAO.getUser(applicationPreferences.getUserId());

        FilterAdaptor adapter = new FilterAdaptor(rewardArrayList,"reward",user.getPoints());
        recyclerView.setAdapter(adapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);



        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle("");

        DrawerLayout drawerMy = (DrawerLayout) findViewById(R.id.reward_drawer);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerMy, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerMy.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_reward);
        navigationView.setItemIconTintList(null);

        View view= navigationView.getHeaderView(0);
        view.setBackgroundColor(Util.setColor(this));
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int id = item.getItemId();
                if (id == R.id.nav_today) {

                    Intent intent = new Intent(RewardActivity.this, MainActivity.class);
                    startActivity(intent);


                } else if (id == R.id.nav_calendar) {
                    Intent intent = new Intent(RewardActivity.this, MyCalendarActivity.class);
                    startActivity(intent);


                }else if(id==R.id.nav_suggestion){
                    Intent intent=new Intent(RewardActivity.this,ActivitySuggestion.class);
                    startActivity(intent);

                } else if (id == R.id.nav_about) {
                    Intent intent = new Intent(RewardActivity.this, ActivityAbout.class);
                    startActivity(intent);


                } else if (id == R.id.logout) {

                    ApplicationPreferences apPref = ApplicationPreferences.getInstance(RewardActivity.this);
                    apPref.deleteUsername();
                    EventManager.getInstance(RewardActivity.this).clearLists();
                    Intent intent = new Intent(RewardActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }

                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.reward_drawer);
                drawer.closeDrawer(GravityCompat.START);

                return false;
            }
        });
    }
}
