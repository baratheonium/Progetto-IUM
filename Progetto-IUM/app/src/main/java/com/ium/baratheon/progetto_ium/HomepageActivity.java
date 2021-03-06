package com.ium.baratheon.progetto_ium;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

public class HomepageActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private NavigationView mDrawerView;
    private TextView selectedTextView;
    private RelativeLayout selectionLayout;
    private User u;

    private boolean selectionMode = false;
    private Integer selectedItemsNumber = 0;
    private List<Integer> selectedItems;

    ArrayAdapter<Reservation> reservationAdapter;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        ImageView drawerButton, deleteButton;
        final FloatingActionButton addButton;
        TextView nameTextView, mailTextView;

        u = Session.getInstance(getApplicationContext()).getUser();

        final List<Reservation> resList = new ArrayList<>();

        if(u.getReservation() != null && !u.getReservation().isEmpty()) {
            for (Integer i : u.getReservation()) {
                resList.add(Reservation.get(i));
            }
        }

        reservationAdapter = new ArrayAdapter<>(this, R.layout.custom_listview, R.id.text, resList);
        listView = (ListView) findViewById(R.id.reservationList);
        listView.setAdapter(reservationAdapter);

        selectionLayout = (RelativeLayout) findViewById(R.id.selectionLayout);
        selectedTextView = (TextView) findViewById(R.id.selectedItemsNumber);

        addButton = (FloatingActionButton) findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Session.getInstance(getApplicationContext()).setUser(u);
                Intent h = new Intent(HomepageActivity.this, NewReservationActivity.class);
                HomepageActivity.this.startActivity(h);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Reservation r = resList.get(position);
                Intent h = new Intent(HomepageActivity.this, ReservationDetailActivity.class);
                h.putExtra("thisReservation", r);
                HomepageActivity.this.startActivity(h);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if(selectionMode){
                    if(selectedItems.contains(u.getReservation().get(position))){
                        selectedItemsNumber--;
                        selectedTextView.setText(String.format(Locale.ITALIAN, "%d", selectedItemsNumber));
                        selectedItems.remove(u.getReservation().get(position));
                        listView.getChildAt(position - listView.getFirstVisiblePosition()).setBackgroundColor(Color.TRANSPARENT);
                    }
                    else {
                        selectedItemsNumber++;
                        selectedTextView.setText(String.format(Locale.ITALIAN, "%d", selectedItemsNumber));
                        selectedItems.add(resList.get(position).getID());
                        listView.getChildAt(position - listView.getFirstVisiblePosition()).setBackgroundColor(Color.LTGRAY);
                    }
                }
                else {
                    selectionMode = true;
                    selectedItemsNumber++;
                    selectedTextView.setText(String.format(Locale.ITALIAN, "%d", selectedItemsNumber));
                    addButton.setVisibility(View.GONE);
                    selectedItems = new ArrayList<>();
                    setTitle("");
                    selectionLayout.setVisibility(View.VISIBLE);

                    listView.getChildAt(position - listView.getFirstVisiblePosition()).setBackgroundColor(Color.LTGRAY);
                    selectedItems.add(resList.get(position).getID());
                }

                if(selectedItemsNumber == 0){
                    selectionMode = false;
                    setTitle("Le tue prenotazioni");
                    selectionLayout.setVisibility(View.GONE);
                    addButton.setVisibility(View.VISIBLE);
                }

                return false;
            }
        });

        deleteButton = (ImageView) findViewById(R.id.deleteSelected);

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(Iterator<Reservation> i = resList.iterator(); i.hasNext();){
                    Reservation r = i.next();
                    if(selectedItems.contains(r.getID())){
                        i.remove();
                    }
                }

                for(Integer i: selectedItems) {
                    reservationAdapter.remove(Reservation.get(i));
                }
                reservationAdapter.notifyDataSetChanged();

                u.removeReservations(selectedItems);

                for(View vv: listView.getTouchables()){
                    vv.setBackgroundColor(Color.TRANSPARENT);
                }

                selectionMode = false;
                setTitle("Le tue prenotazioni");
                selectionLayout.setVisibility(View.GONE);

                Snackbar undoBar = Snackbar.make(findViewById(R.id.homepageLayout),
                        selectedItemsNumber + " elementi eliminati",
                        Snackbar.LENGTH_SHORT);

                undoBar.setAction("ANNULLA", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        List<Reservation> list = DBHandler.getInstance().retrieveReservation(u, selectedItems);

                        resList.addAll(list);
                        u.addReservations(list);

                        Collections.sort(resList, new Comparator<Reservation>(){
                            @Override
                            public int compare(Reservation s1, Reservation s2) {
                                return s2.getDay().compareTo(s1.getDay());
                            }
                        });

                        reservationAdapter.notifyDataSetChanged();

                        selectedItemsNumber = 0;
                        selectedItems.clear();
                    }
                });

                undoBar.setCallback(new Snackbar.Callback() {
                    @Override
                    public void onDismissed(Snackbar snackbar, int event) {
                        if(selectedItemsNumber>0) {
                            DBHandler.getInstance().deleteReservations(selectedItems);
                            Reservation.remove(selectedItems);
                            Reservation.removeAllNull();
                            selectedItemsNumber = 0;
                            selectedItems.clear();
                        }
                    }
                });

                undoBar.setDuration(8000);
                undoBar.show();

                addButton.setVisibility(View.VISIBLE);
            }
        });

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerView = (NavigationView) findViewById(R.id.nav_view);

        if (mDrawerView != null) {
            mDrawerView.setNavigationItemSelectedListener(
                    new NavigationView.OnNavigationItemSelectedListener() {
                        @Override
                        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                            Intent h;

                            switch (menuItem.getItemId()) {
                                case R.id.action_home:
                                    break;
                                case R.id.action_viewCourt:
                                    Session.getInstance(getApplicationContext()).setUser(u);
                                    h = new Intent(HomepageActivity.this, CourtListActivity.class);
                                    HomepageActivity.this.startActivity(h);
                                    finish();
                                    break;
                                case R.id.action_logout:
                                    Session.getInstance(getApplicationContext()).removePrefs();
                                    h = new Intent(HomepageActivity.this, LoginActivity.class);
                                    HomepageActivity.this.startActivity(h);
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
    }
}
