package com.example.paul.myapp.view;


import android.graphics.Color;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;

import com.alamkanak.weekview.MonthLoader;
import com.alamkanak.weekview.WeekView;
import com.alamkanak.weekview.WeekViewEvent;
import com.example.paul.myapp.listener.EventListener;
import com.example.paul.myapp.R;

import com.example.paul.myapp.model.DailyEvent;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import static com.example.paul.myapp.view.ScheduleFragment.FUN;
import static com.example.paul.myapp.view.ScheduleFragment.RELAX;
import static com.example.paul.myapp.view.ScheduleFragment.STUDY;


public class DayFragment extends Fragment implements EventListener {


    public List<WeekViewEvent> events = new ArrayList<>();
    private WeekView mWeekView;
    public int idCreate=1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_one, container, false);
        mWeekView = (WeekView) v.findViewById(R.id.weekView);


        mWeekView.setMonthChangeListener(new MonthLoader.MonthChangeListener() {

            @Override
            public List<WeekViewEvent> onMonthChange(int newYear, int newMonth) {

                return events;
            }
        });

        mWeekView.setEventLongPressListener(new WeekView.EventLongPressListener() {
            @Override
            public void onEventLongPress(WeekViewEvent event, RectF eventRect) {}
        });

        mWeekView.setEmptyViewLongPressListener(new WeekView.EmptyViewLongPressListener() {
            @Override
            public void onEmptyViewLongPress(Calendar time) {}
        });


        WeekView.EventClickListener mEventClickListener = new WeekView.EventClickListener() {
            @Override
            public void onEventClick(WeekViewEvent event, RectF eventRect) {}
        };

        WeekView.EventLongPressListener eventLongPressListener = new WeekView.EventLongPressListener() {
            @Override
            public void onEventLongPress(WeekViewEvent event, RectF eventRect) {}
        };
        mWeekView.notifyDatasetChanged();


        return v;
    }


    @Override
    public void onNewEvent(DailyEvent dailyEvent) {

        Calendar startTime = Calendar.getInstance();
        Calendar endTime = Calendar.getInstance();

        startTime.setTimeInMillis(dailyEvent.getStartDate());
        endTime.setTimeInMillis(dailyEvent.getStartDate() + dailyEvent.getDuration());

        WeekViewEvent event = new WeekViewEvent(idCreate++, dailyEvent.getEvent().getNume(), startTime, endTime);

        if(dailyEvent.getEvent().getType().equals(FUN)) event.setColor(Color.rgb(3,169,244));
        if(dailyEvent.getEvent().getType().equals(STUDY)) event.setColor(Color.rgb(244,67,54));
        if(dailyEvent.getEvent().getType().equals(RELAX)) event.setColor(Color.rgb(102, 187, 106));

        events.add(event);
        mWeekView.notifyDatasetChanged();

    }

    @Override
    public void onEditEvent(DailyEvent dailyEvent) {

    }

    @Override
    public void onDeleteEvent(UUID uuid) {

    }


}