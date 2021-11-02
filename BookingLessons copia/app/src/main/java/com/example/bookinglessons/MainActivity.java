package com.example.bookinglessons;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.bookinglessons.Controller.CustomRequest;
import com.example.bookinglessons.Controller.MySingleton;
import com.example.bookinglessons.Data.BookedLesson;
import com.example.bookinglessons.Data.BookedLessonsViewModel;
import com.example.bookinglessons.Data.Costants;
import com.example.bookinglessons.Data.UserViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.example.bookinglessons.databinding.ActivityMainBinding;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

//Domanda da fare a Marino: ma come faccio a usare lo stesso backend per entrambi i progetti?
public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    String usernameOfLoggedUser;
    String surnameOfLoggedUser;
    String roleOfLoggedUser;
    String sessionId;
    Bundle extras;
    private UserViewModel viewModel;
    private BookedLessonsViewModel bookedLessonsViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        extras = getIntent().getExtras();
        usernameOfLoggedUser = extras.getString("key-username", "NoValue");
        surnameOfLoggedUser = extras.getString("key-surname", "NoValue");
        roleOfLoggedUser = extras.getString("key-role", "NoValue");
        sessionId = extras.getString("key-session-id", "NoValue");
        showWelcomeToast(usernameOfLoggedUser);
        setViewModelUser(usernameOfLoggedUser, roleOfLoggedUser, surnameOfLoggedUser);
        fetchBookedLessons(usernameOfLoggedUser);
        setupUIElements();
    }

    /**
     * Fetch lessons from db and set the model view for the lessons booked
     * @param username
     */
    private void fetchBookedLessons(String username) {
        String url = Costants.URL + "book/bookedLessonsForUser";
        ArrayList<BookedLesson> bookedLessons = new ArrayList<>();
        CustomRequest jsonCustomReq = new CustomRequest(
                Request.Method.GET,
                url,
                null,
                sessionId,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                            Log.d("in onResponse", response.toString());
//                        int i = 0;
//                        try {
//                            JSONArray reservations = response.getJSONArray("reservations");
//                            while(i < reservations.length()) {
//                                JSONObject reservation = reservations.getJSONObject(i);
//                                String idUser = reservation.getString("idUser");
//                                String idTeacher = reservation.getString("idTeacher");
//                                String subject = reservation.getString("nameSubject");
//                                String day = reservation.getString("day");
//                                String slot = reservation.getString("slot");
//                                String status =reservation.getString("status");
//
//                                BookedLesson bookedLesson = new BookedLesson(idUser, idTeacher, slot, subject, day, status);
//                                bookedLessons.add(bookedLesson);
//                                i++;
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        } finally {
//                            setViewModelLessons(bookedLessons);
//                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );
        MySingleton.getInstance(this).addToRequestQueue(jsonCustomReq);
    }

    private void showWelcomeToast(String username) {
        Toast toast = Toast.makeText(getApplicationContext(), "You are logged as: " + username, Toast.LENGTH_SHORT*2);
        toast.show();
    }


    @Override
    protected void onResume() {
        super.onResume();
        fetchBookedLessons(usernameOfLoggedUser);
    }

    private void setupUIElements() {
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
    }

    /**
     * Set the model view for the user so that every fragment has the data for the logged user
     * @param usernameOfLoggedUser
     * @param roleOfLoggedUser
     * @param surnameOfLoggedUser
     */
    private void setViewModelUser(String usernameOfLoggedUser, String roleOfLoggedUser, String surnameOfLoggedUser) {
        viewModel = new ViewModelProvider(this).get(UserViewModel.class);

        viewModel.setUser(usernameOfLoggedUser);
        viewModel.setRole(roleOfLoggedUser);
        viewModel.setSurname(surnameOfLoggedUser);
        viewModel.getUser().observe(this, username -> {
            Log.d("In onCreate", "Share data: " + username);
        });
    }

    /**
     * Pass the array fetched and set the model view for the lessons
     * @param lessons
     */
    private void setViewModelLessons(ArrayList<BookedLesson> lessons) {
        bookedLessonsViewModel = new ViewModelProvider(this).get(BookedLessonsViewModel.class);

        bookedLessonsViewModel.setBookedLessons(lessons);

        bookedLessonsViewModel.getBookedLessons().observe(this, bookedLessons -> {
            Log.d("In getBookedLessons", "Lessons: " + bookedLessons.size());
        });
    }

}