package org.team10424102.whisky.controllers.activities;

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
import org.team10424102.whisky.components.BlackServerApi;
import org.team10424102.whisky.controllers.BaseFragment;
import org.team10424102.whisky.controllers.EndlessRecyclerOnScrollListener;
import org.team10424102.whisky.databinding.MyActivitiesFragmentBinding;
import org.team10424102.whisky.models.Activity;
import org.team10424102.whisky.ui.MarginDownDecoration;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MyActivitiesFragment extends BaseFragment {
    public static final int PAGE_SIZE = 5;

    private List<Activity> mActivities = new ArrayList<>();
    private RecyclerView mList;

    @Inject BlackServerApi mApi;

    private void loadPage(int page) {
        mApi.getActivities("myself", page, PAGE_SIZE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Activity>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<Activity> activities) {
                        mActivities.addAll(activities);
                        mList.getAdapter().notifyDataSetChanged();
                    }
                });
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final MyActivitiesFragmentBinding binding =
                DataBindingUtil.inflate(inflater, R.layout.my_activities_fragment, container, false);

        mList = binding.list;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mList.setLayoutManager(linearLayoutManager);
        mList.addItemDecoration(new MarginDownDecoration(getContext()));
        mList.setAdapter(new ActivitiesAdapter(mActivities));
        mList.addOnScrollListener(new EndlessRecyclerOnScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int current_page) {
                loadPage(current_page);
            }
        });

        return binding.getRoot();
    }
}

