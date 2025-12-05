package com.example.maket;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.example.maket.R;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivity extends AppCompatActivity {
    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host);
        NavController navController = navHostFragment.getNavController();


        AppBarConfiguration appBarConfig = new AppBarConfiguration.Builder(
                R.id.catalogFragment, R.id.categoriesFragment, R.id.favoritesFragment,
                R.id.complaintFragment, R.id.profileFragment
        ).build();
        NavigationUI.setupWithNavController(toolbar, navController, appBarConfig);


        BottomNavigationView bottom = findViewById(R.id.bottom_bar);
        NavigationUI.setupWithNavController(bottom, navController);
    }
}
