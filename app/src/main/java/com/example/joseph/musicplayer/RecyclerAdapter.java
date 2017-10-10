package com.example.joseph.musicplayer;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.SystemClock;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import static android.content.Context.ALARM_SERVICE;

import java.util.List;

/**
 * Created by joseph on 10/10/17.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    List<Car> carList;
    static Intent serviceIntent;
    Context context;

    public RecyclerAdapter(List<Car> carList, Context context) {
        this.carList = carList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.make.setText(carList.get(position).getMake());
        holder.model.setText(carList.get(position).getModel());
        holder.year.setText(carList.get(position).getYear());

    }

    @Override
    public int getItemCount() {
        return carList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView make;
        TextView model;
        TextView year;

        public ViewHolder(final View itemView) {
            super(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                @Override
                public void onClick(View view) {
                    serviceIntent = new Intent(context, MusicPlayer.class);
                    serviceIntent.setAction("car_action");
                    serviceIntent.putExtra("car", "Car " + itemView.findViewById(R.id.make).toString());

                    PendingIntent pendingIntent = PendingIntent.getService(context, 100, serviceIntent,
                            PendingIntent.FLAG_UPDATE_CURRENT);

                    AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
                    alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + 10000, pendingIntent);
                }
            });

            make = itemView.findViewById(R.id.make);
            model = itemView.findViewById(R.id.model);
            year = itemView.findViewById(R.id.year);

        }

    }

}
