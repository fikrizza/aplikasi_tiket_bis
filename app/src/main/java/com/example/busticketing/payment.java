package com.example.busticketing;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.aplikasi_tiket_bis.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class payment extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
    }
    protected void addReservation(String date) {
        String url = "https://overcautious-commis.000webhostapp.com/payment.php";
        //Untuk Akses melalui Emu
//        String url = "https://10.0.2.2:80/rpl/payment.php";

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
                    Toast.makeText(payment.this, "Register Success", Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d("payment.java", "onResponse: " + e);
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