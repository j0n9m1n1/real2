package com.example.entitys.real.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.entitys.real.R;
import com.example.entitys.real.activity.ReportActivity;
import com.example.entitys.real.http.GetReport;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;

public class GetReportService extends JobService {

    SharedPreferences settings = null;
    private static final String TAG = GetReportService.class.getSimpleName();

    @Override
    public boolean onStartJob(final JobParameters params) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                getReport(params);
            }
        }).start();

        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }

    public void getReport(final JobParameters parameters) {

        settings = getSharedPreferences("setting", 0);

        String id = settings.getString("id", "");
        String pw = settings.getString("pw", "");

        new GetReport().execute(id, pw);

        jobFinished(parameters, true);

    }
}


