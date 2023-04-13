package com.example.busticketing;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
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

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class register extends AppCompatActivity {
    private Button register;
    private EditText name;
    private EditText email;
    private EditText password;
    private EditText confirmPassword;
    private CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        name = (EditText) findViewById(R.id.name);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        confirmPassword = (EditText) findViewById(R.id.confirm_password);
        checkBox = (CheckBox) findViewById(R.id.checkbox);

        register = (Button) findViewById(R.id.register_button);
        register.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if (checked()){
                    Log.d("register.java", "onClick: "+email.getText().toString()+": "+password.getText().toString()+": "+name.getText().toString());
                    addAccount(email.getText().toString(),password.getText().toString(),name.getText().toString());
                }
            }
        });
    }

    protected void addAccount(String pasEmail, String pasPassword, String pasName){
        String url = "https://overcautious-commis.000webhostapp.com/register.php";
//        String url = "https://10.0.2.2:80/rpl/register.php";

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("register.java", "onResponse: Masuk onResponse");
                //This code is executed if the server responds, whether or not the response contains data.
                //The String 'response' contains the server's response.
                try {
                    JSONObject respObj = new JSONObject(response);

                    //String message = respObj.getString("message");
                    Log.d("Register.java", "onResponse: "+response);
                    Toast.makeText(register.this, "Register Success", Toast.LENGTH_SHORT).show();
                }
                catch (JSONException e){
                    e.printStackTrace();
                    Log.d("Register.java", "onResponse: "+e);
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

                params.put("email", pasEmail);
                params.put("password", pasPassword);
                params.put("name", pasName);
                Log.d("Register.java", "getParams: "+params);
                return params;
            }
        };

        requestQueue.add(MyStringRequest);
    }

    protected boolean checked(){
        if (inputCheck()){
            if (checkBox.isChecked()){
                if(password.getText().toString().equals(confirmPassword.getText().toString())){
                    return true;
                }else{
                    Toast.makeText(this, "Please Check Your Password", Toast.LENGTH_SHORT).show();
                }
            }
            else {
                Toast.makeText(this, "Please Checked Agreement and Privacy Policy", Toast.LENGTH_SHORT).show();
            }
        }
        return false;
    }

    protected boolean inputCheck(){
        if (!TextUtils.isEmpty(name.getText().toString()))
            if (!TextUtils.isEmpty(email.getText().toString()))
                if (isEmailValid(email.getText().toString())){
                    if (!TextUtils.isEmpty(password.getText().toString()))
                        return true;
                }else{
                    Toast.makeText(this, "Your E-mail Not Valid", Toast.LENGTH_SHORT).show();
                }
        Toast.makeText(this, "Please Check Your Input", Toast.LENGTH_SHORT).show();
        return false;
    }

    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}