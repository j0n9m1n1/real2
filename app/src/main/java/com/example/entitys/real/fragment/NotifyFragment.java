package com.example.entitys.real.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.entitys.real.R;
import com.example.entitys.real.activity.ReportActivity;
import com.example.entitys.real.types.Pushs;

import java.util.ArrayList;


public class NotifyFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    public NotifyFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences settings = getActivity().getSharedPreferences("setting", 0);

        String id = settings.getString("id", "");
        String pw = settings.getString("pw", "");
        String response = "";



        System.out.println(response);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        //ArrayList<Pushs> data = new ArrayList<Pushs>();

        //for(int i=0; i<3; i++){
            //for(int j=0; j<3; j++){
                //data.add(new Pushs("과목"+Integer.toString(i+1), "과제"+Integer.toString(j+1)));
            //}
        //}

        View inf = inflater.inflate(R.layout.fragment_notify, container, false);

        ListView listview = (ListView)inf.findViewById(R.id.listview);

        ListAdapter adapter = new ListAdapter(getActivity(), R.layout.notify_item, ReportActivity.PushList);
        listview.setAdapter(adapter);

        return inf;
    }
}
