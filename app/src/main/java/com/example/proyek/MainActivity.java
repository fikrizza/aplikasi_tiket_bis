package com.example.proyek;

import static com.example.proyek.login.SHARED_PREFS;
import static com.example.proyek.login.sharedpreferences;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.proyek.ui.user.fragment_control;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkSession();
        setContentView(R.layout.activity_main);

        Button buttonLogin = (Button) findViewById(R.id.button_next);
        buttonLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this, login.class);
                startActivity(intent);
            }
        });
    }

    private void checkSession() {
        sharedpreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        String email = sharedpreferences.getString("email", null);
        String password = sharedpreferences.getString("password", null);
        Log.d("mainActivity.java", "checkSession: email : "+email+" Password : "+password);
        if (email != null && password != null) {
            Intent i = new Intent(MainActivity.this, fragment_control.class);
            startActivity(i);
        }
    }
}