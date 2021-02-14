package com.example.paul.myapp.view;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.paul.myapp.R;
import com.example.paul.myapp.database.DbHelper;
import com.example.paul.myapp.database.UserDAO;
import com.example.paul.myapp.model.User;
import com.example.paul.myapp.utils.ApplicationPreferences;
import com.example.paul.myapp.utils.NotificationEventReceiver;

import java.util.Calendar;
import java.util.UUID;


public class RegisterActivity extends AppCompatActivity {

    String username = null;
    String name = null;
    String prename = null;
    String pass = null;
    String year = null;
    int age = -1;

    public FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText usernameReg = (EditText) findViewById(R.id.usernameCreate);
        final EditText nameReg = (EditText) findViewById(R.id.nameCreate);
        final EditText prenameReg = (EditText) findViewById(R.id.prenameCreate);
        final EditText passReg = (EditText) findViewById(R.id.passwordCreate);
        final EditText ageReg = (EditText) findViewById(R.id.ageCreate);
        final FloatingActionButton button = (FloatingActionButton) findViewById(R.id.buttonRegister);
        final EditText yearReg = (EditText) findViewById(R.id.yearCreate);
        //error
        final TextView userError = (TextView) findViewById(R.id.usernameError);
        final TextView nameError = (TextView) findViewById(R.id.nameError);
        final TextView prenameError = (TextView) findViewById(R.id.prenameError);
        final TextView passError = (TextView) findViewById(R.id.passError);
        final TextView ageError = (TextView) findViewById(R.id.ageError);
        final TextView yearError = (TextView) findViewById(R.id.yearError);


        frameLayout = (FrameLayout) findViewById(R.id.fragment_container);


        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle("");

        yearError.setText("ex: IX grade | first year");

        usernameReg.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                username = usernameReg.getText().toString();

                if (username.length() <= 3) {
                    userError.setText("Must have at least 4 characters");
                    username = null;
                } else {
                    userError.setText("");
                }

            }
        });


        nameReg.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                name = nameReg.getText().toString();
                if (name.length() < 3) {
                    nameError.setText("Must have at least 3 characters");
                    name = null;
                } else {
                    nameError.setText("");
                }

            }
        });

        prenameReg.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                prename = prenameReg.getText().toString();
                if (prename.length() < 3) {
                    prenameError.setText("Must have at least 3 characters");
                    prename = null;
                } else {
                    prenameError.setText("");
                }


            }
        });

        passReg.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }


            @Override
            public void afterTextChanged(Editable s) {
                pass = passReg.getText().toString();

                if (pass.length() < 5) {
                    passError.setText("Must have at least 5 characters");
                    pass = null;
                } else {
                    passError.setText("");
                }

            }
        });

        ageReg.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ageError.setText("");
            }

            @Override
            public void afterTextChanged(Editable s) {

                try {
                    if (!ageReg.getText().toString().equals("")) {
                        String aux = ageReg.getText().toString();
                        age = Integer.parseInt(aux);
                        if (age > 100 || age < 3) {
                            ageError.setText("It must be a number between 3 and 100");
                            age = -1;
                        }
                    }
                } catch (NumberFormatException e) {
                    ageError.setText("Enter a valid number");
                }
            }


        });

        yearReg.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                yearError.setText("");
            }

            @Override
            public void afterTextChanged(Editable s) {
                year = yearReg.getText().toString();
                year = year.toLowerCase();
                if (!year.contains("grade") && !year.contains("year")) {
                    yearError.setText("It must be a valid year/grade");
                    year = null;
                }
            }
        });



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (username == null || name == null || prename == null || pass == null || age == -1 || year == null) {
                    Snackbar.make(v, "You must complete correct all the fields", Snackbar.LENGTH_SHORT).show();

                } else {
                    DbHelper db = DbHelper.getInstance(getApplicationContext());
                    UserDAO userdao = new UserDAO(db);
                    User user = userdao.getUserbyName(username);

                    if (user != null) {
                        Snackbar.make(v, "Username already exists", Snackbar.LENGTH_SHORT).show();
                    } else {

                        user = new User(name, prename, pass, year.toLowerCase(), age, username, UUID.randomUUID().toString(),0);

                        ApplicationPreferences appPref = ApplicationPreferences.getInstance(RegisterActivity.this);
                        appPref.setUserId(user.getId());
                        userdao.addUser(user);

                        findViewById(R.id.app_bar_register).setVisibility(View.VISIBLE);
                        Snackbar.make(v, "Account created", Snackbar.LENGTH_SHORT).show();

                        FragmentManager fragmentManager = getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        ScheduleFragment fragment = new ScheduleFragment();

                        fragmentTransaction.setCustomAnimations(R.anim.fab_slide_in_from_left, R.anim.fab_slide_out_to_right);
                        fragmentTransaction.add(R.id.fragment_container, fragment, "ScheduleFragment");
                        frameLayout.setClickable(true);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();


                    }
                }
            }
        });

    }

    @Override
    public void onBackPressed() {

        if (getSupportFragmentManager().getBackStackEntryCount() == 2) {
            ScheduleFragment.sleepReg.setEnabled(true);
            ScheduleFragment.funReg.setEnabled(true);
            ScheduleFragment.studyReg.setEnabled(true);
        }

        if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
            this.finish();

        } else super.onBackPressed();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_done_register, menu);
        MenuItem doneItem = menu.findItem(R.id.done_register);

        doneItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Calendar calendar= Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY,14);
                calendar.set(Calendar.MINUTE,0);
                calendar.set(Calendar.SECOND,0);

                Intent alarm= new Intent(getApplicationContext(),NotificationEventReceiver.class);

                PendingIntent pendingIntent= PendingIntent.getBroadcast(getApplicationContext(),100,alarm,PendingIntent.FLAG_UPDATE_CURRENT);
                AlarmManager alarmManager = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingIntent);
                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                intent.putExtra("register","register");
                startActivity(intent);
                finish();

                return false;
            }
        });

        return true;
    }
}
