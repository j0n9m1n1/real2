package com.example.entitys.real.fragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.entitys.real.R;
import com.example.entitys.real.types.Pushs;

import java.util.ArrayList;

public class NotifyListAdapter extends BaseAdapter {
    private ArrayList<Pushs> list;
    private Context context;
    private int lay;

    public NotifyListAdapter(Context context, int lay, ArrayList<Pushs> list){
        this.list = list;
        this.context = context;
        this.lay = lay;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            LayoutInflater inf = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inf.inflate(this.lay, parent, false);
        }

        TextView title = (TextView)convertView.findViewById(R.id.notify_title);
        TextView item = (TextView)convertView.findViewById(R.id.notify_item);

        title.setText(list.get(position).getReport_title());
        item.setText(list.get(position).getItem());

        return convertView;
    }
}
