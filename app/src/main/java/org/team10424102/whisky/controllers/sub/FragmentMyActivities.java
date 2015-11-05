package org.team10424102.whisky.controllers.sub;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.team10424102.whisky.R;
import org.team10424102.whisky.controllers.adapters.ActivitiesAdapter;
import org.team10424102.whisky.databinding.FragmentMyActivitiesBinding;
import org.team10424102.whisky.models.Activity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FragmentMyActivities extends Fragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final FragmentMyActivitiesBinding binding =
                DataBindingUtil.inflate(inflater, R.layout.fragment_my_activities, container, false);

        final RecyclerView list = binding.list;
        list.setLayoutManager(new LinearLayoutManager(list.getContext()));

        Activity a1 = new Activity();
        a1.setTitle("活动一");
        a1.setStartTime(new Date());
        a1.setLocation("地点一");

        Activity a2 = new Activity();
        a2.setTitle("活动二");
        a2.setStartTime(new Date());
        a2.setLocation("地点二");

        Activity a3 = new Activity();
        a3.setTitle("活动三");
        a3.setStartTime(new Date());
        a3.setLocation("地点三");

        List<Activity> data = new ArrayList<>();
        data.add(a1);
        data.add(a2);
        data.add(a3);

        list.setAdapter(new ActivitiesAdapter(data));

        return binding.getRoot();
    }
}

