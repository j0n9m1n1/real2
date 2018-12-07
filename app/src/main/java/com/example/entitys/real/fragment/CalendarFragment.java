package com.example.entitys.real.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import android.widget.ExpandableListView;
import android.widget.ListView;

import com.example.entitys.real.R;
import com.example.entitys.real.activity.ReportActivity;
import com.example.entitys.real.activity.ReportDetailActivity;
import com.example.entitys.real.calendar_decorators.EventDecorator;
import com.example.entitys.real.calendar_decorators.OneDayDecorator;
import com.example.entitys.real.calendar_decorators.SaturdayDecorator;
import com.example.entitys.real.calendar_decorators.SundayDecorator;
import com.example.entitys.real.http.GetRecentPush;
import com.example.entitys.real.http.GetReport;
import com.example.entitys.real.types.Pushs;
import com.example.entitys.real.types.Reports;
import com.example.entitys.real.types.Subjects;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

public class CalendarFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    private final OneDayDecorator oneDayDecorator = new OneDayDecorator();
    private static MaterialCalendarView materialCalendarView;


    Handler han = null;
    public ProgressDialog dialog = null;
    private Thread thread=null;
    public Subjects subject_temp = null;
    public Reports report_temp = null;
    public static ArrayList<Subjects> DataList = null;
    private String response = null;
    public static ArrayList<Pushs> PushList = null;
    public Pushs push_temp = null;


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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        getActivity().setTitle("일정");
        View inf = inflater.inflate(R.layout.fragment_calendar, container, false);

        materialCalendarView = (MaterialCalendarView)inf.findViewById(R.id.calendarView); // getActivity or getContext로 받으면 에러 inf로 받아야함

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
                    //TODO calendar listview click event
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
