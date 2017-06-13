package com.ium.baratheon.progetto_ium;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class CourtDetailActivity extends AppCompatActivity {

    TextView nameText, mailText, phoneText, beginText, endText, fieldsText, priceText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_court_detail);

        Court thisCourt = (Court) getIntent().getSerializableExtra("thisCourt");

        nameText = (TextView) findViewById(R.id.nameRecapText);
        mailText = (TextView) findViewById(R.id.mailText);
        phoneText = (TextView) findViewById(R.id.phoneText);
        beginText = (TextView) findViewById(R.id.beginText);
        endText = (TextView) findViewById(R.id.endText);
        fieldsText = (TextView) findViewById(R.id.fieldsText);
        priceText = (TextView) findViewById(R.id.priceText);

        nameText.setText(thisCourt.getName());
        mailText.setText(thisCourt.getMail());
        phoneText.setText(thisCourt.getPhoneNumber());
        beginText.setText(thisCourt.getBegin() + ":00");
        endText.setText(thisCourt.getEnd() + ":00");
        fieldsText.setText(thisCourt.getFieldNumber().toString());
        priceText.setText("€ " + thisCourt.getPrice() + ".00");
    }
}
