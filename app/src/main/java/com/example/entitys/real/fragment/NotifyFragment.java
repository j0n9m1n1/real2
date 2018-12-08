package com.example.entitys.real.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ListView;

import com.example.entitys.real.R;
import com.example.entitys.real.activity.HelpActivtiy;
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
import java.util.concurrent.ExecutionException;


public class NotifyFragment extends Fragment {

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

    public NotifyFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
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
//            Intent in = new Intent(getContext(), HelpActivtiy.class);
            startActivity(intent);
            Log.d("clicked", "help");
            return true;

        }

        return super.onOptionsItemSelected(item);

        /*
        switch (item.getItemId()) {
        case R.id.action_settings:
            // User chose the "Settings" item, show the app settings UI...
            return true;

        case R.id.action_favorite:
            // User chose the "Favorite" action, mark the current item
            // as a favorite...
            return true;

        default:
            // If we got here, the user's action was not recognized.
            // Invoke the superclass to handle it.
            return super.onOptionsItemSelected(item);

    }
         */
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        getActivity().setTitle("알림");

        View inf = inflater.inflate(R.layout.fragment_notify, container, false);

        ListView listview = (ListView)inf.findViewById(R.id.listview);

        NotifyListAdapter adapter = new NotifyListAdapter(getActivity(), R.layout.notify_item, ReportActivity.PushList);
        listview.setAdapter(adapter);

        return inf;
    }
}
