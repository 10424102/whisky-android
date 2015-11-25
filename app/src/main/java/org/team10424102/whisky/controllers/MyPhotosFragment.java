package org.team10424102.whisky.controllers;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.team10424102.whisky.App;
import org.team10424102.whisky.R;
import org.team10424102.whisky.databinding.FragmentMyPhotosBinding;
import org.team10424102.whisky.models.LazyImage;
import org.team10424102.whisky.models.User;
import org.team10424102.whisky.ui.MarginDecoration;

import java.util.ArrayList;
import java.util.List;

public class MyPhotosFragment extends Fragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final FragmentMyPhotosBinding binding =
                DataBindingUtil.inflate(inflater, R.layout.fragment_my_photos, container, false);

        final RecyclerView list = binding.grid;
        list.addItemDecoration(new MarginDecoration(getContext()));
        list.setLayoutManager(new GridLayoutManager(getContext(), 3));

        User u = new User();
        u.setNickname(App.getProfile().getUsername());

        List<LazyImage> data = new ArrayList<>();
        data.add(new LazyImage());
        data.add(new LazyImage());
        data.add(new LazyImage());
        data.add(new LazyImage());
        data.add(new LazyImage());
        data.add(new LazyImage());
        data.add(new LazyImage());
        data.add(new LazyImage());

        list.setAdapter(new PhotosAdapter(data));

        return binding.getRoot();
    }
}

