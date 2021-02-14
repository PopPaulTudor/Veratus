package com.example.paul.myapp.adaptor;

import android.app.Activity;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.paul.myapp.R;
import com.example.paul.myapp.controller.EventManager;
import com.example.paul.myapp.model.DailyEvent;
import com.example.paul.myapp.utils.Util;
import com.example.paul.myapp.view.MainActivity;
import com.example.paul.myapp.view.SearchActivity;


import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import static com.example.paul.myapp.view.ScheduleFragment.FUN;
import static com.example.paul.myapp.view.ScheduleFragment.RELAX;
import static com.example.paul.myapp.view.ScheduleFragment.STUDY;


public class RVAdaptor extends RecyclerView.Adapter<RVAdaptor.EventViewHolder> {


    private List<DailyEvent> mDailyEvents;
    private MainActivity mainActivity;
    private static SearchActivity searchActivity;
    private static String adaptorClass = "";
    private static Context context;

    public RVAdaptor(List<DailyEvent> dailyEvents) {
        mDailyEvents = dailyEvents;
    }

    public void setClass(String clas) {
        adaptorClass = clas;

    }


    public void swap(Activity activity, int year, int month, int day) {

        Calendar calendar = Calendar.getInstance();
        if (year != 0) calendar.set(year, month, day, 0, 0, 0);
        calendar.setTimeInMillis(Util.setHourTo24(calendar.getTimeInMillis()));

        mDailyEvents.clear();
        mDailyEvents = EventManager.getInstance(activity).getEventsByDate(calendar.getTimeInMillis());
        Util.sortArray(mDailyEvents);
        notifyDataSetChanged();

    }


    public void deleteEvent(UUID uuid) {
        for (Iterator<DailyEvent> iterator = mDailyEvents.iterator(); iterator.hasNext(); ) {
            DailyEvent dailyEvent = iterator.next();
            if (dailyEvent.getId().equals(uuid)) iterator.remove();
        }
        notifyDataSetChanged();

    }


    public void editEvent(DailyEvent daily) {

        for (int i = 0; i < mDailyEvents.size(); ++i) {
            if (mDailyEvents.get(i).getId().equals(daily.getId())) {
                mDailyEvents.set(i, daily);
            }
        }
        notifyDataSetChanged();


    }


    @Override
    public EventViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        switch (adaptorClass) {
            case "suggestion":
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_element_suggestion, parent, false);
                break;
            case "filter":
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.filter_element, parent, false);
                break;
            default:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_element, parent, false);
                break;
        }
        EventViewHolder eventViewHolder = new EventViewHolder(v);

        if (adaptorClass.equals("main")) mainActivity = (MainActivity) parent.getContext();


        return eventViewHolder;
    }

    @Override
    public void onBindViewHolder(EventViewHolder holder, final int position) {


        Util.sortArray(mDailyEvents);


        if (mDailyEvents.size() != 0) {
            String startDate = Util.setMilitoHour(Util.StarttoHour(mDailyEvents.get(position).getStartDate()));
            String finishDate = Util.setMilitoHour(Util.StarttoHour(mDailyEvents.get(position).getStartDate() + mDailyEvents.get(position).getDuration()));
            String date = Util.militoDate(mDailyEvents.get(position).getStartDate());
            String type = mDailyEvents.get(position).getEvent().getType();

            holder.CardName.setText(mDailyEvents.get(position).getEvent().getNume());
            holder.CardStartHour.setText(startDate);
            holder.CardFinishHour.setText(finishDate);

            if(!adaptorClass.equals("suggestion")) holder.CardDate.setText(date);

            if (type.equals(FUN)) {
                holder.CardImage.setImageResource(R.drawable.ic_fun48);
                holder.color.setBackgroundColor(ContextCompat.getColor(context, R.color.fun));
            }
            if (type.equals(STUDY)) {
                holder.CardImage.setImageResource(R.drawable.ic_study48);
                holder.color.setBackgroundColor(ContextCompat.getColor(context, R.color.study));
            }
            if (type.equals(RELAX)) {
                holder.CardImage.setImageResource(R.drawable.ic_relax48);
                holder.color.setBackgroundColor(ContextCompat.getColor(context, R.color.relax));
            }


            if (adaptorClass.equals("search")) {
                holder.CardDescript.setText(mDailyEvents.get(position).getDescription());
                holder.CardCost.setText(mDailyEvents.get(position).getCost() + "");
            }


            if (mainActivity != null && mDailyEvents.get(position) != null && adaptorClass.equals("main")) {
                holder.mCardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mainActivity.onDisplayEvent(mDailyEvents.get(position));
                    }
                });
            }
        }


    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return mDailyEvents.size();
    }

    public void setContext(Context applicationContext) {
        context = applicationContext;
    }


    class EventViewHolder extends RecyclerView.ViewHolder {

        CardView mCardView;
        TextView CardStartHour;
        TextView CardFinishHour;
        TextView CardName;
        TextView CardDate;
        TextView CardCost;
        TextView CardDescript;
        ImageView CardImage;
        TextView CostText;
        View color;

        EventViewHolder(View itemView) {
            super(itemView);

            if (!adaptorClass.equals("suggestion")) {

                mCardView = (CardView) itemView.findViewById(R.id.cardView);
                CardStartHour = (TextView) itemView.findViewById(R.id.CardStartHour);
                CardFinishHour = (TextView) itemView.findViewById(R.id.CardFinishHour);
                CardName = (TextView) itemView.findViewById(R.id.CardName);
                CardDate = (TextView) itemView.findViewById(R.id.CardDate);
                CardCost = (TextView) itemView.findViewById(R.id.CardCost);
                CardDescript = (TextView) itemView.findViewById(R.id.CardDescription);
                CardImage = (ImageView) itemView.findViewById(R.id.CardImage);
                color = itemView.findViewById(R.id.ColorStart);
                CostText = (TextView) itemView.findViewById(R.id.CostText);

            } else {

                mCardView = (CardView) itemView.findViewById(R.id.cardViewSuggestion);
                CardStartHour = (TextView) itemView.findViewById(R.id.CardStartHourSuggestion);
                CardFinishHour = (TextView) itemView.findViewById(R.id.CardFinishHourSuggestion);
                CardName = (TextView) itemView.findViewById(R.id.CardNameSuggestion);
                CardImage = (ImageView) itemView.findViewById(R.id.CardImageSuggestion);
                color = itemView.findViewById(R.id.ColorStartSuggestion);

            }
            if (adaptorClass.equals("main")) {
                CardDescript.setVisibility(View.GONE);
                CardCost.setVisibility(View.GONE);
                CostText.setVisibility(View.GONE);

            } else if (adaptorClass.equals("search")) {
                CardDescript.setVisibility(View.VISIBLE);
                CardCost.setVisibility(View.VISIBLE);
                CostText.setVisibility(View.VISIBLE);

            }


        }


    }
}




