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
                codeYouWantToRun(params);
            }
        }).start();

        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }

    public void codeYouWantToRun(final JobParameters parameters) {

        settings = getSharedPreferences("setting", 0);

        String id = settings.getString("id", "");
        String pw = settings.getString("pw", "");

        Log.d("JOB TEST","JOB TEST");
        new GetReport().execute(id, pw);
        String channelId = "channel";
        String channelName = "Channel Name";
        Intent intent = new Intent(getApplicationContext(), ReportActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        int requestID = (int) System.currentTimeMillis();
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), requestID, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationManager notiManager = (NotificationManager) getSystemService  (Context.NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {

            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(channelId, channelName, importance);
            notiManager.createNotificationChannel(mChannel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), channelId);
        Intent notificationIntent = new Intent(getApplicationContext(), ReportActivity.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        builder.setContentTitle("JOB TEST")
                .setContentText("JOB TEST")
                .setShowWhen(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setAutoCancel(true)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setSmallIcon(android.R.drawable.btn_star)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_app_logo))
                .setBadgeIconType(R.drawable.ic_app_logo)
                .setContentIntent(pendingIntent);

        notiManager.notify(0, builder.build());
        jobFinished(parameters, true);

    }
}


