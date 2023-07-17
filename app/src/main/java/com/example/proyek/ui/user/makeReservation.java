package com.example.proyek.ui.user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.proyek.R;
import com.example.proyek.object.route;
import com.google.gson.Gson;

public class makeReservation extends AppCompatActivity {
    private EditText inputAccount;
    private EditText inputRoute;
    private EditText inputName;
    private EditText inputTelp;
    private Button buttonNext;
    String TAG = "reservation.java";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_reservation);

        Gson gson = new Gson();
        String strObj = getIntent().getStringExtra("obj");
        route obj = gson.fromJson(strObj, route.class);
        Log.d(TAG, "onCreate: Intent Object Get : "+obj);

        inputName = (EditText)findViewById(R.id.name);
        inputTelp = (EditText)findViewById(R.id.telp);

        Spinner spinnerPayment = (Spinner) this.findViewById(R.id.payment);
        ArrayAdapter adapterPayment = ArrayAdapter.createFromResource(this,
                R.array.payment, R.layout.spinner_item);
        adapterPayment.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinnerPayment.setAdapter(adapterPayment);
        //Get value

        buttonNext = (Button) findViewById(R.id.button_login);
        buttonNext.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if (inputChekced()){
                    Gson gson = new Gson();
                    Intent intent = new Intent(makeReservation.this, confirm_resevation.class);
                    intent.putExtra("obj", gson.toJson(obj));
                    intent.putExtra("name", inputName.getText().toString());
                    intent.putExtra("telp", inputTelp.getText().toString());
                    intent.putExtra("payment", spinnerPayment.getSelectedItem().toString());
                    startActivity(intent);
                }
            }
        });
        ImageView back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    protected boolean inputChekced(){
        boolean name = !TextUtils.isEmpty(inputName.getText().toString());
        boolean telp = !TextUtils.isEmpty(inputTelp.getText().toString());
            if (name && telp){
                Log.d("reservation.java", "inputChekced: Input Get Checked");
                return true;
            }
            Toast.makeText(this, "Please Check Your Input", Toast.LENGTH_SHORT).show();
        return false;
    }
}