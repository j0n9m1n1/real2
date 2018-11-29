package com.example.entitys.real.fragment;
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
    public Subjects subject_temp = null;
    public Reports report_temp = null;
    public static ArrayList<Subjects> DataList = null;
    private String response = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences settings = getActivity().getSharedPreferences("setting", 0);

        String id = settings.getString("id", "");
        String pw = settings.getString("pw", "");

        try {
            response = new GetReport().execute(id, pw).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ArrayList<String> sub_names = new ArrayList<String>();
        DataList = new ArrayList<Subjects>();

        // Inflate the layout for this fragment
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONObject jsonObject2 = new JSONObject();
            JSONObject jsonObject3 = new JSONObject();
            Iterator subject = jsonObject.keys();

            while(subject.hasNext()){
                sub_names.add((String)subject.next());
            }

            for(int i = 0; i < sub_names.size(); i++) {
                jsonObject2 = (JSONObject) jsonObject.get(sub_names.get(i));
                subject_temp = new Subjects(sub_names.get(i));

                for(int j = 0; j < jsonObject2.length(); j++) {
                    jsonObject3 = (JSONObject) jsonObject2.get("과제 " + j);
                    report_temp = new Reports(jsonObject3.get("과제명").toString());

                    for(int k = 0; k < jsonObject3.length(); k++){
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View inf = inflater.inflate(R.layout.fragment_report, container, false);
        listView = (ExpandableListView)inf.findViewById(R.id.expandableListView);

        ExpandAdapter adapter = new ExpandAdapter(getActivity(), R.layout.report_title, R.layout.report_item, DataList);
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