package com.example.bookinglessons;

import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;


public class LoginActivity extends AppCompatActivity  {

    Button loginButton = null;
    Intent intent = null;
    EditText usernameText;
    EditText passwordText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        intent = new Intent(this, MainActivity.class);
        setUpUIElements();
    }

    private void setUpUIElements() {
        if(usernameText == null && passwordText == null) {
            passwordText = findViewById(R.id.passwordField);
            usernameText = findViewById(R.id.usernameField);
        }
        if(loginButton == null) {
            loginButton = findViewById(R.id.loginButton);
            loginButton.setOnClickListener(
                    view -> {
                        intent.putExtra("key-username", usernameText.getText().toString());
                        startActivity(intent);
                    });
        }
    }


}