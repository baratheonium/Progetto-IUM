package com.ium.baratheon.progetto_ium;

import android.content.Intent;
import android.support.annotation.NonNull;
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


public class CourtListActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private NavigationView mDrawerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_court_list);

        ImageView drawerButton;
        TextView nameTextView, mailTextView;

        //Modificato per il test del DB
        CourtArrayAdapter<Court> courtAdapter = new CourtArrayAdapter<>(this, R.layout.listview, R.id.row, Court.courtList);
        ListView listView = (ListView) findViewById(R.id.courtList);
        listView.setAdapter(courtAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                User u = Session.getInstance(getApplicationContext()).getUser();
                Session.getInstance(getApplicationContext()).setUser(u);
                Intent h = new Intent(CourtListActivity.this, CourtDetailActivity.class);
                h.putExtra("thisCourt", Court.courtList.get(position));
                CourtListActivity.this.startActivity(h);
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
                                    User u = Session.getInstance(getApplicationContext()).getUser();
                                    Session.getInstance(getApplicationContext()).setUser(u);
                                    h = new Intent(CourtListActivity.this, HomepageActivity.class);
                                    CourtListActivity.this.startActivity(h);
                                    finish();
                                    break;
                                case R.id.action_viewCourt:
                                    break;
                                case R.id.action_settings:
                                    //activity impostazioni
                                    break;
                                case R.id.action_logout:
                                    Session.getInstance(getApplicationContext()).removePrefs();
                                    h = new Intent(CourtListActivity.this, LoginActivity.class);
                                    CourtListActivity.this.startActivity(h);
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

        User u = Session.getInstance(getApplicationContext()).getUser();
        nameTextView.setText(u.getName() + " " + u.getSurname());
        mailTextView.setText(u.getEmail());
    }
}
