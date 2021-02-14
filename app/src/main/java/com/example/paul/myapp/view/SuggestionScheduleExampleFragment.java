package com.example.paul.myapp.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.paul.myapp.R;
import com.example.paul.myapp.adaptor.RVAdaptor;
import com.example.paul.myapp.controller.EventManager;
import com.example.paul.myapp.model.DailyEvent;
import com.example.paul.myapp.model.Event;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.example.paul.myapp.view.ScheduleFragment.FUN;
import static com.example.paul.myapp.view.ScheduleFragment.RELAX;
import static com.example.paul.myapp.view.ScheduleFragment.STUDY;


public class SuggestionScheduleExampleFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.sugestion_schedule_element, container, false);
        RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.recycler_suggestion);
        EventManager eventManager = EventManager.getInstance(getActivity());
        String tag = getTag();
        List<DailyEvent> dailyEventList = new ArrayList<>();

        if (tag.equals("sport")) {
            Event event = new Event(UUID.randomUUID(), RELAX, "sleep");
            DailyEvent dailyEvent = new DailyEvent(UUID.randomUUID(), event, 1513202400000L, 23400000L, 0, "", "", 0, 0);
            dailyEventList.add(dailyEvent);

            event = new Event(event.getId(), STUDY, "Training");
            dailyEvent = new DailyEvent(dailyEvent.getId(), event, 1513661400000L, 9000000L, 0, "", "", 0, 0);
            dailyEventList.add(dailyEvent);

            event = new Event(event.getId(), STUDY, "Study theory");
            dailyEvent = new DailyEvent(dailyEvent.getId(), event, 1513674000000L, 7200000L, 0, "", "", 0, 0);
            dailyEventList.add(dailyEvent);

            event = new Event(event.getId(), RELAX, "Nap time");
            dailyEvent = new DailyEvent(dailyEvent.getId(), event, 1513681200000L, 7200000L, 0, "", "", 0, 0);
            dailyEventList.add(dailyEvent);

            event = new Event(event.getId(), FUN, "Free time");
            dailyEvent = new DailyEvent(dailyEvent.getId(), event, 1513688400000L, 7200000L, 0, "", "", 0, 0);
            dailyEventList.add(dailyEvent);

            event = new Event(event.getId(), STUDY, "Night practice");
            dailyEvent = new DailyEvent(dailyEvent.getId(), event, 1513695600000L, 10800000L, 0, "", "", 0, 0);
            dailyEventList.add(dailyEvent);

            event = new Event(event.getId(), RELAX, "Sleep");
            dailyEvent = new DailyEvent(dailyEvent.getId(), event, 1513710000000L, 10800000L, 0, "", "", 0, 0);
            dailyEventList.add(dailyEvent);


        } else if (tag.equals("study")) {

            Event event = new Event(UUID.randomUUID(), RELAX, "sleep");
            DailyEvent dailyEvent = new DailyEvent(UUID.randomUUID(), event, 1513202400000L, 25200000L, 0, "", "", 0, 0);
            dailyEventList.add(dailyEvent);

            event = new Event(event.getId(), STUDY, "Book");
            dailyEvent = new DailyEvent(dailyEvent.getId(), event, 1513666800000L, 144000000L, 0, "", "", 0, 0);
            dailyEventList.add(dailyEvent);

            event = new Event(event.getId(), FUN, "Walking");
            dailyEvent = new DailyEvent(dailyEvent.getId(), event, 1513677600000L, 7200000L, 0, "", "", 0, 0);
            dailyEventList.add(dailyEvent);

            event = new Event(event.getId(), STUDY, "Remember the school");
            dailyEvent = new DailyEvent(dailyEvent.getId(), event, 1513684800000L, 10800000L, 0, "", "", 0, 0);
            dailyEventList.add(dailyEvent);

            event = new Event(event.getId(), RELAX, "Nap");
            dailyEvent = new DailyEvent(dailyEvent.getId(), event, 1513697400000L, 7200000L, 0, "", "", 0, 0);
            dailyEventList.add(dailyEvent);

            event = new Event(event.getId(), FUN, "pizza time");
            dailyEvent = new DailyEvent(dailyEvent.getId(), event, 1513704600000L, 5400000L, 0, "", "", 0, 0);
            dailyEventList.add(dailyEvent);

            event = new Event(event.getId(), FUN, "Movie");
            dailyEvent = new DailyEvent(dailyEvent.getId(), event, 1513711800000L, 5400000L, 0, "", "", 0, 0);
            dailyEventList.add(dailyEvent);

            event = new Event(event.getId(), RELAX, "Sleep");
            dailyEvent = new DailyEvent(dailyEvent.getId(), event, 1513715400000L, 9000000L, 0, "", "", 0, 0);
            dailyEventList.add(dailyEvent);

        } else if (tag.equals("sleep")) {

            Event event = new Event(UUID.randomUUID(), RELAX, "sleep");
            DailyEvent dailyEvent = new DailyEvent(UUID.randomUUID(), event, 1513202400000L, 36000000L, 0, "", "", 0, 0);
            dailyEventList.add(dailyEvent);

            event = new Event(event.getId(), STUDY, "Read a book");
            dailyEvent = new DailyEvent(dailyEvent.getId(), event, 1513670400000L, 7200000L, 0, "", "", 0, 0);
            dailyEventList.add(dailyEvent);

            event = new Event(event.getId(), FUN, "Work out");
            dailyEvent = new DailyEvent(dailyEvent.getId(), event, 1513677600000L, 7200000L, 0, "", "", 0, 0);
            dailyEventList.add(dailyEvent);

            event = new Event(event.getId(), RELAX, "Nap time");
            dailyEvent = new DailyEvent(dailyEvent.getId(), event, 1513688400000L, 7200000L, 0, "", "", 0, 0);
            dailyEventList.add(dailyEvent);

            event = new Event(event.getId(), FUN, "Movie");
            dailyEvent = new DailyEvent(dailyEvent.getId(), event, 1513699200000L, 7200000L, 0, "", "", 0, 0);
            dailyEventList.add(dailyEvent);

            event = new Event(event.getId(), STUDY, "Book");
            dailyEvent = new DailyEvent(dailyEvent.getId(), event, 1513706400000L, 7200000L, 0, "", "", 0, 0);
            dailyEventList.add(dailyEvent);

            event = new Event(event.getId(), RELAX, "Sleep");
            dailyEvent = new DailyEvent(dailyEvent.getId(), event, 1513713600000L, 7200000L, 0, "", "", 0, 0);
            dailyEventList.add(dailyEvent);

        } else {

            Event event = new Event(UUID.randomUUID(), RELAX, "sleep");
            DailyEvent dailyEvent = new DailyEvent(UUID.randomUUID(), event, 1513202400000L, 36000000L, 0, "", "", 0, 0);
            dailyEventList.add(dailyEvent);

            event = new Event(event.getId(), STUDY, "Read a book");
            dailyEvent = new DailyEvent(dailyEvent.getId(), event, 1513670400000L, 7200000L, 0, "", "", 0, 0);
            dailyEventList.add(dailyEvent);

            event = new Event(event.getId(), FUN, "Work out");
            dailyEvent = new DailyEvent(dailyEvent.getId(), event, 1513677600000L, 7200000L, 0, "", "", 0, 0);
            dailyEventList.add(dailyEvent);

            event = new Event(event.getId(), RELAX, "Nap time");
            dailyEvent = new DailyEvent(dailyEvent.getId(), event, 1513688400000L, 7200000L, 0, "", "", 0, 0);
            dailyEventList.add(dailyEvent);

            event = new Event(event.getId(), FUN, "Movie");
            dailyEvent = new DailyEvent(dailyEvent.getId(), event, 1513699200000L, 7200000L, 0, "", "", 0, 0);
            dailyEventList.add(dailyEvent);

            event = new Event(event.getId(), STUDY, "Book");
            dailyEvent = new DailyEvent(dailyEvent.getId(), event, 1513706400000L, 7200000L, 0, "", "", 0, 0);
            dailyEventList.add(dailyEvent);

            event = new Event(event.getId(), RELAX, "Sleep");
            dailyEvent = new DailyEvent(dailyEvent.getId(), event, 1513713600000L, 7200000L, 0, "", "", 0, 0);
            dailyEventList.add(dailyEvent);


        }

        RVAdaptor adapter = new RVAdaptor(dailyEventList);
        recyclerView.setHasFixedSize(true);
        adapter.setClass("suggestion");
        adapter.setContext(getContext());
        recyclerView.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);


        return v;
    }


}
