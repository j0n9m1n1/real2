package com.example.entitys.real.calendar_decorators;

import android.graphics.Color;
import android.util.Log;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.spans.DotSpan;

import java.util.Collection;
import java.util.HashSet;

public class EventDecorator implements DayViewDecorator {

    //private final Drawable drawable;
    private HashSet<CalendarDay> dates;

    public EventDecorator(Collection<CalendarDay> dates) { //Activity context
        //drawable = context.getResources().getDrawable(R.drawable.more);
        this.dates = new HashSet<>(dates);
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        Log.w("date test", day.toString());
        return dates.contains(day);
    }

    @Override
    public void decorate(DayViewFacade view) {
        //view.setSelectionDrawable(drawable);
        view.addSpan(new DotSpan(5, Color.RED)); // 날자밑에 점
    }
}