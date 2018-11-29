package com.example.entitys.real.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.entitys.real.R;
import com.example.entitys.real.http.GetRecentPush;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;


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


        ArrayList<Notices> data = new ArrayList<Notices>();

        for(int i=0; i<3; i++){
            for(int j=0; j<3; j++){
                data.add(new Notices("과목"+Integer.toString(i+1), "과제"+Integer.toString(j+1)));
            }
        }

        View inf = inflater.inflate(R.layout.fragment_notify, container, false);

        ListView listview = (ListView)inf.findViewById(R.id.listview);

        ListAdapter adapter = new ListAdapter(getActivity(), R.layout.notify_item, data);
        listview.setAdapter(adapter);

        return inf;
    }
}
