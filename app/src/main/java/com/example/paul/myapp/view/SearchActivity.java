package com.example.paul.myapp.view;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;


import com.example.paul.myapp.adaptor.RVAdaptor;
import com.example.paul.myapp.R;
import com.example.paul.myapp.controller.EventManager;
import com.example.paul.myapp.database.DailyEventDAO;
import com.example.paul.myapp.database.DbHelper;
import com.example.paul.myapp.model.DailyEvent;

import java.util.ArrayList;
import java.util.List;


public class SearchActivity extends Activity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_result_activity);

        String query=getIntent().getStringExtra("query");

        ArrayList<DailyEvent> dailyEventsShow= new ArrayList<>();
        if(EventManager.getInstance(this).getAllDailyEvents().size()!=0) {
            List<DailyEvent> dailyEvents = EventManager.getInstance(this).getAllDailyEvents();

            for (DailyEvent dailyEvent : dailyEvents) {
                if (dailyEvent.getEvent().getNume().contains(query) || dailyEvent.getEvent().getType().contains(query)) {
                    dailyEventsShow.add(dailyEvent);
                }
            }

            RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerSearch);
            recyclerView.setHasFixedSize(true);

            RVAdaptor adapter = new RVAdaptor(dailyEventsShow);
            adapter.setClass("search");
            adapter.setContext(getApplicationContext());
            recyclerView.setAdapter(adapter);


            LinearLayoutManager linearLayoutManager= new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(linearLayoutManager);

        }

    }
}
