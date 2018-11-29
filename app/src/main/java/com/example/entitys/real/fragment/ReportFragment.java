package com.example.entitys.real.fragment;
import com.example.entitys.real.activity.ReportActivity;
import com.example.entitys.real.http.GetReport;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.example.entitys.real.R;
import com.example.entitys.real.activity.ReportDetailActivity;
import com.example.entitys.real.types.Reports;
import com.example.entitys.real.types.Subjects;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ExecutionException;

public class ReportFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    private ExpandableListView listView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

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
                //Toast.makeText(getContext(), "child = "+DataList.get(groupPosition).child.get(childPosition), Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        return inf;
    }
}