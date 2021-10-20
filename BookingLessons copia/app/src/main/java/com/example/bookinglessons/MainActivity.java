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

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    String usernameOfLoggedUser;
    Bundle extras;
    private UserViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        extras = getIntent().getExtras();
        usernameOfLoggedUser = extras.getString("key-username", "NoValue");
        showWelcomeToast(usernameOfLoggedUser);
        setViewModelUser(usernameOfLoggedUser);
        setupUIElements();

    }

    private void showWelcomeToast(String username) {
        Toast toast = Toast.makeText(getApplicationContext(), "Hello " + username, Toast.LENGTH_SHORT*2);
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

    private void setViewModelUser(String usernameOfLoggedUser) {
        viewModel = new ViewModelProvider(this).get(UserViewModel.class);
        viewModel.setUser(usernameOfLoggedUser);
        viewModel.getUser().observe(this, username -> {
            Log.d("In onCreate", "Share data: " + username);
        });
    }

}