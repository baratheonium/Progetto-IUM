package com.ium.baratheon.progetto_ium;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

public class CourtListActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private NavigationView mDrawerView;
    private ImageView drawerButton;
    private TextView nameTextView, mailTextView;
    private User u;
    //Variabili per test DB
    public DBHandler db = new DBHandler(this);
    public List<Court> courtL = new ArrayList<Court>();
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_court_list);

        u = (User) getIntent().getSerializableExtra("user");

        //Inserimento in db di Court di default
        Court court = new Court();
        court.setDefaultCourtList();
        for(Court c: Court.courtList){
            db.insertCourt(c);
        }
        //
        //cursor = db.getAllCourts();

        for(int i=1; i<6; i++){
            courtL.add(db.selectCourt(i));
        }



        //

        //Modificato per il test del DB
        ArrayAdapter<Court> courtAdapter = new CourtArrayAdapter(this, R.layout.listview, R.id.row, u, Court.courtList);
        //ArrayAdapter<Court> courtAdapter = new CourtArrayAdapter(this, R.layout.listview, R.id.row, u, Court.courtList);
        ListView listView = (ListView) findViewById(R.id.courtList);
        listView.setAdapter(courtAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent h = new Intent(CourtListActivity.this, CourtDetailActivity.class);
                h.putExtra("thisCourt", Court.courtList.get(position));
                h.putExtra("user", u);
                CourtListActivity.this.startActivity(h);
            }
        });

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerView = (NavigationView) findViewById(R.id.nav_view);

        if (mDrawerView != null) {
            mDrawerView.setNavigationItemSelectedListener(
                    new NavigationView.OnNavigationItemSelectedListener() {
                        @Override
                        public boolean onNavigationItemSelected(MenuItem menuItem) {
                            switch (menuItem.getItemId()) {
                                case R.id.action_home:
                                    Intent h = new Intent(CourtListActivity.this, HomepageActivity.class);
                                    h.putExtra("user", u);
                                    CourtListActivity.this.startActivity(h);
                                    break;
                                case R.id.action_viewCourt:
                                    break;
                                case R.id.action_settings:
                                    //activity impostazioni
                                    break;
                                case R.id.action_logout:
                                    //invalidare la sessione
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
