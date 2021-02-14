package com.example.paul.myapp.database;

import android.content.ContentValues;

import com.example.paul.myapp.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;



public class UserDAO {

    private DbHelper help;

    public UserDAO(DbHelper help) {
        this.help = help;
    }

    private static ContentValues getContentValues(User user){

        ContentValues values= new ContentValues();

        values.put(CalendarSchema.UserTable.Cols.USERNAME,user.getUsername());
        values.put(CalendarSchema.UserTable.Cols.PASSWORD,user.getPass());
        values.put(CalendarSchema.UserTable.Cols.AGE,user.getAge());
        values.put(CalendarSchema.UserTable.Cols.NAME,user.getName());
        values.put(CalendarSchema.UserTable.Cols.PRENAME,user.getNickname());
        values.put(CalendarSchema.UserTable.Cols.YEAR,user.getYear());
        values.put(CalendarSchema.UserTable.Cols.ID,user.getId());
        values.put(CalendarSchema.UserTable.Cols.POINT,user.getPoints());


        return values;
    }


    public List<User> getUsers(){


        List<User> user = new ArrayList<>();
        CalendarCursorWrapper cursor = (CalendarCursorWrapper) help.queryUser(null, null);
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                user.add(cursor.getUser());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return user;

    }

    public User getUser(String id) {

        CalendarCursorWrapper cursor = (CalendarCursorWrapper) help.queryUser(
                CalendarSchema.UserTable.Cols.ID + " = ?",
                new String[] { id}
        );
        try {
            if (cursor.getCount() == 0) {
                return null;
            }
            cursor.moveToFirst();
            return cursor.getUser();
        } finally {
            cursor.close();
        }
    }

    public User getUserbyName(String id){

        CalendarCursorWrapper cursor = (CalendarCursorWrapper) help.queryUser(
                CalendarSchema.UserTable.Cols.USERNAME + " = ?",
                new String[] { id}
        );
        try {
            if (cursor.getCount() == 0) {
                return null;
            }
            cursor.moveToFirst();
            return cursor.getUser();
        } finally {
            cursor.close();
        }

    }


    public void addUser(User user) {
        ContentValues values = getContentValues(user);
        help.getDatabase().insert(CalendarSchema.UserTable.NAME, null, values);
    }


    public void updateUser(User user) {
        String id = user.getId();
        ContentValues values = getContentValues(user);
        help.getDatabase().update(CalendarSchema.UserTable.NAME, values,
                CalendarSchema.UserTable.Cols.ID + " = ?",
                new String[] {  id });

    }




}
