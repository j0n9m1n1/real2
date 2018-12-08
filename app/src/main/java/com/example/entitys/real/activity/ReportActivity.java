package com.example.entitys.real.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.entitys.real.R;
import com.example.entitys.real.fragment.CalendarFragment;
import com.example.entitys.real.fragment.NotifyFragment;
import com.example.entitys.real.fragment.ReportFragment;
import com.example.entitys.real.http.GetRecentPush;
import com.example.entitys.real.http.GetReport;
//import com.example.entitys.real.service.BgService;
import com.example.entitys.real.service.GetReportService;
import com.example.entitys.real.types.Pushs;
import com.example.entitys.real.types.Reports;
import com.example.entitys.real.types.Subjects;
import com.example.entitys.real.util.BackPressCloseHandler;
import com.example.entitys.real.util.JobDispatcherUtils;
import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.RetryStrategy;
import com.firebase.jobdispatcher.Trigger;
import com.google.firebase.FirebaseApp;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;

public class ReportActivity extends AppCompatActivity {
    private Thread thread=null;

    public Handler han = null;
    public ProgressDialog dialog = null;

    public Subjects subject_temp = null;
    public Reports report_temp = null;
    public static ArrayList<Subjects> DataList = null;
    private String response = null;

    public static ArrayList<Pushs> PushList = null;
    public Pushs push_temp = null;

    private BackPressCloseHandler backPressCloseHandler;

    Fragment currentFragment = null;
    Fragment reportFragment = new ReportFragment();
    Fragment calendarFragment = new CalendarFragment();
    Fragment notifyFragment = new NotifyFragment();
    FragmentTransaction ft;

    ViewPager pager;

    private TextView mTextMessage;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_report://ic_bt_nav_report 선택 시
                    currentFragment = reportFragment;//ic_bt_nav_report fragment 삽입

                    switchFragment(currentFragment);

                    return true;
                case R.id.navigation_calendar://ic_bt_nav_calendar 선택 시
                    currentFragment = calendarFragment;//ic_bt_nav_calendar fragment 삽입
                    switchFragment(currentFragment);
                    return true;
                case R.id.navigation_notifications://notifications 선택 시
                    currentFragment = notifyFragment;//notify fragment 삽입
                    switchFragment(currentFragment);
                    return true;
            }
            return false;
        }
    };

    private void switchFragment(Fragment fragment) {
        ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content, fragment);//id가 content인 곳에 fragment 보여줌
        ft.commit();//실행
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);



        dialog = ProgressDialog.show(this, "과제 가져오는중...", "Please wait...", true);
        han = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                dialog.dismiss();
                //시작화면은 ic_bt_nav_report fragment
                ft = getSupportFragmentManager().beginTransaction();
                currentFragment = reportFragment;
                ft.replace(R.id.content, currentFragment);
                ft.commit();

                BottomNavigationView navigation = (BottomNavigationView)
                        findViewById(R.id.navigation); navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


            }
        };

        SharedPreferences settings = getSharedPreferences("setting", 0);

        final String id = settings.getString("id", "");
        final String pw = settings.getString("pw", "");

        DataList = new ArrayList<Subjects>();

        thread = new Thread(){
            @Override
            public void run() {
                Message msg = han.obtainMessage();

                DataList.clear();
                try {
                    response = new GetReport().execute(id, pw).get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                ArrayList<String> sub_names = new ArrayList<String>();


                // Inflate the layout for this fragment
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject jsonObject2 = new JSONObject();
                    JSONObject jsonObject3 = new JSONObject();
                    Iterator subject = jsonObject.keys();

                    while (subject.hasNext()) {
                        sub_names.add((String) subject.next());
                    }

                    for (int i = 0; i < sub_names.size(); i++) {
                        jsonObject2 = (JSONObject) jsonObject.get(sub_names.get(i));
                        subject_temp = new Subjects(sub_names.get(i));

                        for (int j = 0; j < jsonObject2.length(); j++) {
                            jsonObject3 = (JSONObject) jsonObject2.get("과제 " + j);
                            report_temp = new Reports(jsonObject3.get("과제명").toString());

                            for (int k = 0; k < jsonObject3.length(); k++) {
                                report_temp.reportdetail.add(jsonObject3.get("과제명").toString());
                                report_temp.reportdetail.add(jsonObject3.get("제출방식").toString());
                                report_temp.reportdetail.add(jsonObject3.get("게시일").toString());
                                report_temp.reportdetail.add(jsonObject3.get("마감일").toString());
                                report_temp.reportdetail.add(jsonObject3.get("지각제출").toString());
                                report_temp.reportdetail.add(jsonObject3.get("과제내용").toString());
                            }
                            subject_temp.reportgroup.add(report_temp);
                        }
                        DataList.add(subject_temp);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                // GetRecentPush
                try {
                    response = new GetRecentPush().execute(id, pw).get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    System.out.println(jsonObject);
                    PushList = new ArrayList<Pushs>();
                    //System.out.println(jsonObject.length());

                    if (jsonObject.length() == 0) {
                        push_temp = new Pushs("최근알림없음", "최근알림없음", "최근알림없음");
                        PushList.add(push_temp);
                    } else {
                        for (int i = 0; i < jsonObject.length() / 2; i++) {
                            push_temp = new Pushs("과제명", jsonObject.get("제목 " + i).toString(), jsonObject.get("내용 " + i).toString());
                            PushList.add(push_temp);
                            //System.out.println("PUSHLIST : "+PushList.get(i).title+" "+PushList.get(i).item);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                han.sendMessage(msg);
            }
        };
        thread.start();

        scheduleJob(this);

        backPressCloseHandler = new BackPressCloseHandler(this);
    }

    public static void scheduleJob(Context context) {
        //creating new firebase job dispatcher
        FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(new GooglePlayDriver(context));
        //creating new job and adding it with dispatcher
        dispatcher.cancelAll();
        Job job = createJob(dispatcher);
        dispatcher.mustSchedule(job);
    }

    public static Job createJob(FirebaseJobDispatcher dispatcher){

        Job job = dispatcher.newJobBuilder()
                .setLifetime(Lifetime.FOREVER)
                .setService(GetReportService.class)
                //unique id of the task
                .setTag("GetReportService")
                .setReplaceCurrent(false)
                .setRecurring(true)
                // Run between 30 - 60 seconds from now.
                .setTrigger(JobDispatcherUtils.periodicTrigger(36, 10))
                // retry with exponential backoff
                .setRetryStrategy(RetryStrategy.DEFAULT_LINEAR)
                //.setRetryStrategy(RetryStrategy.DEFAULT_EXPONENTIAL)
                //Run this job only when the network is available.
                .setConstraints(Constraint.ON_ANY_NETWORK, Constraint.DEVICE_CHARGING)
                .build();
        return job;
    }
    @Override
    public void onBackPressed() {
//         super.onBackPressed();
         backPressCloseHandler.onBackPressed();
    }

    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

}
