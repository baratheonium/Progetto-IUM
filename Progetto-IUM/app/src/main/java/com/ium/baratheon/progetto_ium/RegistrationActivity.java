package com.ium.baratheon.progetto_ium;

import android.content.Intent;
import android.opengl.Visibility;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class RegistrationActivity extends AppCompatActivity {
    FloatingActionButton sendButton;
    EditText nameInput, surnameInput, mailInput, ageInput, usernameInput, passwordInput;
    RelativeLayout nameLayout, surnameLayout, ageLayout, mailLayout, usernameLayout, passwordLayout;

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

        nameLayout = (RelativeLayout) findViewById(R.id.nameLayout);
        surnameLayout = (RelativeLayout) findViewById(R.id.surnameLayout);
        ageLayout = (RelativeLayout) findViewById(R.id.ageLayout);
        mailLayout = (RelativeLayout) findViewById(R.id.mailLayout);
        usernameLayout = (RelativeLayout) findViewById(R.id.usernameLayout);
        passwordLayout = (RelativeLayout) findViewById(R.id.passwordLayout);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nameLayout.setBackgroundResource(0);
                surnameLayout.setBackgroundResource(0);
                mailLayout.setBackgroundResource(0);
                ageLayout.setBackgroundResource(0);
                usernameLayout.setBackgroundResource(0);
                passwordLayout.setBackgroundResource(0);

                List<String> empty = new ArrayList<>(),
                        errors= new ArrayList<>();

                if(nameInput.getText().toString().isEmpty()){
                    empty.add("Nome");
                    nameLayout.setBackgroundDrawable(getResources().getDrawable(R.drawable.custom_rectangle));
                }
                if(surnameInput.getText().toString().isEmpty()){
                    empty.add("Cognome");
                    surnameLayout.setBackgroundDrawable(getResources().getDrawable(R.drawable.custom_rectangle));
                }
                if(mailInput.getText().toString().isEmpty()){
                    empty.add("E-mail");
                    mailLayout.setBackgroundDrawable(getResources().getDrawable(R.drawable.custom_rectangle));
                }
                else if(DBHandler.getInstance().lookForMail(mailInput.getText().toString())){
                    errors.add("E-mail già presente");
                    mailLayout.setBackgroundDrawable(getResources().getDrawable(R.drawable.custom_rectangle));
                }
                if(ageInput.getText().toString().isEmpty()){
                    empty.add("Età");
                    ageLayout.setBackgroundDrawable(getResources().getDrawable(R.drawable.custom_rectangle));
                }
                else if(Integer.parseInt(ageInput.getText().toString())<18){
                    errors.add("Devi essere maggiorenne");
                    ageLayout.setBackgroundDrawable(getResources().getDrawable(R.drawable.custom_rectangle));
                }
                if(usernameInput.getText().toString().isEmpty()){
                    empty.add("Username");
                    usernameLayout.setBackgroundDrawable(getResources().getDrawable(R.drawable.custom_rectangle));
                }
                else if(DBHandler.getInstance().lookForUsername(usernameInput.getText().toString())){
                    errors.add("Username già presente");
                    usernameLayout.setBackgroundDrawable(getResources().getDrawable(R.drawable.custom_rectangle));
                }
                if(passwordInput.getText().toString().isEmpty()){
                    empty.add("Password");
                    passwordLayout.setBackgroundDrawable(getResources().getDrawable(R.drawable.custom_rectangle));
                }

                if(!empty.isEmpty() || !errors.isEmpty()){
                    if(!errors.isEmpty()){
                        for(String s: errors){
                            Toast.makeText(RegistrationActivity.this, s, Toast.LENGTH_SHORT).show();
                        }
                    }
                    if(!empty.isEmpty()) {
                        Toast.makeText(RegistrationActivity.this,
                                "Non hai riempito i seguenti campi obbligatori: " + empty.toString().substring(
                                        1, empty.toString().length()-1),
                                Toast.LENGTH_LONG).show();
                    }
                }
                else{
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
