package com.ium.baratheon.progetto_ium;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private DBHandler db = new DBHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Session session = Session.getInstance(this.getApplicationContext());
        //session.removePrefs();
        //DBHandler.getInstance().onUpgrade(db.getWritableDatabase(), 1, 1);
        Utility.setUp();

        if(session.hasPrefs() && (!session.getUsername().isEmpty() && !session.getPassword().isEmpty())) {
            Intent h = new Intent(MainActivity.this, HomepageActivity.class);
            MainActivity.this.startActivity(h);
            finish();
        } else {
            Intent h = new Intent(MainActivity.this, LoginActivity.class);
            MainActivity.this.startActivity(h);
            finish();
        }
    }
}
