package com.example.bookinglessons;

import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.lifecycle.ViewModelProvider;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.bookinglessons.Controller.MySingleton;
import com.example.bookinglessons.Data.Costants;
import com.example.bookinglessons.Data.UserViewModel;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity  {

    private UserViewModel userViewModel;
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
        Log.d("In login activity", "This is the url provided" + Costants.URL);
    }

    private void login() {
        RequestQueue queue = Volley.newRequestQueue(this);
        String username = usernameText.getText().toString();
        String psw = passwordText.getText().toString();
        String url = Costants.URL + "auth/login?id="+username+"&psw="+psw+"";
        // Request a string response from the provided URL.
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.d("In callServer", "Object returned: " +response.getString("Status"));

                            if(response.getString("Status").equals("Logged")) {
                                intent.putExtra("key-username", usernameText.getText().toString());
                                intent.putExtra("surname", response.getString("Surname"));
                                intent.putExtra("role", response.getString("Role"));
                                startActivity(intent);
                            } else {
                                showErrorMessage("Password or username wrong");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
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
                        login();
                    });
        }
    }


}