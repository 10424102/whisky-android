package org.team10424102.whisky.controllers.posts;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import org.team10424102.whisky.App;
import org.team10424102.whisky.R;
import org.team10424102.whisky.components.BlackServerApi;
import org.team10424102.whisky.controllers.EndlessRecyclerOnScrollListener;
import org.team10424102.whisky.databinding.MatchesFragmentBinding;
import org.team10424102.whisky.models.Post;
import org.team10424102.whisky.models.extensions.PostExtensionManager;
import org.team10424102.whisky.ui.MarginDownDecoration;
import org.team10424102.whisky.utils.DimensionUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by yy on 11/5/15.
 */
public class MatchesFragment extends Fragment {
    public static final int PAGE_SIZE = 5;
    private List<Post> mMatchPosts = new ArrayList<>();
    private RecyclerView mList;
    @Inject BlackServerApi mApi;
    @Inject PostExtensionManager mPostExtensionManager;
    private String mCategory = "school";
    private String[] mCategories = {"school", "friends", "focuses"};
    private String[] mLocalizedCategory;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mLocalizedCategory = new String[]{

                getString(R.string.matches_category_school),
                getString(R.string.matches_category_friends),
                getString(R.string.matches_category_focuses)

        };
    }

    private void loadPage(int page) {
        mApi.getPosts(mCategory, page, PAGE_SIZE)
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
                        //mMatchPosts.clear();
                        for (Post p : posts) {
                            mPostExtensionManager.setGameFor(p);
                        }
                        mMatchPosts.addAll(posts);
                        mList.getAdapter().notifyDataSetChanged();
                    }
                });
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ((App) getActivity().getApplication()).getObjectGraph().inject(this);

        MatchesFragmentBinding binding =
                DataBindingUtil.inflate(inflater, R.layout.matches_fragment, container, false);

        // Toolbar
        Toolbar toolbar = binding.toolbar;
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        assert activity.getSupportActionBar() != null;
        final ActionBar ab = activity.getSupportActionBar();
        ab.setDisplayShowTitleEnabled(false);
        ab.setDisplayHomeAsUpEnabled(false);

        if (Build.VERSION.SDK_INT >= 21) {
            toolbar.setPadding(0, DimensionUtils.getStatusBarHeight(), 0, 0);
        }

        // Spinner
        Spinner spinner = binding.spinner;
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(ab.getThemedContext(),
                android.R.layout.simple_spinner_item, mLocalizedCategory);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String category = mCategories[position];
                if (!category.equals(mCategory)) {
                    mMatchPosts.clear();
                    mList.getAdapter().notifyDataSetChanged();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        // RecyclerView
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
        mList.setAdapter(new PostsAdapter(mMatchPosts));

        return binding.getRoot();
    }

}
