package com.github.ppartisan.simplealarms.util;

import android.annotation.TargetApi;
import android.content.res.Resources;
import android.os.Build;
import android.widget.TimePicker;

import java.util.Calendar;

public final class ViewUtils {

    private ViewUtils() { throw new AssertionError(); }

    public static float dpToPx(float dp) {
        return dp*Resources.getSystem().getDisplayMetrics().density;
    }

    public static void setTimePickerTime(TimePicker picker, long time) {

        final Calendar c = Calendar.getInstance();
        c.setTimeInMillis(time);

        final int minutes = c.get(Calendar.MINUTE);
        final int hours = c.get(Calendar.HOUR_OF_DAY);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            picker.setMinute(minutes);
            picker.setHour(hours);
        } else {
            picker.setCurrentMinute(minutes);
            picker.setCurrentHour(hours);
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    public static int getTimePickerMinute(TimePicker picker) {
        return (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                ? picker.getMinute()
                : picker.getCurrentMinute();
    }

    @TargetApi(Build.VERSION_CODES.M)
    public static int getTimePickerHour(TimePicker picker) {
        return (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                ? picker.getHour()
                : picker.getCurrentHour();
    }

}
