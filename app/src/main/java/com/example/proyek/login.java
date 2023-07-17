package com.example.proyek;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.proyek.ui.user.fragment_control;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class login extends AppCompatActivity {
    private EditText inputEmail;
    private EditText inputPass;
    private Button buttonLogin;
    private TextView register;

    // variable for shared preferences.
    public static SharedPreferences sharedpreferences;
    String email, password, accessId,name;
    // creating constant keys for shared preferences.
    public static String SHARED_PREFS = "shared_prefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        checkSession();

        buttonLogin = (Button) findViewById(R.id.button_login);
        inputEmail = (EditText) findViewById(R.id.emailInput);
        inputPass = (EditText)findViewById(R.id.passwordInput);
        buttonLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if (inputChekced()){
                    reqLogin(inputEmail.getText().toString(), inputPass.getText().toString());
                }
            }
        });
        LinearLayout back = (LinearLayout) findViewById(R.id.back_layout);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        register = (TextView)findViewById(R.id.register);
        register.setOnClickListener(v ->{
            Intent intent = new Intent(this, register.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        checkSession();
    }

    protected void reqLogin(String username, String password){
        String url = "https://overcautious-commis.000webhostapp.com/login.php";

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject respObj = null;
                        try {
                            respObj = new JSONObject(response);
                            String message = respObj.getString("message");
                            if (message.equals("Success")) {
                                JSONArray arrayObject = respObj.getJSONArray("data");
                                Log.d("login.java", "onResponse: "+respObj);
                                setSession(arrayObject);
                                Log.d("login.java", "onResponse: Login Success");
                                Intent intent = new Intent(login.this, fragment_control.class);
                                startActivity(intent);
                                finish();
                            }else{
                                Log.d("login.java", "onResponse: Login Failed");
                                Toast.makeText(login.this, message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Login.java", "onErrorResponse: Fail to get response = " + error);
                        Toast.makeText(login.this, "Fail to get response = " + error, Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("username", username);
                params.put("password", password);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    protected boolean inputChekced(){
        boolean email = !TextUtils.isEmpty(inputEmail.getText().toString());
        boolean pass = !TextUtils.isEmpty(inputPass.getText().toString());
        if (email && pass)
            return true;
        Toast.makeText(this, "Please Check Your Input", Toast.LENGTH_SHORT).show();
        return false;
    }

    protected void setSession(JSONArray object){
        SharedPreferences.Editor editor = sharedpreferences.edit();
        for (int i = 0; i < object.length(); i++){
            try {
                JSONObject jsonObject = object.getJSONObject(i);
                editor.putString("id", jsonObject.getString("id"));
                editor.putString("email", jsonObject.getString("email"));
                editor.putString("name", jsonObject.getString("name"));
                editor.putString("accessId", jsonObject.getString("accessId"));
                editor.putString("password", jsonObject.getString("password"));
                editor.apply();
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
        // to save data with key and value.
        editor.apply();
    }
    private void checkSession() {
        // getting the data which is stored in shared preferences.
        sharedpreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        email = sharedpreferences.getString("email", null);
        password = sharedpreferences.getString("password", null);
        accessId = sharedpreferences.getString("accessId", null);
        name = sharedpreferences.getString("name", null);
        Log.d("login.java", "checkSession: email : "+email+" name : "+name+" Password : "+password+" AccessID : "+accessId);
        if (email != null && password != null) {
            Intent i = new Intent(login.this, fragment_control.class);
            startActivity(i);
            finish();
        }
    }
}