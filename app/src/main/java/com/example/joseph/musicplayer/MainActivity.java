package com.example.joseph.musicplayer;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    BoundService boundService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }


    public void buttonClicked(View v) {
        Button button = (Button) v;
        Intent service = new Intent(MainActivity.this, MusicPlayer.class);
        if (!MusicPlayer.IS_SERVICE_RUNNING) {
            service.setAction("foreground_action");
            MusicPlayer.IS_SERVICE_RUNNING = true;
            button.setText("Click Stop to Stop the Music");
        } /*else {
            service.setAction(Constants.ACTION.STOPFOREGROUND_ACTION);
            MusicPlayer.IS_SERVICE_RUNNING = false;
            button.setText("Start Service");
        }*/
        startService(service);
    }

    public void populate(View view) {
        Intent boundIntent = new Intent(this, BoundService.class);
        bindService(boundIntent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            List<Car> list = new ArrayList<>();
            BoundService.MyBinder myBinder = (BoundService.MyBinder) iBinder;

            boundService = myBinder.getService();
            boundService.initData();
            list = boundService.getCarList();
            RecyclerView recyclerView = findViewById(R.id.recyclerView);

            LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
            RecyclerView.ItemAnimator itemAnimator= new DefaultItemAnimator();
            RecyclerAdapter adapter = new RecyclerAdapter(list, MainActivity.this);

            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setItemAnimator(itemAnimator);




        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };
}
