package com.example.paul.myapp.view;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.paul.myapp.controller.EventManager;
import com.example.paul.myapp.listener.EventListener;
import com.example.paul.myapp.R;
import com.example.paul.myapp.model.DailyEvent;
import com.example.paul.myapp.model.Event;
import com.example.paul.myapp.utils.ApplicationPreferences;
import com.example.paul.myapp.utils.Util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.UUID;


public class EventFragment extends Fragment implements EventListener {

    String name = "", descript = "";
    long startDate = -1, startHour = -1, endHour = -1;
    Integer cost = 0;
    int periodInt = 0, untilInt = 0;

    Calendar myCalendar = Calendar.getInstance();
    EditText dateStartEvent;
    EditText hourStartEvent;
    EditText hourEndEvent;
    EditText nameEvent;
    Button saveButton;
    EditText costEvent;
    EditText description;
    CheckBox mCheckBox;
    Spinner period;
    Spinner until;
    RelativeLayout relativeLayout;
    ImageView imageView;

    public static int background_event = 0;
    public MainActivity mainActivity;


    @NonNull
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.event_fragment, container, false);

        dateStartEvent = (EditText) v.findViewById(R.id.dateStart);
        hourStartEvent = (EditText) v.findViewById(R.id.timeStart);
        hourEndEvent = (EditText) v.findViewById(R.id.timeFinish);
        nameEvent = (EditText) v.findViewById(R.id.EventNameCreate);
        saveButton = (Button) v.findViewById(R.id.saveEventCreate);
        costEvent = (EditText) v.findViewById(R.id.costEventCreate);
        description = (EditText) v.findViewById(R.id.descriptionCreate);
        mCheckBox = (CheckBox) v.findViewById(R.id.EventRepeatableCreate);
        period = (Spinner) v.findViewById(R.id.EventRepeatableCreatePeriod);
        until = (Spinner) v.findViewById(R.id.EventRepeatableCreateFor);
        relativeLayout = (RelativeLayout) v.findViewById(R.id.add_event_background);
        imageView = (ImageView) v.findViewById(R.id.typeImageAdd);


        if (background_event == 1)
            imageView.setImageResource(R.drawable.ic_fun48);
        else if (background_event == 2)
            imageView.setImageResource(R.drawable.ic_study48);
        else if (background_event == 3)
            imageView.setImageResource(R.drawable.ic_relax48);


        if (!getTag().equals("fragment")) {
            mainActivity = (MainActivity) getActivity();
            mainActivity.frameLayout.setClickable(true);
        }

        until.setVisibility(View.INVISIBLE);
        period.setVisibility(View.INVISIBLE);

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


        if (!getTag().equals("fragment")) {
            dateStartEvent.setVisibility(View.VISIBLE);
            dateStartEvent.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                            month++;
                            dateStartEvent.setText(dayOfMonth + "/" + month + "/" + year);
                            month--;
                            myCalendar.set(year, month, dayOfMonth);
                            startDate = myCalendar.getTimeInMillis();
                        }
                    }, myCalendar
                            .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                            myCalendar.get(Calendar.DAY_OF_MONTH));
                    datePickerDialog.show();


                }

            });

        } else {
            dateStartEvent.setVisibility(View.INVISIBLE);
            startDate = Calendar.getInstance().getTimeInMillis();
        }


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

        if (!getTag().equals("fragment")) {


            mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    if (isChecked) {
                        until.setVisibility(View.VISIBLE);
                        period.setVisibility(View.VISIBLE);

                        period.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                periodInt = position;

                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });

                        until.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                untilInt = position;

                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });

                    } else {
                        untilInt = 0;
                        periodInt = 0;
                        until.setVisibility(View.INVISIBLE);
                        period.setVisibility(View.INVISIBLE);
                    }
                }
            });

        } else {
            period.setVisibility(View.GONE);
            until.setVisibility(View.GONE);
            periodInt = 2;
            untilInt = 2;
        }


        saveButton.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View v) {

                                              if (name == null || startHour == -1 || startDate == -1 || endHour == -1) {
                                                  Toast.makeText(getContext(), "You must complete the fields with *", Toast.LENGTH_SHORT).show();
                                              } else {
                                                  startDate = Util.setHourTo24(startDate);
                                                  ApplicationPreferences applicationPreferences = ApplicationPreferences.getInstance(getActivity());
                                                  String id = applicationPreferences.getUserId();

                                                  Event event = new Event(UUID.randomUUID(), "", name);
                                                  DailyEvent dailyEventFirst;

                                                  if (startHour > endHour && endHour != 0) {
                                                      dailyEventFirst = new DailyEvent(UUID.randomUUID(), event, startDate + startHour, 86400000L - startHour + endHour, cost, descript, id, periodInt, untilInt);

                                                  } else {
                                                      dailyEventFirst = new DailyEvent(UUID.randomUUID(), event, startDate + startHour, endHour - startHour, cost, descript, id, periodInt, untilInt);
                                                  }

                                                  ArrayList<DailyEvent> dailyEvents = new ArrayList<>();
                                                  DailyEvent dailyEvent1;
                                                  DailyEvent dailyEvent2;
                                                  if ((Util.StarttoHour(dailyEventFirst.getStartDate()) + dailyEventFirst.getDuration()) / 3600000 > 24) {

                                                      dailyEvent1 = new DailyEvent(dailyEventFirst.getId(), dailyEventFirst.getEvent(), dailyEventFirst.getStartDate(), 86400000L - Util.StarttoHour(dailyEventFirst.getStartDate()), dailyEventFirst.getCost(), dailyEventFirst.getDescription(), dailyEventFirst.getUserId(), dailyEventFirst.getRepeatable(), dailyEventFirst.getUntil());
                                                      dailyEvent2 = new DailyEvent(dailyEventFirst.getId(), dailyEventFirst.getEvent(), Util.setHourTo24(dailyEventFirst.getStartDate()) + 86400000L, dailyEventFirst.getDuration() - dailyEvent1.getDuration(), dailyEventFirst.getCost(), dailyEventFirst.getDescription(), dailyEventFirst.getUserId(), dailyEventFirst.getRepeatable(), dailyEventFirst.getUntil());
                                                      dailyEvents.add(dailyEvent1);
                                                      dailyEvents.add(dailyEvent2);
                                                  } else dailyEvents.add(dailyEventFirst);


                                                   for (DailyEvent daily : dailyEvents) {
                                                      Boolean okDialog = false;
                                                      startHour = Util.StarttoHour(daily.getStartDate());
                                                      endHour = Util.StarttoHour(daily.getStartDate()) + daily.getDuration();
                                                      List<DailyEvent> arraylist = EventManager.getInstance(getActivity()).getEventsByDateandNextDate(startDate);

                                                      for (DailyEvent dailyeventList : arraylist) {
                                                          if (Util.setHourTo24(dailyeventList.getStartDate()) == Util.setHourTo24(daily.getStartDate())&&okDialog.equals(false)) {
                                                              Long d1start = Util.StarttoHour(dailyeventList.getStartDate());
                                                              Long d1finish = Util.StarttoHour(dailyeventList.getStartDate() + dailyeventList.getDuration());

                                                              if ((startHour > d1start && endHour < d1finish) ||
                                                                      (startHour > d1start && endHour < d1finish) ||

                                                                      (startHour > d1start && endHour < d1finish) ||
                                                                      (startHour < d1start && endHour > d1finish) ||
                                                                      (startHour == d1start && endHour == d1finish)) {
                                                                  new AlertDialog.Builder(getContext())
                                                                          .setTitle("Hello")
                                                                          .setMessage("You already have programed ")
                                                                          .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                                                              public void onClick(DialogInterface dialog, int which) {
                                                                                  getActivity().getSupportFragmentManager().beginTransaction().remove(EventFragment.this).commit();
                                                                              }

                                                                          }).show();
                                                                  if (!getTag().equals("fragment")) {
                                                                      mainActivity = (MainActivity) getActivity();
                                                                      mainActivity.frameLayout.setClickable(false);
                                                                  }
                                                                  okDialog = true;

                                                              }
                                                          }
                                                      }

                                                      if (!okDialog) {


                                                          if (getTag().equals("fragment")) {

                                                              ScheduleFragment scheduleFragment = (ScheduleFragment) getActivity().getSupportFragmentManager().findFragmentByTag("ScheduleFragment");
                                                              scheduleFragment.onReturnValue(daily);
                                                              getActivity().getSupportFragmentManager().beginTransaction().remove(EventFragment.this).commit();


                                                          } else {
                                                              mainActivity.frameLayout.setClickable(false);
                                                              mainActivity.onReturnValue(daily);
                                                              getActivity().getSupportFragmentManager().beginTransaction().remove(EventFragment.this).commit();

                                                          }

                                                      }
                                                  }
                                              }
                                          }
                                      }
        );
        return v;

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


    @Override
    public void onNewEvent(DailyEvent dailyEvent) {
    }

    @Override
    public void onEditEvent(DailyEvent dailyEvent) {

    }

    @Override
    public void onDeleteEvent(UUID uuid) {

    }


}










