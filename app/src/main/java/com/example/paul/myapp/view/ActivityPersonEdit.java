package com.example.paul.myapp.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.paul.myapp.R;
import com.example.paul.myapp.controller.EventManager;
import com.example.paul.myapp.database.DbHelper;
import com.example.paul.myapp.database.UserDAO;
import com.example.paul.myapp.model.User;
import com.example.paul.myapp.utils.ApplicationPreferences;
import com.example.paul.myapp.utils.Util;

public class ActivityPersonEdit extends AppCompatActivity {

    String username = null;
    String name = null;
    String prename = null;
    String pass = null;
    String year = null;
    int age = -1;
    int cont = 0;
    static DbHelper dbHelper;
    static UserDAO userDAO;
    static String userOld=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__profile_edit);

        final EditText usernameEdit = (EditText) findViewById(R.id.usernameEdit);
        final EditText nameEdit = (EditText) findViewById(R.id.nameEdit);
        final EditText prenameEdit = (EditText) findViewById(R.id.prenameEdit);
        final EditText passEdit = (EditText) findViewById(R.id.passwordEdit);
        final EditText ageEdit = (EditText) findViewById(R.id.ageEdit);
        final FloatingActionButton button = (FloatingActionButton) findViewById(R.id.buttonEdit);
        final EditText yearEdit = (EditText) findViewById(R.id.yearEdit);
        //error
        final TextView userError = (TextView) findViewById(R.id.usernameError);
        final TextView nameError = (TextView) findViewById(R.id.nameError);
        final TextView prenameError = (TextView) findViewById(R.id.prenameError);
        final TextView passError = (TextView) findViewById(R.id.passError);
        final TextView ageError = (TextView) findViewById(R.id.ageError);
        final TextView yearError = (TextView) findViewById(R.id.yearError);


        usernameEdit.setEnabled(false);
        prenameEdit.setEnabled(false);
        nameEdit.setEnabled(false);
        passEdit.setEnabled(false);
        yearEdit.setEnabled(false);
        ageEdit.setEnabled(false);

        final ApplicationPreferences applicationPreferences = ApplicationPreferences.getInstance(this);
        dbHelper = DbHelper.getInstance(this);
        userDAO = new UserDAO(dbHelper);
        final User user = userDAO.getUser(applicationPreferences.getUserId());
        userOld= user.getUsername();

        usernameEdit.setText(user.getUsername());
        username = user.getUsername();
        nameEdit.setText(user.getName());
        name = user.getName();
        prenameEdit.setText(user.getNickname());
        prename = user.getNickname();
        ageEdit.setText(user.getAge() + "");
        age = user.getAge();
        yearEdit.setText(user.getYear());
        year = user.getYear();
        pass=user.getPass();


        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle("");
        toolbar.setVisibility(View.GONE);
        yearError.setText("ex: IX grade | first year");

        usernameEdit.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                username = usernameEdit.getText().toString();

                if (username.length() <= 3) {
                    userError.setText("Must have at least 4 characters");
                    username = null;
                } else {
                    userError.setText("");
                }

            }
        });


        nameEdit.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                name = nameEdit.getText().toString();
                if (name.length() < 3) {
                    nameError.setText("Must have at least 3 characters");
                    name = null;
                } else {
                    nameError.setText("");
                }

            }
        });

        prenameEdit.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                prename = prenameEdit.getText().toString();
                if (prename.length() < 3) {
                    prenameError.setText("Must have at least 3 characters");
                    prename = null;
                } else {
                    prenameError.setText("");
                }


            }
        });

        passEdit.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }


            @Override
            public void afterTextChanged(Editable s) {
                pass = passEdit.getText().toString();

                if (pass.length() < 5) {
                    passError.setText("Must have at least 5 characters");
                    pass = null;
                } else {
                    passError.setText("");
                }

            }
        });

        ageEdit.addTextChangedListener(new TextWatcher() {

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
                    if (!ageEdit.getText().toString().equals("")) {
                        String aux = ageEdit.getText().toString();
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
        yearEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                yearError.setText("");
            }

            @Override
            public void afterTextChanged(Editable s) {
                year = yearEdit.getText().toString();
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

                if (cont % 2 == 0) {
                    usernameEdit.setEnabled(true);
                    prenameEdit.setEnabled(true);
                    passEdit.setEnabled(true);
                    nameEdit.setEnabled(true);
                    yearEdit.setEnabled(true);
                    ageEdit.setEnabled(true);
                    button.setImageResource(R.drawable.ic_save_black_24dp);

                } else {
                    usernameEdit.setEnabled(false);
                    prenameEdit.setEnabled(false);
                    nameEdit.setEnabled(false);
                    passEdit.setEnabled(false);
                    yearEdit.setEnabled(false);
                    ageEdit.setEnabled(false);
                    button.setImageResource(R.drawable.ic_edit_black_24px);

                    if (username == null || name == null || prename == null || pass == null || age == -1 || year == null) {
                        Snackbar.make(v, "You must complete correct all the fields", Snackbar.LENGTH_SHORT).show();

                    } else {
                        User user1 = userDAO.getUserbyName(username);

                        if (user1 != null && username!=userOld) {
                            Snackbar.make(v, "Username already exists", Snackbar.LENGTH_SHORT).show();
                        } else {

                            user1 = new User(name, prename, pass, year, age, username, user.getId(),0);
                            userDAO.updateUser(user1);
                            Snackbar.make(v, "Account changed", Snackbar.LENGTH_SHORT).show();


                        }
                    }

                }

                cont++;

            }
        });

        DrawerLayout drawerMy = (DrawerLayout) findViewById(R.id.drawer_layout_edit);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerMy, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerMy.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_edit);
        navigationView.setItemIconTintList(null);
        View view= navigationView.getHeaderView(0);
        view.setBackgroundColor(Util.setColor(this));
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int id = item.getItemId();
                if (id == R.id.nav_today) {

                    Intent intent = new Intent(ActivityPersonEdit.this, MainActivity.class);
                    startActivity(intent);

                } else if (id == R.id.nav_reward) {
                    Intent intent = new Intent(ActivityPersonEdit.this, RewardActivity.class);
                    startActivity(intent);

                } else if (id == R.id.nav_calendar) {
                    Intent intent = new Intent(ActivityPersonEdit.this, MyCalendarActivity.class);
                    startActivity(intent);

                }else if(id==R.id.nav_suggestion){
                    Intent intent=new Intent(ActivityPersonEdit.this,ActivitySuggestion.class);
                    startActivity(intent);

                } else if (id == R.id.nav_about) {
                    Intent intent = new Intent(ActivityPersonEdit.this, ActivityAbout.class);
                    startActivity(intent);


                } else if (id == R.id.logout) {

                    ApplicationPreferences apPref = ApplicationPreferences.getInstance(ActivityPersonEdit.this);
                    apPref.deleteUsername();
                    EventManager.getInstance(ActivityPersonEdit.this).clearLists();
                    Intent intent = new Intent(ActivityPersonEdit.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }

                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_edit);
                drawer.closeDrawer(GravityCompat.START);

                return false;
            }
        });

    }

}