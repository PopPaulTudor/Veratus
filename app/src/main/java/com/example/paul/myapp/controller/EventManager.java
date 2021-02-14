package com.example.paul.myapp.controller;


import android.app.Activity;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.paul.myapp.database.DailyEventDAO;
import com.example.paul.myapp.database.DbHelper;
import com.example.paul.myapp.database.EventDAO;
import com.example.paul.myapp.database.UserDAO;
import com.example.paul.myapp.model.DailyEvent;
import com.example.paul.myapp.model.User;
import com.example.paul.myapp.utils.ApplicationPreferences;
import com.example.paul.myapp.utils.Util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

public class EventManager {

    private static EventManager instance;

    private DailyEventDAO dailyEventDAO;
    private EventDAO eventDAO;
    private UserDAO userDAO;
    private ApplicationPreferences applicationPreferences;
    private List<DailyEvent> repetableDailyEvents;
    private List<DailyEvent> nonRepetableDailyEvents;

    private EventManager(Activity activity) {
        Log.d("EventManager", "constructor");
        DbHelper dbHelper = DbHelper.getInstance(activity);
        dailyEventDAO = new DailyEventDAO(dbHelper, activity);
        eventDAO = new EventDAO(dbHelper, activity);
        userDAO= new UserDAO(dbHelper);
        applicationPreferences= ApplicationPreferences.getInstance(activity);


    }

    public static EventManager getInstance(Activity activity) {

        if (instance == null) {
            instance = new EventManager(activity);
        }
        return instance;
    }


    public List<DailyEvent> getAllDailyEvents() {
        ArrayList<DailyEvent> allEvents = new ArrayList<>();
        allEvents.addAll(repetableDailyEvents);
        allEvents.addAll(nonRepetableDailyEvents);

        return allEvents;
    }

    public List<DailyEvent> getEventsByDate(long date) {
        List<DailyEvent> dailyEvents = new ArrayList<>();


        for (DailyEvent dailyEvent : nonRepetableDailyEvents) {
            if (Util.setHourTo24(dailyEvent.getStartDate()) == date) {
                dailyEvents.add(dailyEvent);
            }
        }
        for (DailyEvent dailyEvent : repetableDailyEvents) {
            if (Util.setHourTo24(dailyEvent.getStartDate()) == date) dailyEvents.add(dailyEvent);
        }


        return dailyEvents;
    }

    public List<DailyEvent> getEventsByDateandNextDate(long date){
        List<DailyEvent> dailyEvents = new ArrayList<>();


        for (DailyEvent dailyEvent : nonRepetableDailyEvents) {
            if (Util.setHourTo24(dailyEvent.getStartDate()) == date||Util.setHourTo24(dailyEvent.getStartDate()) == date+86400000L ) {
                dailyEvents.add(dailyEvent);
            }
        }
        for (DailyEvent dailyEvent : repetableDailyEvents) {
            if (Util.setHourTo24(dailyEvent.getStartDate()) == date||Util.setHourTo24(dailyEvent.getStartDate()) == date+86400000L) dailyEvents.add(dailyEvent);
        }


        return dailyEvents;
    }

    public void addEvent(DailyEvent dailyEvent) {

        User user= userDAO.getUser(applicationPreferences.getUserId());

        if (eventDAO.getEvent(dailyEvent.getEvent().getId()) == null) {
            eventDAO.addEvent(dailyEvent.getEvent());
        }

        if (dailyEvent.getRepeatable() == 0) {
            nonRepetableDailyEvents.add(dailyEvent);
            dailyEventDAO.addEvent(dailyEvent);
            user.setPoints(user.getPoints()+1);
            userDAO.updateUser(user);
        } else {
            long day = dailyEvent.getStartDate();
            int until = dailyEvent.getUntil();
            int repet = 1;
            user.setPoints(user.getPoints()+3);
            userDAO.updateUser(user);

            if (dailyEvent.getRepeatable() == 2) repet = 7;
            else if (dailyEvent.getRepeatable() == 3) repet = 30;
            else if (dailyEvent.getRepeatable() == 4) repet = 365;
            Calendar final1 = Calendar.getInstance();
            final1.setTimeInMillis(day);

            if (until == 1) final1.setTimeInMillis(day + 7 * 86400000);
            else if (until == 2) final1.set(Calendar.MONTH, final1.get(Calendar.MONTH) + 1);
            else if (until == 3) final1.set(Calendar.YEAR, final1.get(Calendar.YEAR) + 1);

            dailyEventDAO.addEvent(dailyEvent);
            repetableDailyEvents.add(dailyEvent);
            while (day < final1.getTimeInMillis()) {
                day = repet * 86400000 + day;
                DailyEvent dailyEvent1=new DailyEvent(UUID.randomUUID(),dailyEvent.getEvent(),day,dailyEvent.getDuration(),dailyEvent.getCost(),dailyEvent.getDescription(),dailyEvent.getUserId(),dailyEvent.getRepeatable(),dailyEvent.getUntil());
                dailyEventDAO.addEvent(dailyEvent1);
                repetableDailyEvents.add(dailyEvent1);
            }
        }
    }

    public void deleteEvent(DailyEvent daily) {

        dailyEventDAO.deleteEvent(daily);


        for (int i = 0; i < repetableDailyEvents.size(); ++i) {
            if (repetableDailyEvents.get(i).getId().equals(daily.getId()))
                repetableDailyEvents.remove(i);
        }

        for (int i = 0; i < nonRepetableDailyEvents.size(); ++i) {
            if (nonRepetableDailyEvents.get(i).getId().equals(daily.getId()))
                nonRepetableDailyEvents.remove(i);
        }
    }

    public DailyEvent getDailyEvent(UUID uuid) {

        for (DailyEvent dailyEvent : repetableDailyEvents) {
            if (dailyEvent.getId().equals(uuid)) return dailyEvent;
        }

        for (DailyEvent dailyEvent : nonRepetableDailyEvents) {
            if (dailyEvent.getId().equals(uuid)) return dailyEvent;
        }

        return null;

    }


    public void clearLists() {
        nonRepetableDailyEvents.clear();
        repetableDailyEvents.clear();
    }

    public void initiateLists(){

        repetableDailyEvents = dailyEventDAO.getEvents(DailyEventDAO.QUERY_GET_ALL_REPETABLE_EVENT);
        nonRepetableDailyEvents = dailyEventDAO.getEvents(DailyEventDAO.QUERY_GET_ALL_NONREPETABLE_EVENT);
    }


    public void modifyEvent(DailyEvent daily, UUID uuid) {

        for (int i = 0; i < nonRepetableDailyEvents.size(); ++i) {
            if (nonRepetableDailyEvents.get(i).getId().equals(uuid)) {
                nonRepetableDailyEvents.remove(i);
                nonRepetableDailyEvents.add(i, daily);
            }
        }

        for (int i = 0; i < repetableDailyEvents.size(); ++i) {
            if (repetableDailyEvents.get(i).getId().equals(uuid)) {
                repetableDailyEvents.remove(i);
                repetableDailyEvents.add(i, daily);
            }
        }
        dailyEventDAO.updateEvent(daily);

    }
}