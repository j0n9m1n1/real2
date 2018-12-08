package com.example.entitys.real.fragment;
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
    // TODO: Rename parameter arguments, choose names that match
    Handler han = null;
    public ProgressDialog dialog = null;
    private ExpandableListView listView;
    SharedPreferences settings = null;
    private Thread thread=null;

    public Subjects subject_temp = null;
    public Reports report_temp = null;
    public static ArrayList<Subjects> DataList = null;
    private String response = null;

    public static ArrayList<Pushs> PushList = null;
    public Pushs push_temp = null;
    FragmentTransaction ft;
    Fragment currentFragment = null;
    Fragment reportFragment =  this;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        ft = getFragmentManager().beginTransaction();
//        SharedPreferences settings = this.getActivity().getSharedPreferences("setting", 0);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.actionbar_actions, menu);  // Use filter.xml from step 1
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        SharedPreferences settings = this.getActivity().getSharedPreferences("setting", 0);
        final String id = settings.getString("id", "");
        final String pw = settings.getString("pw", "");

        int cid = item.getItemId();
        if(cid == R.id.refresh){

            dialog = ProgressDialog.show(getActivity(), "새로고침...", "Please wait...", true);

            han = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);

                    dialog.dismiss();

                }

            };

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

            Log.d("REFRESH BTN","CLICKED");


            return true;
        }

        return super.onOptionsItemSelected(item);
//        currentFragment  = reportFragment;
//        ft = getFragmentManager().beginTransaction();
//        ft.replace(R.id.content, currentFragment);//id가 content인 곳에 fragment 보여줌
//        ft.commit();//실행


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
                //Toast.makeText(getContext(), "child = "+DataList.get(groupPosition).child.get(childPosition), Toast.LENGTH_SHORT).show();
                return false;
            }
        });

//        setHasOptionsMenu(true);
        return inf;
    }
}