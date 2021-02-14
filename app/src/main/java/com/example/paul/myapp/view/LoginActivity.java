package com.example.paul.myapp.view;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.paul.myapp.R;
import com.example.paul.myapp.database.DbHelper;
import com.example.paul.myapp.database.UserDAO;
import com.example.paul.myapp.model.User;
import com.example.paul.myapp.utils.ApplicationPreferences;
import com.example.paul.myapp.utils.NotificationEventReceiver;

import java.util.Calendar;
import java.util.Random;
import java.util.UUID;


public class LoginActivity extends AppCompatActivity {

    private static final String ALLOWED_CHARACTERS ="0123456789qwertyuiopasdfghjklzxcvbnm";

    String username = null;
    String password = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        User user2= new User("nume","prenume","parola","ix grade",18,"user",UUID.randomUUID().toString(),1001);
        DbHelper db = DbHelper.getInstance(getApplicationContext());
        final UserDAO userdao = new UserDAO(db);
        userdao.addUser(user2);

        final EditText usertext = (EditText) findViewById(R.id.usernameLogin);
        final EditText passtext = (EditText) findViewById(R.id.passwordLogin);
        final Button login = (Button) findViewById(R.id.logginbutton);
        final Button toRegister= (Button) findViewById(R.id.createAccount);
        final Button annon= (Button) findViewById(R.id.Anonymous);

        usertext.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                username = usertext.getText().toString();
            }
        });

        passtext.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                password = passtext.getText().toString();

            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DbHelper db = DbHelper.getInstance(getApplicationContext());
                UserDAO userdao = new UserDAO(db);
                if (username==null || password==null) {
                    Snackbar.make(v, "Complete the fields", Snackbar.LENGTH_LONG).show();}
                else {
                    User user = userdao.getUserbyName(username);
                    if (user == null) {

                        Snackbar.make(v, "Username is invalid", Snackbar.LENGTH_LONG).show();
                    } else if (!user.getPass().equals(password)) {

                        Snackbar.make(v, "Password is invalid", Snackbar.LENGTH_LONG).show();
                    } else {

                        Calendar calendar= Calendar.getInstance();
                        calendar.set(Calendar.HOUR_OF_DAY,14);
                        calendar.set(Calendar.MINUTE,0);
                        calendar.set(Calendar.SECOND,0);

                        Intent alarm= new Intent(getApplicationContext(),NotificationEventReceiver.class);
                        alarm.putExtra("text","Do you want to add an event?");

                        PendingIntent pendingIntent= PendingIntent.getBroadcast(getApplicationContext(),100,alarm,PendingIntent.FLAG_UPDATE_CURRENT);
                        AlarmManager alarmManager = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingIntent);

                        ApplicationPreferences appPref =  ApplicationPreferences.getInstance(LoginActivity.this);
                        appPref.setUserId(user.getId());
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            }
        });

        toRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),RegisterActivity.class);
                startActivity(intent);
            }
        });


    annon.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast.makeText(getApplicationContext(),"Account was created with default settings",Toast.LENGTH_SHORT).show();
            final Random random=new Random();
            final StringBuilder sb=new StringBuilder(5);
            for(int i=0;i<7;++i)
                sb.append(ALLOWED_CHARACTERS.charAt(random.nextInt(ALLOWED_CHARACTERS.length())));
             sb.toString();

            Calendar calendar= Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY,14);
            calendar.set(Calendar.MINUTE,0);
            calendar.set(Calendar.SECOND,0);

            Intent alarm= new Intent(getApplicationContext(),NotificationEventReceiver.class);
            alarm.putExtra("text","Do you want to add an event?");

            PendingIntent pendingIntent= PendingIntent.getBroadcast(getApplicationContext(),100,alarm,PendingIntent.FLAG_UPDATE_CURRENT);
            AlarmManager alarmManager = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingIntent);

            User user=new User(generateString(4),generateString(4),generateString(5),"X grade",17,generateString(6),UUID.randomUUID().toString(),1);
            userdao.addUser(user);
            ApplicationPreferences applicationPreferences= ApplicationPreferences.getInstance(LoginActivity.this);
            applicationPreferences.setUserId(user.getUsername());
            Intent intent =new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }
    });

    }

    String generateString(int nr){
        final Random random=new Random();
        final StringBuilder sb=new StringBuilder(nr);
        for(int i=0;i<nr;++i)
            sb.append(ALLOWED_CHARACTERS.charAt(random.nextInt(ALLOWED_CHARACTERS.length())));
        return sb.toString();

    }
}
