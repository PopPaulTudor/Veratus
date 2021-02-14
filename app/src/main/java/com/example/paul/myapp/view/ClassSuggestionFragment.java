package com.example.paul.myapp.view;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.paul.myapp.R;
import com.example.paul.myapp.database.DbHelper;
import com.example.paul.myapp.database.UserDAO;
import com.example.paul.myapp.model.User;
import com.example.paul.myapp.utils.ApplicationPreferences;
import com.example.paul.myapp.utils.Util;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.example.paul.myapp.view.ScheduleFragment.FUN;
import static com.example.paul.myapp.view.ScheduleFragment.RELAX;
import static com.example.paul.myapp.view.ScheduleFragment.STUDY;


public class ClassSuggestionFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.class_sugestion_fragment, container, false);
        TextView classUser = (TextView) v.findViewById(R.id.class_suggestion_user_class);
        PieChart PieChart = (PieChart) v.findViewById(R.id.pie_chart_suggestion_class);
        TextView message = (TextView) v.findViewById(R.id.text_class);
        TextView textView = (TextView) v.findViewById(R.id.text_class_suggestion);


        int[] color = new int[3];
        String classUserString;
        List<PieEntry> entries = new ArrayList<>();
        PieData pieDataSuggestion;
        PieDataSet pieDataSetSuggestion;

        ApplicationPreferences applicationPreferences = ApplicationPreferences.getInstance(getActivity());
        DbHelper dbHelper = DbHelper.getInstance(getActivity());
        UserDAO userDAO = new UserDAO(dbHelper);
        User user = userDAO.getUser(applicationPreferences.getUserId());
        if (user != null) classUser.setText(user.getYear());
        classUserString = classUser.getText().toString();


        if (classUserString.contains("grade")) {

            if (classUserString.contains("ix")) {

                entries.add(new PieEntry(5, FUN));
                entries.add(new PieEntry(8, STUDY));
                entries.add(new PieEntry(11, RELAX));
                message.setText("You should enjoy high school and make memories.");


            } else if (classUserString.contains("xi")) {
                entries.add(new PieEntry(3, FUN));
                entries.add(new PieEntry(8, STUDY));
                entries.add(new PieEntry(13, RELAX));
                message.setText("You should focus more on your relax and prepare for the next year.");


            } else if (classUserString.contains("xii")) {
                entries.add(new PieEntry(2, FUN));
                entries.add(new PieEntry(12, STUDY));
                entries.add(new PieEntry(10, RELAX));
                message.setText("You should focus more on your bacalaureat exam.");


            } else {
                entries.add(new PieEntry(4, FUN));
                entries.add(new PieEntry(7, STUDY));
                entries.add(new PieEntry(11, RELAX));
                message.setText("You should find what you like to do.");

            }

        }   else if (classUserString.contains("year")) {
            if (classUserString.contains("first")) {
                entries.add(new PieEntry(6, FUN));
                entries.add(new PieEntry(10, STUDY));
                entries.add(new PieEntry(8, RELAX));
                message.setText("You should enjoy the college.");

            } else if (classUserString.contains("second")) {
                entries.add(new PieEntry(5, FUN));
                entries.add(new PieEntry(10, STUDY));
                entries.add(new PieEntry(9, RELAX));
                message.setText("You should combine the work with fun.");

            } else if (classUserString.contains("third")) {
                entries.add(new PieEntry(4, FUN));
                entries.add(new PieEntry(12, STUDY));
                entries.add(new PieEntry(8, RELAX));
                message.setText("You should work in the wanted domain.");

            } else if (classUserString.contains("fourth")) {
                entries.add(new PieEntry(1, FUN));
                entries.add(new PieEntry(14, STUDY));
                entries.add(new PieEntry(9, RELAX));
                message.setText("You should focus more on your final essay.");
            }
        } else {
            classUser.setText("Wrong format of your class.");
            textView.setVisibility(View.INVISIBLE);
            PieChart.setVisibility(View.INVISIBLE);
            message.setVisibility(View.INVISIBLE);
        }

        color[0] = Color.rgb(3, 169, 244);
        color[1] = Color.rgb(244, 67, 54);
        color[2] = Color.rgb(102, 187, 106);

        ColorTemplate.createColors(color);
        pieDataSetSuggestion = new PieDataSet(entries, Util.militoDate(Calendar.getInstance().getTimeInMillis()));
        pieDataSuggestion = new PieData(pieDataSetSuggestion);
        pieDataSetSuggestion.setColors(ColorTemplate.createColors(color));
        PieChart.setData(pieDataSuggestion);
        PieChart.animateX(3000);
        PieChart.getLegend().setEnabled(false);
        PieChart.setHoleRadius(0);
        Description description = new Description();
        description.setText("");
        PieChart.setDescription(description);


        return v;
    }
}
