package com.example.entitys.real.activity;

import android.os.Bundle;
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
import com.example.entitys.real.util.BackPressCloseHandler;
import com.google.firebase.FirebaseApp;

public class ReportActivity extends AppCompatActivity {

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
                case R.id.navigation_report://report 선택 시
                    currentFragment = reportFragment;//report fragment 삽입
                    switchFragment(currentFragment);
                    return true;
                case R.id.navigation_calendar://calendar 선택 시
                    currentFragment = calendarFragment;//calendar fragment 삽입
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

        //시작화면은 report fragment
        ft = getSupportFragmentManager().beginTransaction();
        currentFragment = reportFragment;
        ft.replace(R.id.content, currentFragment);
        ft.commit();

        BottomNavigationView navigation = (BottomNavigationView)
                findViewById(R.id.navigation); navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        backPressCloseHandler = new BackPressCloseHandler(this);
        FirebaseApp.initializeApp(this);
    }

    @Override
    public void onBackPressed() {
//         super.onBackPressed();
         backPressCloseHandler.onBackPressed();
    }
}
