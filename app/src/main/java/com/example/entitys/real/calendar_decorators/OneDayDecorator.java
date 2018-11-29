package com.example.entitys.real.calendar_decorators;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;

import com.example.entitys.real.R;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

import java.util.Date;

public class OneDayDecorator implements DayViewDecorator {
    //private final Drawable drawable;
    private CalendarDay date;

    public OneDayDecorator() { // Activity context
        date = CalendarDay.today();
        //drawable = context.getResources().getDrawable(R.drawable.more);
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return date != null&&day.equals(date);
    }

    @Override
    public void decorate(DayViewFacade view) {
        //view.setSelectionDrawable(drawable);
        view.addSpan(new StyleSpan(Typeface.BOLD));
        view.addSpan(new RelativeSizeSpan((1.4f)));
        view.addSpan(new ForegroundColorSpan(Color.GREEN));
    }

    public void setDate(Date date){
        this.date = CalendarDay.from(date);
    }
}
