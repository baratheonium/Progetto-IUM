package com.ium.baratheon.progetto_ium;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class CourtSelectionActivity extends AppCompatActivity {

    EditText searchBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_court_selection);

        ArrayList<Court> list = new ArrayList<>(Court.courtList);
        Collections.sort(list, new Comparator<Court>() {
            @Override
            public int compare(Court o1, Court o2) {
                List<Integer> favorites = Session.getInstance(getApplicationContext()).getUser().getFavorites();
                boolean first = favorites.contains(o1.getID()),
                        second = favorites.contains(o2.getID());

                if(first && !second){
                    return -1;
                }
                else if(!first && second){
                    return 1;
                }
                else{
                    return ((Integer)o1.getID()).compareTo(o2.getID());
                }
            }
        });

        final CourtArrayAdapter<Court> courtAdapter = new CourtArrayAdapter<>(this, R.layout.listview, R.id.row, list);
        final ListView listView = (ListView) findViewById(R.id.courtList);
        listView.setAdapter(courtAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                User u = Session.getInstance(getApplicationContext()).getUser();
                Intent h = new Intent(CourtSelectionActivity.this, NewReservationActivity.class);
                h.putExtra("courtName", ((Court)courtAdapter.getItem(position)).getName());
                h.putExtra("day", getIntent().getStringExtra("day"));
                h.putExtra("start", getIntent().getStringExtra("start"));
                h.putExtra("end", getIntent().getStringExtra("end"));
                Session.getInstance(getApplicationContext()).setUser(u);
                CourtSelectionActivity.this.startActivity(h);
                finish();
            }
        });

        searchBar = (EditText)findViewById(R.id.searchBar);
        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                courtAdapter.clear();
                for(Court c: Court.courtList){
                    if(c.getName().toLowerCase().contains(s.toString().toLowerCase())){
                        courtAdapter.add(c);
                    }
                }
                courtAdapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}
