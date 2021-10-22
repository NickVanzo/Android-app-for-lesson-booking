package com.example.bookinglessons;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.lifecycle.ViewModelProvider;
import com.example.bookinglessons.Data.UserViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.example.bookinglessons.databinding.ActivityMainBinding;

//Domanda da fare a Marino: ma come faccio a usare lo stesso backend per entrambi i progetti?
public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    String usernameOfLoggedUser;
    String surnameOfLoggedUser;
    String roleOfLoggedUser;
    Bundle extras;
    private UserViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        extras = getIntent().getExtras();
        usernameOfLoggedUser = extras.getString("key-username", "NoValue");
        surnameOfLoggedUser = extras.getString("surname", "NoValue");
        roleOfLoggedUser = extras.getString("role", "NoValue");
        showWelcomeToast(usernameOfLoggedUser);
        setViewModelUser(usernameOfLoggedUser, roleOfLoggedUser, surnameOfLoggedUser);
        setupUIElements();

    }

    private void showWelcomeToast(String username) {
        Toast toast = Toast.makeText(getApplicationContext(), "You are logged as: " + username, Toast.LENGTH_SHORT*2);
        toast.show();
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

    private void setViewModelUser(String usernameOfLoggedUser, String roleOfLoggedUser, String surnameOfLoggedUser) {
        viewModel = new ViewModelProvider(this).get(UserViewModel.class);
        viewModel.setUser(usernameOfLoggedUser);
        viewModel.setRole(roleOfLoggedUser);
        viewModel.setSurname(surnameOfLoggedUser);
        viewModel.getUser().observe(this, username -> {
            Log.d("In onCreate", "Share data: " + username);
        });
    }

}