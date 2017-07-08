package com.ium.baratheon.progetto_ium;

import android.content.Intent;
import android.graphics.Color;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    Button login;
    EditText username, password;
    TextView error, register;
    Boolean wrongPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login = (Button) findViewById(R.id.loginButton);
        username = (EditText) findViewById(R.id.usernameInput);
        password = (EditText) findViewById(R.id.passwordInput);
        error = (TextView) findViewById(R.id.errorText);
        register = (TextView) findViewById(R.id.registerLink);


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent h = new Intent(LoginActivity.this, RegistrationActivity.class);
                LoginActivity.this.startActivity(h);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                error.setVisibility(View.GONE);
                wrongPass = true;

                if (password.getText().toString().isEmpty()) {
                    error.setText(R.string.forgot_password);
                    error.setTextColor(Color.RED);
                    error.setVisibility(View.VISIBLE);
                }
                else {
                    System.out.println("INIZIO");
                    if(DBHandler.getInstance()==null){
                        new DBHandler(getApplicationContext());
                    }
                    User u = DBHandler.getInstance().authenticate(username.getText().toString(), password.getText().toString());
                    if (u != null) {
                        Intent h = new Intent(LoginActivity.this, HomepageActivity.class);
                        Session.getInstance(getApplicationContext()).setPrefs(username.getText().toString(), password.getText().toString(), u);
                        LoginActivity.this.startActivity(h);
                    }
                    else{
                        error.setText(R.string.wrong_combo);
                        error.setTextColor(Color.RED);
                        error.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
    }
}
