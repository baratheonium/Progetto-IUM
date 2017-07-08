package com.ium.baratheon.progetto_ium;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class CourtSelectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_court_selection);

        String day = getIntent().getStringExtra("day"),
                start = getIntent().getStringExtra("start"),
                end = getIntent().getStringExtra("end");


    }
}
