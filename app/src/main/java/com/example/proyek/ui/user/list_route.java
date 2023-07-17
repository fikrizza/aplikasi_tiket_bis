package com.example.proyek.ui.user;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.proyek.R;
import com.example.proyek.item_route;
import com.example.proyek.object.route;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class list_route extends AppCompatActivity {
    private ListView listRoute;
    ArrayList<route> resRoute;
    private item_route adapter;
    String TAG = "list_route.java";
    String depature, arrival, date;
    TextView depatureText, arrivalText, dateText;

    String id_route, responseDepature, responseArrival, responseTime, responseDate, responsePrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_route);
        listRoute = findViewById(R.id.listRoute);

        getValueIntent();

        ImageView back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void getValueIntent(){
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            depature = extras.getString("depature");
            arrival = extras.getString("arrival");
            date = extras.getString("date");
            depatureText = (TextView) findViewById(R.id.depatureRoute);
            arrivalText = (TextView) findViewById(R.id.arrivalRoute);
            dateText = (TextView) findViewById(R.id.dateRoute);

            depatureText.setText(depature);
            arrivalText.setText(arrival);
            dateText.setText(date);

            searchRoute(depature, arrival, date);
        }
    }

    private void getItemRoute(ArrayList<route> object){
        adapter= new item_route(this, object);
        Log.d(TAG, "Object At getItemRoute :"+object);
        listRoute.setAdapter(adapter);
    }

    private void searchRoute(final String depature,String arrival,String date) {
        String url = "https://overcautious-commis.000webhostapp.com/list.php";
        resRoute = new ArrayList<>();

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONArray respObj = null;
                        try {
                            respObj = new JSONArray(response);
                            for (int i = 0; i < respObj.length(); i++){
                                JSONObject jsonObject = respObj.getJSONObject(i);
                                id_route = jsonObject.getString("id_route");
                                responseDepature = jsonObject.getString("depature");
                                responseArrival = jsonObject.getString("arrival");
                                responseTime = jsonObject.getString("time_depature");
                                responsePrice = jsonObject.getString("price");
                                resRoute.add(new route(id_route, responseDepature, responseArrival, responseTime, responsePrice, date));
                            }
                        } catch (JSONException e) {
                            Toast.makeText(list_route.this, "Route Not Found", Toast.LENGTH_SHORT).show();
//                            throw new RuntimeException(e);
                        }
                        Log.d(TAG, "Object At Response: "+resRoute);
                        getItemRoute(resRoute);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("dashboard.java", "onErrorResponse: Fail to get response = " + error);
                        Toast.makeText(list_route.this, "Fail to get response = " + error, Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("depature", depature);
                params.put("arrival", arrival);
                params.put("date", date);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}