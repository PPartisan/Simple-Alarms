package com.github.ppartisan.simplealarms.data;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import com.github.ppartisan.simplealarms.model.Alarm;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public final class DatabaseWriteTest {

    private final Context context = InstrumentationRegistry.getTargetContext();
    private long id;

    @Before
    public void insert() throws Exception {
        id = DatabaseHelper.getInstance(context).addAlarm(new Alarm());
        Assert.assertTrue(id != -1);
    }

    @Test
    public void update() throws Exception {
        final Alarm alarm = new Alarm(id);
        alarm.setIsEnabled(false);
        final int rowsUpdated = DatabaseHelper.getInstance(context).updateAlarm(alarm);
        Assert.assertTrue(rowsUpdated == 1);
    }

    @After
    public void delete() throws Exception {
        final int rowsDeleted = DatabaseHelper.getInstance(context).deleteAlarm(id);
        Assert.assertTrue(rowsDeleted == 1);
    }

}
