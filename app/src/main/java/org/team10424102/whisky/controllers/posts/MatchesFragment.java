package org.team10424102.whisky.controllers.posts;

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
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import org.team10424102.whisky.App;
import org.team10424102.whisky.R;
import org.team10424102.whisky.components.ApiCallback;
import org.team10424102.whisky.controllers.EndlessRecyclerOnScrollListener;
import org.team10424102.whisky.controllers.posts.PostsAdapter;
import org.team10424102.whisky.databinding.FragmentMatchesBinding;
import org.team10424102.whisky.models.Post;
import org.team10424102.whisky.models.enums.EMatchPostsCategory;
import org.team10424102.whisky.ui.MarginDownDecoration;
import org.team10424102.whisky.utils.DimensionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yy on 11/5/15.
 */
public class MatchesFragment extends Fragment {
    public static final int PAGE_SIZE = 5;
    private List<Post> mMatchPosts = new ArrayList<>();
    private EMatchPostsCategory mCategory = EMatchPostsCategory.SCHOOL;
    private RecyclerView mList;

    private void loadMatchPostsFromServer(int page) {
        App.api().getPosts(mCategory.toString(), page, PAGE_SIZE, null).enqueue(new ApiCallback<List<Post>>() {
            @Override
            protected void handleSuccess(List<Post> result) {
                //mMatchPosts.clear();
                for (Post p : result) {
                    App.getPostExtensionManager().setGameFor(p);
                }
                mMatchPosts.addAll(result);
                mList.getAdapter().notifyDataSetChanged();
            }
        });
    }

    private void initToolbar(Toolbar toolbar) {
        // 初始化 Toolbar
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        assert activity.getSupportActionBar() != null;
        final ActionBar ab = activity.getSupportActionBar();
        ab.setDisplayShowTitleEnabled(false);
        ab.setDisplayHomeAsUpEnabled(false);

        if (Build.VERSION.SDK_INT >= 21) {
            toolbar.setPadding(0, DimensionUtils.getStatusBarHeight(), 0, 0);
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentMatchesBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_matches, container, false);

        initToolbar(binding.toolbar);

        mList = binding.list;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mList.setLayoutManager(linearLayoutManager);
        mList.addItemDecoration(new MarginDownDecoration(getContext()));
        mList.addOnScrollListener(new EndlessRecyclerOnScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int current_page) {
                loadMatchPostsFromServer(current_page);
            }
        });
        mList.setAdapter(new PostsAdapter(mMatchPosts));

        Spinner spinner = binding.spinner;
        ArrayAdapter<String> aa = new ArrayAdapter<String>(getContext(), R.layout.spinner_item, new String[]{"校园战况", "好友战况", "关注的人战况"});
        spinner.setAdapter(aa);

        loadMatchPostsFromServer(0);


        return binding.getRoot();
    }

}
