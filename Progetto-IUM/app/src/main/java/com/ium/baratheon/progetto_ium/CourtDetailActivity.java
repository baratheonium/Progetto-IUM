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

        nameText = (TextView) findViewById(R.id.nameRecap);
        mailText = (TextView) findViewById(R.id.mail);
        phoneText = (TextView) findViewById(R.id.phone);
        beginText = (TextView) findViewById(R.id.begin);
        endText = (TextView) findViewById(R.id.end);
        fieldsText = (TextView) findViewById(R.id.fields);
        priceText = (TextView) findViewById(R.id.price);

        nameText.setText(nameText.getText() + "                 " + thisCourt.getName());
        mailText.setText(mailText.getText() + "                    " + thisCourt.getMail());
        phoneText.setText(phoneText.getText() + "            " + thisCourt.getPhoneNumber());
        beginText.setText(beginText.getText() + "      " + thisCourt.getBegin().toString());
        endText.setText(endText.getText() + "                      " + thisCourt.getEnd().toString());
        fieldsText.setText(fieldsText.getText() + "                " + thisCourt.getFieldNumber().toString());
        priceText.setText(priceText.getText() + "               " + thisCourt.getPrice().toString());
    }
}
