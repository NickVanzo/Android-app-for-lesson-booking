package com.example.bookinglessons;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.bookinglessons.Controller.MySingleton;
import com.example.bookinglessons.Data.*;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.example.bookinglessons.databinding.ActivityMainBinding;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;

//Domanda da fare a Marino: ma come faccio a usare lo stesso backend per entrambi i progetti?
public class MainActivity<T extends ViewModel> extends AppCompatActivity {

    private ActivityMainBinding binding;
    String usernameOfLoggedUser;
    String surnameOfLoggedUser;
    String roleOfLoggedUser;
    String sessionId;
    Bundle extras;
    private UserViewModel viewModel;
    private BookedLessonsViewModel bookedLessonsViewModel;
    private DeletedPastLessonsViewModel deletedPastLessonsViewModel;
    RequestQueue requestQueue;

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
        fetchLessons("Da frequentare", bookedLessonsViewModel);
        fetchLessons("Cancellata", deletedPastLessonsViewModel);
        fetchLessons("Frequentata", deletedPastLessonsViewModel);

        setupUIElements();
        requestQueue = MySingleton.getInstance(this.getApplicationContext()).getRequestQueue();
    }

    /**
     * Fetch lessons from db and set the model view for the lessons booked
     */
    private <T extends ViewModel> void fetchLessons(String state, T v) {
        String url = Costants.URL + "book/bookedLessonsForUser?state="+state;
        ArrayList<BookedLesson> bookedLessons = new ArrayList<>();
        JsonObjectRequest jsonCustomReq = new JsonObjectRequest(
                url,
                null,
                response -> {
                    int i = 0;
                    try {
                        JSONArray reservations = response.getJSONArray("reservations");
                        while(i < reservations.length()) {
                            JSONObject reservation = reservations.getJSONObject(i);
                            String idUser = reservation.getString("idUser");
                            String idTeacher = reservation.getString("idTeacher");
                            String subject = reservation.getString("nameSubject");
                            String day = reservation.getString("day");
                            String slot = reservation.getString("slot");
                            String status =reservation.getString("status");

                            BookedLesson bookedLesson = new BookedLesson(idUser, idTeacher, slot, subject, day, status);
                            bookedLessons.add(bookedLesson);
                            i++;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } finally {
                        switch (state) {
                            case "Frequentata":
                                deletedPastLessonsViewModel.setPastLessons(bookedLessons);
                                break;
                            case "Da frequentare":
                                setViewModelLessons(bookedLessons);
                                break;
                            case "Cancellata":
                                deletedPastLessonsViewModel.setDeletedLessons(bookedLessons);
                                break;
                            default:
                                Log.d("in fetchLessons", "There was an error while fetching lessons");
                        }
                    }
                },
                error -> {

                }
        );
        MySingleton.getInstance(this.getApplicationContext()).addToRequestQueue(jsonCustomReq);
    }

    private void showWelcomeToast(String username) {
        Toast toast = Toast.makeText(getApplicationContext(), "You are logged as: " + username, Toast.LENGTH_SHORT*2);
        toast.show();
    }


    @Override
    protected void onResume() {
        super.onResume();
        fetchLessons("Da frequentare", bookedLessonsViewModel);
        fetchLessons("Cancellata", deletedPastLessonsViewModel);
        fetchLessons("Frequentata", deletedPastLessonsViewModel);
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
        deletedPastLessonsViewModel = new ViewModelProvider(this).get(DeletedPastLessonsViewModel.class);

        viewModel.setUser(usernameOfLoggedUser);
        viewModel.setRole(roleOfLoggedUser);
        viewModel.setSurname(surnameOfLoggedUser);
        viewModel.getUser().observe(this, username -> {
        });
    }

    /**
     * Pass the array fetched and set the model view for the lessons
     * @param lessons
     */
    private void setViewModelLessons(ArrayList<BookedLesson> lessons) {
        bookedLessonsViewModel = new ViewModelProvider(this).get(BookedLessonsViewModel.class);

        bookedLessonsViewModel.setBookedLessons(lessons);
    }

}