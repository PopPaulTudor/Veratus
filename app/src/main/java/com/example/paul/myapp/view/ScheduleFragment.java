package com.example.paul.myapp.view;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringDef;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



import com.example.paul.myapp.controller.EventManager;
import com.example.paul.myapp.listener.EventListener;
import com.example.paul.myapp.R;
import com.example.paul.myapp.model.DailyEvent;
import com.example.paul.myapp.listener.DialogFragmentListener;
import com.example.paul.myapp.utils.Util;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class ScheduleFragment extends Fragment implements DialogFragmentListener {

    public static final String FUN = "Fun";
    public static final String RELAX = "Relax";
    public static final String STUDY = "Study";

    TabLayout tabLayout;
    ViewPager viewPager;
    public static FloatingActionButton sleepReg;
    public static FloatingActionButton funReg;
    public static FloatingActionButton studyReg;
    FloatingActionMenu menuReg;
    EventListener mEventListener;


    @StringDef({FUN, RELAX, STUDY})
    @Retention(RetentionPolicy.SOURCE)
    public @interface type {
    }

    @ScheduleFragment.type
    String currentType = FUN;

    public void setCurrentType(@type String currentType) {
        this.currentType = currentType;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.daily_create_fragment, container, false);

        viewPager = (ViewPager) v.findViewById(R.id.viewpager);
        tabLayout = (TabLayout) v.findViewById(R.id.tabs);

        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);


        sleepReg = (FloatingActionButton) v.findViewById(R.id.sleepEventReg);
        funReg = (FloatingActionButton) v.findViewById(R.id.funEventReg);
        studyReg = (FloatingActionButton) v.findViewById(R.id.schoolEventReg);
        menuReg = (FloatingActionMenu) v.findViewById(R.id.floatingActionMenu);

        EventManager eventManager=EventManager.getInstance(getActivity());
        eventManager.initiateLists();

        sleepReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager frgmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = frgmentManager.beginTransaction();
                EventFragment fragment = new EventFragment();
                EventFragment.background_event = 3;
                fragmentTransaction.replace(R.id.fragment_container_create_event, fragment, "fragment");
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                setCurrentType(RELAX);
                sleepReg.setEnabled(false);
                funReg.setEnabled(false);
                studyReg.setEnabled(false);

            }
        });

        funReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager frgmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = frgmentManager.beginTransaction();
                EventFragment fragment = new EventFragment();
                EventFragment.background_event = 1;
                fragmentTransaction.replace(R.id.fragment_container_create_event, fragment, "fragment");
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                setCurrentType(FUN);
                sleepReg.setEnabled(false);
                funReg.setEnabled(false);
                studyReg.setEnabled(false);

            }
        });

        studyReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager frgmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = frgmentManager.beginTransaction();
                EventFragment fragment = new EventFragment();

                fragmentTransaction.replace(R.id.fragment_container_create_event, fragment, "fragment");
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                EventFragment.background_event = 2;
                setCurrentType(STUDY);
                sleepReg.setEnabled(false);
                funReg.setEnabled(false);
                studyReg.setEnabled(false);
            }
        });


        return v;
    }

    private void setupViewPager(ViewPager viewPager) {

        final ViewPagerAdapter adapter = new ViewPagerAdapter(getFragmentManager());
        adapter.addFragment(new DayFragment(), "Monday");
        adapter.addFragment(new DayFragment(), "Tuesday");
        adapter.addFragment(new DayFragment(), "Wednesday");
        adapter.addFragment(new DayFragment(), "Thursday");
        adapter.addFragment(new DayFragment(), "Friday");
        adapter.addFragment(new DayFragment(), "Saturday");
        adapter.addFragment(new DayFragment(), "Sunday");
        viewPager.setAdapter(adapter);

        mEventListener = (EventListener) adapter.getItem(0);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                DayFragment currentFragment = (DayFragment) adapter.getItem(position);
                mEventListener = currentFragment;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }


    class ViewPagerAdapter extends FragmentPagerAdapter {

        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }

    }


    @Override
    public void onReturnValue(DailyEvent dailyEvent) {

        dailyEvent.getEvent().setType(currentType);
        Calendar calendar = Calendar.getInstance();

        ArrayList<DailyEvent> dailyEvents= new ArrayList<>();
        DailyEvent dailyEvent1;
        DailyEvent dailyEvent2;
        if(Util.StarttoHour(dailyEvent.getStartDate())+dailyEvent.getDuration()>24){

            dailyEvent1= new DailyEvent(dailyEvent.getId(),dailyEvent.getEvent(),dailyEvent.getStartDate(),86400000L-Util.StarttoHour(dailyEvent.getStartDate()),dailyEvent.getCost(),dailyEvent.getDescription(),dailyEvent.getUserId(),dailyEvent.getRepeatable(),dailyEvent.getUntil());
            dailyEvent2= new DailyEvent(dailyEvent.getId(),dailyEvent.getEvent(),Util.setHourTo24(dailyEvent.getStartDate())+86400000L,dailyEvent.getDuration()-dailyEvent1.getDuration(),dailyEvent.getCost(),dailyEvent.getDescription(),dailyEvent.getUserId(),dailyEvent.getRepeatable(),dailyEvent.getUntil());
            dailyEvents.add(dailyEvent1);
            dailyEvents.add(dailyEvent2);
        }else  dailyEvents.add(dailyEvent);


        int tab = viewPager.getCurrentItem() + 1;
        int day;

        for(DailyEvent daily:dailyEvents) {

            if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) day = 7;
            else day = calendar.get(Calendar.DAY_OF_WEEK) - 1;
            int var = tab - day;
            long interval = var * 86400000;
            mEventListener.onNewEvent(daily);
            daily.setStartDate(daily.getStartDate() + interval);

            EventManager.getInstance(getActivity()).addEvent(daily);

        }
        sleepReg.setEnabled(true);
        funReg.setEnabled(true);
        studyReg.setEnabled(true);

    }


}