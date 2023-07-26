package com.example.proyek.ui.user;

import static com.example.proyek.login.SHARED_PREFS;
import static com.example.proyek.login.sharedpreferences;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.proyek.R;
import com.example.proyek.item_reservation;
import com.example.proyek.object.reserved;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class reservation extends AppCompatActivity {
    private ListView listReservation;
    private item_reservation adapter;
    String TAG = "reservation.java";
    ArrayList<reserved> arrReserv;
    String id_account;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);

        LinearLayout back = (LinearLayout) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent intent = new Intent(reservation.this, fragment_control.class);
                startActivity(intent);
            }
        });
        listReservation = findViewById(R.id.listReservation);
        sharedpreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        id_account = sharedpreferences.getString("id", null);
        getReservation(id_account);
    }

    private void getItemReserv(ArrayList<reserved> object){
        Log.d(TAG, "getItemReserv: At Item Reserv Obj : "+object);
        adapter= new item_reservation(this, object);
        listReservation.setAdapter(adapter);
    }

    private void getReservation(String id){
        String url = "https://overcautious-commis.000webhostapp.com/getReservation.php";
        arrReserv = new ArrayList<>();

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject respObj = null;
                        try {
                            respObj = new JSONObject(response);
                            JSONArray jsonArray = respObj.getJSONArray("data");
                            for (int i = 0; i < jsonArray.length(); i++){
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String id_reservation = jsonObject.getString("id_reservation");
                                String date = jsonObject.getString("date");
                                String bookCode = jsonObject.getString("bookCode");
                                id_account = jsonObject.getString("id_account");
                                String id_route = jsonObject.getString("id_route");
                                String name = jsonObject.getString("name");
                                String telp = jsonObject.getString("telp");
                                String paymentMethod = jsonObject.getString("paymentMethod");
                                String paymentStatus = jsonObject.getString("paymentStatus");
                                String depature = jsonObject.getString("depature");
                                String arrival = jsonObject.getString("arrival");
                                String timeDepature = jsonObject.getString("time_depature");
                                String dateDepature = jsonObject.getString("date_depature");
                                String price = jsonObject.getString("price");
                                Log.d(TAG, "onResponse: objResp "+jsonObject);

                                arrReserv.add(new reserved(id_reservation, date, bookCode, id_account, id_route,
                                                            name, telp, paymentMethod, paymentStatus, depature, arrival,
                                                            timeDepature, dateDepature, price));
                            }
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                        Log.d(TAG, "onResponse: ArrReserv : "+arrReserv);
                        getItemReserv(arrReserv);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("dashboard.java", "onErrorResponse: Fail to get response = " + error);
                        Toast.makeText(reservation.this, "Fail to get response = " + error, Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("id_account", id);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}