package com.ium.baratheon.progetto_ium;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class ReservationSearchResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_search_result);

        User user = Session.getInstance(getApplicationContext()).getUser();
        List<Reservation> list = new ArrayList<>();
        String courtName = getIntent().getStringExtra("courtName");


        if(courtName!=null && !courtName.isEmpty()) {

        } else {
            if (user.getFavorites() != null) {
                for (Integer i : user.getFavorites()) {

                }
            } else {

            }
        }

    }


}
