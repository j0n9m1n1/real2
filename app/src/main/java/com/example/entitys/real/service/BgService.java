package com.example.entitys.real.service;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

public class BgService extends Service {
    SharedPreferences settings = null;

    String id = null;
    String pw = null;
    @Override
    public IBinder onBind(Intent intent) {
        // Service 객체와 (화면단 Activity 사이에서)
        // 통신(데이터를 주고받을) 때 사용하는 메서드
        // 데이터를 전달할 필요가 없으면 return null;

        return null;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        Notification notification = new Notification();
        startForeground(1, notification);

        settings = getSharedPreferences("setting", 0);
        id = settings.getString("id", "");
        pw = settings.getString("pw", "");

        System.out.println("IDIDIDPWPWPW: " + id  + pw);

        TimerTask tt = new TimerTask(){
            @Override
            public void run() {
                System.out.println("ID : "+id);
                System.out.println("PW : "+pw);
                Log.d("SERVICE TEST", " " + id);
                Log.d("SERVICE TEST", " " + pw);
//                try {
//                    new GetReport().execute(id, pw).get();
//                } catch (ExecutionException e) {
//                    e.printStackTrace();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
            }
        };
        Timer timer = new Timer();
        timer.schedule(tt, 0, 3000); // ms
//        timer.schedule(tt, 0, 60 * 60 * 1000); // ms

    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // 서비스가 호출될 때마다 실행
        System.out.println("start command");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // 서비스가 종료될 때 실행

        Log.d("test", "서비스의 onDestroy");
    }

}
