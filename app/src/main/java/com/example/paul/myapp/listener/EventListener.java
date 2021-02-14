package com.example.paul.myapp.listener;

import com.example.paul.myapp.model.DailyEvent;

import java.util.UUID;

/**
 * Created by Paul on 06.04.2017.
 */

public interface EventListener {

    void onNewEvent(DailyEvent dailyEvent);

    void onEditEvent(DailyEvent dailyEvent);

    void onDeleteEvent(UUID uuid);

}
