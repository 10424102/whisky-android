package org.team10424102.whisky.controllers.sub;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.team10424102.whisky.Global;
import org.team10424102.whisky.R;
import org.team10424102.whisky.controllers.adapters.PostsAdapter;
import org.team10424102.whisky.databinding.FragmentMyPostsBinding;
import org.team10424102.whisky.models.Post;
import org.team10424102.whisky.models.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by yy on 11/5/15.
 */
public class FragmentMyPosts extends Fragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final FragmentMyPostsBinding binding =
                DataBindingUtil.inflate(inflater, R.layout.fragment_my_posts, container, false);

        final RecyclerView list = binding.list;
        list.setLayoutManager(new LinearLayoutManager(list.getContext()));

        User u = new User();
        u.setDisplayName(Global.getProfile().getUsername());

        Post a1 = new Post();
        a1.setContent("推文内容 1");
        a1.setSender(u);
        a1.setDevice("iPhone6s plus");
        a1.setSendTime(new Date());

        Post a2 = new Post();
        a2.setContent("推文内容 2");
        a2.setSender(u);
        a2.setDevice("小米手机");
        a2.setSendTime(new Date());

        Post a3 = new Post();
        a3.setContent("推文内容 3");
        a3.setSender(u);
        a3.setDevice("诺基亚极品砖头机");
        a3.setSendTime(new Date());

        List<Post> data = new ArrayList<>();
        data.add(a1);
        data.add(a2);
        data.add(a3);

        list.setAdapter(new PostsAdapter(data));

        return binding.getRoot();
    }
}

