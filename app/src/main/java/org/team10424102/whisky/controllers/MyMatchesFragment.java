package org.team10424102.whisky.controllers;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.team10424102.whisky.R;
import org.team10424102.whisky.components.BlackServerApi;
import org.team10424102.whisky.databinding.MyMatchesFragmentBinding;
import org.team10424102.whisky.models.Post;
import org.team10424102.whisky.models.extensions.PostExtensionManager;
import org.team10424102.whisky.ui.MarginDownDecoration;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by yy on 11/5/15.
 */
public class MyMatchesFragment extends BaseFragment {
    public static final int PAGE_SIZE = 5;
    private List<Post> mMatchPosts = new ArrayList<>();
    private RecyclerView mList;
    @Inject BlackServerApi mApi;
    @Inject PostExtensionManager mPostExtensionManager;

    private void loadPage(int page) {
        mApi.getPosts("myself", page, PAGE_SIZE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Post>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<Post> posts) {
                        for (Post p : posts) {
                            mPostExtensionManager.setGameFor(p);
                        }
                        mMatchPosts.addAll(posts);
                        mList.getAdapter().notifyDataSetChanged();
                    }
                });
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final MyMatchesFragmentBinding binding =
                DataBindingUtil.inflate(inflater, R.layout.my_matches_fragment, container, false);

        RecyclerView bindings = binding.bindings;
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        bindings.setLayoutManager(layoutManager);
        bindings.setAdapter(new GameBindingsAdapter());

        mList = binding.list;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mList.setLayoutManager(linearLayoutManager);
        mList.addItemDecoration(new MarginDownDecoration(getContext()));
        mList.addOnScrollListener(new EndlessRecyclerOnScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int current_page) {
                loadPage(current_page);
            }
        });
        mList.setAdapter(new PostsMyselfAdapter(mMatchPosts));

        return binding.getRoot();
    }
}

