package com.example.proyek.ui.user;

import static com.example.proyek.login.SHARED_PREFS;
import static com.example.proyek.login.sharedpreferences;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.proyek.R;
import com.example.proyek.object.route;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import me.mutasem.datetimepickeredittext.DatePickerEditText;

public class dashboard extends Fragment {
    ImageSlider imageSlider;
    AutoCompleteTextView depature, arrival;
    DatePickerEditText inputDate;
    String dateValue;
    ArrayList<String> routeDepature, routeArrival;
    ArrayList<route> resRoute;
    private boolean getListRoute;
    String TAG = "dashboard.java";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dashboard, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setValue(view);

        ArrayList<SlideModel> slideModels = new ArrayList<>();
        // imageList.add(SlideModel("String Url" or R.drawable, "title") You can add title
        slideModels.add(new SlideModel(R.drawable.image1, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.image2, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.image3, ScaleTypes.FIT));
        imageSlider = (ImageSlider) view.findViewById(R.id.image_slider);
        imageSlider.setImageList(slideModels);

        depature = view.findViewById(R.id.inputDepature);
        arrival = view.findViewById(R.id.inputArrival);

        getDepature();
        ArrayAdapter<String> adapterDepature = new ArrayAdapter<String>(getContext(),android.R.layout.simple_dropdown_item_1line,routeDepature);
        depature.setAdapter(adapterDepature);
        depature.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedValue = (String) parent.getItemAtPosition(position);
                getArrival(selectedValue);
                ArrayAdapter<String> adapterArrival = new ArrayAdapter<String>(getContext(),android.R.layout.simple_dropdown_item_1line,routeArrival);
                arrival.setAdapter(adapterArrival);
            }
        });
        getArrival(depature.getText().toString());
        ArrayAdapter<String> adapterArrival = new ArrayAdapter<String>(getContext(),android.R.layout.simple_dropdown_item_1line,routeArrival);
        arrival.setAdapter(adapterArrival);

        inputDate = view.findViewById(R.id.date);
        SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd", Locale.getDefault());
        inputDate.setDateListener(new DatePickerEditText.DateListener() {
            @Override
            public void onDateChange(Calendar date) {
                dateValue = sdf.format(date.getTime());
            }
        });

        Button buttonSearch = view.findViewById(R.id.search_button);
        buttonSearch.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
//                Log.d(TAG, "onViewCreated: "+depature.getText().toString());
                if (checkInput()) {
                    Intent intent = new Intent(getContext(), list_route.class);
                    intent.putExtra("depature", depature.getText().toString());
                    intent.putExtra("arrival", arrival.getText().toString());
                    intent.putExtra("date", dateValue);
                    startActivity(intent);
                }
            }
        });
    }

    private void getArrival(String depature) {
        if (!TextUtils.isEmpty(depature)) {
            getArrivalBased(depature,"https://overcautious-commis.000webhostapp.com/getArrivalBasedDepature.php");
        }else{
            getArrivalBased(depature,"https://overcautious-commis.000webhostapp.com/getArrival.php");
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        checkSession();
    }

    private void checkSession() {
        sharedpreferences = getContext().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        String email = sharedpreferences.getString("EMAIL_KEY", null);
        String password = sharedpreferences.getString("PASSWORD_KEY", null);
        if (email != null && password != null) {
            Intent i = new Intent(getContext(), fragment_control.class);
            startActivity(i);
        }
    }

    private void setValue(View view){
        sharedpreferences = getContext().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        String name = sharedpreferences.getString("name", null);
        TextView nameUs = view.findViewById(R.id.nameUser);
        nameUs.setText(name);
    }

    private boolean checkInput(){
        if (!TextUtils.isEmpty(depature.getText().toString()) && !TextUtils.isEmpty(arrival.getText().toString()))
            if (dateValue != null)
                return true;
        return false;
    }

    private void getDepature(){
        String url = "https://overcautious-commis.000webhostapp.com/getDepature.php";
        routeDepature = new ArrayList<>();

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject respObj = null;
                        try {
                            respObj = new JSONObject(response);
                            JSONArray respArr = respObj.getJSONArray("data");
                            for (int i = 0; i < respArr.length(); i++){
                                JSONObject objectDepature = respArr.getJSONObject(i);
                                String temp = objectDepature.getString("depature");
                                routeDepature.add(temp);
                            }
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                        Log.d("dashboard.java", "onResponse: Hasil Import Depature : "+routeDepature);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("dashboard.java", "onErrorResponse: Fail to get response = " + error);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("value", "1");
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void getArrivalBased(final String depature, String url) {
        routeArrival = new ArrayList<>();

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject respObj = null;
                        try {
                            respObj = new JSONObject(response);
                            JSONArray respArr = respObj.getJSONArray("data");
                            for (int i = 0; i < respArr.length(); i++){
                                JSONObject objectDepature = respArr.getJSONObject(i);
                                String temp = objectDepature.getString("arrival");
                                routeArrival.add(temp);
                            }
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                        Log.d("dashboard.java", "onResponse: Hasil Import Arrival : "+routeArrival);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "Fail to get response = " + error, Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("depature", depature);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}