package com.example.proyek.ui.user;

import static com.example.proyek.login.SHARED_PREFS;
import static com.example.proyek.login.sharedpreferences;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.proyek.R;
import com.example.proyek.login;

public class profile extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setValue(view);

        LinearLayout logout = (LinearLayout) view.findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.clear();
                editor.apply();

                Intent intent = new Intent(getContext(), login.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
        LinearLayout resButton = (LinearLayout) view.findViewById(R.id.layoutReservation);
        resButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getContext(), reservation.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

    }

    private void setValue(View view){
        sharedpreferences = getContext().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        String name = sharedpreferences.getString("name", null);
        String email = sharedpreferences.getString("email", null);
        TextView nameUs = view.findViewById(R.id.profileName);
        TextView profileEmail = view.findViewById(R.id.profileEmail);
        nameUs.setText(name);
        profileEmail.setText(email);
        Log.d("profile.java", "setValue: email : "+email+" name : "+name);
    }

}