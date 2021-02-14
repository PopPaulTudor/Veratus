package com.example.paul.myapp.view;


import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.RectF;
import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import android.view.View;
import android.widget.CalendarView;
import android.widget.TextView;

import com.alamkanak.weekview.MonthLoader;
import com.alamkanak.weekview.WeekView;
import com.alamkanak.weekview.WeekViewEvent;
import com.example.paul.myapp.R;
import com.example.paul.myapp.controller.EventManager;
import com.example.paul.myapp.model.DailyEvent;
import com.example.paul.myapp.model.Event;
import com.example.paul.myapp.utils.ApplicationPreferences;
import com.example.paul.myapp.utils.Util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.example.paul.myapp.view.ScheduleFragment.FUN;
import static com.example.paul.myapp.view.ScheduleFragment.RELAX;
import static com.example.paul.myapp.view.ScheduleFragment.STUDY;


public class MyCalendarActivity extends AppCompatActivity {

    private WeekView mWeekView;
    private CalendarView mCalendarView;
    private TextView hoursLeftEditText;
    public List<WeekViewEvent> events = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_calendar);
        mWeekView = (WeekView) findViewById(R.id.weekView_my);
        mCalendarView = (CalendarView) findViewById(R.id.MyCalendar);
        hoursLeftEditText = (TextView) findViewById(R.id.hourLeft);
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        final Calendar calendarOld = Calendar.getInstance();
        final Calendar calendar = Calendar.getInstance();


        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mToolbar.setTitle("");
        final List<DailyEvent> dailyEvents = EventManager.getInstance(this).getAllDailyEvents();
        final EventManager eventManager = EventManager.getInstance(this);

        for (DailyEvent dailyEvent : dailyEvents) {
            int id = 1;
            Calendar startTime = Calendar.getInstance();
            Calendar endTime = Calendar.getInstance();
            startTime.setTimeInMillis(dailyEvent.getStartDate());
            endTime.setTimeInMillis(dailyEvent.getStartDate() + dailyEvent.getDuration());
            WeekViewEvent weekView = new WeekViewEvent(id++, dailyEvent.getEvent().getNume(), startTime, endTime);
            if (dailyEvent.getEvent().getType().equals(FUN))
                weekView.setColor(Color.rgb(3, 169, 244));
            if (dailyEvent.getEvent().getType().equals(STUDY))
                weekView.setColor(Color.rgb(244, 67, 54));
            if (dailyEvent.getEvent().getType().equals(RELAX))
                weekView.setColor(Color.rgb(102, 187, 106));
            events.add(weekView);
        }


        mWeekView.setVisibility(View.INVISIBLE);
        mWeekView.setOnEventClickListener(new WeekView.EventClickListener() {
            @Override
            public void onEventClick(WeekViewEvent event, RectF eventRect) {

            }
        });


        mWeekView.setEventLongPressListener(new WeekView.EventLongPressListener() {
            @Override
            public void onEventLongPress(WeekViewEvent event, RectF eventRect) {

            }
        });

        MonthLoader.MonthChangeListener mMonthChangeListener = new MonthLoader.MonthChangeListener() {
            @Override
            public List<WeekViewEvent> onMonthChange(int newYear, int newMonth) {
                return returnList(newMonth, newYear);
            }

        };


        mWeekView.setMonthChangeListener(mMonthChangeListener);


        mCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {

                calendar.set(year, month, dayOfMonth, 0, 0, 0);
                long sum = 0;

                if (calendar.getTimeInMillis() != calendarOld.getTimeInMillis()) {
                    List<DailyEvent> arrayList = eventManager.getEventsByDate(Util.setHourTo24(calendar.getTimeInMillis()));
                    for (DailyEvent dailyEvent : arrayList) {
                        sum += dailyEvent.getDuration();
                    }

                    int hourLeft = (int) (sum / 3600000);
                    hourLeft = 24 - hourLeft;
                    hoursLeftEditText.setText("");
                    if (hourLeft == 0) hoursLeftEditText.setText("Your agenda is full.");
                    else if (hourLeft == 1)
                        hoursLeftEditText.setText("You have 1 hour free.\n\nYou shouldn't make more plans.");
                    else if (hourLeft > 1 && hourLeft <= 3)
                        hoursLeftEditText.setText("You have " + hourLeft + " hours free.\n\nYou shouldn't make more plans.");
                    else if (hourLeft > 3 && hourLeft <= 8)
                        hoursLeftEditText.setText("You have " + hourLeft + " hours free.\n\nYou have time for one more activity.");
                    else if (hourLeft > 9 && hourLeft <= 14)
                        hoursLeftEditText.setText("You have " + hourLeft + " hours free.\n\nYou have enough time to " + Util.getActivityy());
                    else if (hourLeft > 14)
                        hoursLeftEditText.setText("You have " + hourLeft + " hours free.\n\nYou have all the time in the world.");
                    calendarOld.set(year, month, dayOfMonth, 0, 0, 0);


                } else {
                    Intent intent = new Intent(MyCalendarActivity.this, MainActivity.class);
                    intent.putExtra("year", year);
                    intent.putExtra("month", month);
                    intent.putExtra("day", dayOfMonth);
                    intent.putExtra("Calendar", 1);
                    startActivity(intent);
                }


            }
        });


        DrawerLayout drawerMy = (DrawerLayout) findViewById(R.id.drawer_layout_my);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerMy, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerMy.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_my);
        navigationView.setItemIconTintList(null);
        View view= navigationView.getHeaderView(0);
        view.setBackgroundColor(Util.setColor(this));
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int id = item.getItemId();
                if (id == R.id.nav_today) {

                    Intent intent = new Intent(MyCalendarActivity.this, MainActivity.class);
                    startActivity(intent);

                } else if (id == R.id.nav_reward) {
                    Intent intent = new Intent(MyCalendarActivity.this, RewardActivity.class);
                    startActivity(intent);


                } else if (id == R.id.profile) {
                    Intent intent = new Intent(MyCalendarActivity.this, ActivityPersonEdit.class);
                    startActivity(intent);

                } else if (id == R.id.nav_suggestion) {
                    Intent intent = new Intent(MyCalendarActivity.this, ActivitySuggestion.class);
                    startActivity(intent);


                } else if (id == R.id.nav_about) {
                    Intent intent = new Intent(MyCalendarActivity.this, ActivityAbout.class);
                    startActivity(intent);


                } else if (id == R.id.logout) {

                    ApplicationPreferences apPref = ApplicationPreferences.getInstance(MyCalendarActivity.this);
                    apPref.deleteUsername();
                    EventManager.getInstance(MyCalendarActivity.this).clearLists();
                    Intent intent = new Intent(MyCalendarActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }

                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_my);
                drawer.closeDrawer(GravityCompat.START);

                return false;
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_my_google_calendar, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.menu_item_my_google) {

            mCalendarView.setVisibility(View.VISIBLE);
            hoursLeftEditText.setVisibility(View.VISIBLE);
            mWeekView.setVisibility(View.GONE);


            return true;
        }
        if (id == R.id.menu_item_my_alam) {

            mCalendarView.setVisibility(View.GONE);
            hoursLeftEditText.setVisibility(View.GONE);
            mWeekView.setVisibility(View.VISIBLE);

            return true;
        }

        return super.onOptionsItemSelected(item);

    }

    List<WeekViewEvent> returnList(int month, int year) {

        List<WeekViewEvent> auxevents = new ArrayList<>();
        for (WeekViewEvent weekViewEvent : events) {

            if (weekViewEvent.getStartTime().get(Calendar.MONTH) == month && weekViewEvent.getStartTime().get(Calendar.YEAR) == year) {
                auxevents.add(weekViewEvent);
            }
        }

        return auxevents;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
