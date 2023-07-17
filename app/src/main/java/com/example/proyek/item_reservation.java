package com.example.proyek;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.proyek.object.reserved;
import com.example.proyek.ui.user.detail_reservation;
import com.google.gson.Gson;

import java.util.ArrayList;

public class item_reservation extends ArrayAdapter<reserved> {
    private ArrayList<reserved> dataSet;
    Context context;
    private static class ViewHolder {
        TextView name;
        TextView depature;
        TextView arrival;
        TextView date;
        TextView time;
        TextView bookCode;
    }

    public item_reservation(Context context, ArrayList<reserved> data) {
        super(context, R.layout.list_view_reservation, data);
        this.dataSet = data;
        this.context = context;
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        reserved dataModel = getItem(i);
        item_reservation.ViewHolder viewHolder;
        if (view == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            view = layoutInflater.inflate(R.layout.list_view_reservation, null);
            viewHolder = new item_reservation.ViewHolder();
            viewHolder.depature = (TextView) view.findViewById(R.id.depature);
            viewHolder.arrival = (TextView) view.findViewById(R.id.arrival);
            viewHolder.date = (TextView) view.findViewById(R.id.date);
            viewHolder.time = (TextView) view.findViewById(R.id.time);
            viewHolder.name = (TextView) view.findViewById(R.id.name);
            viewHolder.bookCode = (TextView) view.findViewById(R.id.bookCode);
            view.setTag(viewHolder);
        } else {
            viewHolder = (item_reservation.ViewHolder) view.getTag();
        }

        viewHolder.name.setText(dataModel.getName());
        viewHolder.depature.setText(dataModel.getDepature());
        viewHolder.arrival.setText(dataModel.getArrival());
        viewHolder.date.setText(dataModel.getDateDepature());
        viewHolder.time.setText(dataModel.getTimeDepature());
        viewHolder.bookCode.setText(dataModel.getBookCode());

        Button editButton = (Button) view.findViewById(R.id.selectedButton);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(getContext(), update_admin.class);
//                intent.putExtra("idReservation",dataModel.getIdReservation());
//                intent.putExtra("medicRecord",dataModel.getMedicRecord());
//                context.startActivity(intent);
                Gson gson = new Gson();
                Intent intent = new Intent(getContext(), detail_reservation.class);
                intent.putExtra("obj", gson.toJson(dataModel));
                context.startActivity(intent);
            }
        });
        return view;
    }
}
