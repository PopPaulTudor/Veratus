package com.example.paul.myapp.database;


import android.database.Cursor;
import android.database.CursorWrapper;
import com.example.paul.myapp.model.DailyEvent;
import com.example.paul.myapp.model.Event;
import com.example.paul.myapp.model.User;

import java.util.UUID;

class CalendarCursorWrapper extends CursorWrapper {


    CalendarCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public DailyEvent getDailyEvent() {


        String uuid = getString(getColumnIndex(CalendarSchema.DailyTable.Cols.UUID));
        long startEvent = getLong(getColumnIndex(CalendarSchema.DailyTable.Cols.START_EVENT));
        long duration = getLong(getColumnIndex(CalendarSchema.DailyTable.Cols.DURATION));
        int cost = getInt(getColumnIndex(CalendarSchema.DailyTable.Cols.COST));
        int repeatable= getInt(getColumnIndex(CalendarSchema.DailyTable.Cols.REPEATABLE));
        int until=getInt(getColumnIndex(CalendarSchema.DailyTable.Cols.UNTIL));
        String description= getString(getColumnIndex(CalendarSchema.DailyTable.Cols.DESCRIPTION));
        String username= getString(getColumnIndex(CalendarSchema.DailyTable.Cols.USER_ID));


        String eventId = getString(getColumnIndex(CalendarSchema.DailyTable.Cols.EVENT_ID));
        String name=getString(getColumnIndex(CalendarSchema.EventTable.Cols.NUME));
        String type=getString(getColumnIndex(CalendarSchema.EventTable.Cols.TYPE));

        Event event= new Event(UUID.fromString(eventId),type,name);
        DailyEvent dailyEvent = new DailyEvent(UUID.fromString(uuid));

        dailyEvent.setUntil(until);
        dailyEvent.setRepeatable(repeatable);
        dailyEvent.setUserId(username);
        dailyEvent.setEvent(event);
        dailyEvent.setCost(cost);
        dailyEvent.setStartDate(startEvent);
        dailyEvent.setDuration(duration);
        dailyEvent.setDescription(description);

        return dailyEvent;
    }


    public Event getEvent(){

        String uuid= getString(getColumnIndex(CalendarSchema.EventTable.Cols.ID));
        String nume= getString(getColumnIndex(CalendarSchema.EventTable.Cols.NUME));
        String type= getString(getColumnIndex(CalendarSchema.EventTable.Cols.TYPE));

        Event event = new Event(UUID.fromString(uuid));
        event.setNume(nume);
        event.setType(type);

        return event;
    }


    User getUser(){

        String username=getString(getColumnIndex(CalendarSchema.UserTable.Cols.USERNAME));
        String paasword=getString(getColumnIndex(CalendarSchema.UserTable.Cols.PASSWORD));
        String  name=  getString(getColumnIndex(CalendarSchema.UserTable.Cols.NAME));
        String prename= getString(getColumnIndex(CalendarSchema.UserTable.Cols.PRENAME));
        int age= getInt(getColumnIndex(CalendarSchema.UserTable.Cols.AGE));
        String year=  getString(getColumnIndex(CalendarSchema.UserTable.Cols.YEAR));
        String id=getString(getColumnIndex(CalendarSchema.UserTable.Cols.ID));
        int point= getInt(getColumnIndex(CalendarSchema.UserTable.Cols.POINT));

        return new User(name,prename,paasword,year,age,username,id,point);

    }




}
