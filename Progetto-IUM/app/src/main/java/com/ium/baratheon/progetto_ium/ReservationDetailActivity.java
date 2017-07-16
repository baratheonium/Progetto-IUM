package com.ium.baratheon.progetto_ium;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.app.Activity;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class ReservationDetailActivity extends Activity {

    TextView nameText, mailText, phoneText, dayText, startText, endText, priceText,
            nameLabel, mailLabel, phoneLabel, dayLabel, startLabel, endLabel, priceLabel,
            cancelText;

    FloatingActionButton cancelButton;
    RelativeLayout confirmLayout, detailLayout;

    Button okButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_detail);

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

        confirmLayout = (RelativeLayout) findViewById(R.id.confirmLayout);
        detailLayout = (RelativeLayout) findViewById(R.id.detailLayout);

        cancelButton = (FloatingActionButton) findViewById(R.id.cancelButton);

        Calendar cal = Calendar.getInstance();

        if(r.getDay().after(cal) ||
                (r.getBegin() > cal.get(Calendar.HOUR_OF_DAY)+2) &&
                        r.getDay().get(Calendar.DAY_OF_MONTH) == cal.get(Calendar.DAY_OF_MONTH) &&
                        r.getDay().get(Calendar.MONTH) == cal.get(Calendar.MONTH) &&
                        r.getDay().get(Calendar.YEAR) == cal.get(Calendar.YEAR)) {
            
            cancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    detailLayout.setVisibility(View.GONE);
                    confirmLayout.setVisibility(View.VISIBLE);
                }
            });
        }
        else{
            cancelButton.setVisibility(View.GONE);
        }

        okButton = (Button) findViewById(R.id.okButton);

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmLayout.setVisibility(View.GONE);
                detailLayout.setVisibility(View.VISIBLE);
                User u = Session.getInstance(getApplicationContext()).getUser();
                System.out.println(u.getReservation());
                u.removeReservation(r);
                System.out.println(u.getReservation());
                System.out.println(Reservation.reservationList);
                Reservation.remove(r.getID());
                Reservation.removeAllNull();
                System.out.println(Reservation.reservationList);
                DBHandler.getInstance().deleteReservation(r.getID());
                Session.getInstance(getApplicationContext()).setUser(u);
                Intent h = new Intent(ReservationDetailActivity.this, HomepageActivity.class);
                ReservationDetailActivity.this.startActivity(h);
                finish();
            }
        });

        cancelText = (TextView) findViewById(R.id.cancelText);

        cancelText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmLayout.setVisibility(View.GONE);
                detailLayout.setVisibility(View.VISIBLE);
            }
        });
    }

}
