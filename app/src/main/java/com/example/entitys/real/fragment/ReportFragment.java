package com.example.entitys.real.fragment;
import com.example.entitys.real.activity.HelpActivtiy;
import com.example.entitys.real.activity.LoginActivity;
import com.example.entitys.real.activity.ReportActivity;
import com.example.entitys.real.http.GetRecentPush;
import com.example.entitys.real.http.GetReport;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.example.entitys.real.R;
import com.example.entitys.real.activity.ReportDetailActivity;
import com.example.entitys.real.types.Pushs;
import com.example.entitys.real.types.Reports;
import com.example.entitys.real.types.Subjects;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ExecutionException;

public class ReportFragment extends Fragment {

    private ExpandableListView listView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.actionbar_actions, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int cid = item.getItemId();

        if(cid == R.id.refresh){

            Intent in = new Intent(getContext(), ReportActivity.class);
            startActivity(in);
            return true;
        }

        if(cid == R.id.logout){

            Log.d("clicked", "logout");
            Intent in = new Intent(getContext(), LoginActivity.class);
            startActivity(in);
            return true;
        }

        if(cid == R.id.help){

            Intent intent = new Intent(getActivity(), HelpActivtiy.class);
            startActivity(intent);
            Log.d("clicked", "help");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        getActivity().setTitle("과제");

        View inf = inflater.inflate(R.layout.fragment_report, container, false);
        listView = (ExpandableListView)inf.findViewById(R.id.expandableListView);

        ExpandAdapter adapter = new ExpandAdapter(getActivity(), R.layout.report_title, R.layout.report_item, ReportActivity.DataList);
        //listView.setIndicatorBounds(width-50, width); //이 코드를 지우면 화살표 위치가 바뀐다.
        listView.setAdapter(adapter);

        listView.setOnChildClickListener(new ExpandableListView.OnChildClickListener(){
            // TODO ExpandableListView setClickEvent
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Intent intent = new Intent(getActivity(), ReportDetailActivity.class);
                intent.putExtra("groupPosition", groupPosition);
                intent.putExtra("childPosition", childPosition);
                startActivity(intent);
                return false;
            }
        });

        return inf;
    }
}