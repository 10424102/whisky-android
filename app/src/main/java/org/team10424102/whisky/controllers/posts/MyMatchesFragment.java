package org.team10424102.whisky.controllers.posts;

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
import org.team10424102.whisky.controllers.EndlessRecyclerOnScrollListener;
import org.team10424102.whisky.databinding.FragmentMyMatchesBinding;
import org.team10424102.whisky.models.Post;
import org.team10424102.whisky.models.extensions.PostExtensionManager;
import org.team10424102.whisky.ui.MarginDownDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yy on 11/5/15.
 */
public class MyMatchesFragment extends Fragment {
    public static final int PAGE_SIZE = 5;
    private List<Post> mMatchPosts = new ArrayList<>();
    private RecyclerView mList;
    private ApiService mApiService;
    private PostExtensionManager mPostExtensionManager;

    private void loadMatchPostsFromServer(int page) {
        mApiService.getPosts("myself", page, PAGE_SIZE, null).enqueue(new ApiCallback<List<Post>>() {
            @Override
            protected void handleSuccess(List<Post> result) {
                //mMatchPosts.clear();
                for (Post p : result) {
                    mPostExtensionManager.setGameFor(p);
                }
                mMatchPosts.addAll(result);
                mList.getAdapter().notifyDataSetChanged();
            }
        });
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final FragmentMyMatchesBinding binding =
                DataBindingUtil.inflate(inflater, R.layout.fragment_my_matches, container, false);


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
                loadMatchPostsFromServer(current_page);
            }
        });
        mList.setAdapter(new PostsMyselfAdapter(mMatchPosts));


        mApiService = (ApiService)App.getInstance().getComponent(ApiService.class);
        mPostExtensionManager = (PostExtensionManager)App.getInstance().getComponent(PostExtensionManager.class);



        loadMatchPostsFromServer(0);

        return binding.getRoot();
    }
}

