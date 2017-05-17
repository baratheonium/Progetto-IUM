package com.ium.baratheon.progetto_ium;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class RegistrationActivity extends AppCompatActivity {
    FloatingActionButton sendButton;
    EditText nameInput, surnameInput, mailInput, ageInput, usernameInput, passwordInput;
    TextView nameError, surnameError, mailError, ageError, usernameError, passwordError;
    Boolean errorFlag=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        sendButton = (FloatingActionButton) findViewById(R.id.sendButton);
        nameInput = (EditText) findViewById(R.id.nameInput);
        surnameInput = (EditText) findViewById(R.id.surnameInput);
        mailInput = (EditText) findViewById(R.id.mailInput);
        ageInput = (EditText) findViewById(R.id.ageInput);
        usernameInput = (EditText) findViewById(R.id.usernameInput);
        passwordInput = (EditText) findViewById(R.id.passwordInput);

        nameError = (TextView) findViewById(R.id.nameError);
        surnameError = (TextView) findViewById(R.id.surnameError);
        mailError = (TextView) findViewById(R.id.mailError);
        ageError = (TextView) findViewById(R.id.ageError);
        usernameError = (TextView) findViewById(R.id.usernameError);
        passwordError = (TextView) findViewById(R.id.passwordError);

        nameError.setVisibility(View.GONE);
        surnameError.setVisibility(View.GONE);
        mailError.setVisibility(View.GONE);
        ageError.setVisibility(View.GONE);
        usernameError.setVisibility(View.GONE);
        passwordError.setVisibility(View.GONE);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                errorFlag=false;

                if(nameInput.getText().length()==0){
                    nameError.setVisibility(View.VISIBLE);
                    errorFlag=true;
                }
                if(surnameInput.getText().length()==0){
                    surnameError.setVisibility(View.VISIBLE);
                    errorFlag=true;
                }
                if(mailInput.getText().length()==0){
                    mailError.setVisibility(View.VISIBLE);
                    errorFlag=true;
                }
                if(ageInput.getText().length()==0){
                    ageError.setVisibility(View.VISIBLE);
                    errorFlag=true;
                }
                else if(Integer.parseInt(ageInput.getText().toString())<18){
                    ageError.setText("Devi essere maggiorenne");
                    ageError.setVisibility(View.VISIBLE);
                    errorFlag=true;
                }
                else if(Integer.parseInt(ageInput.getText().toString())>60){
                    ageError.setText("Sicuro di avere le gambe?");
                    ageError.setVisibility(View.VISIBLE);
                    errorFlag=true;
                }
                if(usernameInput.getText().length()==0){
                    usernameError.setVisibility(View.VISIBLE);
                    errorFlag=true;
                }
                if(passwordInput.getText().length()==0){
                    passwordError.setVisibility(View.VISIBLE);
                    errorFlag=true;
                }

                if(!errorFlag){
                    Intent h = new Intent(RegistrationActivity.this, CompleteRegistrationActivity.class);
                    h.putExtra("name", nameInput.getText().toString());
                    h.putExtra("surname", surnameInput.getText().toString());
                    h.putExtra("mail", mailInput.getText().toString());
                    h.putExtra("age", ageInput.getText().toString());
                    h.putExtra("username", usernameInput.getText().toString());
                    h.putExtra("password", passwordInput.getText().toString());
                    RegistrationActivity.this.startActivity(h);
                }
            }
        });
    }
}
