package com.ium.baratheon.progetto_ium;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class CompleteRegistrationActivity extends AppCompatActivity {

    TextView editPic, nameRecap, ageRecap, mailRecap, usernameRecap;
    FloatingActionButton confirmButton;
    ImageView proPic;
    DBHandler db = DBHandler.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_registration);

        editPic = (TextView) findViewById(R.id.editPic);
        nameRecap = (TextView) findViewById(R.id.nameRecap);
        ageRecap = (TextView) findViewById(R.id.ageRecap);
        mailRecap = (TextView) findViewById(R.id.mailRecap);
        usernameRecap = (TextView) findViewById(R.id.usernameRecap);
        confirmButton = (FloatingActionButton) findViewById(R.id.confirmButton);
        proPic = (ImageView) findViewById(R.id.proPic);

        nameRecap.setText(getIntent().getStringExtra("name") + " " + getIntent().getStringExtra("surname"));
        ageRecap.setText(getIntent().getStringExtra("age"));
        mailRecap.setText(getIntent().getStringExtra("mail"));
        usernameRecap.setText(getIntent().getStringExtra("username"));

        editPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap b = proPic.getDrawable().equals(getResources().getDrawable(R.drawable.default_pro_pic)) ?
                        ((BitmapDrawable)proPic.getDrawable()).getBitmap() : null;

                User u = new User(getIntent().getStringExtra("username"),
                        getIntent().getStringExtra("password"),
                        getIntent().getStringExtra("name"),
                        getIntent().getStringExtra("surname"),
                        getIntent().getStringExtra("mail"),
                        Integer.parseInt(getIntent().getStringExtra("age")), b);

                //Inserimento nel DB?
                db.insertUser(u);

                Toast.makeText(getApplicationContext(), "Utente registrato con successo!", Toast.LENGTH_SHORT).show();

                Intent h = new Intent(CompleteRegistrationActivity.this, LoginActivity.class);
                CompleteRegistrationActivity.this.startActivity(h);
            }
        });
    }
}
