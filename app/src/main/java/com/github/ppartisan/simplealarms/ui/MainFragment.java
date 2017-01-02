package com.github.ppartisan.simplealarms.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.ppartisan.simplealarms.R;
import com.github.ppartisan.simplealarms.adapter.AlarmsAdapter;
import com.github.ppartisan.simplealarms.model.Alarm;

import java.util.ArrayList;
import java.util.List;

public final class MainFragment extends Fragment {

    private AlarmsAdapter mAdapter;

    public static MainFragment newInstance() {

        Bundle args = new Bundle();

        MainFragment fragment = new MainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View v = inflater.inflate(R.layout.fragment_main, container, false);

        final RecyclerView rv = (RecyclerView) v.findViewById(R.id.recycler);
        mAdapter = new AlarmsAdapter();
        rv.setAdapter(mAdapter);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        rv.setItemAnimator(new DefaultItemAnimator());

        mAdapter.setAlarms(buildDummyAlarms());

        return v;

    }

    private static List<Alarm> buildDummyAlarms() {

        final List<Alarm> alarms = new ArrayList<>(3);

        for (int i = 0; i < 3; i++) {
            final Alarm alarm = new Alarm();
            alarm.setTime(System.currentTimeMillis() - (i*1000000));
            alarm.setDay(Alarm.SUN, true);
            alarm.setDay(Alarm.MON, true);
            alarm.setLabel("Number: " + i);
            alarms.add(alarm);
        }

        return alarms;

    }

}
