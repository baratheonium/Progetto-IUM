package com.ium.baratheon.progetto_ium;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ReservationSearchResultActivity extends AppCompatActivity {
    TextView editCourt, editDay, editStart, editEnd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_search_result);

        ListView listView = (ListView) findViewById(R.id.reservationList);
        User user = Session.getInstance(getApplicationContext()).getUser();
        final List<Reservation> list = new ArrayList<>();
        String courtName = getIntent().getStringExtra("courtName"),
                day = getIntent().getStringExtra("day"),
                start = getIntent().getStringExtra("start"),
                end = getIntent().getStringExtra("end");

        editCourt = (TextView) findViewById(R.id.editCourt);
        editDay = (TextView) findViewById(R.id.editDay);
        editStart = (TextView) findViewById(R.id.editStart);
        editEnd = (TextView) findViewById(R.id.editEnd);

        if(courtName!=null && !courtName.isEmpty()){
            editCourt.setText(courtName);
        }
        if(day!=null && !day.isEmpty()){
            editDay.setText(day);
        }
        if(start!=null && !start.isEmpty()){
            editStart.setText(start);
        }
        if(end!=null && !end.isEmpty()){
            editEnd.setText(end);
        }

        if(courtName!=null && !courtName.isEmpty()) {
            Court c = Court.getCourt(courtName);
            list.addAll(Utility.populateSearchResult(c, day, start, end));
        } else {
            if (user.getFavorites() != null && !user.getFavorites().isEmpty()) {
                for (Integer id : user.getFavorites()) {
                    Court c = Court.getCourt(id);
                    list.addAll(Utility.populateSearchResult(c, day, start, end));
                }
            } else {
                for(Court c: Court.courtList){
                    list.addAll(Utility.populateSearchResult(c, day, start, end));
                }
            }
        }

        ArrayAdapter<Reservation> reservationArrayAdapter = new ArrayAdapter<>(this, R.layout.custom_listview, R.id.text, list);
        listView.setAdapter(reservationArrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                User u = Session.getInstance(getApplicationContext()).getUser();
                Session.getInstance(getApplicationContext()).setUser(u);
                Intent h = new Intent(ReservationSearchResultActivity.this, ConfirmReservationActivity.class);
                h.putExtra("thisReservation", list.get(position));
                ReservationSearchResultActivity.this.startActivity(h);
            }
        });
    }


}
