package com.example.entitys.real.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.entitys.real.R;
import com.example.entitys.real.calendar_decorators.EventDecorator;
import com.example.entitys.real.calendar_decorators.OneDayDecorator;
import com.example.entitys.real.calendar_decorators.SaturdayDecorator;
import com.example.entitys.real.calendar_decorators.SundayDecorator;
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
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

        final ArrayList<String> result = new ArrayList<String>();
        result.add("2018,11,18");
        result.add("2018,11,13");
        result.add("2018,11,10");
        result.add("2018,11,9");
        result.add("2018,11,8");
        result.add("0000,00,0");

        new ApiSimulator(result, materialCalendarView).executeOnExecutor(Executors.newSingleThreadExecutor());

        materialCalendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                int Year = date.getYear();
                int Month = date.getMonth() + 1;
                int Day = date.getDay();

                //Log.i("Year test", Year + "");
                //Log.i("Month test", Month + "");
                //Log.i("Day test", Day + "");

                String shot_Day = Year + "," + Month + "," + Day; // result와 같은 형식
                for(int i=0; i<result.size(); i++) {
                    if (shot_Day.equals(result.get(i))) {
                        Log.i("success", shot_Day);
                        break;
                    } else {
                        Log.i("fail", shot_Day);
                    }
                }
                //Log.i("shot_Day test", shot_Day + "");

                materialCalendarView.clearSelection();
                TextView textView = (TextView)getView().findViewById(R.id.calendarText);
                textView.setText(shot_Day);

                //Toast.makeText(getApplicationContext(), shot_Day , Toast.LENGTH_SHORT).show();
            }
        });


        // Inflate the layout for this fragment
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
                CalendarDay day = CalendarDay.from(calendar);
                String[] time = Time_Result2.get(i).split(",");
                int year = Integer.parseInt(time[0]);
                int month = Integer.parseInt(time[1]);
                int dayy = Integer.parseInt(time[2]);
                //Log.i("year", Integer.toString(year));
                //Log.i("month", Integer.toString(month));
                //Log.i("day", Integer.toString(dayy));

                dates.add(day);
                calendar.set(year, month - 1, dayy);
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
