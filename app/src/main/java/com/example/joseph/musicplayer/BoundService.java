package com.example.joseph.musicplayer;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by joseph on 10/10/17.
 */

public class BoundService extends Service {

    IBinder iBinder = new MyBinder();
    List<Car> carList;

    public BoundService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return iBinder;
    }

    public class MyBinder extends Binder {
        public BoundService getService() {
            return BoundService.this;
        }
    }

    public void initData() {
        carList = new ArrayList<>();
        carList.add(new Car("dodge", "ram", "2017" ));
        carList.add(new Car("ford", "mustang", "2017" ));
        carList.add(new Car("Honda", "accord", "2017" ));
        carList.add(new Car("Honda", "accord", "2017" ));
        carList.add(new Car("Honda", "accord", "2017" ));
        carList.add(new Car("Honda", "accord", "2017" ));
        carList.add(new Car("Honda", "accord", "2017" ));
        carList.add(new Car("Honda", "accord", "2017" ));
        carList.add(new Car("Honda", "accord", "2017" ));
        carList.add(new Car("Honda", "accord", "2017" ));
        carList.add(new Car("Honda", "accord", "2017" ));
        carList.add(new Car("Honda", "accord", "2017" ));
        carList.add(new Car("Honda", "accord", "2017" ));
    }

    public List<Car> getCarList() {
        return carList;
    }

    public boolean addCar(Car car) {
        return carList.add(car);
    }

}
