package org.team10424102.whisky.controllers;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.team10424102.whisky.R;
import org.team10424102.whisky.components.BlackServerApi;
import org.team10424102.whisky.databinding.MyPhotosFragmentBinding;
import org.team10424102.whisky.models.LazyImage;
import org.team10424102.whisky.ui.MarginDecoration;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MyPhotosFragment extends BaseFragment {
    public static final int PAGE_SIZE = 15;

    @Inject BlackServerApi mApi;
    List<LazyImage> mPhotos = new ArrayList<>();
    RecyclerView mGrid;

    private void loadPage(int page) {
        mApi.getPhotos(page, PAGE_SIZE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<LazyImage>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<LazyImage> photos) {
                        mPhotos.addAll(photos);
                        mGrid.getAdapter().notifyDataSetChanged();
                    }
                });
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final MyPhotosFragmentBinding binding =
                DataBindingUtil.inflate(inflater, R.layout.my_photos_fragment, container, false);

        mGrid = binding.grid;
        mGrid.addItemDecoration(new MarginDecoration(getContext()));
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 3);
        mGrid.setLayoutManager(layoutManager);
        mGrid.setAdapter(new PhotosAdapter(mPhotos));
        mGrid.addOnScrollListener(new EndlessRecyclerOnScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int currentPage) {
                loadPage(currentPage);
            }
        });

        return binding.getRoot();
    }
}

