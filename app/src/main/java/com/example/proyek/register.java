package com.example.proyek;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

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
                    addAccount(email.getText().toString(),password.getText().toString(),name.getText().toString());
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
    }

    protected void addAccount(String pasEmail, String pasPassword, String pasName){
        String url = "https://overcautious-commis.000webhostapp.com/register.php";
        //Code Dibawah Digunakan untuk local host
//        String url = "https://10.0.2.2:80/rpl/register.php";

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject respObj = new JSONObject(response);
                    Log.d("Register.java", "onResponse: "+response);
                    Toast.makeText(register.this, "Register Success", Toast.LENGTH_SHORT).show();
                    finish();
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
                return params;
            }
        };

        requestQueue.add(MyStringRequest);
    }
    protected boolean checked(){
        String pass = password.getText().toString();
        String confPass = confirmPassword.getText().toString();

        if (inputCheck() && checkBox.isChecked()){
            if(pass.equals(confPass)){
                return true;
            }else{
                Toast.makeText(this, "Please Check Your Password", Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(this, "Please Checked Agreement and Privacy Policy", Toast.LENGTH_SHORT).show();
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