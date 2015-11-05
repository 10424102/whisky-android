package org.team10424102.whisky.controllers.sub;

import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.team10424102.whisky.R;
import org.team10424102.whisky.controllers.adapters.MatchResultsAdapter2;
import org.team10424102.whisky.databinding.FragmentMatchesBinding;
import org.team10424102.whisky.models.Post;
import org.team10424102.whisky.models.User;
import org.team10424102.whisky.utils.DimensionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by yy on 11/5/15.
 */
public class FragmentMatches extends Fragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentMatchesBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_matches, container, false);

        // 初始化 Toolbar
        Toolbar toolbar = binding.toolbar;
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
//        assert activity.getSupportActionBar() != null;
//        final ActionBar ab = activity.getSupportActionBar();
//        ab.setDisplayShowTitleEnabled(false);
//        ab.setDisplayHomeAsUpEnabled(false);

        if (Build.VERSION.SDK_INT >= 21) {
            toolbar.setPadding(0, DimensionUtils.getStatusBarHeight(getResources()), 0, 0);
        }

        final RecyclerView list = binding.list;
        list.setLayoutManager(new LinearLayoutManager(list.getContext()));

        User u = new User();
        u.setDisplayName("王尼玛");
        u.setSignature("我是王尼玛，啦啦啦");

        Post a1 = new Post();
        a1.setContent("当我抓住鼠标的时候，你们就输了 1");
        a1.setSendTime(new Date());
        a1.setSender(u);

        Post a2 = new Post();
        a2.setContent("当我抓住鼠标的时候，你们就输了 2");
        a2.setSendTime(new Date());
        a2.setSender(u);

        Post a3 = new Post();
        a3.setContent("当我抓住鼠标的时候，你们就输了 3");
        a3.setSendTime(new Date());
        a3.setSender(u);

        List<Post> data = new ArrayList<>();
        data.add(a1);
        data.add(a2);
        data.add(a3);

        list.setAdapter(new MatchResultsAdapter2(data));

        return binding.getRoot();
    }

}
