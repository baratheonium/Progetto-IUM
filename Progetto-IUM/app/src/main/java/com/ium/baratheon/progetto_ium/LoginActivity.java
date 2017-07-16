package com.ium.baratheon.progetto_ium;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class LoginActivity extends AppCompatActivity {
    Button login;
    EditText username, password;
    TextView register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login = (Button) findViewById(R.id.loginButton);
        username = (EditText) findViewById(R.id.usernameInput);
        password = (EditText) findViewById(R.id.passwordInput);
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
                if(!username.getText().toString().isEmpty() && !password.getText().toString().isEmpty()) {
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
                        Toast.makeText(LoginActivity.this, R.string.wrong_combo, Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    if (username.getText().toString().isEmpty()) {
                        Toast.makeText(LoginActivity.this, R.string.forgot_username, Toast.LENGTH_SHORT).show();
                    }
                    if (password.getText().toString().isEmpty()) {
                        Toast.makeText(LoginActivity.this, R.string.forgot_password, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
