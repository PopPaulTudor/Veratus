package com.example.paul.myapp.model;

import java.util.UUID;


public  class DailyEvent {

    private UUID id;
    private String userId;
    private Event event;
    private long startDate;
    private long duration;
    private Integer cost;
    private String description;
    private Integer repeatable;
    private Integer until;


    public DailyEvent(UUID id, Event event, long startDate, long duration, Integer cost, String description, String userId, Integer repeatable, Integer until) {
        this.id = id;
        this.startDate = startDate;
        this.duration = duration;
        this.cost = cost;
        this.userId = userId;
        this.description = description;
        this.event=event;
        this.repeatable= repeatable;
        this.until=until;

    }


    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public DailyEvent(UUID iid) {
        id = iid;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public long getStartDate() {
        return startDate;
    }

    public void setStartDate(long startDate) {
        this.startDate = startDate;
    }

    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getRepeatable() {
        return repeatable;
    }

    public void setRepeatable(Integer repeatable) {
        this.repeatable = repeatable;
    }

    public Integer getUntil() {
        return until;
    }

    public void setUntil(Integer until) {
        this.until = until;
    }

}
