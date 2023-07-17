package com.example.proyek.ui.user;

import static com.example.proyek.login.SHARED_PREFS;
import static com.example.proyek.login.sharedpreferences;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.proyek.R;
import com.example.proyek.object.route;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class confirm_resevation extends AppCompatActivity {
    String TAG = "confirm_reservation.java";
    String name, telp, id_account, id_route, payment;
    route resObj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_resevation);
        Gson gson = new Gson();
        String intentObj = getIntent().getStringExtra("obj");
        resObj = gson.fromJson(intentObj, route.class);
        name = getIntent().getStringExtra("name");
        telp = getIntent().getStringExtra("telp");
        payment = getIntent().getStringExtra("payment");
        sharedpreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        id_account = sharedpreferences.getString("id", null);
        id_route = resObj.getId_rute();
        Log.d(TAG, "ID : "+id_account+" ID Route : "+id_route+" Name : "+name+" Telp : "+telp+" Payment : "+payment);

        setValue();

        Button buttonReserv = (Button) findViewById(R.id.reservation_button);
        buttonReserv.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                reqResevation();
            }
        });
        Button back = (Button) findViewById(R.id.back_button);
        back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){finish();
            }
        });
    }

    private void setValue() {
        TextView name = findViewById(R.id.nameReservation);
        TextView telp = findViewById(R.id.telpReservation);
        TextView time = findViewById(R.id.timeReservation);
        TextView date = findViewById(R.id.dateReservation);
        TextView payment = findViewById(R.id.paymentReservation);
        TextView nominal = findViewById(R.id.nominalReservation);

        name.setText(getIntent().getStringExtra("name"));
        telp.setText(getIntent().getStringExtra("telp"));
        time.setText(resObj.getTime());
        date.setText(resObj.getDate());
        payment.setText(getIntent().getStringExtra("payment"));
        nominal.setText(resObj.getPrice());
    }


    public void reqResevation(){
        String url = "https://overcautious-commis.000webhostapp.com/reservation.php";

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject respObj = null;
                        try {
                            respObj = new JSONObject(response);
                            Log.d(TAG, "onResponse: getObject : "+respObj);
                            doIntent(respObj.getInt("status"), respObj.getString("message"));
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
//                        Log.d("reservation.java", "onResponse: "+response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, "onErrorResponse: Fail to get response = " + error);
                        Toast.makeText(confirm_resevation.this, "Fail to get response = " + error, Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("id_account", id_account);
                params.put("id_rute", id_route);
                params.put("name", name);
                params.put("telp", telp);
                params.put("payment", payment);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    public void doIntent(int status, String message){
        Log.d(TAG, "doIntent: "+status);
        if (status == 100){
            Intent intent = new Intent(confirm_resevation.this, reservation_success.class);
            finish();
            startActivity(intent);
        }
        else {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        }
    }
}