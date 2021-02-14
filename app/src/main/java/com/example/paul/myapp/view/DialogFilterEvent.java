package com.example.paul.myapp.view;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.paul.myapp.R;
import com.example.paul.myapp.adaptor.FilterAdaptor;
import com.example.paul.myapp.controller.EventManager;
import com.example.paul.myapp.listener.EventListener;


public class DialogFilterEvent extends DialogFragment {


    FilterAdaptor adapter;
    Long date;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.dialog_filter_events, container, false);

        RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.recyclerFilter);
        recyclerView.setHasFixedSize(true);

        date=getArguments().getLong("DateFilter");
        adapter = new FilterAdaptor(EventManager.getInstance(getActivity()).getEventsByDate(date),"filter");
        recyclerView.setAdapter(adapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);

        return v;
    }

    @Override
    public void onViewCreated(View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);

        Button saveButton = (Button) v.findViewById(R.id.done_filter);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (adapter.getDailyEvent2() != null && adapter.getDailyEvent1() != null) {
                    EventListener eventListener = (EventListener) getActivity();
                    eventListener.onDeleteEvent(adapter.getDailyEvent1().getId());
                    eventListener.onDeleteEvent(adapter.getDailyEvent2().getId());
                    EventManager.getInstance(getActivity()).deleteEvent(adapter.getDailyEvent1());
                    EventManager.getInstance(getActivity()).deleteEvent(adapter.getDailyEvent2());
                    Snackbar.make(getView(), "Last 2 events were deleted", Snackbar.LENGTH_SHORT).show();
                }
                getActivity().getFragmentManager().popBackStack();


            }
        });
    }

}
