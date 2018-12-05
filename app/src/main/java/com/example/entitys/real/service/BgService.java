package com.example.entitys.real.service;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

import com.example.entitys.real.R;
import com.example.entitys.real.activity.LoginActivity;
import com.example.entitys.real.activity.ReportActivity;
import com.example.entitys.real.http.GetRecentPush;
import com.example.entitys.real.http.GetReport;
import com.example.entitys.real.types.Pushs;
import com.example.entitys.real.types.Reports;
import com.example.entitys.real.types.Subjects;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;

public class BgService extends Service {
    String id = null;
    String pw = null;
    @Override
    public IBinder onBind(Intent intent) {
        // Service 객체와 (화면단 Activity 사이에서)
        // 통신(데이터를 주고받을) 때 사용하는 메서드
        // 데이터를 전달할 필요가 없으면 return null;
        id = intent.getStringExtra("id");
        pw = intent.getStringExtra("pw");
        System.out.println("IDIDIDPWPWPW: " + id  + pw);
        return null;
    }
    @Override
    public void onCreate() {
        super.onCreate();



        TimerTask tt = new TimerTask(){
            @Override
            public void run() {
                Log.d("SERVICE TEST", "qwe" + id);
                Log.d("SERVICE TEST", "qwe" + pw);
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


        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // 서비스가 종료될 때 실행

        Log.d("test", "서비스의 onDestroy");
    }

}
