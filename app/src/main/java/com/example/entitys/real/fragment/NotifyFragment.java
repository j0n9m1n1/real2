package com.example.entitys.real.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.example.entitys.real.R;
import com.example.entitys.real.activity.HelpActivtiy;
import com.example.entitys.real.activity.LoginActivity;
import com.example.entitys.real.activity.ReportActivity;

public class NotifyFragment extends Fragment {

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

        int cid = item.getItemId();

        if(cid == R.id.refresh){

            Intent in = new Intent(getContext(), ReportActivity.class);
            startActivity(in);
            return true;
        }

        if(cid == R.id.logout){

            Intent in = new Intent(getContext(), LoginActivity.class);
            startActivity(in);
            return true;
        }

        if(cid == R.id.help){

            Intent intent = new Intent(getActivity(), HelpActivtiy.class);
            startActivity(intent);
            return true;

        }

        return super.onOptionsItemSelected(item);
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
