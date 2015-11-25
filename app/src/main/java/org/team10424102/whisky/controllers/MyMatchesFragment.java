package org.team10424102.whisky.controllers;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.team10424102.whisky.R;
import org.team10424102.whisky.databinding.FragmentMyMatchesBinding;
import org.team10424102.whisky.models.Post;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yy on 11/5/15.
 */
public class MyMatchesFragment extends Fragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final FragmentMyMatchesBinding binding =
                DataBindingUtil.inflate(inflater, R.layout.fragment_my_matches, container, false);

        final RecyclerView list = binding.list;
        list.setLayoutManager(new LinearLayoutManager(getContext()));

        Post a1 = new Post();
        a1.setContent("当我抓住鼠标的时候，你们就输了 1");

        Post a2 = new Post();
        a2.setContent("当我抓住鼠标的时候，你们就输了 2 这是一条非常非常长的内容，在一行之内肯定显示不下来，起码要用三行来显示，所以旁边的评论和赞的按钮位置就很重要了");

        Post a3 = new Post();
        a3.setContent("当我抓住鼠标的时候，你们就输了 3");

        List<Post> data = new ArrayList<>();
        data.add(a1);
        data.add(a2);
        data.add(a3);

        list.setAdapter(new MatchResultsAdapter(data));

        return binding.getRoot();
    }
}

