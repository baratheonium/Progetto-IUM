package com.ium.baratheon.progetto_ium;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Logger;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Session session = Session.getInstance(this.getApplicationContext());

        System.out.print("\n\n\n\n" + session.hasPrefs() + "\n\n\n\n\n");
        System.out.print("\n\n\n\n" + session.getPrefs().getAll() + "\n\n\n\n\n");

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
