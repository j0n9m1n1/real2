package com.example.entitys.real.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import com.example.entitys.real.R;
import com.example.entitys.real.activity.HelpActivtiy;
import com.example.entitys.real.activity.LoginActivity;
import com.example.entitys.real.activity.ReportActivity;
import com.example.entitys.real.activity.ReportDetailActivity;
import com.example.entitys.real.calendar_decorators.EventDecorator;
import com.example.entitys.real.calendar_decorators.OneDayDecorator;
import com.example.entitys.real.calendar_decorators.SaturdayDecorator;
import com.example.entitys.real.calendar_decorators.SundayDecorator;
import com.example.entitys.real.types.Pushs;
import com.example.entitys.real.types.Reports;
import com.example.entitys.real.types.Subjects;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.Executors;

public class CalendarFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    private final OneDayDecorator oneDayDecorator = new OneDayDecorator();
    private static MaterialCalendarView materialCalendarView;

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
            startActivity(intent);
            Log.d("clicked", "help");
            return true;
        }

        return super.onOptionsItemSelected(item);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        getActivity().setTitle("일정");
        View inf = inflater.inflate(R.layout.fragment_calendar, container, false);

        materialCalendarView = (MaterialCalendarView)inf.findViewById(R.id.calendarView);

        materialCalendarView.state().edit()
                .setFirstDayOfWeek(Calendar.SUNDAY)
                .setMinimumDate(CalendarDay.from(2016, 0, 1))//시작일
                .setMaximumDate(CalendarDay.from(2030, 12, 31))//끝일
                .setCalendarDisplayMode(CalendarMode.MONTHS)
                .commit();

        materialCalendarView.addDecorators(
                new SundayDecorator(),
                new SaturdayDecorator(),
                oneDayDecorator);

        final ArrayList<Subjects> DataList = ReportActivity.DataList;
        final ArrayList<String> result = new ArrayList<String>();

        for(int i=0; i<DataList.size(); i++){
            for(int j=0; j<DataList.get(i).reportgroup.size(); j++){
                if(!DataList.get(i).reportgroup.get(j).reportdetail.get(3).equals("과제없음")) {
                    String[] time = DataList.get(i).reportgroup.get(j).reportdetail.get(3).split(" ");
                    result.add(time[0]);
                }
            }
        }

        new ApiSimulator(result, materialCalendarView).executeOnExecutor(Executors.newSingleThreadExecutor());

        materialCalendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                int Year = date.getYear();
                int Month = date.getMonth() + 1;
                int Day = date.getDay();
                String shot_Day;

                if(Month<10){
                    if (Day<10){
                        shot_Day = Year + ".0" + Month + ".0" + Day; // result와 같은 형식
                    }else {
                        shot_Day = Year + ".0" + Month + "." + Day; // result와 같은 형식
                    }
                }else{
                    if (Day<10){
                        shot_Day = Year + "." + Month + ".0" + Day; // result와 같은 형식
                    }else {
                        shot_Day = Year + "." + Month + "." + Day; // result와 같은 형식
                    }
                }

                ListView listview = (ListView)getView().findViewById(R.id.listview);

                final ArrayList<Pushs> Reportlist = new ArrayList<Pushs>();
                Pushs pushs = null;

                for(int i=0; i<DataList.size(); i++){
                    for(int j=0; j<DataList.get(i).reportgroup.size(); j++){
                        if(!DataList.get(i).reportgroup.get(j).reportdetail.get(3).equals("과제없음")) {
                            String[] time = DataList.get(i).reportgroup.get(j).reportdetail.get(3).split(" ");

                            if(shot_Day.equals(time[0])){
                                pushs = new Pushs(DataList.get(i).subjectname,
                                        DataList.get(i).reportgroup.get(j).reportdetail.get(0), DataList.get(i).reportgroup.get(j).reportdetail.get(3));
                                Reportlist.add(pushs);
                            }
                        }
                    }
                }

                CalendarListAdapter adapter = new CalendarListAdapter(getActivity(), R.layout.calendar_item, Reportlist);
                listview.setAdapter(adapter);


                listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    //TODO ic_bt_nav_calendar listview click event
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        int groupposition = 0;
                        int childposition = 0;

                        for(int i=0; i<DataList.size(); i++){
                            if(Reportlist.get(position).subject_title.equals(DataList.get(i).subjectname)){
                                groupposition = i;
                            }
                            for(int j=0; j<DataList.get(i).reportgroup.size(); j++){
                                if(Reportlist.get(position).report_title.equals(DataList.get(i).reportgroup.get(j).reportname)){
                                    childposition = j;
                                    break;
                                }
                            }
                            if(childposition != 0){
                                break;
                            }
                        }

                        Intent intent = new Intent(getActivity(), ReportDetailActivity.class);
                        intent.putExtra("groupPosition", groupposition);
                        intent.putExtra("childPosition", childposition);
                        startActivity(intent);
                    }
                });

                materialCalendarView.clearSelection();
            }
        });


        return inf;
    }

    public static class ApiSimulator extends AsyncTask<Void, Void, List<CalendarDay>> {

        ArrayList<String> Time_Result2;
        MaterialCalendarView materialCalendarView;

        ApiSimulator(ArrayList<String> Time_Result, MaterialCalendarView materialCalendarView) {
            this.materialCalendarView = materialCalendarView;
            this.Time_Result2 = Time_Result;
        }

        @Override
        protected List<CalendarDay> doInBackground(@NonNull Void... voids) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Calendar calendar = Calendar.getInstance();
            ArrayList<CalendarDay> dates = new ArrayList<>();

            /*특정날짜 달력에 점표시해주는곳*/
            /*월은 0이 1월 년,일은 그대로*/
            //string 문자열인 Time_Result 을 받아와서 ,를 기준으로짜르고 string을 int 로 변환

            for (int i = 0; i < Time_Result2.size(); i++) {
                String[] time = Time_Result2.get(i).split("\\.");
                int year = Integer.parseInt(time[0]);
                int month = Integer.parseInt(time[1]);
                int dayy = Integer.parseInt(time[2]);

                calendar.set(year, month - 1, dayy);
                CalendarDay day = CalendarDay.from(calendar);
                dates.add(day);
            }
            return dates;
        }

        @Override
        protected void onPostExecute(@NonNull List<CalendarDay> calendarDays) {
            super.onPostExecute(calendarDays);

            materialCalendarView.addDecorator(new EventDecorator(calendarDays)); //MainActivity.this
        }
    }
}
