package com.example.paul.myapp.view;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.paul.myapp.R;
import com.example.paul.myapp.controller.EventManager;
import com.example.paul.myapp.listener.EventListener;
import com.example.paul.myapp.model.DailyEvent;
import com.example.paul.myapp.model.Event;
import com.example.paul.myapp.utils.Util;
import com.github.clans.fab.FloatingActionButton;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.UUID;


public class EditEventFragment extends Fragment {


    String name = "", descript = "";
    long startDate = -1, startHour = -1, endHour = -1;
    Integer cost = 0;
    float durationOld = 0;

    Calendar myCalendar = Calendar.getInstance();
    EditText dateStartEvent;
    EditText hourStartEvent;
    EditText hourEndEvent;
    EditText nameEvent;
    EditText costEvent;
    EditText description;
    EventListener eventListener;
    RelativeLayout relativeLayout;
    ImageView imageView;
    public static FloatingActionButton doneEvent;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_detailed_event, container, false);

        UUID uuid = UUID.fromString(getArguments().getString("DailyDetailedKey"));
        eventListener = (EventListener) getActivity();

        final DailyEvent mDailyEvent = EventManager.getInstance(getActivity()).getDailyEvent(uuid);
        durationOld = (float) (mDailyEvent.getDuration() / 3600000.0);

        dateStartEvent = (EditText) v.findViewById(R.id.dateStartDisplay);
        hourStartEvent = (EditText) v.findViewById(R.id.timeStartDisplay);
        hourEndEvent = (EditText) v.findViewById(R.id.timeFinishDisplay);
        nameEvent = (EditText) v.findViewById(R.id.EventNameDisplay);
        costEvent = (EditText) v.findViewById(R.id.costEventDisplay);
        description = (EditText) v.findViewById(R.id.descriptionDisplay);
        imageView= (ImageView) v.findViewById(R.id.typeImageDisplay);
        relativeLayout = (RelativeLayout) v.findViewById(R.id.edit_relative_layout);

        switch (mDailyEvent.getEvent().getType()) {
            case "Fun":
                imageView.setImageResource(R.drawable.ic_fun48);
                break;
            case "Relax":
                imageView.setImageResource(R.drawable.ic_relax48);
                break;
            default:
                imageView.setImageResource(R.drawable.ic_study48);

                break;
        }


        final FloatingActionButton deleteEvent = (FloatingActionButton) v.findViewById(R.id.deleteEvent);
        final FloatingActionButton editEvent = (FloatingActionButton) v.findViewById(R.id.editEvent);
        doneEvent = (FloatingActionButton) v.findViewById(R.id.doneEvent);

        nameEvent.setText(mDailyEvent.getEvent().getNume());
        name = mDailyEvent.getEvent().getNume();

        dateStartEvent.setText(Util.militoDate(mDailyEvent.getStartDate()));
        startDate = Util.setHourTo24(mDailyEvent.getStartDate());

        hourStartEvent.setText(Util.setMilitoHour(Util.StarttoHour(mDailyEvent.getStartDate())));
        startHour = Util.StarttoHour(mDailyEvent.getStartDate());

        hourEndEvent.setText(Util.setMilitoHour(Util.StarttoHour(mDailyEvent.getStartDate()) + mDailyEvent.getDuration()));
        endHour = Util.StarttoHour(startHour + mDailyEvent.getDuration());

        costEvent.setText(mDailyEvent.getCost() + "");
        cost = mDailyEvent.getCost();
        description.setText(mDailyEvent.getDescription());
        descript = mDailyEvent.getDescription();


        setDisable();
        doneEvent.setVisibility(View.GONE);
        doneEvent.setEnabled(false);

        nameEvent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                name = nameEvent.getText().toString();
            }
        });

        costEvent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                try {
                    if (!costEvent.getText().toString().equals("")) {
                        String aux = costEvent.getText().toString();
                        cost = Integer.parseInt(aux);
                    }
                } catch (NumberFormatException e) {
                    Toast.makeText(getContext(), "Enter a valid cost", Toast.LENGTH_SHORT).show();
                }

            }
        });
        description.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                descript = description.getText().toString();
            }
        });


        dateStartEvent.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new DatePickerDialog(getContext(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                startDate = myCalendar.getTimeInMillis();
            }
        });


        hourStartEvent.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();

                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);

                TimePickerDialog mTimePicker = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        if (selectedHour < 10 && selectedMinute < 10) {
                            hourStartEvent.setText("0" + selectedHour + ":" + "0" + selectedMinute);
                        } else if (selectedHour < 10) {
                            hourStartEvent.setText("0" + selectedHour + ":" + selectedMinute);
                        } else if (selectedMinute < 10) {
                            hourStartEvent.setText(+selectedHour + ":" + "0" + selectedMinute);
                        } else hourStartEvent.setText(selectedHour + ":" + selectedMinute);

                        startHour = selectedMinute * 60000 + selectedHour * 3600000;

                    }
                }, hour, minute, true);
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });

        hourEndEvent.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;

                mTimePicker = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {

                        if (selectedHour < 10 && selectedMinute < 10) {
                            hourEndEvent.setText("0" + selectedHour + ":" + "0" + selectedMinute);
                        } else if (selectedHour < 10) {
                            hourEndEvent.setText("0" + selectedHour + ":" + selectedMinute);
                        } else if (selectedMinute < 10) {
                            hourEndEvent.setText(+selectedHour + ":" + "0" + selectedMinute);
                        } else hourEndEvent.setText(selectedHour + ":" + selectedMinute);

                        endHour = selectedHour * 3600000 + selectedMinute * 60000;

                    }
                }, hour, minute, true);
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });


        deleteEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean ok = false;
                double duration = mDailyEvent.getDuration() / 3600000.0;
                String type = mDailyEvent.getEvent().getType();
                int colorObj = Util.getColor(type);

                for (Iterator<PieEntry> it = MainActivity.MainDataSetChart.getValues().iterator(); it.hasNext(); ) {
                    PieEntry s = it.next();
                    if (s.getLabel().equals(type)) {
                        s.setY((float) (s.getY() - duration));
                        if (s.getY() == 0.0) {
                            it.remove();
                            ok = true;
                        }

                        MainActivity.MainDataSetChart.getValues().get(0).setY((float) (MainActivity.MainDataSetChart.getValues().get(0).getY() + duration));
                    }
                }


                for (int i = 0; i < MainActivity.colorSize; ++i) {
                    if (MainActivity.color[i] == colorObj&& ok) {
                        for (int j = i; j < MainActivity.colorSize-1; ++j) {
                            MainActivity.color[j] = MainActivity.color[j + 1];
                        }

                        MainActivity.colorSize--;
                    }
                }


                MainActivity.MainDataSetChart.setColors(ColorTemplate.createColors(MainActivity.color));
                MainActivity.MainDataSetChart.notifyDataSetChanged();
                MainActivity.MainDataChart.notifyDataChanged();
                MainActivity.MainChart.notifyDataSetChanged();
                MainActivity.MainChart.invalidate();

                eventListener.onDeleteEvent(mDailyEvent.getId());
                EventManager.getInstance(getActivity()).deleteEvent(mDailyEvent);
                Toast.makeText(getContext(), "Event Deleted", Toast.LENGTH_SHORT).show();
                getActivity().getSupportFragmentManager().popBackStack();
                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.frameLayout.setClickable(false);

                EventManager eventManager = EventManager.getInstance(getActivity());
                if (eventManager.getEventsByDate(Util.setHourTo24(mDailyEvent.getStartDate())).size() == 0) {
                    MainActivity.recyclerView.setVisibility(View.GONE);
                    MainActivity.messageNoEvents.setVisibility(View.VISIBLE);
                } else {
                    MainActivity.recyclerView.setVisibility(View.VISIBLE);
                    MainActivity.messageNoEvents.setVisibility(View.INVISIBLE);
                }
            }
        });

        editEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dateStartEvent.setEnabled(true);
                hourStartEvent.setEnabled(true);
                hourEndEvent.setEnabled(true);
                nameEvent.setEnabled(true);
                costEvent.setEnabled(true);
                description.setEnabled(true);
                doneEvent.setVisibility(View.VISIBLE);
                doneEvent.setEnabled(true);
            }
        });

        doneEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Event event = new Event(mDailyEvent.getEvent().getId(), mDailyEvent.getEvent().getType(), name);
                DailyEvent daily = new DailyEvent(mDailyEvent.getId(), event, startDate + startHour, endHour - startHour, cost, descript, mDailyEvent.getUserId(), mDailyEvent.getRepeatable(), mDailyEvent.getUntil());
                float duration = (float) ((endHour - startHour) / 3600000.0);
                if (name == null || startHour == -1 || startDate == -1 || endHour == -1) {
                    Toast.makeText(getContext(), "You must complete the fields with *", Toast.LENGTH_SHORT).show();
                } else {
                    startDate = Util.setHourTo24(startDate);
                    Boolean okDialog = false;
                    List<DailyEvent> arraylist = EventManager.getInstance(getActivity()).getAllDailyEvents();

                    for (DailyEvent dailyevent : arraylist) {
                        if (Util.setHourTo24(dailyevent.getStartDate()) == startDate && !(mDailyEvent.getId().equals(dailyevent.getId()))) {

                            Long d1start = Util.StarttoHour(dailyevent.getStartDate());
                            Long d1finish = Util.StarttoHour(dailyevent.getStartDate() + dailyevent.getDuration());

                            if ((startHour > d1start && startHour < d1finish) || (endHour > d1start && endHour < d1finish) || (startHour > d1start && endHour < d1finish) || (startHour < d1start && endHour > d1finish)) {
                                new AlertDialog.Builder(getContext())
                                        .setTitle("Hello").setMessage("You already have programed ")
                                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                getActivity().getSupportFragmentManager().beginTransaction().remove(EditEventFragment.this).commit();
                                            }

                                        }).show();
                                MainActivity mainActivity = (MainActivity) getActivity();
                                mainActivity.frameLayout.setClickable(false);

                                okDialog = true;
                                getActivity().getSupportFragmentManager().beginTransaction().remove(EditEventFragment.this).commit();
                            }
                        }

                    }
                    if (!okDialog) {

                        String type = mDailyEvent.getEvent().getType();
                        for (PieEntry pieEntry : MainActivity.MainDataSetChart.getValues()) {
                            if (pieEntry.getLabel().equals(type)) {
                                double durationNew = duration - durationOld;
                                pieEntry.setY((float) (pieEntry.getY() + durationNew));
                                MainActivity.MainDataSetChart.getValues().get(0).setY((float) (MainActivity.MainDataSetChart.getValues().get(0).getY() - durationNew));
                                if (pieEntry.getY() == 0)
                                    MainActivity.MainDataSetChart.getValues().remove(pieEntry);
                            }
                        }
                        MainActivity.MainDataSetChart.notifyDataSetChanged();
                        MainActivity.MainDataChart.notifyDataChanged();
                        MainActivity.MainChart.notifyDataSetChanged();
                        MainActivity.MainChart.invalidate();


                        EventManager.getInstance(getActivity()).modifyEvent(daily, daily.getId());
                        eventListener.onEditEvent(daily);
                        MainActivity mainActivity = (MainActivity) getActivity();
                        mainActivity.frameLayout.setClickable(false);
                        setDisable();
                        getActivity().getSupportFragmentManager().beginTransaction().remove(EditEventFragment.this).commit();

                    }
                }
            }
        });


        return v;
    }


    void setDisable() {

        dateStartEvent.setEnabled(false);
        hourStartEvent.setEnabled(false);
        hourEndEvent.setEnabled(false);
        nameEvent.setEnabled(false);
        costEvent.setEnabled(false);
        description.setEnabled(false);
        doneEvent.setVisibility(View.GONE);
        doneEvent.setEnabled(false);

    }

    private void updateLabel() {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        dateStartEvent.setText(sdf.format(myCalendar.getTime()));
    }

    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }

    };


}
