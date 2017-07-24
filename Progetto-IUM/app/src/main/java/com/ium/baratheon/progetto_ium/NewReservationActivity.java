package com.ium.baratheon.progetto_ium;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Calendar;

public class NewReservationActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private NavigationView mDrawerView;
    private ImageView drawerButton, emptyName, emptyDay, emptyStart, emptyEnd;
    private TextView nameTextView, mailTextView, dayText, startText, endText,
            courtNameLabel, dayLabel, startLabel, endLabel;
    private EditText courtNameText;
    private Button okButton, okCalendarButton, cancelButton, cancelCalendarButton;
    FloatingActionButton searchButton;
    private RelativeLayout timePickerLayout, datePickerLayout, startLayout, endLayout;
    private TimePicker timePicker;
    private CalendarView calendarView;
    private User u;
    private Calendar c = Calendar.getInstance();
    private String picker = "",
            currentDay = Utility.toDoubleDigit(c.get(Calendar.DAY_OF_MONTH)),
            currentMonth = Utility.toDoubleDigit(c.get(Calendar.MONTH) + 1),
            currentYear = Utility.toDoubleDigit(c.get(Calendar.YEAR));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_reservation);

        courtNameLabel = (TextView) findViewById(R.id.courtNameLabel);
        dayLabel = (TextView) findViewById(R.id.dayLabel);
        startLabel = (TextView) findViewById(R.id.startLabel);
        endLabel = (TextView) findViewById(R.id.endLabel);

        courtNameLabel.setTypeface(null, Typeface.BOLD);
        dayLabel.setTypeface(null, Typeface.BOLD);
        startLabel.setTypeface(null, Typeface.BOLD);
        endLabel.setTypeface(null, Typeface.BOLD);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerView = (NavigationView) findViewById(R.id.nav_view);
        u = Session.getInstance(getApplicationContext()).getUser();

        if (mDrawerView != null) {
            mDrawerView.setNavigationItemSelectedListener(
                    new NavigationView.OnNavigationItemSelectedListener() {
                        @Override
                        public boolean onNavigationItemSelected(MenuItem menuItem) {
                            Intent h;

                            switch (menuItem.getItemId()) {
                                case R.id.action_home:
                                    h = new Intent(NewReservationActivity.this, HomepageActivity.class);
                                    NewReservationActivity.this.startActivity(h);
                                    finish();
                                    break;
                                case R.id.action_viewCourt:
                                    h = new Intent(NewReservationActivity.this, CourtListActivity.class);
                                    NewReservationActivity.this.startActivity(h);
                                    finish();
                                    break;
                                case R.id.action_logout:
                                    Session.getInstance(getApplicationContext()).removePrefs();
                                    h = new Intent(NewReservationActivity.this, LoginActivity.class);
                                    NewReservationActivity.this.startActivity(h);
                                    finish();
                                    break;
                            }

                            mDrawerLayout.closeDrawers();
                            return true;
                        }
                    });
        }

        drawerButton = (ImageView) findViewById(R.id.drawer_item_icon);
        drawerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.openDrawer(mDrawerView);
            }
        });

        nameTextView = (TextView) findViewById(R.id.nameTextView);
        mailTextView = (TextView) findViewById(R.id.mailTextView);

        nameTextView.setText(u.getName() + " " + u.getSurname());
        mailTextView.setText(u.getEmail());

        courtNameText = (EditText) findViewById(R.id.courtNameText);
        dayText = (TextView) findViewById(R.id.dayText);
        startText = (TextView) findViewById(R.id.startText);
        endText = (TextView) findViewById(R.id.endText);

        dayText.setText(Utility.toDoubleDigit(c.get(Calendar.DAY_OF_MONTH)) + "-" + Utility.toDoubleDigit(c.get(Calendar.MONTH) + 1) + "-" + c.get(Calendar.YEAR));
        startText.setText(Utility.toDoubleDigit(c.get(Calendar.HOUR_OF_DAY)) + ":" + Utility.toDoubleDigit(c.get(Calendar.MINUTE)));

        if(getIntent().getStringExtra("courtName")!=null){
            courtNameText.setText(getIntent().getStringExtra("courtName"));
        }
        if(getIntent().getStringExtra("day")!=null){
            dayText.setText(getIntent().getStringExtra("day"));
        }
        if(getIntent().getStringExtra("start")!=null){
            startText.setText(getIntent().getStringExtra("start"));
        }
        if(getIntent().getStringExtra("end")!=null){
            endText.setText(getIntent().getStringExtra("end"));
        }

        //Più facile da manutenere se si crea una map<chiave,valore> e l'immagine di svuotamento
        emptyName = (ImageView) findViewById(R.id.emptyName);
        emptyDay = (ImageView) findViewById(R.id.emptyDay);
        emptyStart = (ImageView) findViewById(R.id.emptyStart);
        emptyEnd = (ImageView) findViewById(R.id.emptyEnd);

        datePickerLayout = (RelativeLayout) findViewById(R.id.datePickerLayout);
        timePickerLayout = (RelativeLayout) findViewById(R.id.timePickerLayout);
        timePicker = (TimePicker) findViewById(R.id.timePicker);
        okButton = (Button) findViewById(R.id.okButton);
        okCalendarButton = (Button) findViewById(R.id.okCalendarButton);
        cancelButton = (Button) findViewById(R.id.cancelButton);
        cancelCalendarButton = (Button) findViewById(R.id.cancelCalendarButton);
        calendarView = (CalendarView) findViewById(R.id.calendarView);

        searchButton = (FloatingActionButton) findViewById(R.id.searchButton);

        courtNameText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent h = new Intent(NewReservationActivity.this, CourtSelectionActivity.class);

                h.putExtra("day", dayText.getText().toString());
                h.putExtra("start", startText.getText().toString());
                h.putExtra("end", endText.getText().toString());

                NewReservationActivity.this.startActivity(h);
            }
        });

        startText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePickerLayout.setVisibility(View.VISIBLE);
                searchButton.setVisibility(View.GONE);
                picker = "start";
            }
        });

        endText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePickerLayout.setVisibility(View.VISIBLE);
                searchButton.setVisibility(View.GONE);
                picker = "end";
            }
        });

        emptyName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                courtNameText.getText().clear();
            }
        });

        emptyDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dayText.setText("");
            }
        });

        emptyStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startText.setText("");
                startLayout.setBackgroundColor(View.GONE);
            }
        });

        emptyEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                endText.setText("");
                endLayout.setBackgroundColor(View.GONE);
            }
        });

        timePicker.setCurrentHour(c.get(Calendar.HOUR_OF_DAY));
        timePicker.setCurrentMinute(c.get(Calendar.MINUTE));

        okButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                timePickerLayout.setVisibility(View.GONE);
                searchButton.setVisibility(View.VISIBLE);

                String hour = Utility.toDoubleDigit(timePicker.getCurrentHour()) + ":" + Utility.toDoubleDigit(timePicker.getCurrentMinute());

                switch(picker){
                    case "start":
                        startText.setText(hour);
                        break;
                    case "end":
                        endText.setText(hour);
                        break;
                }
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                timePickerLayout.setVisibility(View.GONE);
                searchButton.setVisibility(View.VISIBLE);
            }
        });

        calendarView.setDate(System.currentTimeMillis());
        calendarView.setMinDate(System.currentTimeMillis());

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month,
                                            int dayOfMonth) {
                    currentDay = Utility.toDoubleDigit(dayOfMonth);
                    currentMonth = Utility.toDoubleDigit(month + 1);
                    currentYear = String.valueOf(year);
            }
        });

        dayText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerLayout.setVisibility(View.VISIBLE);
                searchButton.setVisibility(View.GONE);
            }
        });

        okCalendarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerLayout.setVisibility(View.GONE);
                searchButton.setVisibility(View.VISIBLE);
                dayText.setText(currentDay + "-" + currentMonth + "-" + currentYear);
            }
        });

        cancelCalendarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerLayout.setVisibility(View.GONE);
                searchButton.setVisibility(View.VISIBLE);
            }
        });

        startLayout = (RelativeLayout) findViewById(R.id.startLayout);
        endLayout = (RelativeLayout) findViewById(R.id.endLayout);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean errorFlag, emptyDay, emptyStart, emptyEnd = emptyStart = emptyDay = errorFlag = false;

                if(dayText.getText().equals("")) emptyDay = true;
                if(startText.getText().equals("")) emptyStart = true;
                if(endText.getText().equals("")) emptyEnd = true;

                startLayout.setBackgroundColor(Color.TRANSPARENT);
                endLayout.setBackgroundColor(Color.TRANSPARENT);

                if(!emptyStart){
                    if(!emptyDay && Utility.isToday(dayText.getText().toString()) && Utility.isPastHour(startText.getText().toString())){
                        startLayout.setBackgroundDrawable(getResources().getDrawable(R.drawable.custom_rectangle));
                        errorFlag = true;
                        Toast.makeText(getApplicationContext(), "Non puoi prenotare per un'ora passata!", Toast.LENGTH_SHORT).show();
                    }

                    if(!emptyEnd && Utility.isEndBeforeStart(startText.getText().toString(), endText.getText().toString())){
                        endLayout.setBackgroundDrawable(getResources().getDrawable(R.drawable.custom_rectangle));
                        errorFlag = true;
                        Toast.makeText(getApplicationContext(), "Non puoi finire prima di iniziare!", Toast.LENGTH_SHORT).show();
                    }
                }

                if(!errorFlag){
                    Intent h = new Intent(NewReservationActivity.this, ReservationSearchResultActivity.class);

                    h.putExtra("courtName", courtNameText.getText().toString());
                    h.putExtra("day", dayText.getText().toString());
                    h.putExtra("start", startText.getText().toString());
                    h.putExtra("end", endText.getText().toString());

                    NewReservationActivity.this.startActivity(h);
                }
            }
        });
    }
}
