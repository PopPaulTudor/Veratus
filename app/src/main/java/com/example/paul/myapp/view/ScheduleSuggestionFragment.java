package com.example.paul.myapp.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import com.example.paul.myapp.R;


public class ScheduleSuggestionFragment extends Fragment {

    public static FrameLayout frameLayout;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.schedule_suggestion_fragment, container, false);

        Button sportSuggestion = (Button) v.findViewById(R.id.ScheduleSuggestion1);
        Button studySuggestion = (Button) v.findViewById(R.id.ScheduleSuggestion2);
        Button sleepSuggestion = (Button) v.findViewById(R.id.ScheduleSuggestion3);
        Button practiceSuggestion = (Button) v.findViewById(R.id.ScheduleSuggestion4);
        frameLayout = (FrameLayout) v.findViewById(R.id.frame_layout_suggestion);


        sportSuggestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                frameLayout.setClickable(true);
                FragmentManager frgmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = frgmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.fab_slide_in_from_left, R.anim.fab_slide_in_from_right);
                SuggestionScheduleExampleFragment fragment = new SuggestionScheduleExampleFragment();
                fragmentTransaction.replace(R.id.frame_layout_suggestion, fragment, "sport");
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        });

        studySuggestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                frameLayout.setClickable(true);
                FragmentManager frgmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = frgmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.fab_slide_in_from_left, R.anim.fab_slide_in_from_right);
                SuggestionScheduleExampleFragment fragment = new SuggestionScheduleExampleFragment();
                fragmentTransaction.replace(R.id.frame_layout_suggestion, fragment, "study");
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        });

        sleepSuggestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                frameLayout.setClickable(true);
                FragmentManager frgmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = frgmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.fab_slide_in_from_left, R.anim.fab_slide_in_from_right);
                SuggestionScheduleExampleFragment fragment = new SuggestionScheduleExampleFragment();
                fragmentTransaction.replace(R.id.frame_layout_suggestion, fragment, "sleep");
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        });

        practiceSuggestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                frameLayout.setClickable(true);
                FragmentManager frgmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = frgmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.fab_slide_in_from_left, R.anim.fab_slide_in_from_right);
                SuggestionScheduleExampleFragment fragment = new SuggestionScheduleExampleFragment();
                fragmentTransaction.replace(R.id.frame_layout_suggestion, fragment, "practice");
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        });
        return v;
    }
}
