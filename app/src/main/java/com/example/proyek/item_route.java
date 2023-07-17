package com.example.proyek;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.proyek.object.route;
import com.example.proyek.ui.user.makeReservation;
import com.google.gson.Gson;

import java.util.ArrayList;

public class item_route extends ArrayAdapter<route> {
    Context context;
    private ArrayList<route> dataSet;
    String TAG = "item_route.java";

    public item_route(Context context, ArrayList<route> data) {
        super(context, R.layout.list_view_layout, data);
        this.dataSet = data;
        this.context = context;
    }

    private static class ViewHolder {
        TextView depature;
        TextView arrival;
        TextView date;
        TextView price;
        TextView time;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        route dataModel = getItem(i);
        ViewHolder viewHolder;
        if (view == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            view = layoutInflater.inflate(R.layout.list_view_layout, null);
            viewHolder = new ViewHolder();
            viewHolder.depature = (TextView) view.findViewById(R.id.itemDepature);
            viewHolder.arrival = (TextView) view.findViewById(R.id.itemArrival);
            viewHolder.date = (TextView) view.findViewById(R.id.itemDate);
            viewHolder.price = (TextView) view.findViewById(R.id.price);
            viewHolder.time = (TextView) view.findViewById(R.id.time);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.depature.setText(dataModel.getDepature());
        viewHolder.arrival.setText(dataModel.getArrival());
        viewHolder.date.setText(dataModel.getDate());
        viewHolder.price.setText(dataModel.getPrice());
        viewHolder.time.setText(dataModel.getTime());

        Button editButton = (Button) view.findViewById(R.id.selectedButton);

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(getContext(), update_admin.class);
//                intent.putExtra("idReservation",dataModel.getIdReservation());
//                intent.putExtra("medicRecord",dataModel.getMedicRecord());
//                context.startActivity(intent);
                Gson gson = new Gson();
                Intent intent = new Intent(getContext(), makeReservation.class);
                intent.putExtra("obj", gson.toJson(dataModel));
                context.startActivity(intent);
            }
        });
        // Return the completed view to render on screen
        return view;
    }
}
