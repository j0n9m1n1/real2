package com.example.entitys.real.service;

import android.content.SharedPreferences;
import android.os.Handler;
import android.util.Log;

import com.example.entitys.real.http.GetReport;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;

public class GetReportService extends JobService {
    SharedPreferences settings = null;
    String id = null;
    String pw = null;
    private static final String TAG = GetReportService.class.getSimpleName();

    @Override
    public boolean onStartJob(final JobParameters params) {
        //Offloading work to a new thread.
        new Thread(new Runnable() {
            @Override
            public void run() {
                codeYouWantToRun(params);
            }
        }).start();

        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }

    public void codeYouWantToRun(final JobParameters parameters) {

        settings = getSharedPreferences("setting", 0);
        id = settings.getString("id", "");
        pw = settings.getString("pw", "");

        Log.d("Job print","IDIDIDPWPWPW: " + id  + pw);
        new GetReport().execute(id, pw);

        jobFinished(parameters, true);

    }
}


