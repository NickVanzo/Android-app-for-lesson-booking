package com.example.bookinglessons.View.Activities;

import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.android.volley.*;
import com.android.volley.toolbox.*;
import com.example.bookinglessons.Network.MySingleton;
import com.example.bookinglessons.Model.ViewModels.UserViewModel;
import com.example.bookinglessons.R;
import org.json.JSONException;

import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.MalformedURLException;

public class LoginActivity extends AppCompatActivity  {

    private UserViewModel userViewModel;
    Button loginButton = null;
    Intent intent = null;
    EditText usernameText;
    EditText passwordText;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        CookieManager cookieManager = new CookieManager();
        CookieHandler.setDefault(cookieManager);
        intent = new Intent(this, MainActivity.class);
        setUpUIElements();
        // initialize the singleton
        requestQueue = MySingleton.getInstance(this.getApplicationContext()).getRequestQueue();
    }


    private void login() throws MalformedURLException {
        RequestQueue queue = Volley.newRequestQueue(this);
        String username = usernameText.getText().toString();
        String psw = passwordText.getText().toString();
        String url = getResources().getString(R.string.servlet_url) + "auth/login?id="+username+"&psw="+psw+"";

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url,
                null,
                response -> {
                    try {
                        intent.putExtra("key-username", usernameText.getText().toString());
                        intent.putExtra("key-role", response.getJSONObject("user").getString("role"));
                        intent.putExtra("key-surname", response.getJSONObject("user").getString("surname"));
                        intent.putExtra("key-session-id", response.getString("id"));
                        startActivity(intent);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> {
                    VolleyLog.d("In onErrorResponse", "Error: " + error.getMessage());
                    Toast.makeText(getApplicationContext(),
                            error.getMessage(), Toast.LENGTH_SHORT).show();
                });

        MySingleton.getInstance(this.getApplicationContext()).addToRequestQueue(jsonObjReq);
    }

    private void showErrorMessage(String error) {
        Toast toast = Toast.makeText(getApplicationContext(), error, Toast.LENGTH_SHORT*2);
        toast.show();
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
                        try {
                            login();
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        }
                    });
        }
    }


}