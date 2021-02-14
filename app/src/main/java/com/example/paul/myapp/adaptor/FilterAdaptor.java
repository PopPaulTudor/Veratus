package com.example.paul.myapp.adaptor;

import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.paul.myapp.R;
import com.example.paul.myapp.model.DailyEvent;
import com.example.paul.myapp.model.Reward;
import com.example.paul.myapp.utils.Util;

import java.util.List;


public class FilterAdaptor extends RecyclerView.Adapter<FilterAdaptor.EventViewHolder> {


    private List<DailyEvent> mDailyEvents;
    private List<Reward> rewards;
    private DailyEvent dailyEvent1;
    private DailyEvent dailyEvent2;
    private int minin1 = Integer.MAX_VALUE;
    private int minin2 = Integer.MAX_VALUE;
    private String myClass;
    private int classPoints;


    public FilterAdaptor(List<DailyEvent> dailyEvents, String myClassPar) {
        mDailyEvents = dailyEvents;
        myClass = myClassPar;
    }

    public FilterAdaptor(List<Reward> rewardList, String myClassPar, int points) {
        rewards = rewardList;
        myClass = myClassPar;
        classPoints = points;

    }


    @Override
    public FilterAdaptor.EventViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v;
        if (myClass.equals("filter"))
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.filter_element, parent, false);
        else
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.award_element, parent, false);
        return new EventViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final EventViewHolder holder, final int position) {

        if (myClass.equals("filter")) {

            Util.sortArray(mDailyEvents);

            if (mDailyEvents.size() != 0) {
                String startDate = Util.setMilitoHour(Util.StarttoHour(mDailyEvents.get(position).getStartDate()));
                String finishDate = Util.setMilitoHour(Util.StarttoHour(mDailyEvents.get(position).getStartDate() + mDailyEvents.get(position).getDuration()));

                holder.CardName.setText(mDailyEvents.get(position).getEvent().getNume());
                holder.FilterStartHour.setText(startDate);
                holder.FilterFinishHour.setText(finishDate);

                holder.grade.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) throws NumberFormatException {
                        if (!holder.grade.getText().toString().equals("")) {

                            int minCurent = Integer.valueOf(holder.grade.getText().toString());
                            if (minCurent < minin1) {
                                minin1 = minCurent;
                                dailyEvent1 = mDailyEvents.get(position);

                            } else if (minCurent < minin2) {
                                minin2 = minCurent;
                                dailyEvent2 = mDailyEvents.get(position);
                            }

                        }

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                    }
                });

            }
        } else {
            holder.textAward.setText(rewards.get(position).getText());
            holder.imageAward.setImageResource(rewards.get(position).getImage());

            if (classPoints <=rewards.get(position).getTarget()) {
                ColorMatrix matrix = new ColorMatrix();
                matrix.setSaturation(0);
                ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);
                holder.imageAward.setColorFilter(filter);
                holder.imageAward.setAlpha(100);
            }
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {


        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        if (myClass.equals("filter")) {
            return mDailyEvents.size();
        } else return rewards.size();
    }

    public DailyEvent getDailyEvent1() {
        return dailyEvent1;
    }

    public DailyEvent getDailyEvent2() {
        return dailyEvent2;
    }


    class EventViewHolder extends RecyclerView.ViewHolder {


        TextView FilterStartHour;
        TextView FilterFinishHour;
        TextView CardName;
        EditText grade;
        ImageView imageAward;
        TextView textAward;


        EventViewHolder(View itemView) {
            super(itemView);

            if (myClass.equals("filter")) {
                FilterStartHour = (TextView) itemView.findViewById(R.id.filter_start);
                FilterFinishHour = (TextView) itemView.findViewById(R.id.filter_finish);
                CardName = (TextView) itemView.findViewById(R.id.filter_nume);
                grade = (EditText) itemView.findViewById(R.id.filter_nota);
            } else {
                imageAward = (ImageView) itemView.findViewById(R.id.image_reward_element);
                textAward = (TextView) itemView.findViewById(R.id.text_reward_element);
            }
        }
    }


}
