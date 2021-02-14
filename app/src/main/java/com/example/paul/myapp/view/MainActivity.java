package com.example.paul.myapp.view;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.support.annotation.NonNull;
import android.support.annotation.StringDef;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;


import com.example.paul.myapp.R;
import com.example.paul.myapp.adaptor.RVAdaptor;
import com.example.paul.myapp.controller.EventManager;
import com.example.paul.myapp.database.DbHelper;
import com.example.paul.myapp.database.UserDAO;
import com.example.paul.myapp.listener.CardListener;
import com.example.paul.myapp.listener.EventListener;
import com.example.paul.myapp.model.DailyEvent;
import com.example.paul.myapp.model.Event;
import com.example.paul.myapp.model.User;
import com.example.paul.myapp.utils.ApplicationPreferences;
import com.example.paul.myapp.listener.DialogFragmentListener;
import com.example.paul.myapp.utils.NotificationEventReceiver;
import com.example.paul.myapp.utils.Util;
import com.github.clans.fab.FloatingActionButton;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import static com.example.paul.myapp.view.ScheduleFragment.FUN;
import static com.example.paul.myapp.view.ScheduleFragment.RELAX;
import static com.example.paul.myapp.view.ScheduleFragment.STUDY;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, DialogFragmentListener, CardListener, EventListener {


    @StringDef({FUN, RELAX, STUDY})
    @Retention(RetentionPolicy.SOURCE)
    public @interface type {
    }

    @ScheduleFragment.type
    String currentType = FUN;

    public void setCurrentType(@ScheduleFragment.type String currentType) {
        this.currentType = currentType;
    }

    public static RecyclerView recyclerView;
    public RVAdaptor adapter;
    public int year;
    public int month;
    public int day;
    EventManager eventManager;
    public FrameLayout frameLayout;
    public static PieChart MainChart;
    public static PieData MainDataChart;
    public static PieDataSet MainDataSetChart;
    public static TextView messageNoEvents;
    static ArrayList<PieEntry> entries = new ArrayList<>();
    public static int[] color = new int[5];
    public static int colorSize = 1;
    final Calendar calendar = Calendar.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        FloatingActionButton sleepAdd = (FloatingActionButton) findViewById(R.id.sleepEvent);
        FloatingActionButton funAdd = (FloatingActionButton) findViewById(R.id.funEvent);
        FloatingActionButton studyAdd = (FloatingActionButton) findViewById(R.id.schoolEvent);
        MainChart = (PieChart) findViewById(R.id.pieChartMain);
        frameLayout = (FrameLayout) findViewById(R.id.fragment_container_create_event);
        messageNoEvents = (TextView) findViewById(R.id.message_no_events);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerMain);


        eventManager = EventManager.getInstance(this);
        eventManager.initiateLists();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle("");
        messageNoEvents.setVisibility(View.INVISIBLE);
        frameLayout.setClickable(false);
        year = getIntent().getIntExtra("year", 0);
        month = getIntent().getIntExtra("month", 0);
        day = getIntent().getIntExtra("day", 0);

        if (year != 0) calendar.set(year, month, day, 0, 0, 0);
        calendar.setTimeInMillis(Util.setHourTo24(calendar.getTimeInMillis()));

        if (eventManager.getEventsByDate(calendar.getTimeInMillis()).size() == 0) {
            recyclerView.setVisibility(View.GONE);
            messageNoEvents.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            messageNoEvents.setVisibility(View.INVISIBLE);
        }

        recyclerView.setHasFixedSize(true);
        adapter = new RVAdaptor(eventManager.getEventsByDate(calendar.getTimeInMillis()));
        adapter.setClass("main");
        adapter.setContext(getApplicationContext());
        recyclerView.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);


        sleepAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager frgmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = frgmentManager.beginTransaction();
                EventFragment fragment = new EventFragment();
                EventFragment.background_event = 3;
                fragmentTransaction.replace(R.id.fragment_container_create_event, fragment, "activity");
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                setCurrentType(RELAX);
            }
        });

        funAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager frgmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = frgmentManager.beginTransaction();
                EventFragment fragment = new EventFragment();
                EventFragment.background_event = 1;
                fragmentTransaction.replace(R.id.fragment_container_create_event, fragment, "activity");
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                setCurrentType(FUN);

            }
        });
        studyAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager frgmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = frgmentManager.beginTransaction();
                EventFragment fragment = new EventFragment();
                fragmentTransaction.replace(R.id.fragment_container_create_event, fragment, "activity");
                frameLayout.setClickable(true);
                EventFragment.background_event = 2;
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                setCurrentType(STUDY);
            }
        });


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);
        View view = navigationView.getHeaderView(0);
        view.setBackgroundColor(Util.setColor(this));


    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
            this.finish();
        } else {
            getSupportFragmentManager().popBackStack();
            frameLayout.setClickable(false);

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_search_item, menu);
        MenuItem searchItem = menu.findItem(R.id.menu_item_search);
        final SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                Intent intent = new Intent(MainActivity.this, SearchActivity.class).putExtra("query", query);
                startActivity(intent);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;


    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.nav_today) {

            Intent intent = new Intent(MainActivity.this, MainActivity.class);
            startActivity(intent);
            finish();

        } else if (id == R.id.nav_calendar) {

            Intent intent = new Intent(MainActivity.this, MyCalendarActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_reward) {
            Intent intent = new Intent(MainActivity.this, RewardActivity.class);
            startActivity(intent);


        } else if (id == R.id.nav_about) {
            Intent intent = new Intent(this, ActivityAbout.class);
            startActivity(intent);

        } else if (id == R.id.profile) {
            Intent intent = new Intent(MainActivity.this, ActivityPersonEdit.class);
            startActivity(intent);

        } else if (id == R.id.nav_suggestion) {
            Intent intent = new Intent(MainActivity.this, ActivitySuggestion.class);
            startActivity(intent);

        } else if (id == R.id.logout) {


            ApplicationPreferences apPref = ApplicationPreferences.getInstance(this);
            apPref.deleteUsername();
            EventManager.getInstance(this).clearLists();
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return false;
    }

    @Override
    public void onReturnValue(DailyEvent daily) {

        daily.getEvent().setType(currentType);


        EventManager.getInstance(this).addEvent(daily);
        float duration = (float) (daily.getDuration() / 3600000.0);
        boolean ok = false;
        if (Util.setHourTo24(daily.getStartDate()) == calendar.getTimeInMillis()) {
            for (PieEntry pieEntry : entries) {
                if (pieEntry.getLabel().equals(daily.getEvent().getType())) {
                    pieEntry.setY(pieEntry.getY() + duration);
                    entries.get(0).setY(entries.get(0).getY() - duration);
                    ok = true;
                }
            }
            if (!ok) {
                entries.add(new PieEntry(duration, daily.getEvent().getType()));
                entries.get(0).setY(entries.get(0).getY() - duration);
                switch (daily.getEvent().getType()) {
                    case FUN:
                        color[colorSize++] = Color.rgb(3, 169, 244);
                        break;
                    case STUDY:
                        color[colorSize++] = Color.rgb(244, 67, 54);
                        break;
                    default:
                        color[colorSize++] = Color.rgb(102, 187, 106);
                        break;
                }
            }
        }

        if (Calendar.getInstance().getTimeInMillis() < daily.getStartDate()) {
            if (!daily.getEvent().getType().equals(RELAX)) {


                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(daily.getStartDate());

                Intent alarm = new Intent(getApplicationContext(), NotificationEventReceiver.class);

                if (daily.getEvent().getType().equals(FUN)) {
                    alarm.putExtra("text", "It's time to prepare for " + daily.getEvent().getNume() + ".");
                } else if (daily.getEvent().getType().equals(STUDY)) {
                    alarm.putExtra("text", "Learning time:" + daily.getEvent().getNume() + " is starting.");
                }


                PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 100, alarm, PendingIntent.FLAG_UPDATE_CURRENT);
                AlarmManager alarmManager = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
                alarmManager.set(0, calendar.getTimeInMillis(), pendingIntent);
            }


        }


        adapter.swap(this, year, month, day);

        List<DailyEvent> dailyEvents2 = EventManager.getInstance(this).getAllDailyEvents();
        if (!Util.isSpace(dailyEvents2, Util.setHourTo24(daily.getStartDate()), 0)) {

            ApplicationPreferences applicationPreferences = ApplicationPreferences.getInstance(this);
            DbHelper dbHelper = DbHelper.getInstance(this);
            UserDAO userDAO = new UserDAO(dbHelper);
            User user = userDAO.getUser(applicationPreferences.getUserId());
            user.setPoints(user.getPoints() + 5);
            userDAO.updateUser(user);

            Bundle bundle = new Bundle();
            bundle.putLong("DateFilter", Util.setHourTo24(daily.getStartDate()));
            DialogFilterEvent dialogFilterEvent = new DialogFilterEvent();
            dialogFilterEvent.setArguments(bundle);
            dialogFilterEvent.show(getFragmentManager(), "Dialog");
        }

        MainDataSetChart.setColors(ColorTemplate.createColors(color));
        MainDataSetChart.notifyDataSetChanged();
        MainDataChart.notifyDataChanged();
        MainChart.notifyDataSetChanged();
        MainChart.invalidate();

        recyclerView.setVisibility(View.VISIBLE);
        messageNoEvents.setVisibility(View.INVISIBLE);


    }

    @Override
    protected void onResume() {
        super.onResume();
        setPieChart();

    }

    @Override
    public void onDisplayEvent(DailyEvent dailyEvent) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        EditEventFragment fragment = new EditEventFragment();

        Bundle bundle = new Bundle();
        bundle.putString("DailyDetailedKey", dailyEvent.getId().toString());
        fragment.setArguments(bundle);
        fragmentTransaction.add(R.id.fragment_container_create_event, fragment, "EditEventFragment");
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();


    }

    @Override
    public void onNewEvent(DailyEvent dailyEvent) {

    }

    @Override
    public void onEditEvent(DailyEvent dailyEvent) {
        adapter.editEvent(dailyEvent);
    }

    @Override
    public void onDeleteEvent(UUID uuid) {
        adapter.deleteEvent(uuid);
    }


    void setPieChart() {
        entries.clear();
        colorSize = 1;
        color = new int[5];
        entries.add(new PieEntry(24, "Free"));
        color[0] = Color.rgb(97, 97, 97);

        for (DailyEvent dailyEvent : eventManager.getEventsByDate(calendar.getTimeInMillis())) {
            boolean ok = false;
            float duration = (float) (dailyEvent.getDuration() / 3600000.0);
            for (PieEntry pieEntry : entries) {
                if (pieEntry.getLabel().equals(dailyEvent.getEvent().getType())) {
                    pieEntry.setY(pieEntry.getY() + duration);
                    entries.get(0).setY(entries.get(0).getY() - duration);
                    ok = true;
                }
            }
            if (!ok) {
                entries.add(new PieEntry(duration, dailyEvent.getEvent().getType()));
                entries.get(0).setY(entries.get(0).getY() - duration);
                switch (dailyEvent.getEvent().getType()) {
                    case FUN:
                        color[colorSize++] = Color.rgb(3, 169, 244);
                        break;
                    case STUDY:
                        color[colorSize++] = Color.rgb(244, 67, 54);
                        break;
                    default:
                        color[colorSize++] = Color.rgb(102, 187, 106);
                        break;
                }
            }
        }

        MainDataSetChart = new PieDataSet(entries, " ");
        MainDataChart = new PieData(MainDataSetChart);
        MainDataSetChart.setColors(ColorTemplate.createColors(color));
        MainChart.setData(MainDataChart);
        MainChart.animateY(1500);
        Description description = new Description();
        description.setText(Util.militoDate(calendar.getTimeInMillis()));
        description.setTextSize(15);
        MainChart.setDescription(description);
        MainChart.getLegend().setEnabled(false);
        MainChart.setHoleRadius(0);
    }

}
