package com.example.busticketing;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class dashboard extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;

    home homeFragment = new home();
    MapsFragment mpsfrgament = new MapsFragment();
    profile proffragment = new profile();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        bottomNavigationView = findViewById(R.id.botom_navigation);
        getSupportFragmentManager().beginTransaction().replace(R.id.dashboard_container,homeFragment).commit();

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.navigation_home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.dashboard_container,homeFragment).commit();
                        return true;
                    case R.id.navigation_maps:
                        getSupportFragmentManager().beginTransaction().replace(R.id.dashboard_container,mpsfrgament).commit();
                        return true;
                    case R.id.navigation_profile:
                        getSupportFragmentManager().beginTransaction().replace(R.id.dashboard_container,proffragment).commit();
                        return true;
                }

                return false;
            }
        });
    }
}