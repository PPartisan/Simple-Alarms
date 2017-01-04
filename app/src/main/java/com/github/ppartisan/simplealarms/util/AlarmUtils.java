package com.github.ppartisan.simplealarms.util;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.util.SparseBooleanArray;

import com.github.ppartisan.simplealarms.data.DatabaseHelper;
import com.github.ppartisan.simplealarms.model.Alarm;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import static com.github.ppartisan.simplealarms.data.DatabaseHelper.COL_FRI;
import static com.github.ppartisan.simplealarms.data.DatabaseHelper.COL_IS_ENABLED;
import static com.github.ppartisan.simplealarms.data.DatabaseHelper.COL_LABEL;
import static com.github.ppartisan.simplealarms.data.DatabaseHelper.COL_MON;
import static com.github.ppartisan.simplealarms.data.DatabaseHelper.COL_SAT;
import static com.github.ppartisan.simplealarms.data.DatabaseHelper.COL_SUN;
import static com.github.ppartisan.simplealarms.data.DatabaseHelper.COL_THURS;
import static com.github.ppartisan.simplealarms.data.DatabaseHelper.COL_TIME;
import static com.github.ppartisan.simplealarms.data.DatabaseHelper.COL_TUES;
import static com.github.ppartisan.simplealarms.data.DatabaseHelper.COL_WED;
import static com.github.ppartisan.simplealarms.data.DatabaseHelper._ID;

public final class AlarmUtils {

    private static final SimpleDateFormat TIME_FORMAT =
            new SimpleDateFormat("h:mm", Locale.getDefault());
    private static final SimpleDateFormat AM_PM_FORMAT =
            new SimpleDateFormat("a", Locale.getDefault());

    private static final int REQUEST_ALARM = 1;
    private static final String[] PERMISSIONS_ALARM = {
            Manifest.permission.VIBRATE
    };

    private AlarmUtils() { throw new AssertionError(); }

    public static void checkAlarmPermissions(Activity activity) {

        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return;
        }

        final int permission = ActivityCompat.checkSelfPermission(
                activity, Manifest.permission.VIBRATE
        );

        if(permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_ALARM,
                    REQUEST_ALARM
            );
        }

    }

    public static ContentValues toContentValues(Alarm alarm) {

        final ContentValues cv = new ContentValues(10);

        cv.put(COL_TIME, alarm.getTime());
        cv.put(COL_LABEL, alarm.getLabel());

        final SparseBooleanArray days = alarm.getDays();
        cv.put(COL_MON, days.get(Alarm.MON) ? 1 : 0);
        cv.put(COL_TUES, days.get(Alarm.TUES) ? 1 : 0);
        cv.put(COL_WED, days.get(Alarm.WED) ? 1 : 0);
        cv.put(COL_THURS, days.get(Alarm.THURS) ? 1 : 0);
        cv.put(COL_FRI, days.get(Alarm.FRI) ? 1 : 0);
        cv.put(COL_SAT, days.get(Alarm.SAT) ? 1 : 0);
        cv.put(COL_SUN, days.get(Alarm.SUN) ? 1 : 0);

        cv.put(DatabaseHelper.COL_IS_ENABLED, alarm.isEnabled());

        return cv;

    }

    public static ArrayList<Alarm> buildAlarmList(Cursor c) {

        if (c == null) return new ArrayList<>();

        final int size = c.getCount();

        final ArrayList<Alarm> alarms = new ArrayList<>(size);

        if (c.moveToFirst()){
            do {

                final long id = c.getLong(c.getColumnIndex(_ID));
                final long time = c.getLong(c.getColumnIndex(COL_TIME));
                final String label = c.getString(c.getColumnIndex(COL_LABEL));
                final boolean mon = c.getInt(c.getColumnIndex(COL_MON)) == 1;
                final boolean tues = c.getInt(c.getColumnIndex(COL_TUES)) == 1;
                final boolean wed = c.getInt(c.getColumnIndex(COL_WED)) == 1;
                final boolean thurs = c.getInt(c.getColumnIndex(COL_THURS)) == 1;
                final boolean fri = c.getInt(c.getColumnIndex(COL_FRI)) == 1;
                final boolean sat = c.getInt(c.getColumnIndex(COL_SAT)) == 1;
                final boolean sun = c.getInt(c.getColumnIndex(COL_SUN)) == 1;
                final boolean isEnabled = c.getInt(c.getColumnIndex(COL_IS_ENABLED)) == 1;

                final Alarm alarm = new Alarm(id, time, label);
                alarm.setDay(Alarm.MON, mon);
                alarm.setDay(Alarm.TUES, tues);
                alarm.setDay(Alarm.WED, wed);
                alarm.setDay(Alarm.THURS, thurs);
                alarm.setDay(Alarm.FRI, fri);
                alarm.setDay(Alarm.SAT, sat);
                alarm.setDay(Alarm.SUN, sun);

                alarm.setIsEnabled(isEnabled);

                alarms.add(alarm);

            } while (c.moveToNext());
        }

        return alarms;

    }

    public static String getReadableTime(long time) {
        return TIME_FORMAT.format(time);
    }

    public static String getAmPm(long time) {
        return AM_PM_FORMAT.format(time);
    }

    public static boolean isAlarmActive(Alarm alarm) {

        final SparseBooleanArray days = alarm.getDays();

        boolean isActive = false;
        int count = 0;

        while (count < days.size() && !isActive) {
            isActive = days.valueAt(count);
            count++;
        }

        return isActive;

    }

    public static int getNotificationId(Alarm alarm) {
        final long id = alarm.getId();
        return (int) (id^(id>>>32));
    }

    public static String getActiveDaysAsString(Alarm alarm) {

        StringBuilder builder = new StringBuilder("Active Days: ");

        if(alarm.getDay(Alarm.MON)) builder.append("Monday, ");
        if(alarm.getDay(Alarm.TUES)) builder.append("Tuesday, ");
        if(alarm.getDay(Alarm.WED)) builder.append("Wednesday, ");
        if(alarm.getDay(Alarm.THURS)) builder.append("Thursday, ");
        if(alarm.getDay(Alarm.FRI)) builder.append("Friday, ");
        if(alarm.getDay(Alarm.SAT)) builder.append("Saturday, ");
        if(alarm.getDay(Alarm.SUN)) builder.append("Sunday.");

        if(builder.substring(builder.length()-2).equals(", ")) {
            builder.replace(builder.length()-2,builder.length(),".");
        }

        return builder.toString();

    }

}
