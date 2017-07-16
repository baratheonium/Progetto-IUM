package com.ium.baratheon.progetto_ium;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.app.Activity;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class ConfirmReservationActivity extends Activity {

    TextView nameText, mailText, phoneText, dayText, startText, endText, priceText,
            nameLabel, mailLabel, phoneLabel, dayLabel, startLabel, endLabel, priceLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_reservation);

        final Reservation r = (Reservation) getIntent().getSerializableExtra("thisReservation");
        Court c = r.getCourt();

        nameText = (TextView) findViewById(R.id.nameText);
        mailText = (TextView) findViewById(R.id.mailText);
        phoneText = (TextView) findViewById(R.id.phoneText);
        dayText = (TextView) findViewById(R.id.dayText);
        startText = (TextView) findViewById(R.id.startText);
        endText = (TextView) findViewById(R.id.endText);
        priceText = (TextView) findViewById(R.id.priceText);
        nameLabel = (TextView) findViewById(R.id.nameLabel);
        mailLabel = (TextView) findViewById(R.id.mailLabel);
        phoneLabel = (TextView) findViewById(R.id.phoneLabel);
        dayLabel = (TextView) findViewById(R.id.dayLabel);
        startLabel = (TextView) findViewById(R.id.startLabel);
        endLabel = (TextView) findViewById(R.id.endLabel);
        priceLabel = (TextView) findViewById(R.id.priceLabel);

        nameLabel.setTypeface(null, Typeface.BOLD);
        mailLabel.setTypeface(null, Typeface.BOLD);
        phoneLabel.setTypeface(null, Typeface.BOLD);
        dayLabel.setTypeface(null, Typeface.BOLD);
        startLabel.setTypeface(null, Typeface.BOLD);
        endLabel.setTypeface(null, Typeface.BOLD);
        priceLabel.setTypeface(null, Typeface.BOLD);

        nameText.setText(c.getName());
        mailText.setText(c.getMail());
        phoneText.setText(c.getPhoneNumber());
        dayText.setText(new SimpleDateFormat("dd-MM-yyyy", Locale.ITALIAN).format(r.getDay().getTime()));
        startText.setText(String.valueOf(r.getBegin()));
        endText.setText(String.valueOf(r.getEnd()));
        priceText.setText(String.valueOf(c.getPrice()));

        FloatingActionButton confirmButton = (FloatingActionButton) findViewById(R.id.confirmButton);

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Reservation.add(r);
                User u = Session.getInstance(getApplicationContext()).getUser();
                u.addReservation(r.getID());
                Session.getInstance(getApplicationContext()).setUser(u);
                DBHandler.getInstance().insertReservation(r, u.getUsername());

                Intent h = new Intent(ConfirmReservationActivity.this, HomepageActivity.class);
                ConfirmReservationActivity.this.startActivity(h);
            }
        });
    }

}
