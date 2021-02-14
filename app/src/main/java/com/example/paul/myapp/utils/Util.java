package com.example.paul.myapp.utils;

import android.app.Activity;
import android.graphics.Color;
import android.util.Log;

import com.example.paul.myapp.database.DbHelper;
import com.example.paul.myapp.database.UserDAO;
import com.example.paul.myapp.model.ColorModal;
import com.example.paul.myapp.model.DailyEvent;
import com.example.paul.myapp.model.User;
import com.example.paul.myapp.view.ScheduleFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import static com.example.paul.myapp.view.ScheduleFragment.FUN;
import static com.example.paul.myapp.view.ScheduleFragment.RELAX;
import static com.example.paul.myapp.view.ScheduleFragment.STUDY;


public class Util {


    private static ColorModal funModal = new ColorModal(Color.rgb(3, 169, 244), FUN);
    private static ColorModal studyModal = new ColorModal(Color.rgb(244, 67, 54), STUDY);
    private static ColorModal relaxModal = new ColorModal(Color.rgb(102, 187, 106), RELAX);


    public static int getColor(String type) {
        if (funModal.getName().equals(type)) return funModal.getColorAtribute();
        else if (studyModal.getName().equals(type)) return studyModal.getColorAtribute();
        else return relaxModal.getColorAtribute();

    }

    public static String militoDate(long date) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/M/yyyy");
        dateFormat.setTimeZone(calendar.getTimeZone());


        return dateFormat.format(calendar.getTime());
    }


    public static long StarttoHour(long start) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(start);


        long aux = calendar.get(Calendar.HOUR_OF_DAY) * 3600000 + calendar.get(Calendar.MINUTE) * 60000;

        return aux;
    }

    public static long setHourTo24(long date) {

        Calendar aux = Calendar.getInstance();
        aux.setTimeInMillis(date);
        aux.set(Calendar.HOUR, 0);
        aux.set(Calendar.MINUTE, 0);
        aux.set(Calendar.SECOND, 0);
        aux.set(Calendar.MILLISECOND, 0);
        aux.set(Calendar.HOUR_OF_DAY, 0);


        return aux.getTimeInMillis();

    }

    public static String setMilitoHour(long millis) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);

        int hour = calendar.get(Calendar.HOUR_OF_DAY) - 2;
        if (hour == -2) hour = 22;
        if (hour == -1) hour = 23;

        int minute = calendar.get(Calendar.MINUTE);

        String time = "";

        if (hour < 10 && minute < 10) {
            time = "0" + hour + ":" + "0" + minute;
        } else if (minute < 10) {
            time = hour + ":" + "0" + minute;
        } else if (hour < 10) {
            time = "0" + hour + ":" + minute;
        } else time = hour + ":" + minute;

        Log.d("Util", "setMiliToHour time = " + time);
        return time;
    }

    public static void sortArray(List<DailyEvent> dailyEvents) {

        DailyEvent dailyEventAux;

        for (int i = 0; i < dailyEvents.size(); ++i) {
            for (int j = i + 1; j < dailyEvents.size(); ++j) {

                if (Util.StarttoHour(dailyEvents.get(i).getStartDate()) > Util.StarttoHour(dailyEvents.get(j).getStartDate())) {
                    dailyEventAux = dailyEvents.get(i);
                    dailyEvents.set(i, dailyEvents.get(j));
                    dailyEvents.set(j, dailyEventAux);
                }
            }

        }
    }

    public static boolean isSpace(List<DailyEvent> dailyEvents, long day, long suma) {

        for (DailyEvent daily : dailyEvents) {
            if (setHourTo24(daily.getStartDate()) == day) {
                suma = suma + daily.getDuration();
            }
        }
        return suma < 79200000L;


    }

    public static String getActivityy(){

        ArrayList<String> activities= new ArrayList<>();
        activities.clear();
        activities.add("go out with friends.");
        activities.add("go swimming.");
        activities.add("go to the gym.");
        activities.add("learn something new.");
        activities.add("make feature plans.");
        activities.add("walk your dog.");
        activities.add("take a nap.");
        activities.add("go wild.");

        Random randomGenerator= new Random();
        int index = randomGenerator.nextInt(activities.size());

        return activities.get(index);
    }

    public static int setColor(Activity activity){
        ApplicationPreferences applicationPreferences=ApplicationPreferences.getInstance(activity);
        DbHelper dbHelper= DbHelper.getInstance(activity);
        UserDAO userDAO= new UserDAO(dbHelper);
        User user= userDAO.getUser(applicationPreferences.getUserId());

        if(user.getPoints()<20) return Color.rgb(76,175,80);
        else if(user.getPoints()>=20&&user.getPoints()<500) return Color.rgb(80,50,20);
        else if(user.getPoints()>=500&&user.getPoints()<1000) return Color.rgb(189,189,189);
        else if(user.getPoints()>=1000&&user.getPoints()<3000) return Color.rgb(218,165,32);
        else return Color.rgb(3,169,244);

    }


}
