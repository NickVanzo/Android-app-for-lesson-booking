package com.example.bookinglessons;

import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.bookinglessons.Controller.MySingleton;
import org.json.JSONObject;

import java.net.URL;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URLConnection;

public class LoginActivity extends AppCompatActivity  {

    Button loginButton = null;
    Intent intent = null;
    EditText usernameText;
    EditText passwordText;
    public static String request = "helloServlet";
    public static final String URL = ("http://10.0.2.2:8080/demoWebDevelopment_war_exploded/" + request);

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        intent = new Intent(this, MainActivity.class);
        setUpUIElements();
        Log.d("In login activity", "This is the url provided" + URL);
        callServer();
    }

    private void callServer() {
        RequestQueue queue = Volley.newRequestQueue(this);

        String url = "http://10.0.2.2:8080/demoWebDevelopment_war_exploded/hello-servlet";
        // Request a string response from the provided URL.
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("In callServer", "Object returned: " +response.toString());
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Log.d("In onErrorResponse", "An error occured: " + error.getMessage());
                        error.printStackTrace();
                    }
                });
        // Access the RequestQueue through your singleton class.

        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
        Log.d("In call server", "HELLO");
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