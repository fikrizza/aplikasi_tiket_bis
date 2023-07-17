package com.example.proyek.ui.user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.proyek.R;

public class reservation_success extends AppCompatActivity {
    Button buttonDashboard;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_success);

        buttonDashboard = (Button) findViewById(R.id.button_dashboard);
        buttonDashboard.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(reservation_success.this, fragment_control.class);
                startActivity(intent);
            }
        });
    }
}