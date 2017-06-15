package com.ium.baratheon.progetto_ium;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class HomepageActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private NavigationView mDrawerView;
    private ImageView drawerButton, deleteButton;
    private FloatingActionButton addButton;
    private TextView nameTextView, mailTextView, selectedTextView;
    private RelativeLayout selectionLayout;
    private User u;

    private boolean selectionMode = false;
    private Integer selectedItemsNumber = 0;
    private List<Reservation> selectedItems;

    ArrayAdapter<Reservation> reservationAdapter;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        //CAMBIARE RIFERIMENTO ALL'UTENTE DI SESSIONE
        u = Session.getInstance(getApplicationContext()).getUser();

        reservationAdapter = new ArrayAdapter<Reservation>(this, R.layout.row, u.getReservation());
        listView = (ListView) findViewById(R.id.reservationList);
        listView.setAdapter(reservationAdapter);

        selectionLayout = (RelativeLayout) findViewById(R.id.selectionLayout);
        selectedTextView = (TextView) findViewById(R.id.selectedItemsNumber);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if(selectionMode){
                    if(selectedItems.contains(u.getReservation().get(position))){
                        selectedItemsNumber--;
                        selectedTextView.setText(selectedItemsNumber.toString());
                        selectedItems.remove(u.getReservation().get(position));
                        listView.getChildAt(position - listView.getFirstVisiblePosition()).setBackgroundColor(Color.TRANSPARENT);
                    }
                    else {
                        selectedItemsNumber++;
                        selectedTextView.setText(selectedItemsNumber.toString());
                        selectedItems.add(u.getReservation().get(position));
                        listView.getChildAt(position - listView.getFirstVisiblePosition()).setBackgroundColor(Color.LTGRAY);
                    }
                }
                else {
                    selectionMode = true;
                    selectedItemsNumber++;
                    selectedTextView.setText(selectedItemsNumber.toString());
                    selectedItems = new ArrayList<>();
                    setTitle("");
                    selectionLayout.setVisibility(View.VISIBLE);

                    listView.getChildAt(position - listView.getFirstVisiblePosition()).setBackgroundColor(Color.LTGRAY);
                    selectedItems.add(u.getReservation().get(position));
                }

                if(selectedItemsNumber == 0){
                    selectionMode = false;
                    setTitle("Le tue prenotazioni");
                    selectionLayout.setVisibility(View.GONE);
                }

                return false;
            }
        });

        deleteButton = (ImageView) findViewById(R.id.deleteSelected);

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(Iterator<Reservation> i = u.getReservation().iterator(); i.hasNext();){
                    if(selectedItems.contains(i.next())){
                        i.remove();
                    }
                }

                for(View vv: listView.getTouchables()){
                    vv.setBackgroundColor(Color.TRANSPARENT);
                }

                selectedItems.clear();
                selectionMode = false;
                setTitle("Le tue prenotazioni");
                selectionLayout.setVisibility(View.GONE);

                Snackbar undoBar = Snackbar.make(findViewById(R.id.homepageLayout),
                        selectedItemsNumber + " elementi eliminati",
                        Snackbar.LENGTH_SHORT);
                undoBar.setAction("ANNULLA", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //ANNULLA LA TRANSAZIONE E RIPRISTINA GLI ELEMENTI
                    }
                });
                undoBar.show();

                selectedItemsNumber = 0;
            }
        });

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerView = (NavigationView) findViewById(R.id.nav_view);

        if (mDrawerView != null) {
            mDrawerView.setNavigationItemSelectedListener(
                    new NavigationView.OnNavigationItemSelectedListener() {
                        @Override
                        public boolean onNavigationItemSelected(MenuItem menuItem) {
                            Intent h;

                            switch (menuItem.getItemId()) {
                                case R.id.action_home:
                                    break;
                                case R.id.action_viewCourt:
                                    h = new Intent(HomepageActivity.this, CourtListActivity.class);
                                    HomepageActivity.this.startActivity(h);
                                    finish();
                                    break;
                                case R.id.action_settings:
                                   // Intent g = new Intent(HomepageActivity.this, Main2Activity.class);
                                   // HomepageActivity.this.startActivity(g);
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

        addButton = (FloatingActionButton) findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent h = new Intent(HomepageActivity.this, NewReservationActivity.class);
                HomepageActivity.this.startActivity(h);
            }
        });
    }
}
