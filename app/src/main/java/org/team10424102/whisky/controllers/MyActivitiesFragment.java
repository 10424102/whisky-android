package org.team10424102.whisky.controllers;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.team10424102.whisky.App;
import org.team10424102.whisky.R;
import org.team10424102.whisky.components.ApiCallback;
import org.team10424102.whisky.components.api.ApiService;
import org.team10424102.whisky.databinding.FragmentMyActivitiesBinding;
import org.team10424102.whisky.models.Activity;
import org.team10424102.whisky.ui.MarginDownDecoration;

import java.util.ArrayList;
import java.util.List;

public class MyActivitiesFragment extends Fragment {
    public static final int PAGE_SIZE = 5;

    private List<Activity> mActivities = new ArrayList<>();
    private RecyclerView mList;
    private ApiService mApiService;

    private void loadActivitiesFromServer(int page) {
        mApiService.getActivities("myself", page, PAGE_SIZE).enqueue(
                new ApiCallback<List<Activity>>() {
                    @Override
                    protected void handleSuccess(List<Activity> result) {
                        mActivities.addAll(result);
                        mList.getAdapter().notifyDataSetChanged();
                    }
                });
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final FragmentMyActivitiesBinding binding =
                DataBindingUtil.inflate(inflater, R.layout.fragment_my_activities, container, false);

        mList = binding.list;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mList.setLayoutManager(linearLayoutManager);
        mList.addItemDecoration(new MarginDownDecoration(getContext()));
        mList.setAdapter(new ActivitiesAdapter(mActivities));
        mList.addOnScrollListener(new EndlessRecyclerOnScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int current_page) {
                loadActivitiesFromServer(current_page);
            }
        });

        mApiService = (ApiService) App.getInstance().getComponent(ApiService.class);

        loadActivitiesFromServer(0);

        return binding.getRoot();
    }
}

