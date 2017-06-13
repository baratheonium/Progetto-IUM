package com.ium.baratheon.progetto_ium;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;//test
import java.util.List;//test

public class LoginActivity extends AppCompatActivity {
    Button login;
    EditText username, password;
    TextView error, register;
    Boolean notExisting;
    Boolean wrongPass;

    //Variabili test DB
    public DBHandler db = new DBHandler(this);
    public List<User> userL = new ArrayList<User>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login = (Button) findViewById(R.id.loginButton);
        username = (EditText) findViewById(R.id.usernameInput);
        password = (EditText) findViewById(R.id.passwordInput);
        error = (TextView) findViewById(R.id.errorText);
        register = (TextView) findViewById(R.id.registerLink);

        //Setto dei Court e User di default (test)
        Court.setDefaultCourtList();
        User.getDefaultUser();

        //Inserimento in db di User di default
        User user = new User();
        user.setDefaultUserList();
        for(User u: User.userList){
            db.insertUser(u);
        }
        for(int i=1; i<4; i++){
            userL.add(db.selectUser("admin" + i));
        }
        //

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
                notExisting = true;
                wrongPass = true;

                //Controllo Username e Password
                for(User u: userL) { //userL invece di User.userList (test)
                    if (username.getText().toString().equals(u.getUsername())){
                        if(password.getText().toString().equals(u.getPassword())){
                            Intent h = new Intent(LoginActivity.this, HomepageActivity.class);
                            h.putExtra("user", u);
                            LoginActivity.this.startActivity(h);
                            notExisting = false;
                        }
                        else {
                            error.setText("Combinazione username o password errata!");
                            error.setTextColor(Color.RED);
                            error.setVisibility(View.VISIBLE);
                            notExisting = false;
                        }
                        if(password.getText().toString().isEmpty()){
                            error.setText("Hai dimenticato la password!");
                            error.setTextColor(Color.RED);
                            error.setVisibility(View.VISIBLE);
                        }
                    }
                }

                if(notExisting) {
                    error.setText("Username: " + username.getText().toString() + " non esistente!");
                    error.setTextColor(Color.RED);
                    error.setVisibility(View.VISIBLE);
                }

            }
        });
    }
}
