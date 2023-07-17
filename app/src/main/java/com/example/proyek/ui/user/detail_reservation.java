package com.example.proyek.ui.user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.proyek.R;
import com.example.proyek.object.reserved;
import com.example.proyek.object.route;
import com.google.gson.Gson;

public class detail_reservation extends AppCompatActivity {
    String TAG = "detail_reservaion.java";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_reservation);

        Gson gson = new Gson();
        String strObj = getIntent().getStringExtra("obj");
        reserved obj = gson.fromJson(strObj, reserved.class);
        Log.d(TAG, "onCreate: Intent Object Get : "+obj);

        LinearLayout back = (LinearLayout) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        setValue(obj);
    }

    private void setValue(reserved obj){
        TextView name = findViewById(R.id.name);
        TextView telp = findViewById(R.id.telp);
        TextView time = findViewById(R.id.time);
        TextView date = findViewById(R.id.date);
        TextView paymentMethod = findViewById(R.id.paymentMethod);
        TextView paymentStatus = findViewById(R.id.paymentStatus);
        TextView nominalPayment = findViewById(R.id.nominalPayment);

        name.setText(obj.getName());
        telp.setText(obj.getTelp());
        time.setText(obj.getTimeDepature());
        date.setText(obj.getDateDepature());
        paymentMethod.setText(obj.getPaymentMethod());
        paymentStatus.setText(getStatus(obj.getPaymentStatus()));
        nominalPayment.setText(obj.getPrice());
    }
    private String getStatus(String status){
        if (status == "1")
            return "Done";
        return "Not Payment Yet";
    }
}