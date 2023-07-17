package com.example.proyek.ui.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.proyek.R;
import com.example.proyek.ui.user.dashboard;
import com.example.proyek.ui.user.profile;
import com.example.proyek.ui.user.ticket_menu;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class fragment_control extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_control);

        dashboard dashboardFragment = new dashboard();
        ticket_menu ticketFragment = new ticket_menu();
        profile profileFragment = new profile();

        bottomNavigationView = findViewById(R.id.botom_navigation);
        getSupportFragmentManager().beginTransaction().replace(R.id.dashboard_container,dashboardFragment).commit();

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.navigation_home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.dashboard_container,dashboardFragment).commit();
                        return true;
                    case R.id.navigation_ticket:
                        getSupportFragmentManager().beginTransaction().replace(R.id.dashboard_container,ticketFragment).commit();
                        return true;
                    case R.id.navigation_profile:
                        getSupportFragmentManager().beginTransaction().replace(R.id.dashboard_container,profileFragment).commit();
                        return true;
                }

                return false;
            }
        });
    }
}