package com.cs411.RolyPoly;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.style.ForegroundColorSpan;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

import java.util.Collection;
import java.util.HashSet;

public class EventDecorator implements DayViewDecorator {
    private final int color;
    private HashSet<CalendarDay> dates;
    private final Drawable drawable;

    public EventDecorator(Activity context, int color, Collection<CalendarDay> dates) {
        this.drawable = context.getResources().getDrawable(R.drawable.ic_lens_black_24dp);
        this.color = color;
        this.dates = new HashSet<>(dates);
    }

    @Override
    public boolean shouldDecorate(CalendarDay calendarDay) {
        return dates.contains(calendarDay);
    }

    @Override
    public void decorate(DayViewFacade dayViewFacade) {
//        dayViewFacade.addSpan(new DotSpan(5, color));
//        dayViewFacade.addSpan(new RelativeSizeSpan(1.8f)); // for TextSize

        dayViewFacade.addSpan(new ForegroundColorSpan(Color.WHITE)); // for TextColor
        dayViewFacade.setSelectionDrawable(drawable);
    }

//    public void addDates(Collection<CalendarDay> newDates){
//        dates.addAll(newDates);
//    }

    public void updateCollection(Collection<CalendarDay> newDates){
        this.dates = new HashSet<>(newDates);
    }
}
