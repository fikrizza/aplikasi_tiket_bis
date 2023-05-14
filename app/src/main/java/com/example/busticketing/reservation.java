package com.example.busticketing;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class reservation extends AppCompatActivity {
    DataHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);

        EditText inputdate = (EditText) findViewById(R.id.##);
    }

    protected String generateCode(){
        Random generator = new Random();
        StringBuilder randomStringBuilder = new StringBuilder();

        int randomLength = generator.nextInt(8);
        char tempChar;

        for (int i = 0; i < randomLength; i++){
            tempChar = (char) (generator.nextInt(96) + 32);
            randomStringBuilder.append(tempChar);
        }
        return randomStringBuilder.toString();
    }

    protected String currentSession(){
        String sessionId = null;
        dbHelper = new DataHelper(this);

        SQLiteDatabase read = dbHelper.getReadableDatabase();

        Cursor cursor = read.rawQuery("SELECT * FROM sesi",null);
        cursor.moveToFirst();

        if (cursor.getCount() > 0 && cursor.getCount() <= 1) {
            sessionId = cursor.getString(cursor.getColumnIndex("idUser"));
            Log.d("reservation.java", "id  : "+cursor.getString(cursor.getColumnIndex("idUser")));
        }
        else{
            Intent intent = new Intent(this, login.class);
            startActivity(intent);
            finish();
        }
        cursor.close();

        return sessionId;
    }

    protected String getIdRute(){
        String idRute;

        Bundle extras = getIntent().getExtras();
        idRute = extras.getString("idRute");

        return idRute;
    };

    protected String dateFormat(String date){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateFormated = null;
        try {
            dateFormated = String.valueOf(dateFormat.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateFormated;
    }


    protected void addReservation(String date) {
        String url = "https://overcautious-commis.000webhostapp.com/reservation.php";
        //Untuk Akses melalui Emu
//        String url = "https://10.0.2.2:80/rpl/reservation.php";

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //This code is executed if the server responds, whether or not the response contains data.
                //The String 'response' contains the server's response.
                try {
                    JSONObject respObj = new JSONObject(response);

                    //String message = respObj.getString("message");
                    Log.d("reservation.java", "onResponse: " + respObj);
                    Toast.makeText(reservation.this, "Register Success", Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d("reservation.java", "onResponse: " + e);
                }
            }
        }, new Response.ErrorListener() { //Create an error listener to handle errors appropriately.
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Volley Error", Toast.LENGTH_SHORT).show();
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("date", dateFormat(date));
                params.put("bookCode", generateCode());
                params.put("id_account", currentSession());
                params.put("id_rute", getIdRute());
                Log.d("reservation.java", "getParams: " + params);
                return params;
            }
        };

        requestQueue.add(MyStringRequest);
    }
}

// Pass Var Use Intent
//    Intent intent = new Intent(healthQueue.this, confirm_reservation.class);
//                    intent.putExtra("hospital",hospitalName);
//                            intent.putExtra("clinic",clinicName);
//                            intent.putExtra("assurance",assuranceName);
//                            intent.putExtra("dateReservation",inputdate.getText().toString());
//                            intent.putExtra("medicRecord",medicrecord.getText().toString());
//                            startActivity(intent);

//Get Var Use Intent
//Bundle extras = getIntent().getExtras();
//        if (extras != null) {
//                hospital = extras.getString("hospital");
//                clinic = extras.getString("clinic");
//                assurance = extras.getString("assurance");
//                dateReservation = extras.getString("dateReservation");
//                medicRecord = extras.getString("medicRecord");
//                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//                try {
//                date_var = dateFormat.parse(dateReservation);
//                } catch (ParseException e) {
//                e.printStackTrace();
//                }
//                }

